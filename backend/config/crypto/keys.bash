set -e

cd "$(dirname "$0")" || exit

MODE="$1"
ENV="$2"

ALIAS="itmo-dating"
ALIAS_BACKEND="$ALIAS-backend"
VALIDITY=1
BACKEND_INSTALL_PATH="foundation/src/main/resources/keystore"
#CONSUL_INSTALL_PATH="consul/config"
GATEWAY_INSTALL_PATH="gateway/src/main/resources/keystore"
PASSWORD="$ITMO_DATING_KEY_STORE_PASSWORD"

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
    -CA "$ALIAS_BACKEND-ca.crt" \
    -CAkey "$ALIAS_BACKEND-ca.key" \
    -CAcreateserial \
    -in "$ALIAS_BACKEND.csr" \
    -out "$ALIAS_BACKEND.crt"

  echo "Packaging keys and certificates..."
  openssl pkcs12 -export \
    -password pass:"$PASSWORD" \
    -certfile "$ALIAS_BACKEND-ca.crt" \
    -inkey "$ALIAS_BACKEND.key" \
    -in "$ALIAS_BACKEND.crt" \
    -out "$ALIAS_BACKEND.p12"
}

function copy() {
  DIR="$1"
  FILE="$2"

  mkdir -p "../../$DIR"
  cp "$FILE" "../../$DIR/$FILE"
}

function distribute() {
  echo "Phase: Distribute"

  echo "Copying package to the backend..."
  copy "$BACKEND_INSTALL_PATH" "$ALIAS_BACKEND.p12"

  echo "Copying package to the gateway..."
  copy "$GATEWAY_INSTALL_PATH" "$ALIAS_BACKEND.p12"
}

function remove() {
  DIR="$1"
  FILE="$2"

  rm -rf "../../$DIR/$FILE"
  rm -rf "$FILE"
}

function clear() {
  echo "Phase: Clear"

  echo "Removing package from the backend..."
  remove "$BACKEND_INSTALL_PATH" "$ALIAS_BACKEND.p12"

  echo "Removing package from the gateway..."
  remove "$GATEWAY_INSTALL_PATH" "$ALIAS_BACKEND.p12"

  rm -rf "$ALIAS_BACKEND-ca.key"
  rm -rf "$ALIAS_BACKEND-ca.crt"
  rm -rf "$ALIAS_BACKEND.key"
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
