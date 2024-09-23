-- Establecemos la lógica del trigger
CREATE OR REPLACE FUNCTION update_value_with_JAU01_AssetModel_id()
RETURNS trigger
LANGUAGE 'plpgsql'
COST 100
VOLATILE NOT LEAKPROOF
AS $BODY$
BEGIN
    -- Actualizar el campo Value con el valor del id de la tabla
    NEW.Value := NEW.JAU01_AssetModel_ID::text;  -- Convertimos el valor numérico a texto
    RETURN NEW;
END;
$BODY$;

--- Asignamos el trigger a la tabla
CREATE TRIGGER trg_update_value_with_JAU01_AssetModel_id
BEFORE INSERT ON JAU01_AssetModel
FOR EACH ROW
EXECUTE FUNCTION update_value_with_JAU01_AssetModel_id();
