// groovy: JAU01_NumberOnly
import org.compiere.model.PO;
PO entity = A_PO;

try {
    // Obtiene el valor actual del campo
    String currentName = entity.get_ValueAsString("Phone");

    // Verifica si el valor no es nulo
    if (currentName != null) {
        // Expresión regular que busca si el valor contiene números
        if (currentName.matches("^[0-9]+$")) {
            return "@Error@ No puede contener letras.";
        }
    }
    
    // Si todo está bien, no retorna error
    result = "";

} catch (Exception e) {
    return "@Error@ " + e.getLocalizedMessage();
}