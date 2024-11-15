set -e

cd "$(dirname "$0")" || exit

MODE="$1"
ENV="$2"

ALIAS="itmo-dating"
KEYSTORE="keystore.p12"
INSTALL_PATH="foundation/src/main/resources/keystore"
PASSWORD="$ITMO_DATING_KEY_STORE_PASSWORD"

function copy() {
  cp "$1" "../../$INSTALL_PATH/$1"
}

function remove() {
  rm -f "$1" "../../$INSTALL_PATH/$1"
}

function generate() {
  keytool \
    -genkeypair \
    -alias      "$ALIAS" \
    -keyalg     RSA \
    -keysize    4096 \
    -validity   1 \
    -dname      "CN=localhost" \
    -keypass    "$PASSWORD" \
    -keystore   "$KEYSTORE" \
    -storeType  PKCS12 \
    -storepass  "$PASSWORD"

  openssl pkcs12 -in "$KEYSTORE" -nocerts -out "$ALIAS-private.pem"
  openssl pkcs12 -in "$KEYSTORE" -nokeys  -out "$ALIAS-public.pem"

  copy "$KEYSTORE"
  copy "$ALIAS-private.pem"
  copy "$ALIAS-public.pem"
}

function clear() {
  remove "$KEYSTORE"
  remove "$ALIAS-private.pem"
  remove "$ALIAS-public.pem"
}

if [ "$ENV" = "test" ]; then
  PASSWORD="testing-keystore-password"
fi

if [ "$MODE" = "generate" ]; then
  generate
elif [ "$MODE" = "clear" ]; then
  clear
else
  echo "Error: Invalid argument '$MODE'."
  echo "Usage: $0 <generate|clear>"
  exit 1
fi
