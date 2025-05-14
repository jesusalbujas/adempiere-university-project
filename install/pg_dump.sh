#!/bin/bash

docker exec -it project-two.postgresql.service pg_dump -U adempiere -Fc -d adempiere > /tmp/adempiere.backup -v
echo "Respaldo realizado"

cp /tmp/adempiere.backup /opt/Development/github/postgres-university/postgresql/seed.backup
echo "Copia realizada"