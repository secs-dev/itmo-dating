FROM hashicorp/vault

COPY ./config/itmo-dating-backend-ca.crt    /vault/config/itmo-dating-backend-ca.crt
COPY ./config/itmo-dating-backend.crt       /vault/config/itmo-dating-backend.crt
COPY ./config/itmo-dating-backend.key       /vault/config/itmo-dating-backend.key
COPY ./config/vault.hcl                     /vault/config/vault.hcl

COPY ./run.sh /vault/run.sh

RUN chmod -R 755 /vault/config && \
    chmod 644 /vault/config/itmo-dating-backend-ca.crt && \
    chmod 644 /vault/config/itmo-dating-backend.crt && \
    chmod 644 /vault/config/itmo-dating-backend.key && \
    chmod 644 /vault/config/vault.hcl

RUN mkdir /vault/data

CMD ["sh", "./vault/run.sh"]