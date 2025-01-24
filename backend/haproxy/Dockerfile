FROM haproxy:latest

COPY ./config/itmo-dating-backend.pem       /usr/local/etc/haproxy/itmo-dating-backend.pem
COPY ./config/itmo-dating-backend-ca.crt    /usr/local/etc/haproxy/itmo-dating-backend-ca.crt

COPY ./config/haproxy.cfg /usr/local/etc/haproxy/haproxy.cfg
