// groovy:AssetDeliveryDevolution
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

// Obtener el estatus de devolución enviado desde A_Asset_Status_Devolution_Proc
def getAssetStatus() {
    String status = A_ProcessInfo.getParameterAsString("A_Asset_Status_Devolution_Proc")
    if (status == null || status.trim().isEmpty()) {
        status = "DB" // Si no se selecciona ningún estatus, se asigna "DB" por defecto
    }
    return status.trim()
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
def nextSeqNo = DB.getSQLValue(trxName, 
    """
    SELECT COALESCE(MAX(CAST(A_Asset_Delivery_ID AS INTEGER)), 299999) + 1
    FROM A_Asset_Delivery
    """)
nextSeqNo = Math.max(nextSeqNo, 300001)

int returned = 0
int errors = 0
try {
    MAsset asset = new MAsset(ctx, assetId, trxName)
    asset.setIsInPosession(isReturned)
    asset.saveEx()

    Timestamp movementDate = null
    if (movementDateAssetString != null && !movementDateAssetString.trim().isEmpty()) {
        try {
            movementDate = Timestamp.valueOf(movementDateAssetString)
        } catch (Exception e) {
            errors++
        }
    }

    int bPartnerId = getBPartnerId()
    MAssetDelivery assetDelivery = new MAssetDelivery(asset, bPartnerId, getUserId(), movementDate)
    assetDelivery.setDescription(description)
    String assetStatus = getAssetStatus()
    assetDelivery.set_ValueOfColumn("A_Asset_Status", assetStatus)
    assetDelivery.set_ValueOfColumn("IsTI", true)
    assetDelivery.set_ValueOfColumn("IsDevolutionTI", true)
    assetDelivery.setA_Asset_Delivery_ID(nextSeqNo)
    assetDelivery.set_ValueOfColumn("MovementDateAsset", movementDateAssetString)
    assetDelivery.saveEx()

    // Actualizar `a_asset_status_actual` en todos los activos con el mismo `A_Asset_ID`
    int count = DB.getSQLValue(trxName, 
        "SELECT COUNT(*) FROM A_Asset_Delivery WHERE A_Asset_ID = ?", assetId)
    
    if (count > 1) {
        // Actualizar todos los registros con el mismo `A_Asset_ID`
        DB.executeUpdate("UPDATE A_Asset_Delivery SET a_asset_status_actual = 'AD' WHERE A_Asset_ID = ?", assetId, trxName)
    } else {
        // Actualizar solo el registro actual
        assetDelivery.set_ValueOfColumn("a_asset_status_actual", "AD")
        assetDelivery.saveEx()
    }

    MBPartner bPartner = new MBPartner(ctx, bPartnerId, trxName)
    def assetsAssignedCount = bPartner.get_ValueAsInt("AssetsAssignedCount")
    bPartner.set_ValueOfColumn("AssetsAssignedCount", Math.max(assetsAssignedCount - 1, 0))
    bPartner.saveEx()

    returned++
} catch (Exception e) {
    errors++
}

// Resultados del proceso
return "@Devueltos@=${returned} - @Errores@=${errors}"
