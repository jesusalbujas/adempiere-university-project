CREATE OR REPLACE FUNCTION adempiere.insert_or_update_m_locator()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
BEGIN
    -- Verificar si ya existe un localizador para el almacén y la organización
    IF EXISTS (
        SELECT 1
        FROM M_Locator loc
        WHERE loc.M_Warehouse_ID = NEW.M_Warehouse_ID
        AND loc.AD_Org_ID = NEW.AD_Org_ID
    ) THEN
        -- Actualizar el localizador si ya existe
        UPDATE M_Locator
        SET
            AD_Client_ID = NEW.AD_Client_ID,
            Value = NEW.Name,  -- Actualiza el valor del localizador con el nombre del almacén
            Updated = NEW.Updated,
            UpdatedBy = NEW.UpdatedBy,
            IsActive = NEW.IsActive -- Actualizar el estado del localizador basado en el almacén
        WHERE M_Warehouse_ID = NEW.M_Warehouse_ID
        AND AD_Org_ID = NEW.AD_Org_ID;

    ELSE
        -- Insertar un nuevo localizador si no existe
        INSERT INTO M_Locator (
            M_Locator_ID,
            AD_Client_ID,
            AD_Org_ID,
            M_Warehouse_ID,
            Value,
            X,
            Y,
            Z,
            PriorityNo,
            IsActive,
            IsDefault,
            Created,
            CreatedBy,
            UpdatedBy,
            Updated
        )
        SELECT
            nextid(62, 'N'),         -- Genera el ID del localizador
            NEW.AD_Client_ID,        -- Cliente al que pertenece el almacén
            NEW.AD_Org_ID,           -- Organización
            NEW.M_Warehouse_ID,      -- ID del almacén
            NEW.Name,                -- Valor por defecto para el nuevo localizador (Nombre del almacén)
            0,                       -- X coordenada
            0,                       -- Y coordenada
            0,                       -- Z coordenada
            50,                      -- Prioridad del localizador
            NEW.IsActive,            -- El localizador está activo o inactivo según el almacén
            'N',                     -- No es el localizador por defecto
            NEW.Created,             -- Fecha de creación
            NEW.CreatedBy,           -- ID del usuario que lo creó
            NEW.UpdatedBy,           -- ID del usuario que lo actualizó
            NEW.Updated              -- Fecha de actualización
        FROM m_warehouse ml
        WHERE ml.M_Warehouse_ID = NEW.M_Warehouse_ID
        AND ml.AD_Org_ID = 50006;
    END IF;

    RETURN NEW;
END;
$BODY$;

ALTER FUNCTION adempiere.insert_or_update_m_locator()
    OWNER TO adempiere;

--- Agregar el TRIGGER a la tabla M_Warehouse

CREATE TRIGGER trg_insert_or_update_m_locator
AFTER INSERT OR UPDATE ON M_Warehouse
FOR EACH ROW
EXECUTE FUNCTION adempiere.insert_or_update_m_locator();
