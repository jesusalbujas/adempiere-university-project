// JAU01_SerNoFromProduct
import org.compiere.util.DB;
String serNo = "";
if (A_Tab.getValue("M_Product_ID") != null) {
    int productId = (int) A_Tab.getValue("M_Product_ID");
    String sql = "SELECT SerNo FROM M_Product WHERE M_Product_ID = ?";
    serNo = DB.getSQLValueString(null, sql, productId);
}
if (serNo != null && serNo != "") {
    A_Tab.setValue("SerNo", serNo);
}

result = "";