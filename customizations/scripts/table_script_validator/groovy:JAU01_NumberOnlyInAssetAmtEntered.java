import org.compiere.model.PO;
PO entity = A_PO;

try {
    // Validar el campo "AssetAmtEntered" para asegurarse de que solo contenga números
    String assetAmtEntered = entity.get_ValueAsString("AssetAmtEntered");

    // Verifica si el valor de "AssetAmtEntered" no es null
    if (assetAmtEntered != null) {
        // Verifica si contiene solo números (sin caracteres especiales)
        if (!assetAmtEntered.matches("^\\d+\$")) {
            return "@Error@ El valor no puede ser cero, ni permite caracteres especiales, verifique e intente nuevamente.";
        }

        // Verifica que el valor no sea cero
        if (Integer.parseInt(assetAmtEntered) == 0) {
            return "@Error@ El valor no puede ser cero, verifique los datos introducidos e intente nuevamente.";
        }
    }

    // Si todo está bien, no se devuelve ningún error
    result = "";

} catch (Exception e) {
    return "@Error@ " + e.getLocalizedMessage();
}
