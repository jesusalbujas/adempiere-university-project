import org.compiere.model.Query;
import org.compiere.model.PO;
PO entity = A_PO;

try {
    String currentName = entity.get_ValueAsString("Name"); // Nombre del proveedor
    String currentCompanyName = entity.get_ValueAsString("Name2"); // Nombre de la empresa
    String tableName = entity.get_TableName();
    
    // Obtiene el valor actual del campo "IsVendor"
    boolean isVendor = entity.get_ValueAsBoolean("IsVendor");

    // Obtiene el valor actual del campo "IsEmployee"
    boolean isEmployee = entity.get_ValueAsBoolean("IsEmployee");

    // Solo realiza la validación si "IsVendor" es verdadero y "IsEmployee" es falso
    if (isVendor && !isEmployee) {
        // Verifica si el nombre del proveedor y el nombre de la empresa no son null
        if (currentName != null && currentCompanyName != null) {
            // Verifica si el nombre del proveedor ya existe en la misma empresa
            int referenceId = new Query(A_Ctx, tableName, 
                "UPPER(TRIM(Name)) = UPPER(TRIM(?)) AND UPPER(TRIM(Name2)) = UPPER(TRIM(?)) AND " + tableName + "_ID <> ?", 
                entity.get_TrxName())
                .setParameters(currentName, currentCompanyName, entity.get_ID())
                .setClient_ID()
                .firstId();
                
            if (referenceId > 0) {
                return "@Error@ Proveedor con este nombre ya existe en esta empresa";
            }
        }
    }

    // Si todo está bien, no devuelve error
    result = "";

} catch (Exception e) {
    return "@Error@ " + e.getLocalizedMessage();
}
