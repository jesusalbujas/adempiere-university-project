-- Establecemos la lógica del trigger
CREATE OR REPLACE FUNCTION update_value_with_HR_Department_id()
RETURNS trigger
LANGUAGE 'plpgsql'
COST 100
VOLATILE NOT LEAKPROOF
AS $BODY$
BEGIN
    -- Actualizar el campo Value con el valor del id de la tabla
    NEW.Value := NEW.HR_Department_ID::text;  -- Convertimos el valor numérico a texto
    RETURN NEW;
END;
$BODY$;

--- Asignamos el trigger a la tabla
CREATE TRIGGER trg_update_value_with_HR_Department_id
BEFORE INSERT ON HR_Department
FOR EACH ROW
EXECUTE FUNCTION update_value_with_HR_Department_id();



UPDATE AD_Sequence SET CurrentNext=8 WHERE AD_Sequence_ID=53107;