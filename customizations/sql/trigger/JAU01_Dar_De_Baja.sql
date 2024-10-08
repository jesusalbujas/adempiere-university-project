CREATE OR REPLACE FUNCTION update_asset_on_disposal()
RETURNS TRIGGER AS
$$
BEGIN
    -- Verificar si el campo 'docaction' es igual a "'C" (incluyendo la comilla simple literal)
    IF NEW.docaction = '''C' THEN
        -- Actualizar los campos en la tabla a_asset
        UPDATE a_asset
        SET 
            IsDisposed = 'Y',
            IsInPosession = 'N',
            IsActive = 'N',
            AssetDisposalDate = NEW.A_Disposed_Date,
            A_Asset_Status = 'DI'
        WHERE 
            A_Asset_ID = NEW.A_Asset_ID; -- Suponiendo que A_Asset_ID sea la clave que relaciona ambas tablas
    END IF;

    RETURN NEW;
END;
$$
LANGUAGE plpgsql;

-- Crear el trigger que se ejecutará después de la inserción en a_asset_disposed
CREATE TRIGGER trg_update_asset_on_disposal
AFTER INSERT ON a_asset_disposed
FOR EACH ROW
EXECUTE FUNCTION update_asset_on_disposal();