// beanshell:07_PST_NumberOnlyInPST_RIF

// Obtiene el valor actual del campo
String PST_RIFValue = A_Tab.getValue("PST_RIF");

// Verifica si el valor no es nulo y es una cadena de texto
if (PST_RIFValue == null) {
    return "Error: El campo está vacío.";
}

// Convertir el valor a String
String currentPST_RIF = PST_RIFValue.toString();

// Verifica si el valor contiene solo números
if (!currentPST_RIF.matches("^[0-9]+$")) {
    // Limpia el valor del campo para evitar el guardado
    A_Tab.setValue("PST_RIF", "");
    
    // Muestra un error y evita guardar (solo en zk)
    return "Error: Solo se permiten números en el campo RIF.";
} 

// no mostrar nada si todo esta bien
result = "";