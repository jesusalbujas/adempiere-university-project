// groovy:AssetDeliveryDevolution
import org.compiere.asset.model.MAsset
import org.compiere.asset.model.MAssetDelivery
import org.compiere.model.Query

// Obtener el ID del activo
def getAssetId() {
    // Obtener el parámetro como cadena
    String assetIdString = A_ProcessInfo.getParameterAsString("A_Asset_ID")

    // Verificar si la cadena es nula o vacía
    if (assetIdString == null || assetIdString.trim().isEmpty()) {
        return null
    }

    // Convertir a Integer
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

// Procesar el activo
int delivered = 0
int errors = 0
try {
    // Obtener el activo
    MAsset asset = new MAsset(ctx, assetId, trxName)

    // Actualizar estado del activo
    asset.setIsInPosession(isReturned)
    asset.saveEx()

    // Crear entrega de activo
    int bPartnerId = getBPartnerId()
    MAssetDelivery assetDelivery = new MAssetDelivery(asset, bPartnerId, 0, movementDate)
    assetDelivery.setDescription(description)
    assetDelivery.saveEx()

    delivered++
    println("Activo procesado: ${asset.getA_Asset_ID()}")
} catch (Exception e) {
    errors++
    println("Error procesando activo ID: ${assetId}, Mensaje: ${e.getMessage()}")
}

// Resultados del proceso
println("Proceso finalizado: Entregados=${delivered}, Errores=${errors}")
return "@Devueltos@=${delivered} - @Errores@=${errors}"
