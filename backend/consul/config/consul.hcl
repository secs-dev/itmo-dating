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
