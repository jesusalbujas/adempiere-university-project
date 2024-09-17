// groovy: JAU01_NoNumbersInName
import org.compiere.model.PO;
PO entity = A_PO;

try {
    // Obtiene el valor actual del campo 'Name'
    String currentName = entity.get_ValueAsString("Name");

    // Verifica si el valor no es nulo
    if (currentName != null) {
        // Expresión regular que busca si el valor contiene números
        if (currentName.matches(".*\\d.*")) {
            return "@Error@ El campo Nombre no puede contener números.";
        }
    }
    
    // Si todo está bien, no retorna error
    result = "";

} catch (Exception e) {
    return "@Error@ " + e.getLocalizedMessage();
}
