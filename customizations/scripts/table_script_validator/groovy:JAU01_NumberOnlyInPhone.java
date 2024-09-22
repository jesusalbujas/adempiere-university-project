import org.compiere.model.PO;
PO entity = A_PO;

try {
    // Obtiene el valor actual del campo "isvendor"
    boolean isVendor = entity.get_ValueAsBoolean("IsVendor");

    // Obtiene el valor actual del campo "isemployee"
    boolean isEmployee = entity.get_ValueAsBoolean("IsEmployee");

    // Solo realiza la validación si "IsVendor" es verdadero y "IsEmployee" es falso
    if (isVendor && !isEmployee) {
        // Obtiene el valor actual del campo "Phone"
        String currentPhone = entity.get_ValueAsString("Phone");

        // Verifica si el valor no es null
        if (currentPhone != null) {
            // Verifica si el valor contiene letras
            if (!currentPhone.matches('^[0-9]+$')) {
                return "@Error@ Solo se permiten números en el campo teléfono.";
            }
        }
    }

    // Si todo está bien o las condiciones no se cumplend devolvera error
    result = "";

} catch (Exception e) {
    return "@Error@ " + e.getLocalizedMessage();
}
