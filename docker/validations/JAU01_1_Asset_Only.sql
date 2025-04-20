(
    (
        NOT EXISTS (
            SELECT 1
            FROM a_asset_delivery ad
            WHERE ad.A_Asset_ID = @A_Asset_ID@
            AND ad.A_Asset_Status = 'PR'
        )
    )
    OR 
    (
        EXISTS (
            SELECT 1
            FROM a_asset_delivery ad
            WHERE ad.A_Asset_ID = @A_Asset_ID@
            AND ad.A_Asset_Status = 'PR'
        )
        AND HR_Department_ID IN (1000001)
    )
)
AND IsEmployee = 'Y'
AND AD_Org_ID = 50006
AND (
    HR_Department_ID IN (1000001, 1000002, 1000004, 1000005, 1000000)
    OR NOT EXISTS (
        SELECT 1
        FROM A_Asset_Delivery at
        WHERE at.Last_Employee_ID = C_BPartner.C_BPartner_ID
        AND at.A_Asset_Status = 'EU'
        AND at.MovementDate = (
            SELECT MAX(at2.MovementDate)
            FROM A_Asset_Delivery at2
            WHERE at2.Last_Employee_ID = C_BPartner.C_BPartner_ID
        )
    )
)
