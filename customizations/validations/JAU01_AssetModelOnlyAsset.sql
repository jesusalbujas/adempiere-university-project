EXISTS (
    SELECT 1
    FROM A_Asset a
    WHERE a.A_Asset_ID = @A_Asset_ID@
      AND a.JAU01_AssetModel_ID = JAU01_AssetModel.JAU01_AssetModel_ID
)


---

@SQL=SELECT JAU01_AssetModel_ID 
FROM JAU01_AssetModel
WHERE EXISTS (
    SELECT 1
    FROM A_Asset a
    WHERE a.A_Asset_ID = @A_Asset_ID@
      AND a.JAU01_AssetModel_ID = JAU01_AssetModel.JAU01_AssetModel_ID
)
