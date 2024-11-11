AD_Org_ID = 50006
AND M_Locator_ID IS NOT NULL
AND (
    (A_Asset_Status_Reubication IN ('DN', 'RB') AND M_Locator_ID = 50006)
    OR
    (
        (EXISTS (
            SELECT 1
            FROM M_Product p
            JOIN A_Asset a ON p.M_Product_ID = a.M_Product_ID
            WHERE a.A_Asset_ID = @A_Asset_ID@
            AND p.M_Product_ID IN (2, 3, 4, 5, 6, 7, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19)
        ) AND M_Locator_ID IN (1000007, 1000006, 1000005)) 
        OR
        (EXISTS (
            SELECT 1
            FROM M_Product p
            JOIN A_Asset a ON p.M_Product_ID = a.M_Product_ID
            WHERE a.A_Asset_ID = @A_Asset_ID@
            AND p.M_Product_ID IN (8, 9)
        ) AND M_Locator_ID IN (1000011, 1000012))
    )
)
