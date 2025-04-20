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
        AND HR_Department_ID = 1000001
    )
)
AND IsEmployee = 'Y'
AND AD_Org_ID = 50006
