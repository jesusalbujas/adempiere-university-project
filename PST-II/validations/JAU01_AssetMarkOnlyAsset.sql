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

---

EXISTS (
    SELECT 1
    FROM JAU01_AssetModel
    WHERE JAU01_AssetModel.JAU01_AssetModel_ID = @JAU01_AssetModel_ID@
    AND JAU01_AssetModel.JAU01_AssetMark_ID = JAU01_AssetMark.JAU01_AssetMark_ID
)