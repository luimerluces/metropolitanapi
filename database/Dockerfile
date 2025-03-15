FROM postgres:17-alpine

ENV POSTGRES_USER admin
ENV POSTGRES_PASSWORD admin
ENV POSTGRES_DB metropolitan_db

COPY database/metropolitan_data.sql  /docker-entrypoint-initdb.d/
