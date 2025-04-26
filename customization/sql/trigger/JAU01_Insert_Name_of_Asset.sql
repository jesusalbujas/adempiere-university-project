CREATE OR REPLACE FUNCTION update_asset_name()
RETURNS TRIGGER AS $$
DECLARE
    product_name TEXT;
    asset_mark_name TEXT;
    asset_model_name TEXT;
    serno_prefix TEXT;
BEGIN
    -- Obtener el nombre del producto asociado al m_product_id en a_asset
    SELECT name INTO product_name 
    FROM m_product 
    WHERE m_product_id = NEW.m_product_id;

    -- Obtener el nombre de la marca del activo (jau01_assetmark)
    SELECT name INTO asset_mark_name 
    FROM jau01_assetmark 
    WHERE jau01_assetmark_id = NEW.jau01_assetmark_id;

    -- Obtener el nombre del modelo del activo (jau01_assetmodel)
    SELECT name INTO asset_model_name 
    FROM jau01_assetmodel 
    WHERE jau01_assetmodel_id = NEW.jau01_assetmodel_id;

    -- Extraer los primeros 6 caracteres de serno
    serno_prefix := LEFT(NEW.serno, 6);

    -- Concatenar los valores en el formato requerido
    NEW.name := serno_prefix || ' ' || product_name || ' ' || asset_mark_name || ' ' || asset_model_name;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER trg_update_asset_name
BEFORE INSERT OR UPDATE ON a_asset
FOR EACH ROW
EXECUTE FUNCTION update_asset_name();
