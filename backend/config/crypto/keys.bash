set -e

cd "$(dirname "$0")" || exit

MODE="$1"
ENV="$2"

ALIAS="itmo-dating"
ALIAS_BACKEND="$ALIAS-backend"
KEYSTORE="keystore.p12"
BACKEND_INSTALL_PATH="foundation/src/main/resources/keystore"
GATEWAY_INSTALL_PATH="gateway/src/main/resources/keystore"
PASSWORD="$ITMO_DATING_KEY_STORE_PASSWORD"

function copy() {
  mkdir -p "../../$BACKEND_INSTALL_PATH"
  mkdir -p "../../$GATEWAY_INSTALL_PATH"
  cp "$1" "../../$BACKEND_INSTALL_PATH/$1"
  cp "$1" "../../$GATEWAY_INSTALL_PATH/$1"
}

function remove() {
  rm -f "$1" "../../$BACKEND_INSTALL_PATH/$1" "../../$GATEWAY_INSTALL_PATH/$1"
}

function generate() {
  echo "Generating the backend key pair keystore..."
  keytool \
    -genkeypair \
    -alias      "$ALIAS" \
    -keyalg     RSA \
    -keysize    4096 \
    -validity   1 \
    -dname      "CN=localhost" \
    -ext        "san=dns:localhost,dns:authik,dns:matchmaker,dns:people" \
    -keypass    "$PASSWORD" \
    -keystore   "$KEYSTORE" \
    -storeType  PKCS12 \
    -storepass  "$PASSWORD"

  echo "Exporting the backend private key..."
  openssl pkcs12 -in "$KEYSTORE" -nocerts -out "$ALIAS_BACKEND-private.pem" \
    -passin pass:"$PASSWORD" -passout pass:"$PASSWORD"

  echo "Exporting the backend public key..."
  openssl pkcs12 -in "$KEYSTORE" -nokeys  -out "$ALIAS_BACKEND-public.pem"  \
    -passin pass:"$PASSWORD" -passout pass:"$PASSWORD"

  copy "$KEYSTORE"
  copy "$ALIAS_BACKEND-private.pem"
  copy "$ALIAS_BACKEND-public.pem"
}

function clear() {
  remove "$KEYSTORE"
  remove "$ALIAS_BACKEND-private.pem"
  remove "$ALIAS_BACKEND-public.pem"
}

if [ "$ENV" = "test" ]; then
  PASSWORD="testing-keystore-password"
fi

if [ "$MODE" = "generate" ]; then
  generate
elif [ "$MODE" = "clean" ]; then
  clear
else
  echo "Error: Invalid argument '$MODE'."
  echo "Usage: $0 <generate|clean>"
  exit 1
fi
