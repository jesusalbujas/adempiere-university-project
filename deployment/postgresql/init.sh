#!/bin/bash
createdb -U adempiere keycloak
createdb -U adempiere adempiere_test
pg_restore -U adempiere -d adempiere < /tmp/seed.backup -v
pg_restore -U adempiere -d adempiere_test < /tmp/seed.backup -v
pg_restore -U adempiere -d keycloak < /tmp/keycloak.backup -v


AFTER_RUN_DIR="/tmp/after_run"
if [ -d "$AFTER_RUN_DIR" ]; then
    find "$AFTER_RUN_DIR" -maxdepth 1 -type f -name '*.sql' -print0 | while IFS= read -r -d '' file; do
        echo "importing $file"
        psql -U postgres < "$file"
    done
fi
