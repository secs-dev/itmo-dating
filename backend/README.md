# ITMO Dating backend

## Build & Run

For building and testing a project check the [GitHub Workflow](../.github/workflows/gradle.yml).

### TLS Keys

Generate self-signed certificates for the backend.

```bash
export ITMO_DATING_KEYSTORE_PASSWORD="<...>"
bash ./script/crypto/keys.bash generate
```

It will also duplicate backend keys to use at gateway edge.

On production replace `*-external.p12` with widely trusted certificates.

### Building

To build all backend services just do simple:

```bash
gradle bootJar
```

### Running Vault & Consul

Firstly deploy `Consul` and `Vault`. Then initialize the new Vault or unseal it.

```bash
docker compose up --build -d consul vault
```

Create the `itmo-dating` Secret Engine with KV v1 (!!).

There are secrets you need for each service.

#### People

```json
{
  "itmo-dating.auth.jwt.public-key": "<...>",
  "itmo-dating.matchmaker.url": "https://matchmaker/api",
  "itmo-dating.postgres.db": "postgres",
  "itmo-dating.postgres.host": "database-primary.dating.se.ifmo.ru",
  "itmo-dating.postgres.password": "<...>",
  "itmo-dating.postgres.username": "postgres",
  "itmo-dating.s3.bucket.profile-photos": "profile-photos",
  "itmo-dating.s3.host": "object-storage.dating.se.ifmo.ru",
  "itmo-dating.s3.password": "<...>",
  "itmo-dating.s3.port": "9000",
  "itmo-dating.s3.username": "<...>"
}
```

#### Matchmaker

```json
{
  "itmo-dating.auth.jwt.public-key": "<...>",
  "itmo-dating.postgres.db": "postgres",
  "itmo-dating.postgres.host": "database-primary.dating.se.ifmo.ru",
  "itmo-dating.postgres.password": "<...>",
  "itmo-dating.postgres.username": "postgres"
}
```

#### Authik

```bash
{
  "itmo-dating.auth.jwt.duration": "PT2H",
  "itmo-dating.auth.jwt.private-key": "<...>",
  "itmo-dating.auth.jwt.public-key":  "<...>",
  "itmo-dating.postgres.db": "postgres",
  "itmo-dating.postgres.host": "database-primary.dating.se.ifmo.ru",
  "itmo-dating.postgres.password": "<...>",
  "itmo-dating.postgres.username": "postgres",
  "itmo-dating.telegram.bot-token": "<...>"
}
```

### Running the Config Server

When Vault is ready and unsealed, you can start Config Service.

```bash
docker compose up --build -d config
```

### Running other services

```bash
export ITMO_DATING_VAULT_TOKEN="<...>"
docker compose up --build -d
```
