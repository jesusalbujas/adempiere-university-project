#!/bin/bash

# Creacion de la BD Keycloak
createdb -U adempiere keycloak

# Restaurar BD principal
pg_restore -U adempiere -d adempiere < /tmp/seed.backup -v

echo "BD Principal Restaurada"

sleep 5
# Restaurar BD de Keycloak
pg_restore -U adempiere -d keycloak < /tmp/keycloak.backup -v
echo "BD keycloak restaurada"

sleep 5

createdb -U adempiere adempiere_test

pg_restore -U adempiere -d adempiere_test < /tmp/seed.backup -v

# Aplicar tuneo a la bd
AFTER_RUN_DIR="/tmp/after_run"
if [ -d "$AFTER_RUN_DIR" ]; then
    find "$AFTER_RUN_DIR" -maxdepth 1 -type f -name '*.sql' -print0 | while IFS= read -r -d '' file; do
        echo "importing $file"
        psql -U postgres < "$file"
    done
fi
