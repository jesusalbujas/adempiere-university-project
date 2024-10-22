IsEmployee='Y' 
AND AD_Org_ID=50006 
AND EXISTS (
    SELECT 1
    FROM A_Asset_Delivery at
    WHERE at.Last_Employee_ID = C_BPartner.C_BPartner_ID
    AND at.A_Asset_ID = @A_Asset_ID@
    AND at.MovementDate = (
        SELECT MAX(at2.MovementDate)
        FROM A_Asset_Delivery at2
        WHERE at2.A_Asset_ID = @A_Asset_ID@
    )
)
