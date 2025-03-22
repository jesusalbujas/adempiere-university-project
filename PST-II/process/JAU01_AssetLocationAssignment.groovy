// groovy:AssetAssignmentProcess
import org.compiere.asset.model.MAsset
import java.sql.Timestamp
import org.compiere.model.Query
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

// Obtener el ID de la ubicación nueva desde el parámetro M_Locator_ID
def getNewLocatorId() {
    String newLocatorIdString = A_ProcessInfo.getParameterAsString("M_Locator_ID")
    if (newLocatorIdString == null || newLocatorIdString.trim().isEmpty()) {
        return null
    }
    return newLocatorIdString.toInteger()
}

// Obtener el ID del usuario desde los parámetros como entero
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

// Obtener parámetros necesarios
def ctx = A_Ctx
def trxName = A_TrxName

// Obtener IDs
def assetId = getAssetId()
def newLocatorId = getNewLocatorId()
def adUserId = getUserId()
def movementDateAssetString = A_ProcessInfo.getParameterAsString("MovementDateAsset")

// Validar que todos los parámetros requeridos estén presentes
if (assetId == null || newLocatorId == null || adUserId == null) {
    println("Parámetros proporcionados - A_Asset_ID: ${assetId}, M_Locator_ID: ${newLocatorId}, AD_User_ID: ${adUserId}")
    return "@Error@ No se proporcionaron los parámetros requeridos: A_Asset_ID, M_Locator_ID o AD_User_ID."
}

try {
    // Cargar el activo
    MAsset asset = new MAsset(ctx, assetId, trxName)
    
    // Verificar si el activo existe
    if (asset == null || asset.getA_Asset_ID() == 0) {
        return "@Error@ @Activo no encontrado.@"
    }

    // Convertir la cadena 'movementDateAssetString' a tipo Timestamp
    Timestamp movementDate = null
    if (movementDateAssetString != null && !movementDateAssetString.trim().isEmpty()) {
        try {
            // Convertir la cadena a Timestamp, asumiendo el formato 'YYYY-MM-DD HH:MI:SS'
            movementDate = Timestamp.valueOf(movementDateAssetString)
        } catch (Exception e) {
            println("Error al convertir MovementDateAsset: ${e.getMessage()}")
        }
    }

    // Asignar la ubicación nueva al activo
    asset.set_ValueOfColumn("M_Locator_ID", newLocatorId)
    
    // Marcar el campo IsAssignedUbication como true
    asset.set_ValueOfColumn("IsUbicationAssigned", true)

    // Asignar el valor original de MovementDateAsset como String
    asset.set_ValueOfColumn("MovementDateAsset", movementDateAssetString)

    // Guardar el activo
    asset.saveEx()

    // Generar un nuevo UUID
    def uuid = UUID.randomUUID().toString()

    // Obtener los valores requeridos para el INSERT
    def adClientId = 11
    def adOrgId = 50006
    def createdDate = new Timestamp(System.currentTimeMillis())
    def updatedDate = createdDate
    def isActive = 'Y'
    def assetSerialNumber = asset.getSerNo() ?: ''
        
    // Validar que el usuario existe en AD_User antes de continuar
    int userExists = DB.getSQLValue(trxName, "SELECT COUNT(*) FROM ad_user WHERE ad_user_id = ?", adUserId)
    if (userExists == 0) {
        return "@Error@ Usuario con ID ${adUserId} no existe en la base de datos."
    }

    // Generar el próximo valor para A_Asset_Delivery_ID, comenzando desde 1000
    def nextSeqNo = DB.getSQLValue(trxName, "SELECT COALESCE(MAX(CAST(A_Asset_Delivery_ID AS INTEGER)), 1999) + 1 FROM A_Asset_Delivery")

    // Insertar un nuevo registro en la tabla A_Asset_Delivery, incluyendo MovementDate y MovementDateAsset
    String insertSql = """
        INSERT INTO A_Asset_Delivery (
            AD_Client_ID, AD_Org_ID, Created, CreatedBy, IsActive, A_Asset_Delivery_ID,
            Updated, UpdatedBy, UUID, A_Asset_ID, SerNo, M_Locator_ID, MovementDate, MovementDateAsset, IsAssigned, IsMobiliary, IsMobiliaryAssigned, AD_User_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """
    DB.executeUpdateEx(insertSql, [
        adClientId,
        adOrgId,
        createdDate,
        adUserId,                   // CreatedBy
        isActive,
        nextSeqNo,
        updatedDate,
        adUserId,                   // UpdatedBy
        uuid,
        assetId,
        assetSerialNumber,
        newLocatorId,
        movementDate,               // MovementDate como Timestamp
        movementDateAssetString,     // MovementDateAsset como String
        'Y',                         // IsAssigned
        'Y',                         // IsMobiliary
        'Y',                         // IsMobiliaryAssigned
        adUserId
    ] as Object[], trxName)

    println("Ubicación asignada para activo ${asset.getA_Asset_ID()}")

    return "@Proceso completado@: Ubicación asignada correctamente."
} catch (Exception e) {
    println("Error al asignar la ubicación para el activo ID: ${assetId}, Mensaje: ${e.getMessage()}")
    return "@Error@ Error al asignar la ubicación: ${e.getMessage()}"
}
