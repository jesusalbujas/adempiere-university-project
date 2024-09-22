import org.compiere.model.PO;
PO entity = A_PO;

try {
    // Obtiene el valor actual del campo "isvendor"
    boolean isVendor = entity.get_ValueAsBoolean("IsVendor");

    // Obtiene el valor actual del campo "isemployee"
    boolean isEmployee = entity.get_ValueAsBoolean("IsEmployee");

    // Solo realiza la validación si "IsEmployee" es verdadero y "IsVendor" es falso
    if (isEmployee && !isVendor) {
        // Obtiene el valor actual del campo "TaxID"
        String currentTaxID = entity.get_ValueAsString("TaxID");

        // Verifica si el valor no es null
        if (currentTaxID != null) {
            // Verifica si el valor contiene letras
            if (!currentTaxID.matches('^[0-9]+$')) {
                return "@Error@ Solo se permiten números, verifique los datos introducidos e intente nuevamente.";
            }
        }
    }

    // Si todo está bien o las condiciones no se cumplend devolvera error
    result = "";

} catch (Exception e) {
    return "@Error@ " + e.getLocalizedMessage();
}
