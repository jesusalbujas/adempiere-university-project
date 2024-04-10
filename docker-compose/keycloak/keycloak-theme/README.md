# Keycloak Theme

- Para agregar el tema personalizado al contenedor Keycloak, siga estos pasos:


  - Copie la carpeta donde se encuentra el tema

```sh
docker cp `<CUSTOM_THEME>` <KEYCLOAK_CONTAINER>:/opt/jboss/keycloak/themes/<CUSTOM_THEME>
```

  - Inicie sesión como administrador y seleccione su tema.

![admin-console](https://github.com/erpcya/docs/assets/134967453/ca96a7ec-f4c6-43cf-9215-9271eca523ec)
