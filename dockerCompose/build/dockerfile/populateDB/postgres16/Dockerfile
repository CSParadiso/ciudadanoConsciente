FROM postgres:13.14-alpine

# Copy the SQL dump into the container
COPY backup.sql /docker-entrypoint-initdb.d/

# Change permissions on the SQL dump to ensure it's accessible by PostgreSQL
RUN chown postgres:postgres /docker-entrypoint-initdb.d/backup.sql
RUN chmod 755 /docker-entrypoint-initdb.d/backup.sql

# Set environment variables for PostgreSQL user and password
ENV POSTGRES_USER=ciudadano
ENV POSTGRES_PASSWORD=citizen
ENV PGDATA=/data

