EXISTS (
    SELECT 1
    FROM A_Asset a
    WHERE a.A_Asset_ID = @A_Asset_ID@
      AND a.JAU01_AssetMark_ID = JAU01_AssetMark.JAU01_AssetMark_ID
)

---
@SQL=SELECT JAU01_AssetMark_ID
FROM JAU01_AssetMark
WHERE EXISTS (
    SELECT 1
    FROM A_Asset a
    WHERE a.A_Asset_ID = @A_Asset_ID@
      AND a.JAU01_AssetMark_ID = JAU01_AssetMark.JAU01_AssetMark_ID
)
