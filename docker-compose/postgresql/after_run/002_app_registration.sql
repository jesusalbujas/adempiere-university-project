-- connects to the adempiere database and adempiere schema
\c adempiere adempiere

--Desactivar el principal
UPDATE AD_AppRegistration 
SET IsActive = 'N' 
WHERE AD_AppRegistration_ID=1000005;

--Activar el necesario
UPDATE AD_AppRegistration 
SET Name = 'Iniciar Sesión con Keycloak', IsActive = 'Y' 
WHERE AD_AppRegistration_ID=1000006; 

--AUTHORIZATION_ENDPOINT
UPDATE AD_AppRegistration_Para
SET ParameterValue = 'http://api.adempiere.io:3333/realms/adempiere/protocol/openid-connect/auth'
WHERE AD_AppRegistration_Para_ID=1000011;

--TOKEN ENDPOINT 
UPDATE AD_AppRegistration_Para
SET ParameterValue = 'http://api.adempiere.io:3333/realms/adempiere/protocol/openid-connect/token'
WHERE AD_AppRegistration_Para_ID=1000012;

--USER INFO
UPDATE AD_AppRegistration_Para
SET ParameterValue = 'http://api.adempiere.io:3333/realms/adempiere/protocol/openid-connect/userinfo'
WHERE AD_AppRegistration_Para_ID=1000013;

--REDIRECT URL 
UPDATE AD_AppRegistration_Para 
SET ParameterValue = 'http://api.adempiere.io/webui' 
WHERE AD_AppRegistration_Para_ID=1000008;
