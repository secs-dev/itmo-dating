cd "$(dirname "$0")"
export $(cat .env | xargs)
