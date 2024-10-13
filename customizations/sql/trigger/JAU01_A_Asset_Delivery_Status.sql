CREATE OR REPLACE FUNCTION check_asset_status() 
RETURNS TRIGGER AS $$
BEGIN
    -- Validar si el asset está en posesión
    IF EXISTS (
        SELECT 1
        FROM a_asset
        WHERE a_asset_id = NEW.a_asset_id
        AND IsInPosession = 'N'
    ) THEN
        -- Si IsInPosession es 'N', actualizar a_asset_status a 'En Uso'
        UPDATE a_asset_delivery
        SET a_asset_status = 'EU'
        WHERE a_asset_delivery_id = NEW.a_asset_delivery_id;
        
    ELSIF EXISTS (
        SELECT 1
        FROM a_asset
        WHERE a_asset_id = NEW.a_asset_id
        AND IsInPosession = 'Y'
    ) THEN
        -- Si IsInPosession cambia a 'Y', actualizar a 'Disponible'
        UPDATE a_asset_delivery
        SET a_asset_status = 'DB'
        WHERE a_asset_delivery_id = NEW.a_asset_delivery_id;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


--- Crear el trigger
CREATE TRIGGER trg_check_asset_status
AFTER INSERT ON a_asset_delivery
FOR EACH ROW
EXECUTE FUNCTION check_asset_status();