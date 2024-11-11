// groovy:AssetLocation
import org.compiere.asset.model.MAsset
import java.sql.Timestamp
import org.compiere.util.DB
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

// Obtener el ID del usuario desde el parámetro AD_User_ID
def getUserId() {
    try {
        String userIdString = A_ProcessInfo.getParameterAsString("AD_User_ID")
        if (userIdString == null || userIdString.trim().isEmpty()) {
            println("AD_User_ID no fue proporcionado o está vacío.")
            return null
        }
        int userId = userIdString.toInteger()
        println("AD_User_ID obtenido: ${userId}")
        return userId
    } catch (Exception e) {
        println("Error al convertir AD_User_ID: ${e.getMessage()}")
        return null
    }
}

// Parámetros necesarios
def ctx = A_Ctx
def trxName = A_TrxName
def defaultLocatorId = 50006
def movementDateAssetString = A_ProcessInfo.getParameterAsString("MovementDateAsset")

// Obtener IDs
def assetId = getAssetId()
def newLocatorId = getNewLocatorId()
def adUserId = getUserId()

// Validar que todos los parámetros requeridos estén presentes
if (assetId == null || newLocatorId == null || adUserId == null) {
    return "@Error@ @Faltan los parámetros requeridos (Activo Fijo, Ubicación a Asignar o Usuario).@"
}

try {
    MAsset asset = new MAsset(ctx, assetId, trxName)
    if (asset == null || asset.getA_Asset_ID() == 0) {
        return "@Error@ @Activo no encontrado.@"
    }

    def currentLocatorId = asset.get_ValueAsInt("M_Locator_ID")

    // Convertir la cadena 'movementDateAssetString' a tipo Timestamp
    Timestamp movementDate = null
    if (movementDateAssetString != null && !movementDateAssetString.trim().isEmpty()) {
        try {
            movementDate = Timestamp.valueOf(movementDateAssetString)
        } catch (Exception e) {
            println("Error al convertir MovementDateAsset: ${e.getMessage()}")
        }
    }

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
    asset.set_ValueOfColumn("MovementDateAsset", movementDateAssetString)
    asset.saveEx()

    // Insertar registro en A_Asset_Delivery
    def uuid = UUID.randomUUID().toString()
    def adClientId = asset.getAD_Client_ID()
    def adOrgId = asset.getAD_Org_ID()
    def createdDate = new Timestamp(System.currentTimeMillis())
    def isActive = 'Y'
    def assetSerialNumber = asset.getSerNo() ?: ''
    def nextSeqNo = DB.getSQLValue(trxName, "SELECT COALESCE(MAX(CAST(A_Asset_Delivery_ID AS INTEGER)), 999) + 1 FROM A_Asset_Delivery")

    String insertSql = """
        INSERT INTO A_Asset_Delivery (
            AD_Client_ID, AD_Org_ID, Created, CreatedBy, IsActive, A_Asset_Delivery_ID,
            Updated, UpdatedBy, UUID, A_Asset_ID, SerNo, JAU01_Location_Assigned,
            MovementDate, MovementDateAsset, IsAssigned, IsMobiliary, IsReubicate, M_Locator_ID, AD_User_ID) 
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
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
        currentLocatorId,  // Aquí se inserta M_Locator_ID en JAU01_Location_Assigned
        movementDate,
        movementDateAssetString,
        'Y',  // IsAssigned
        'Y',  // IsMobiliary
        'Y',  // IsReubicate (nombre correcto de la columna)
        newLocatorId,        // Aquí se inserta JAU01_Location_Assigned en M_Locator_ID
        adUserId
    ] as Object[], trxName)

    return "@Proceso completado@: Reubicación de activo completado con éxito."
} catch (Exception e) {
    println("Error al actualizar ubicación del activo ID: ${assetId}, Mensaje: ${e.getMessage()}")
  
}