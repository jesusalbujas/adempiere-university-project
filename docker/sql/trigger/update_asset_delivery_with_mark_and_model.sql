CREATE OR REPLACE FUNCTION update_asset_delivery_with_mark_and_model()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE A_Asset_Delivery
    SET 
        JAU01_AssetMark_ID = (SELECT JAU01_AssetMark_ID FROM A_Asset WHERE A_Asset_ID = NEW.A_Asset_ID LIMIT 1),
        JAU01_AssetModel_ID = (SELECT JAU01_AssetModel_ID FROM A_Asset WHERE A_Asset_ID = NEW.A_Asset_ID LIMIT 1)
        ManagerAsset = 10000001
    WHERE 
        A_Asset_ID = NEW.A_Asset_ID;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER trg_update_asset_delivery_mark_and_model
AFTER INSERT ON A_Asset_Delivery
FOR EACH ROW
EXECUTE FUNCTION update_asset_delivery_with_mark_and_model();
