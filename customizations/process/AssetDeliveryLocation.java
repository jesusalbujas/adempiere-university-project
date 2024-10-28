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

// Obtener el ID de la nueva ubicación desde JAU01_Location_Assigned
def getNewLocatorId() {
    String newLocatorIdString = A_ProcessInfo.getParameterAsString("JAU01_Location_Assigned")
    if (newLocatorIdString == null || newLocatorIdString.trim().isEmpty()) {
        return null
    }
    return newLocatorIdString.toInteger()
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
def newLocatorId = getNewLocatorId()

// Validar que ambos parámetros estén presentes
if (assetId == null || newLocatorId == null) {
    return "@Error@ @No se proporcionaron los parámetros requeridos (A_Asset_ID o JAU01_Location_Assigned).@"
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

    // Obtener la ubicación actual del activo (M_Locator_ID)
    def currentLocatorId = asset.get_ValueAsInt("M_Locator_ID")

    // Asignar la ubicación actual a JAU01_Old_Location_Assigned
    asset.set_ValueOfColumn("JAU01_Old_Location_Assigned", currentLocatorId)

    // Determinar si es una entrega o una devolución
    if (currentLocatorId == defaultLocatorId && newLocatorId != defaultLocatorId) {
        // Es una entrega (movimiento desde la ubicación por defecto a otra)
        asset.set_ValueOfColumn("DateDelivered", movementDate) // Usar Timestamp
        println("Fecha de entrega asignada: DateDelivered=${movementDate}")
    } else if (currentLocatorId != defaultLocatorId && newLocatorId == defaultLocatorId) {
        // Es una devolución (movimiento de otra ubicación a la ubicación por defecto)
        asset.set_ValueOfColumn("DateReturned", movementDate) // Usar Timestamp
        println("Fecha de retorno asignada: DateReturned=${movementDate}")
    } else if (currentLocatorId != defaultLocatorId && newLocatorId != defaultLocatorId) {
        // Movimiento de una ubicación distinta a otra que no es la por defecto
        def dateDelivered = asset.get_Value("DateDelivered")
        if (dateDelivered == null) {
            // Si nunca se ha entregado antes, registrar la fecha de entrega
            asset.set_ValueOfColumn("DateDelivered", movementDate) // Usar Timestamp
            println("Fecha de entrega asignada por primera vez: DateDelivered=${movementDate}")
        }
        asset.set_ValueOfColumn("DateReturned", null) // Reiniciar la fecha de retorno al mover a otra ubicación
    }

    // Actualizar la ubicación a la nueva (JAU01_Location_Assigned)
    asset.set_ValueOfColumn("M_Locator_ID", newLocatorId)
    asset.set_ValueOfColumn("MovementDate", movementDate) // Mantener como Timestamp
    asset.saveEx()

    // Confirmación de que las fechas se han guardado correctamente
    def dateDeliveredDB = asset.get_Value("DateDelivered")
    def dateReturnedDB = asset.get_Value("DateReturned")
    def movementDateDB = asset.get_Value("MovementDate")
    println("Confirmación post-guardado: DateDelivered=${dateDeliveredDB}, DateReturned=${dateReturnedDB}, MovementDate=${movementDateDB}")

    println("Ubicación actualizada para el activo ID ${asset.getA_Asset_ID()}: Nueva ubicación M_Locator_ID=${newLocatorId}")
    
    return "@Proceso completado@: Ubicación y fechas actualizadas para el activo ID ${asset.getA_Asset_ID()}."
} catch (Exception e) {
    println("Error al actualizar la ubicación para el activo ID: ${assetId}, Mensaje: ${e.getMessage()}")
    return "@Error@ Error al actualizar la ubicación: ${e.getMessage()}"
}
