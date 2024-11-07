CREATE OR REPLACE FUNCTION update_asset_name()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.name IS DISTINCT FROM (SELECT name FROM m_product WHERE m_product_id = NEW.m_product_id) THEN
        NEW.name := (SELECT name FROM m_product WHERE m_product_id = NEW.m_product_id);
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_update_asset_name
BEFORE INSERT OR UPDATE ON a_asset
FOR EACH ROW
EXECUTE FUNCTION update_asset_name();
