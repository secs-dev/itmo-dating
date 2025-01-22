node_name = "consul-singleton"
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
  http_max_conns_per_client = 2000
}

data_dir = "/opt/consul/data"

tls {
  defaults {
    ca_file = "/consul/config/itmo-dating-backend-ca.crt"
    cert_file = "/consul/config/itmo-dating-backend.crt"
    key_file = "/consul/config/itmo-dating-backend.key"
    tls_min_version = "TLSv1_3"
    verify_incoming = false
    verify_outgoing = false
    verify_server_hostname = false
  }
}

ui_config {
  enabled = true
}

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
