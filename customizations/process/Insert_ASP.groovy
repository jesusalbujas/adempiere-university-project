import org.compiere.model.PO;
import org.compiere.model.MRole;
import org.compiere.util.DB;
import org.compiere.util.Env;
import java.util.UUID;
import java.sql.Timestamp;

PO entity = A_PO;
Integer roleID = entity.get_ValueAsInt("AD_Role_ID");
Integer clientID = entity.get_ValueAsInt("AD_Client_ID");
Integer orgID = entity.get_ValueAsInt("AD_Org_ID");
Boolean isActive = entity.get_ValueAsBoolean("IsActive");
Integer createdBy = entity.get_ValueAsInt("CreatedBy");
Integer updatedBy = entity.get_ValueAsInt("UpdatedBy");

// Lista de ASP_Level_IDs que deseas insertar
List<Integer> aspLevelIDs = [1000001, 50000];  // Usamos una lista en lugar de un arreglo

// Obtener la fecha de creación y actualización
Timestamp currentDate = new Timestamp(System.currentTimeMillis());

if (roleID != null && roleID > 0) {
    for (Integer aspLevelID : aspLevelIDs) {
        // Verificar si ya existe un registro con el ASP_Level_ID y roleID específicos
        String checkSql = "SELECT COUNT(*) FROM ASP_Level_Access WHERE ASP_Level_ID = ? AND AD_Role_ID = ?";
        int count = DB.getSQLValue(null, checkSql, aspLevelID, roleID);

        // Si no existe el registro, realizar la inserción
        if (count == 0) {
            // Generar un UUID aleatorio
            String uuid = UUID.randomUUID().toString();

            // Insertar en ASP_Level_Access
            String insertSql = """
                INSERT INTO ASP_Level_Access (
                    ASP_Level_ID, AD_Client_ID, AD_Org_ID, Created, CreatedBy, IsActive, 
                    Updated, UpdatedBy, UUID, AD_Role_ID
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

            // Ejecutar la inserción usando DB.executeUpdateEx
            DB.executeUpdateEx(insertSql, [
                aspLevelID,
                clientID,
                orgID,
                currentDate,
                createdBy,
                isActive ? "Y" : "N",
                currentDate,
                updatedBy,
                uuid,
                roleID
            ] as Object[], null);
        }
    }
}

result = "";
