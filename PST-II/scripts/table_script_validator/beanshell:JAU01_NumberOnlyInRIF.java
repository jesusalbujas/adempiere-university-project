// beanshell:JAU01_NumberOnlyInJAU01_RIF

// Obtiene el valor actual del campo
String JAU01_RIFValue = A_Tab.getValue("JAU01_RIF");

// Verifica si el valor no es nulo y es una cadena de texto
if (JAU01_RIFValue == null) {
    return "@Error@ El campo está vacío.";
}

// Convertir el valor a String
String currentJAU01_RIF = JAU01_RIFValue.toString();

// Verifica si el valor contiene solo números
if (!currentJAU01_RIF.matches("^[0-9]+$")) {
    // Limpia el valor del campo para evitar el guardado
    A_Tab.setValue("JAU01_RIF", "");
    
    // Muestra un error y evita guardar (solo en zk)
    return "@Error@ Solo se permiten números.";
} 

// no mostrar nada si todo esta bien
result = "";