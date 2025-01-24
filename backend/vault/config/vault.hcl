ui              = true
disable_mlock   = true

listener "tcp" {
    address             = "0.0.0.0:8200"
    cluster_address     = "0.0.0.0:8201"
    tls_client_ca_file  = "/vault/config/itmo-dating-backend-ca.crt"
    tls_cert_file       = "/vault/config/itmo-dating-backend.crt"
    tls_key_file        = "/vault/config/itmo-dating-backend.key"
    tls_min_version     = "tls13"
}

telemetry {
  prometheus_retention_time = "12h"
}

storage "consul" {
  address           = "server.dc1.consul:8501"
  max_parallel      = 32
  path              = "vault/"
  scheme            = "https"
  tls_ca_file       = "/vault/config/itmo-dating-backend-ca.crt"
  tls_cert_file     = "/vault/config/itmo-dating-backend.crt"
  tls_key_file      = "/vault/config/itmo-dating-backend.key"
  tls_min_version   = "tls13"
}
