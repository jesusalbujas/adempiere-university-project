--- Modelo
CAST(JAU01_AssetModel.Value AS INTEGER) ASC, JAU01_AssetModel.Created ASC

--- Marca
CAST(JAU01_AssetMark.Value AS INTEGER) ASC, JAU01_AssetMark.Created ASC

--- Activo Fijo

A_Asset.Created DESC

--- Producto
CAST(M_Product.Value AS INTEGER) ASC, M_Product.Created ASC