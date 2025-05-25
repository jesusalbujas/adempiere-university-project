import org.compiere.model.PO;
import org.compiere.model.Query;

PO entity = A_PO;

try {
    // Obtener el valor actual de A_Asset_ID
    Integer assetID = entity.get_ValueAsInt("A_Asset_ID");
    String tableName = entity.get_TableName();  // Obtener el nombre de la tabla dinámicamente
    
    if (assetID != null && assetID > 0) {
        // Validar si ya existe un registro con el mismo A_Asset_ID
        int existingRecordId = new Query(A_Ctx, tableName, "A_Asset_ID = ? AND " + tableName + "_ID <> ?", entity.get_TrxName())
            .setParameters(assetID, entity.get_ID())
            .setClient_ID()
            .firstId();

        // Si se encuentra un registro que cumple con las condiciones, devolver el error
        if (existingRecordId > 0) {
            return "@Error@ El activo ya se encuentra completado.";
        }
    }
    
    // Si no hay duplicados, no devolver ningún error
    result = "";
    
} catch (Exception e) {
    // En caso de error, devolver el mensaje de error
    return "@Error@ " + e.getLocalizedMessage();
}
