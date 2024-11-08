CREATE OR REPLACE FUNCTION update_asset_delivery_movementdate() 
RETURNS TRIGGER AS $$
BEGIN
    -- Actualizar el campo movementdate con el valor del campo created del nuevo registro
    UPDATE a_asset_delivery
    SET movementdate = NEW.created
    WHERE a_asset_delivery_id = NEW.a_asset_delivery_id;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_update_asset_delivery_movementdate
AFTER INSERT ON a_asset_delivery
FOR EACH ROW
EXECUTE FUNCTION update_asset_delivery_movementdate();


ALTER TABLE a_asset_delivery DISABLE TRIGGER trg_update_asset_delivery_movementdate;
