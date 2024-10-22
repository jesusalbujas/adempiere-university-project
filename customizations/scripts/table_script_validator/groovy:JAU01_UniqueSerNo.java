// groovy:JAU01_UniqueTaxID
import org.compiere.model.PO;
import org.compiere.model.Query;
PO entity = A_PO;
try {
	String currentName = entity.get_ValueAsString("SerNo");
    String tableName = entity.get_TableName();
  if(currentName != null) {
    int referenceId = new Query(A_Ctx, tableName, "UPPER(TRIM(SerNo)) = UPPER(TRIM(?)) AND " + tableName + "_ID <> ?", entity.get_TrxName()).setParameters(currentName, entity.get_ID()).setClient_ID().firstId();
    if(referenceId > 0) {
        return "@Error@ Serial ya existe";
    }
  }
	result = "";
} catch(Exception e) {
	return "@Error@ " + e.getLocalizedMessage();
}