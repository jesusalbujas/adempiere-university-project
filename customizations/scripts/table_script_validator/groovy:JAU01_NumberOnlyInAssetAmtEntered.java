import org.compiere.model.PO;
PO entity = A_PO;

try {
    // Validar el campo "AssetAmtEntered" para asegurarse de que solo contenga números y puntos decimales
    String assetAmtEntered = entity.get_ValueAsString("AssetAmtEntered");
    String aQty = entity.get_ValueAsString("A_QTY_Current")
    // Verifica si el valor de "AssetAmtEntered" no es null
    if (assetAmtEntered != null) {
        // Verifica si contiene solo números con un punto decimal opcional
        if (!assetAmtEntered.matches("^\\d+(\\.\\d+)?\$")) {
            return "@Error@ El Costo del Activo no puede contener caracteres especiales excepto puntos decimales, verifique e intente nuevamente.";
        }

        // Verifica que el valor no sea cero
        if (Double.parseDouble(assetAmtEntered) == 0) {
            return "@Error@ El Costo del Activo no puede ser cero, verifique los datos introducidos e intente nuevamente.";
        }
    }

    // if (aQty != null) {
    //     // Verifica si contiene solo números con un punto decimal opcional
    //     if (!aQty.matches("^\\d+(\\.\\d+)?\$")) {
    //         return "@Error@ La cantidad del activo no puede contener caracteres especiales excepto puntos decimales, verifique e intente nuevamente.";
    //     }

    //     // Verifica que el valor no sea cero
    //     if (Double.parseDouble(aQty) == 0) {
    //         return "@Error@ La cantidad del activo no puede ser cero, verifique los datos introducidos e intente nuevamente.";
    //     }
    // }

    // Si todo está bien, no se devuelve ningún error
    result = "";

} catch (Exception e) {
    return "@Error@ " + e.getLocalizedMessage();
}
