// AssetDeliveryProcess

import org.compiere.asset.model.MAsset
import org.compiere.asset.model.MAssetDelivery
import org.compiere.model.MBPartner
import org.compiere.util.DB
import java.sql.Timestamp

// Obtener el ID del activo
def getAssetId() {
    String assetIdString = A_ProcessInfo.getParameterAsString("A_Asset_ID")
    if (assetIdString == null || assetIdString.trim().isEmpty()) {
        return null
    }
    return assetIdString.toInteger()
}

// Obtener el ID del socio de negocios (C_BPartner_ID) desde el contexto
def getBPartnerId() {
    return A_ProcessInfo.getParameterAsInt("C_BPartner_ID")
}

// Obtener el ID de usuario (AD_User_ID) desde el contexto
def getUserId() {
    return A_ProcessInfo.getParameterAsInt("AD_User_ID")
}

// Obtener parámetros
def isReturned = A_ProcessInfo.getParameterAsBoolean("IsReturnedToOrganization")
def movementDateAssetString = A_ProcessInfo.getParameterAsString("MovementDateAsset")
def description = A_ProcessInfo.getParameterAsString("Description")
def ctx = A_Ctx
def trxName = A_TrxName

// Obtener el ID del activo
def assetId = getAssetId()
if (assetId == null) {
    return "@Error@ @No se seleccionó ningún activo fijo.@"
}

// Generar el próximo valor para A_Asset_Delivery_ID
def nextSeqNo = DB.getSQLValue(trxName, "SELECT COALESCE(MAX(CAST(A_Asset_Delivery_ID AS INTEGER)), 99999) + 1 FROM A_Asset_Delivery")

// Procesar el activo
int delivered = 0
int errors = 0
try {
    // Obtener el activo
    MAsset asset = new MAsset(ctx, assetId, trxName)

    // Actualizar estado del activo
    asset.setIsInPosession(isReturned)
    asset.set_ValueOfColumn("JAU01_M_Locator", null)
    asset.set_ValueOfColumn("M_Locator_ID", null)
    asset.saveEx()

    // Convertir la cadena 'movementDateAssetString' a tipo Timestamp
    Timestamp movementDate = movementDateAssetString ? Timestamp.valueOf(movementDateAssetString) : null

    // Crear entrega de activo
    int bPartnerId = getBPartnerId()
    MBPartner bPartner = new MBPartner(ctx, bPartnerId, trxName)

    // Validar el límite de activos permitidos en el cargo del socio de negocios
    def hrDepartmentId = bPartner.get_ValueAsInt("HR_Department_ID")
    def limitAssetsAllowed = DB.getSQLValue(trxName, "SELECT LimitAssetsAllowed FROM HR_Department WHERE HR_Department_ID=?", hrDepartmentId)
    def assetsAssignedCount = bPartner.get_ValueAsInt("AssetsAssignedCount")

    // Si el límite de activos es nulo o 0, asignar sin restricciones
    if (limitAssetsAllowed == null || limitAssetsAllowed == 0) {
        // No bloqueamos la asignación, simplemente asignamos el activo
    } else if (assetsAssignedCount >= limitAssetsAllowed) {
        return "@Error@ El empleado ya alcanzó el límite de activos permitidos."
    }

    // Crear el registro de entrega del activo
    MAssetDelivery assetDelivery = new MAssetDelivery(asset, bPartnerId, getUserId(), movementDate)
    assetDelivery.setDescription(description)
    assetDelivery.set_ValueOfColumn("A_Asset_Status", "EU")
    assetDelivery.set_ValueOfColumn("IsTI", true)
    assetDelivery.set_ValueOfColumn("IsAssignedTI", true)
    assetDelivery.setA_Asset_Delivery_ID(nextSeqNo)
    assetDelivery.set_ValueOfColumn("MovementDateAsset", movementDateAssetString)
    assetDelivery.saveEx()

    // Incrementar el contador de activos asignados al socio de negocios
    bPartner.set_ValueOfColumn("AssetsAssignedCount", assetsAssignedCount + 1)
    bPartner.saveEx()

    delivered++
    println("Activo procesado: ${asset.getA_Asset_ID()}")
} catch (Exception e) {
    errors++
    println("Error procesando activo ID: ${assetId}, Mensaje: ${e.getMessage()}")
}

// Resultados del proceso
println("Proceso finalizado: Entregados=${delivered}, Errores=${errors}")
return "@Entregados@=${delivered} - @Errores@=${errors}"
