ALTER TABLE C_BPartner 
ALTER COLUMN Value_Vendor TYPE varchar(10);  -- Cambiar de numeric a varchar(10)


ALTER TABLE C_BPartner 
ADD COLUMN employee_id numeric(10) NULL,  -- Permitir valores nulos
ADD COLUMN vendor_id numeric(10) NULL;    -- Permitir valores nulos


--- Crear secuencia de proveedor.

CREATE SEQUENCE vendor_id_seq
START WITH 1  -- Valor inicial (debe ser 1 o mayor)
INCREMENT BY 1  -- Incremento de 1 en 1
MINVALUE 0  -- Valor mínimo
NO MAXVALUE  -- No valor máximo
CACHE 1;  -- Número de valores que puede almacenar en memoria

CREATE SEQUENCE another_id_seq
START WITH 100  -- Valor inicial (debe ser 100 o mayor)
INCREMENT BY 1  -- Incremento de 1 en 1
MINVALUE 0  -- Valor mínimo
NO MAXVALUE  -- No valor máximo
CACHE 1;  -- Número de valores que puede almacenar en memoria
----------------------------------------------------------------
-- Crear secuencia a Empleado.

CREATE SEQUENCE employee_id_seq
START WITH 2  -- Valor inicial
INCREMENT BY 1  -- Incremento de 1 en 1
MINVALUE 0  -- No valor mínimo
NO MAXVALUE  -- No valor máximo
CACHE 1;  -- Número de valores que puede almacenar en memoria


-------------------------------------------------------------------

-- Crear la función del trigger
CREATE OR REPLACE FUNCTION assign_ids()
RETURNS trigger
LANGUAGE plpgsql
AS $$
BEGIN
    -- Inicializa los valores en NULL
    NEW.vendor_id := NULL;  
    NEW.Value_Vendor := NULL;
    NEW.employee_id := NULL;  
    NEW.Value := NULL;  

    -- Verifica si el registro es un proveedor
    IF NEW.IsVendor = 'Y' THEN
        NEW.vendor_id := nextval('vendor_id_seq');  -- Asigna el siguiente valor de la secuencia
        NEW.Value_Vendor := NEW.vendor_id::text;  -- Asigna el valor del vendor_id a Value_Vendor
        NEW.Value := nextval('another_id_seq');  -- Asigna 100 (o el valor que desees) a Value
    END IF;

    -- Verifica si el registro es un empleado
    IF NEW.IsEmployee = 'Y' THEN
        NEW.employee_id := nextval('employee_id_seq');  -- Asigna el siguiente valor de la secuencia
        NEW.Value := NEW.employee_id::text;  -- Asigna el valor del employee_id a Value
    END IF;

    RETURN NEW;
END;
$$;


-- Crear el trigger
CREATE TRIGGER trg_assign_ids
BEFORE INSERT ON C_BPartner
FOR EACH ROW
EXECUTE FUNCTION assign_ids();



-- Reiniciar la secuencia de proveedores a 1
ALTER SEQUENCE vendor_id_seq RESTART WITH 1;

-- Reiniciar la secuencia de otro ID a 100
ALTER SEQUENCE another_id_seq RESTART WITH 100;

-- Reiniciar la secuencia de empleados para que el siguiente valor sea 2
ALTER SEQUENCE employee_id_seq RESTART WITH 2;