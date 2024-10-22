// JAU01_JAU01_AssetMark_IDFromProduct
import org.compiere.util.DB;

int assetModelId = 0;  // Variable para almacenar el ID del modelo de activo

// Verificar si JAU01_AssetMark_ID tiene un valor asignado
if (A_Tab.getValue("JAU01_AssetMark_ID") != null) {
    int markId = (int) A_Tab.getValue("JAU01_AssetMark_ID");  // Obtener el valor de la marca

    // Consulta SQL para obtener el ID del modelo asociado a la marca
    String sql = "SELECT JAU01_AssetModel_ID FROM JAU01_AssetModel WHERE JAU01_AssetMark_ID = ?";

    // Ejecutar la consulta y obtener el ID del modelo (como entero)
    assetModelId = DB.getSQLValue(null, sql, markId);
}

// Verificar si se obtuvo un ID válido del modelo
if (assetModelId > 0) {
    // Asignar el valor de JAU01_AssetModel_ID al campo en la pestaña actual
    A_Tab.setValue("JAU01_AssetModel_ID", assetModelId);
}

// Devolver un resultado vacío para finalizar el script
result = "";
