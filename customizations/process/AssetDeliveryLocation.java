// groovy:AssetLocationUpdateProcessWithDates
import org.compiere.asset.model.MAsset
import java.sql.Timestamp
import java.time.ZonedDateTime
import java.time.ZoneId
import org.compiere.util.DB
import org.compiere.util.Env
import java.util.UUID

// Obtener el ID del activo
def getAssetId() {
    String assetIdString = A_ProcessInfo.getParameterAsString("A_Asset_ID")
    if (assetIdString == null || assetIdString.trim().isEmpty()) {
        return null
    }
    return assetIdString.toInteger()
}

// Obtener ID del parámetro JAU01_Location_Assigned
def getNewLocatorId() {
    String newLocatorIdString = A_ProcessInfo.getParameterAsString("JAU01_Location_Assigned")
    if (newLocatorIdString == null || newLocatorIdString.trim().isEmpty()) {
        return null
    }
    return newLocatorIdString.toInteger()
}

// Obtener fecha y hora de movimiento
def getMovementDate() {
    def movementDate = A_ProcessInfo.getParameterAsTimestamp("MovementDate")
    if (movementDate == null) {
        return ZonedDateTime.now(ZoneId.of("UTC"))
    }
    return movementDate.toInstant().atZone(ZoneId.systemDefault())
}

// Parámetros necesarios
def ctx = A_Ctx
def trxName = A_TrxName
def defaultLocatorId = 50006

// Obtener IDs
def assetId = getAssetId()
def newLocatorId = getNewLocatorId()

if (assetId == null || newLocatorId == null) {
    return "@Error@ @Faltan los parámetros requeridos (Activo Fijo o Ubicación a Asignar).@"
}

try {
    MAsset asset = new MAsset(ctx, assetId, trxName)
    if (asset == null || asset.getA_Asset_ID() == 0) {
        return "@Error@ @Activo no encontrado.@"
    }

    def movementDate = getMovementDate()
    def currentLocatorId = asset.get_ValueAsInt("M_Locator_ID")

    asset.set_ValueOfColumn("JAU01_Old_Location_Assigned", currentLocatorId)

    if (currentLocatorId == defaultLocatorId && newLocatorId != defaultLocatorId) {
        asset.set_ValueOfColumn("DateDelivered", movementDate)
    } else if (currentLocatorId != defaultLocatorId && newLocatorId == defaultLocatorId) {
        asset.set_ValueOfColumn("DateReturned", movementDate)
    } else if (currentLocatorId != defaultLocatorId && newLocatorId != defaultLocatorId) {
        if (asset.get_Value("DateDelivered") == null) {
            asset.set_ValueOfColumn("DateDelivered", movementDate)
        }
        asset.set_ValueOfColumn("DateReturned", null)
    }

    asset.set_ValueOfColumn("M_Locator_ID", newLocatorId)

    Timestamp movementTimestamp = Timestamp.from(movementDate.toInstant())
    asset.set_ValueOfColumn("MovementDate", movementTimestamp)
    asset.saveEx()

    // Insertar registro en A_Asset_Delivery
    def uuid = UUID.randomUUID().toString()
    def adClientId = asset.getAD_Client_ID()
    def adOrgId = asset.getAD_Org_ID()
    def createdDate = new Timestamp(System.currentTimeMillis())
    def adUserId = Env.getAD_User_ID(ctx)
    def isActive = 'Y'
    def assetSerialNumber = asset.getSerNo() ?: ''
    def nextSeqNo = DB.getSQLValue(trxName, "SELECT COALESCE(MAX(CAST(A_Asset_Delivery_ID AS INTEGER)), 999) + 1 FROM A_Asset_Delivery")

    // Intercambiar valores de M_Locator_ID y JAU01_Location_Assigned para el insert
    String insertSql = """
        INSERT INTO A_Asset_Delivery (
            AD_Client_ID, AD_Org_ID, Created, CreatedBy, IsActive, A_Asset_Delivery_ID,
            Updated, UpdatedBy, UUID, A_Asset_ID, SerNo, JAU01_Location_Assigned,
            MovementDate, IsAssigned, IsMobiliary, IsReubicate, M_Locator_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """
    DB.executeUpdateEx(insertSql, [
        adClientId,
        adOrgId,
        createdDate,
        adUserId,
        isActive,
        nextSeqNo,
        createdDate,
        adUserId,
        uuid,
        assetId,
        assetSerialNumber,
        newLocatorId,           // JAU01_Location_Assigned será el valor del parámetro M_Locator_ID
        movementTimestamp,
        'Y',  // IsAssigned
        'Y',  // IsMobiliary
        'Y',  // IsReubicate
        currentLocatorId        // M_Locator_ID será el valor de JAU01_Location_Assigned
    ] as Object[], trxName)

    return "@Proceso completado@: Reubicación de activo completado con éxito."
} catch (Exception e) {
    println("Error al actualizar ubicación del activo ID: ${assetId}, Mensaje: ${e.getMessage()}")
    return "@Error@ Error al actualizar la ubicación: ${e.getMessage()}"
}
