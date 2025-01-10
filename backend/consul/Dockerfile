FROM hashicorp/consul

COPY ./config/itmo-dating-backend.key /consul/config/itmo-dating-backend.key
COPY ./config/itmo-dating-backend.crt /consul/config/itmo-dating-backend.crt
COPY ./config/itmo-dating-backend-ca.crt /consul/config/itmo-dating-backend-ca.crt
COPY ./config/consul.hcl /consul/config/consul.hcl

COPY ./run.sh /consul/run.sh

RUN chmod -R 755 /consul/config && \
    chmod 644 /consul/config/itmo-dating-backend.key && \
    chmod 644 /consul/config/itmo-dating-backend.crt && \
    chmod 644 /consul/config/itmo-dating-backend-ca.crt && \
    chmod 644 /consul/config/consul.hcl
