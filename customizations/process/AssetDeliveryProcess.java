// groovy:AssetDeliveryProcess
import org.compiere.asset.model.MAsset
import org.compiere.asset.model.MAssetDelivery
import org.compiere.model.Query
import org.compiere.util.DB

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

// Obtener parámetros
def isReturned = A_ProcessInfo.getParameterAsBoolean("IsReturnedToOrganization")
def movementDate = A_ProcessInfo.getParameterAsTimestamp("MovementDate")
def description = A_ProcessInfo.getParameterAsString("Description")
def ctx = A_Ctx
def trxName = A_TrxName

// Obtener el ID del activo
def assetId = getAssetId()
if (assetId == null) {
    return "@Error@ @No se seleccionó ningún activo fijo.@"
}

// Generar el próximo valor para A_Asset_Delivery_ID, comenzando desde 100000
def nextSeqNo = DB.getSQLValue(trxName, "SELECT COALESCE(MAX(CAST(A_Asset_Delivery_ID AS INTEGER)), 99999) + 1 FROM A_Asset_Delivery")

// Procesar el activo
int delivered = 0
int errors = 0
try {
    // Obtener el activo
    MAsset asset = new MAsset(ctx, assetId, trxName)

    // Actualizar estado del activo
    asset.setIsInPosession(isReturned)

    // Vaciar los campos JAU01_M_Locator y M_Locator_ID
    asset.set_ValueOfColumn("JAU01_M_Locator", null)
    asset.set_ValueOfColumn("M_Locator_ID", null)
    println("Campos JAU01_M_Locator y M_Locator_ID vaciados") // Log para confirmar

    // Guardar cambios en el activo
    asset.saveEx()

    // Crear entrega de activo
    int bPartnerId = getBPartnerId()
    MAssetDelivery assetDelivery = new MAssetDelivery(asset, bPartnerId, 0, movementDate)
    assetDelivery.setDescription(description)

    // Asignar el estado del activo como "EU"
    assetDelivery.set_ValueOfColumn("A_Asset_Status", "EU")
    println("Estatus asignado a A_Asset_Status: EU") // Log para confirmar el estatus

    // Establecer IsTI en true
    assetDelivery.set_ValueOfColumn("IsTI", true)
    println("IsTI asignado a true") // Log para confirmar el valor de IsTI

    // Asignar el ID de entrega de activo utilizando el próximo valor generado
    assetDelivery.setA_Asset_Delivery_ID(nextSeqNo)
    println("Nuevo ID asignado a A_Asset_Delivery_ID: ${nextSeqNo}") // Log para confirmar el ID asignado

    // Guardar la entrega de activo
    assetDelivery.saveEx()

    delivered++
    println("Activo procesado: ${asset.getA_Asset_ID()}")
} catch (Exception e) {
    errors++
    println("Error procesando activo ID: ${assetId}, Mensaje: ${e.getMessage()}")
}

// Resultados del proceso
println("Proceso finalizado: Entregados=${delivered}, Errores=${errors}")
return "@Entregados@=${delivered} - @Errores@=${errors}"
