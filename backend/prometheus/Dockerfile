FROM prom/prometheus:latest

COPY ./prometheus.yml /etc/prometheus/prometheus.yml

COPY ./vault.token.txt /etc/prometheus/vault.token.txt

CMD [ \
    "--config.file=/etc/prometheus/prometheus.yml", \
    "--storage.tsdb.retention.size=500MB", \
    "--storage.tsdb.retention.time=3d" \
]
