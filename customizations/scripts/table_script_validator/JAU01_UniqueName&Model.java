// groovy:JAU01_UniqueName&Model
import org.compiere.model.PO;
import org.compiere.model.Query;
PO entity = A_PO;

try {
    String currentName = entity.get_ValueAsString("Name");
    int assetMarkId = entity.get_ValueAsInt("JAU01_AssetMark_ID"); // Campo de la marca
    String tableName = entity.get_TableName();
    
    if (currentName != null) {
        int referenceId = new Query(A_Ctx, tableName, 
            "UPPER(TRIM(Name)) = UPPER(TRIM(?)) AND " + tableName + "_ID <> ? AND JAU01_AssetMark_ID = ?", 
            entity.get_TrxName())
            .setParameters(currentName, entity.get_ID(), assetMarkId)
            .setClient_ID()
            .firstId();
        
        // Si se encuentra un registro con el mismo nombre y la misma marca, retorna un error
        if (referenceId > 0) {
            return "@Error@ El modelo ya existe para la marca seleccionada.";
        }
    }

    result = "";
    
} catch (Exception e) {
    return "@Error@ " + e.getLocalizedMessage();
}
