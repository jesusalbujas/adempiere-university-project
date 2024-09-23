// beanshell:JAU01_NumberOnlyInTaxID

// Obtiene el valor actual del campo
String TaxIDValue = A_Tab.getValue("TaxID");

// Verifica si el valor no es nulo y es una cadena de texto
if (TaxIDValue == null) {
    return "@Error@ El campo está vacío.";
}

// Convertir el valor a String
String currentTaxID = TaxIDValue.toString();

// Verifica si el valor contiene solo números
if (!currentTaxID.matches("^[0-9]+$")) {
    // Limpia el valor del campo para evitar el guardado
    A_Tab.setValue("TaxID", "");
    
    // Muestra un error y evita guardar (solo en zk)
    return "@Error@ Solo se permiten números.";
} 

// no mostrar nada si todo esta bien
result = "";