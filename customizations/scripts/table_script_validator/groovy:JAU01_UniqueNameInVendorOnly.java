// groovy:JAU01_UniqueNameInVendorOnly
import org.compiere.model.Query;
import org.compiere.model.PO;
PO entity = A_PO;

try {
    String currentName = entity.get_ValueAsString("Name");
    String tableName = entity.get_TableName();
    
    // Obtiene el valor actual del campo "isvendor"
    boolean isVendor = entity.get_ValueAsBoolean("IsVendor");

    // Obtiene el valor actual del campo "isemployee"
    boolean isEmployee = entity.get_ValueAsBoolean("IsEmployee");

    // Solo realiza la validación si "IsVendor" es verdadero y "IsEmployee" es falso
    if (isVendor && !isEmployee) {
        // Obtiene el valor actual del campo
        // Verifica si el valor no es null
        if (currentName != null) {
            // Verifica si el valor ya existe.
            int referenceId = new Query(A_Ctx, tableName, "UPPER(TRIM(Name)) = UPPER(TRIM(?)) AND " + tableName + "_ID <> ?", entity.get_TrxName()).setParameters(currentName, entity.get_ID()).setClient_ID().firstId();
            if (referenceId > 0) {
                return "@Error@ Nombre ya existe";
            }
        }
    }

    // Si todo está bien o las condiciones no se cumplen devolvera error
    result = "";

} catch (Exception e) {
    return "@Error@ " + e.getLocalizedMessage();
}
