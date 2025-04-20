import org.compiere.util.DB;

// Inicializa la variable para almacenar el valor original del número de serie
String originalSerNo = "";

// Verifica si el campo "A_Asset_ID" tiene un valor (es decir, si estamos editando un activo existente)
if (A_Tab.getValue("A_Asset_ID") != null) {
    // Obtiene el valor original del número de serie del registro antes de realizar el cambio
    originalSerNo = DB.getSQLValueString(null, "SELECT SerNo FROM A_Asset WHERE A_Asset_ID = ?", A_Tab.getValue("A_Asset_ID"));
    System.out.println("Número de serie original: " + originalSerNo);

    // Verifica si el campo "SerNo" tiene un valor (nuevo número de serie)
    if (A_Tab.getValue("SerNo") != null) {
        // Obtiene el nuevo valor del número de serie
        String newSerNo = A_Tab.getValue("SerNo").toString();
        System.out.println("Nuevo número de serie ingresado: " + newSerNo);

        // Verifica si el nuevo valor es diferente al original
        if (!newSerNo.equals(originalSerNo)) {
            // Consulta para verificar si el nuevo número de serie ya existe en la base de datos
            String sql = "SELECT COUNT(*) FROM A_Asset WHERE SerNo = ?";
            int count = DB.getSQLValue(null, sql, newSerNo);

            // Si el número de serie ya existe en la base de datos
            if (count > 0) {
                System.out.println("El nuevo número de serie ya existe en la base de datos: " + newSerNo);

                // Restaura el valor original del número de serie
                A_Tab.setValue("SerNo", originalSerNo);
                System.out.println("El número de serie ha sido restaurado al valor original: " + originalSerNo);
            } else {
                System.out.println("El nuevo número de serie no existe en la base de datos. Guardando el nuevo número.");
            }
        } else {
            System.out.println("El nuevo número de serie es el mismo que el original. No se realizan cambios.");
        }
    } else {
        System.out.println("El campo 'SerNo' está vacío o no existe.");
    }
} else {
    System.out.println("El campo 'A_Asset_ID' es nulo, no se puede obtener el número de serie original.");
}

result = "";
