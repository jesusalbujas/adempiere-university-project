// beanshell:JAU01_NumberOnlyInPhone

// Obtiene el valor actual del campo
String phoneValue = A_Tab.getValue("Phone");

// Verifica si el valor no es nulo y es una cadena de texto
if (phoneValue == null) {
    return "@Error@ El campo está vacío.";
}

// Convertir el valor a String
String currentPhone = phoneValue.toString();

// Verifica si el valor contiene solo números
if (!currentPhone.matches("^[0-9]+$")) {
    // Limpia el valor del campo para evitar el guardado
    A_Tab.setValue("Phone", "");
    
    // Muestra un error y evita guardar (solo en zk)
    return "@Error@ Solo se permiten números.";
} 

// no mostrar nada si todo esta bien
result = "";
