-- Establecemos la lógica del trigger
CREATE OR REPLACE FUNCTION update_value_with_C_BPartner_id()
RETURNS trigger
LANGUAGE 'plpgsql'
COST 100
VOLATILE NOT LEAKPROOF
AS $BODY$
BEGIN
    -- Verificamos si el registro es de un empleado (IsEmployee = 'Y') y no de un proveedor
    IF NEW.IsVendor = 'N' AND NEW.IsEmployee = 'Y' THEN
        -- Actualizamos el campo Value con el valor del ID del registro (C_BPartner_ID)
        NEW.Value := NEW.C_BPartner_ID::text;  -- Convertimos el valor numérico a texto
    END IF;
    
    -- Retornamos el nuevo valor del registro
    RETURN NEW;
END;
$BODY$;


--- Asignamos el trigger a la tabla
CREATE TRIGGER trg_update_value_with_C_BPartner_id
BEFORE INSERT ON C_BPartner
FOR EACH ROW
EXECUTE FUNCTION update_value_with_C_BPartner_id();

DROP TRIGGER IF EXISTS trg_update_value_with_C_BPartner_id ON C_BPartner;

DROP FUNCTION IF EXISTS update_value_with_C_BPartner_id();