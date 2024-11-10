// JAU01_SerNoFromProduct
import org.compiere.util.DB;

Integer limitassetsAllowed = null;
Object departmentIdObj = A_Tab.getValue("HR_Department_ID");

if (departmentIdObj != null && departmentIdObj instanceof Integer) {
    int departmentId = ((Integer) departmentIdObj).intValue(); // Realizamos el casting solo si el valor es Integer
    String sql = "SELECT LimitAssetsAllowed FROM HR_Department WHERE HR_Department_ID = ?";
    limitassetsAllowed = DB.getSQLValue(null, sql, departmentId);  // Obtener como Integer

    // Si LimitAssetsAllowed es null o 0, vaciar el campo
    if (limitassetsAllowed == null || limitassetsAllowed == 0) {
        A_Tab.setValue("LimitAssetsAllowed", null);
    } else {
        // Si hay un valor válido diferente de 0, asignarlo
        A_Tab.setValue("LimitAssetsAllowed", limitassetsAllowed);
    }
} else {
    // Si HR_Department_ID se elimina o es inválido, vaciar el campo LimitAssetsAllowed
    A_Tab.setValue("LimitAssetsAllowed", null);
}

result = "";
