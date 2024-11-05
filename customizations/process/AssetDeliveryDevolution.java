// groovy:AssetDeliveryDevolution
import org.compiere.asset.model.MAsset
import org.compiere.asset.model.MAssetDelivery
import org.compiere.model.Query

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

// Obtener el estatus de devolución enviado desde A_Asset_Status_Devolution_Proc
def getAssetStatus() {
    String status = A_ProcessInfo.getParameterAsString("A_Asset_Status_Devolution_Proc")
    if (status == null || status.trim().isEmpty()) {
        // Si no se selecciona ningún estatus, se asigna "DB" por defecto
        status = "DB"
        println("No se seleccionó estatus, se asigna por defecto: " + status)
    } else {
        println("Estatus recibido: " + status)
    }
    return status.trim()
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

    // Actualizar estado del activo (posesión)
    asset.setIsInPosession(isReturned) // Cambia a true o false según se devuelve
    asset.saveEx()

    // Crear entrega de activo
    int bPartnerId = getBPartnerId()
    MAssetDelivery assetDelivery = new MAssetDelivery(asset, bPartnerId, 0, movementDate)
    assetDelivery.setDescription(description)

    // Obtener y asignar el estatus de devolución
    String assetStatus = getAssetStatus()
    println("AssetStatus recibido o asignado: " + assetStatus)  // Log para ver qué estatus se está enviando

    // Asignar el estatus al campo A_Asset_Status
    assetDelivery.set_ValueOfColumn("A_Asset_Status", assetStatus)
    println("Estatus asignado a A_Asset_Status: " + assetStatus)

    // Establecer IsTI en true
    assetDelivery.set_ValueOfColumn("IsTI", true)
    println("IsTI asignado a true") // Log para confirmar el valor de IsTI

    // Guardar la entrega de activo
    assetDelivery.saveEx()
    println("Registro guardado en A_Asset_Delivery con ID: ${assetDelivery.getA_Asset_Delivery_ID()}")

    delivered++
    println("Activo procesado: ${asset.getA_Asset_ID()}")
} catch (Exception e) {
    errors++
    println("Error procesando activo ID: ${assetId}, Mensaje: ${e.getMessage()}")
}

// Resultados del proceso
println("Proceso finalizado: Entregados=${delivered}, Errores=${errors}")
return "@Devueltos@=${delivered} - @Errores@=${errors}"
