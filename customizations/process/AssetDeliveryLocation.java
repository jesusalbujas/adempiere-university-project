// groovy:AssetLocationUpdateProcessWithDates
import org.compiere.asset.model.MAsset
import java.sql.Timestamp

// Obtener el ID del activo
def getAssetId() {
    String assetIdString = A_ProcessInfo.getParameterAsString("A_Asset_ID")
    if (assetIdString == null || assetIdString.trim().isEmpty()) {
        return null
    }
    return assetIdString.toInteger()
}

// Obtener el ID de la nueva ubicación
def getLocatorId() {
    String locatorIdString = A_ProcessInfo.getParameterAsString("M_Locator_ID")
    if (locatorIdString == null || locatorIdString.trim().isEmpty()) {
        return null
    }
    return locatorIdString.toInteger()
}

// Obtener la fecha de movimiento como parámetro
def getMovementDate() {
    Timestamp movementDate = A_ProcessInfo.getParameterAsTimestamp("MovementDate")
    if (movementDate == null) {
        return new Timestamp(System.currentTimeMillis())
    }
    return movementDate
}

// Obtener parámetros necesarios
def ctx = A_Ctx
def trxName = A_TrxName
def defaultLocatorId = 50006  // ID de ubicación por defecto

// Obtener IDs
def assetId = getAssetId()
def locatorId = getLocatorId()

// Validar que ambos parámetros estén presentes
if (assetId == null || locatorId == null) {
    return "@Error@ @No se proporcionaron los parámetros requeridos (A_Asset_ID o M_Locator_ID).@"
}

try {
    // Cargar el activo
    MAsset asset = new MAsset(ctx, assetId, trxName)
    
    // Verificar si el activo existe
    if (asset == null || asset.getA_Asset_ID() == 0) {
        return "@Error@ @Activo no encontrado.@"
    }

    // Obtener la fecha de movimiento
    def movementDate = getMovementDate()

    // Obtener la ubicación actual del activo
    def currentLocatorId = asset.get_ValueAsInt("M_Locator_ID")

    // Determinar si es una entrega o una devolución
    if (currentLocatorId == defaultLocatorId && locatorId != defaultLocatorId) {
        // Es una entrega (movimiento desde la ubicación por defecto a otra)
        asset.set_ValueOfColumn("datedelivered", movementDate)
        println("Fecha de entrega asignada: datedelivered=${movementDate}")
    } else if (currentLocatorId != defaultLocatorId && locatorId == defaultLocatorId) {
        // Es una devolución (movimiento de otra ubicación a la ubicación por defecto)
        asset.set_ValueOfColumn("datereturned", movementDate)
        println("Fecha de retorno asignada: datereturned=${movementDate}")
    }

    // Actualizar la ubicación y la fecha de movimiento
    asset.set_ValueOfColumn("M_Locator_ID", locatorId)
    asset.set_ValueOfColumn("MovementDate", movementDate)
    asset.saveEx()

    // Confirmación de que las fechas se han guardado correctamente
    def dateDeliveredDB = asset.get_Value("datedelivered")
    def dateReturnedDB = asset.get_Value("datereturned")
    def movementDateDB = asset.get_Value("MovementDate")
    println("Confirmación post-guardado: datedelivered=${dateDeliveredDB}, datereturned=${dateReturnedDB}, MovementDate=${movementDateDB}")

    println("Ubicación actualizada para el activo ID ${asset.getA_Asset_ID()}: Nueva ubicación M_Locator_ID=${locatorId}")
    
    return "@Proceso completado@: Ubicación y fechas actualizadas para el activo ID ${asset.getA_Asset_ID()}."
} catch (Exception e) {
    println("Error al actualizar la ubicación para el activo ID: ${assetId}, Mensaje: ${e.getMessage()}")
    return "@Error@ Error al actualizar la ubicación: ${e.getMessage()}"
}
