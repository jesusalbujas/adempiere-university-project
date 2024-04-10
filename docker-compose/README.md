# Requerimientos

- [Docker Compose v2.16.0 or later](https://docs.docker.com/compose/install/linux/)

```Shell
docker compose version
Docker Compose version v2.16.0
```

## Información Adicional

Este servicio simplemente expone el puerto `80` y usted debe solicitarlo usando `api.adempiere.io` (para Linux simplemente agregue este dominio a `/etc/hosts`).

El servicio principal que responde a todas las solicitudes es un "nginx".

```bash
GNU nano 4.8 /etc/hosts
127.0.0.1 servidor local
127.0.1.1 customserver
<Tu-IP-aquí> api.adempiere.io
```

## Definición para levantar ZK y DB en diferentes Compose

- Ejecuta para construir y levantar el servicio de Base de Datos:

```bash
docker compose -f docker-compose-db.yml up --build -d
```

- Ejecuta para levantar el servicio de Aplicaciones:

```bash
docker compose -f docker-compose-zk.yml up
```

- Detener y eliminar contenedores y red.

```bash
docker compose -f docker-compose-db.yml -f docker-compose-zk.yml down
```
