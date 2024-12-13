# ITMO Dating backend

## Build & Run

For building and testing a project check the [GitHub Workflow](../.github/workflows/gradle.yml).

To run the backend you need to make Spring Boot jars and run docker compose, but before do not forget to prepare environment variables and secret keys.

```bash
source config/env/local.sh
bash config/crypto/keys.bash generate
gradle bootJar
docker compose up --build
```

To connect to database you can enter the database container and login into `psql`. For example,

```bash
docker exec -it itmo-dating-matchmaker-database-1 bash
psql -h localhost -p 5432 -d $POSTGRES_DB -U $POSTGRES_USER
```
