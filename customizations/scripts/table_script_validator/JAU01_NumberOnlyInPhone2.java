// groovy: JAU01_NumberOnly
import org.compiere.model.PO;
PO entity = A_PO;

try {
    // Obtiene el valor actual del campo
    String currentName = entity.get_ValueAsString("Phone2");

    // Verifica si el valor no es nulo
    if (currentName != null) {
        // Verifica si el valor contiene algo distinto a números
        if (!currentName.matches('^[0-9]+$')) {
            return "@Error@ Solo se permiten números.";
        }
    }
    
    // Si todo está bien, no retorna error
    result = "";

} catch (Exception e) {
    return "@Error@ " + e.getLocalizedMessage();
}
