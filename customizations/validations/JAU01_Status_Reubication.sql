AD_Org_ID = 50006
AND M_Locator_ID IS NOT NULL
AND M_Locator_ID != (SELECT a.M_Locator_ID FROM A_Asset a WHERE a.A_Asset_ID = @A_Asset_ID@)
AND (
    (EXISTS (
        SELECT 1 
        FROM M_Product p
        JOIN A_Asset a ON p.M_Product_ID = a.M_Product_ID
        WHERE a.A_Asset_ID = @A_Asset_ID@
          AND p.M_Product_Category_ID = 2
    ) AND M_Locator_ID IN (1000007, 1000006, 1000005))
    OR
    (EXISTS (
        SELECT 1 
        FROM M_Product p
        JOIN A_Asset a ON p.M_Product_ID = a.M_Product_ID
        WHERE a.A_Asset_ID = @A_Asset_ID@
          AND p.M_Product_Category_ID = 3
    ))
)
AND (
    (@A_Asset_Status_Reubicacion@ IN ('DN', 'RB') AND JAU01_Location_Assigned = 50006)
    OR 
    (@A_Asset_Status_Reubicacion@ NOT IN ('DN', 'RB'))
)
