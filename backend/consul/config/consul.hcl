server = true
bind_addr = "0.0.0.0"
client_addr = "0.0.0.0"

ports {
  http = 8500
  https = 8501
  grpc = -1
}

acl {
  enabled = true
  default_policy = "allow"
  enable_token_persistence = true
}

data_dir = "/opt/consul/data"

ui = true

verify_incoming = true
verify_outgoing = true
verify_server_hostname = true
tls_min_version = "TLSv1_3"
key_file = "/consul/config/itmo-dating-backend.key"
cert_file = "/consul/config/itmo-dating-backend.crt"
ca_file = "/consul/config/itmo-dating-backend-ca.crt"

services = [
  {
    name = "authik-database"
    address = "authik-database"
    port = 5432
    check = {
      id = "authik-database-check"
      name = "Authik PostgreSQL Health Check"
      tcp = "authik-database:5432"
      interval = "10s"
      timeout = "1s"
    }
  },
  {
    name = "matchmaker-database"
    address = "matchmaker-database"
    port = 5432
    check = {
      id = "matchmaker-database-check"
      name = "Matchmaker PostgreSQL Health Check"
      tcp = "matchmaker-database:5432"
      interval = "10s"
      timeout = "1s"
    }
  },
  {
    name = "people-database"
    address = "people-database"
    port = 5432
    check = {
      id = "people-database-check"
      name = "People PostgreSQL Health Check"
      tcp = "people-database:5432"
      interval = "10s"
      timeout = "1s"
    }
  },
]
