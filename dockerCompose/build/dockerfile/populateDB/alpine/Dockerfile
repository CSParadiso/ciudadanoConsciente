FROM postgres:16

# Copy the SQL dump into the container's PostgreSQL data directory
COPY backup.sql /var/lib/postgresql/data/

# Change permissions on the SQL dump to ensure it's accessible by PostgreSQL
RUN chown postgres:postgres /var/lib/postgresql/data/backup.sql
RUN chmod 755 /var/lib/postgresql/data/backup.sql

# Set environment variables for PostgreSQL user and password
ENV POSTGRES_USER=saimon
ENV POSTGRES_PASSWORD=citizen
ENV PGDATA=/var/lib/postgresql/data

