// JAU01_JAU01_AssetMark_IDFromProduct
import org.compiere.util.DB;

int markId = 0;  // Variable para almacenar el ID de la marca

// Verificar si JAU01_AssetModel_ID tiene un valor asignado
if (A_Tab.getValue("JAU01_AssetModel_ID") != null) {
    int assetModelId = (int) A_Tab.getValue("JAU01_AssetModel_ID");  // Obtener el valor del modelo

    // Consulta SQL para obtener la marca asociada al modelo
    String sql = "SELECT JAU01_AssetMark_ID FROM JAU01_AssetModel WHERE JAU01_AssetModel_ID = ?";

    // Ejecutar la consulta y obtener el ID de la marca (como entero)
    markId = DB.getSQLValue(null, sql, assetModelId);
}

// Verificar si se obtuvo un ID válido de la marca
if (markId > 0) {
    // Asignar el valor de JAU01_AssetMark_ID al campo en la pestaña actual
    A_Tab.setValue("JAU01_AssetMark_ID", markId);
}

// Devolver un resultado vacío para finalizar el script
result = "";
