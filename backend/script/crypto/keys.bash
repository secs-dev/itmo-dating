set -e

cd "$(dirname "$0")" || exit

MODE="$1"
ENV="$2"

ALIAS="itmo-dating"
ALIAS_BACKEND="$ALIAS-backend"
ALIAS_EXTERNAL="$ALIAS-external"

VALIDITY=1
PASSWORD="$ITMO_DATING_KEYSTORE_PASSWORD"

INTERNAL_INSTALL_PATH="src/main/resources/keystore"
STARTER_TLS_INSTALL_PATH="starter-tls/$INTERNAL_INSTALL_PATH"
STARTER_SERVICE_DISCOVERY_INSTALL_PATH="starter-service-discovery/$INTERNAL_INSTALL_PATH"
GATEWAY_INSTALL_PATH="gateway/$INTERNAL_INSTALL_PATH"
CONSUL_INSTALL_PATH="consul/config"
VAULT_INSTALL_PATH="vault/config"

function generate() {
  echo "Phase: Generate"

  echo "Generating the private key for CA certificate..."
  openssl genpkey \
    -algorithm RSA \
    -out "$ALIAS_BACKEND-ca.key" 2> /dev/null

  echo "Generating the self-signed CA certificate..."
  openssl req -x509 -new \
    -nodes \
    -days "$VALIDITY" \
    -config ca.cnf \
    -key "$ALIAS_BACKEND-ca.key" \
    -out "$ALIAS_BACKEND-ca.crt"

  echo "Generating the private key for services..."
  openssl genpkey \
    -algorithm RSA \
    -out "$ALIAS_BACKEND.key" 2> /dev/null

  echo "Generating the Certificate Signing Request (CSR)..."
  openssl req -new \
    -config csr.cnf \
    -key "$ALIAS_BACKEND.key" \
    -out "$ALIAS_BACKEND.csr"

  echo "Signing the CSR with self-signed CA to create a certificate..."
  openssl x509 -req \
    -sha256 \
    -days "$VALIDITY" \
    -extfile csr.cnf -extensions req_ext \
    -CAcreateserial \
    -CA "$ALIAS_BACKEND-ca.crt" \
    -CAkey "$ALIAS_BACKEND-ca.key" \
    -in "$ALIAS_BACKEND.csr" \
    -out "$ALIAS_BACKEND.crt"

  echo "Packaging keys and certificates..."
  openssl pkcs12 -export \
    -password pass:"$PASSWORD" \
    -inkey "$ALIAS_BACKEND.key" \
    -in "$ALIAS_BACKEND.crt" \
    -certfile "$ALIAS_BACKEND-ca.crt" \
    -out "$ALIAS_BACKEND.p12"

  echo "Converting PKCS12 to JKS..."
  keytool -importkeystore \
    -srcstoretype PKCS12 \
    -srckeystore "$ALIAS_BACKEND.p12" \
    -srcstorepass "$PASSWORD" \
    -deststoretype JKS \
    -destkeystore "$ALIAS_BACKEND.jks" \
    -deststorepass "$PASSWORD"

  echo "Copying PKCS12 as external certificate"
  cp "$ALIAS_BACKEND.p12" "$ALIAS_EXTERNAL.p12"
}

function copy() {
  DIR="$1"
  FILE="$2"

  mkdir -p "../../$DIR"
  cp "$FILE" "../../$DIR/$FILE"
}

function distribute() {
  echo "Phase: Distribute"

  echo "Copying package to the starter-tls..."
  copy "$STARTER_TLS_INSTALL_PATH" "$ALIAS_BACKEND.p12"

  echo "Copying package to the starter-service-discovery..."
  copy "$STARTER_SERVICE_DISCOVERY_INSTALL_PATH" "$ALIAS_BACKEND.jks"

  echo "Copying package to the gateway..."
  copy "$GATEWAY_INSTALL_PATH" "$ALIAS_EXTERNAL.p12"

  echo "Copying keys to the consul..."
  copy "$CONSUL_INSTALL_PATH" "$ALIAS_BACKEND.key"
  copy "$CONSUL_INSTALL_PATH" "$ALIAS_BACKEND.crt"
  copy "$CONSUL_INSTALL_PATH" "$ALIAS_BACKEND-ca.crt"

  echo "Copying keys to the vault..."
  copy "$VAULT_INSTALL_PATH" "$ALIAS_BACKEND.key"
  copy "$VAULT_INSTALL_PATH" "$ALIAS_BACKEND.crt"
  copy "$VAULT_INSTALL_PATH" "$ALIAS_BACKEND-ca.crt"
}

function remove() {
  DIR="$1"
  FILE="$2"

  rm -rf "../../$DIR/$FILE"
  rm -rf "$FILE"
}

function clear() {
  echo "Phase: Clear"

  echo "Removing package from the starter-tls..."
  remove "$STARTER_TLS_INSTALL_PATH" "$ALIAS_BACKEND.p12"

  echo "Removing package from the starter-service-discovery..."
  remove "$STARTER_SERVICE_DISCOVERY_INSTALL_PATH" "$ALIAS_BACKEND.jks"

  echo "Removing package from the gateway..."
  remove "$GATEWAY_INSTALL_PATH" "$ALIAS_EXTERNAL.p12"

  echo "Removing keys from the consul..."
  remove "$CONSUL_INSTALL_PATH" "$ALIAS_BACKEND.key"
  remove "$CONSUL_INSTALL_PATH" "$ALIAS_BACKEND.crt"
  remove "$CONSUL_INSTALL_PATH" "$ALIAS_BACKEND-ca.crt"

  echo "Removing keys from the vault..."
  remove "$VAULT_INSTALL_PATH" "$ALIAS_BACKEND.key"
  remove "$VAULT_INSTALL_PATH" "$ALIAS_BACKEND.crt"
  remove "$VAULT_INSTALL_PATH" "$ALIAS_BACKEND-ca.crt"

  echo "Removing local outputs..."
  rm -rf "$ALIAS_BACKEND.crt"
  rm -rf "$ALIAS_BACKEND.csr"
  rm -rf "$ALIAS_BACKEND.key"
  rm -rf "$ALIAS_BACKEND.p12"
  rm -rf "$ALIAS_BACKEND-ca.key"
  rm -rf "$ALIAS_BACKEND-ca.crt"
  rm -rf "$ALIAS_BACKEND-ca.srl"
}

if [ "$ENV" = "test" ]; then
  PASSWORD="testing-keystore-password"
fi

if [ "$MODE" = "generate" ]; then
  generate
  distribute
elif [ "$MODE" = "clean" ]; then
  clear
else
  echo "Error: Invalid argument '$MODE'."
  echo "Usage: $0 <generate|clean>"
  exit 1
fi
