-- Establecemos la lógica del trigger
CREATE OR REPLACE FUNCTION update_value_with_A_Asset_id()
RETURNS trigger
LANGUAGE 'plpgsql'
COST 100
VOLATILE NOT LEAKPROOF
AS $BODY$
BEGIN
    -- Actualizar el campo Value con el valor del id de la tabla
    NEW.Value := NEW.A_Asset_ID::text;  -- Convertimos el valor numérico a texto
    RETURN NEW;
END;
$BODY$;

--- Asignamos el trigger a la tabla
CREATE TRIGGER trg_update_value_with_A_Asset_id
BEFORE INSERT ON A_Asset
FOR EACH ROW
EXECUTE FUNCTION update_value_with_A_Asset_id();