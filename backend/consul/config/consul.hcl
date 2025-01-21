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

limits {
  http_max_conns_per_client = 400
}

data_dir = "/opt/consul/data"

ui = true

verify_incoming = false
verify_outgoing = false
verify_server_hostname = false

tls_min_version = "TLSv1_3"
key_file = "/consul/config/itmo-dating-backend.key"
cert_file = "/consul/config/itmo-dating-backend.crt"
ca_file = "/consul/config/itmo-dating-backend-ca.crt"

services = [
  {
    name = "database"
    address = "database"
    port = 5432
    check = {
      id = "database-check"
      name = "PostgreSQL Health Check"
      tcp = "database:5432"
      interval = "10s"
      timeout = "1s"
    }
  },
]
