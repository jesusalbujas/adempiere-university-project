CREATE OR REPLACE FUNCTION update_jau01_m_locator() 
RETURNS TRIGGER AS $$
BEGIN
    -- Asigna el valor al campo JAU01_M_Locator directamente en NEW para evitar recursión
    NEW.JAU01_M_Locator := (SELECT value FROM M_Locator WHERE M_Locator_ID = NEW.M_Locator_ID);

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

---------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------

CREATE TRIGGER trg_update_jau01_m_locator
BEFORE UPDATE OF M_Locator_ID
ON A_Asset
FOR EACH ROW
EXECUTE FUNCTION update_jau01_m_locator();
