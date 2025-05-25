// beanshell:01_PST_Employee_LimitAssetsAllowed
/*  
Este script trae el valor del límite de activos
de la tabla PST_Departments hasta la tabla PST_Employees.
*/
import org.compiere.util.DB;

Integer PST_LimitAssetsAllowed = null;
// Obtenemos el id del departamento
Object departmentIdObj = A_Tab.getValue("PST_Department_ID");

// validamos que no este vacío
if (departmentIdObj != null && departmentIdObj instanceof Integer) {
    int departmentId = ((Integer) departmentIdObj).intValue(); // Realizamos el casting solo si el valor es Integer
    String sql = "SELECT PST_LimitAssetsAllowed FROM PST_Department WHERE PST_Department_ID = ?";
    PST_LimitAssetsAllowed = DB.getSQLValue(null, sql, departmentId);  // Obtener como Integer

    // Si PST_LimitAssetsAllowed es null o 0, vaciar el campo
    if (PST_LimitAssetsAllowed == null || PST_LimitAssetsAllowed == 0) {
        A_Tab.setValue("PST_LimitAssetsAllowed", null);
    } else {
        // Si hay un valor válido diferente de 0, asignarlo
        A_Tab.setValue("PST_LimitAssetsAllowed", PST_LimitAssetsAllowed);
    }
} else {
    // Si PST_Departments se elimina o es inválido, vaciar el campo PST_LimitAssetsAllowed
    A_Tab.setValue("PST_LimitAssetsAllowed", null);
}

result = "";
