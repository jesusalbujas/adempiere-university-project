--- Agregar la Secuencia (modificar el nombre según la tabla)
CREATE SEQUENCE <name-here>_value_seq
  START WITH 100000
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;

--- Crear Función para ejecutar la lógica. (modificar el nombre según la tabla)
--- En <sequence> cambiar por el nombre de la secuencia previamente creada.
CREATE OR REPLACE FUNCTION set_<TABLE-NAME>_value()
RETURNS TRIGGER AS $$
BEGIN
  IF NEW.Value IS NULL THEN
    NEW.Value := NEXTVAL('<sequence>_seq');
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

--- Agregar el Trigger a la tabla.
--- Cambiar el <table-name> por el nombre de la tabla.
CREATE TRIGGER trg_set_<TABLE-NAME>_value
BEFORE INSERT ON <TABLE-NAME>
FOR EACH ROW
EXECUTE FUNCTION set_<TABLE-NAME>_value();