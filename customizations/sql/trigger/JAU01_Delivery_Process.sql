CREATE OR REPLACE FUNCTION update_isinposession_in_delivery()
RETURNS trigger AS $$
BEGIN
    -- Actualiza IsInPosession en A_Asset_Delivery basado en el valor de A_Asset
    UPDATE A_Asset_Delivery
    SET IsInPosession = (SELECT IsInPosession FROM A_Asset WHERE A_Asset_ID = NEW.A_Asset_ID)
    WHERE A_Asset_ID = NEW.A_Asset_ID;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER trg_update_isinposession
AFTER INSERT ON A_Asset_Delivery
FOR EACH ROW
EXECUTE FUNCTION update_isinposession_in_delivery();



------------------------------------------------------
------------------------------------------------------
------------------------------------------------------
--- Agregar columna para guardar el último empleado asignado.

ALTER TABLE A_Asset_Delivery 
ADD COLUMN Last_Employee_ID NUMERIC(10);

------------------------------------------------------
------------------------------------------------------
CREATE OR REPLACE FUNCTION insert_last_employee_id()
RETURNS trigger AS $$
BEGIN
    -- Si el activo está asignado (IsInPosession = 'N')
    IF NEW.IsInPosession = 'N' THEN
        -- Guarda el C_BPartner_ID en Last_Employee_ID
        NEW.Last_Employee_ID := NEW.C_BPartner_ID;

    -- Si el activo está devuelto (IsInPosession = 'Y')
    ELSIF NEW.IsInPosession = 'Y' THEN
        -- Copia el valor de Last_Employee_ID en C_BPartner_ID
        NEW.C_BPartner_ID := NEW.Last_Employee_ID;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Crear el trigger para inserciones y actualizaciones
CREATE TRIGGER trg_insert_update_last_employee_id
BEFORE INSERT OR UPDATE ON A_Asset_Delivery
FOR EACH ROW
EXECUTE FUNCTION insert_last_employee_id();

------------------------------------------------------
------------------------------------------------------

-- Función para manejar la devolución y asignar el último empleado
CREATE OR REPLACE FUNCTION handle_asset_return()
RETURNS trigger AS $$
DECLARE
    v_last_employee_id NUMERIC(10);
BEGIN
    -- Verifica si el activo ha sido devuelto (IsInPosession = 'Y' y el estado es 'DB')
    IF NEW.IsInPosession = 'Y' AND NEW.A_Asset_Status = 'DB' OR NEW.A_Asset_Status = 'RB' OR NEW.A_Asset_Status = 'DNC' OR NEW.A_Asset_Status = 'PR' THEN
        -- Busca el último empleado asignado desde el registro anterior donde el activo estaba en posesión
        SELECT d.Last_Employee_ID
        INTO v_last_employee_id
        FROM A_Asset_Delivery d
        WHERE d.A_Asset_ID = NEW.A_Asset_ID
          AND d.A_Asset_Status = 'EU'  --  buscamos registros donde el activo estaba en uso
        ORDER BY d.Created DESC  -- Toma el último registro de asignación
        LIMIT 1;

        -- Asigna el Last_Employee_ID al nuevo registro de devolución
        IF v_last_employee_id IS NOT NULL THEN
            NEW.Last_Employee_ID := v_last_employee_id; -- Asigna el último empleado
        END IF;

        -- Asigna el nombre del empleado al Last_Employee_Assigned_ID (usando Last_Employee_ID)
        IF NEW.Last_Employee_ID IS NOT NULL THEN
            SELECT e.Name INTO NEW.Last_Employee_Assigned_ID
            FROM C_BPartner e
            WHERE e.C_BPartner_ID = NEW.Last_Employee_ID;
        ELSE
            NEW.Last_Employee_Assigned_ID := NULL;  --  sea NULL si no hay empleado
        END IF;

    ELSIF NEW.IsInPosession = 'N' THEN
        -- Cuando se asigna un activo (nueva asignación), guarda el C_BPartner_ID como Last_Employee_ID
        NEW.Last_Employee_ID := NEW.C_BPartner_ID;  -- Aquí se mantiene el C_BPartner_ID como Last_Employee_ID
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


-- Trigger para manejar la devolución en el nuevo registro
CREATE TRIGGER trg_handle_asset_return
BEFORE INSERT OR UPDATE ON A_Asset_Delivery
FOR EACH ROW
EXECUTE FUNCTION handle_asset_return();

------------------------------------------------------