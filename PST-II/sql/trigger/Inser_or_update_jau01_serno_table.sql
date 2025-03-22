CREATE OR REPLACE FUNCTION trg_insert_jau01_serno()
RETURNS TRIGGER AS $$
DECLARE
    new_serno_id INT;
BEGIN
    -- Iniciar el ID desde 1 o continuar desde el último ID
    SELECT COALESCE(MAX(JAU01_SerNo_ID), 0) + 1 INTO new_serno_id FROM JAU01_SerNo;

    -- Insertar el nuevo registro en JAU01_SerNo con el nuevo ID
    INSERT INTO JAU01_SerNo (JAU01_SerNo_ID, a_asset_id, serno)
    VALUES (new_serno_id, NEW.a_asset_id, NEW.serno);

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER trg_on_a_asset
AFTER INSERT OR UPDATE OF a_asset_id, serno ON a_asset
FOR EACH ROW
EXECUTE FUNCTION trg_insert_jau01_serno();
