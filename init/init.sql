CREATE EXTENSION IF NOT EXISTS postgis;
CREATE EXTENSION IF NOT EXISTS postgis_topology;
CREATE EXTENSION IF NOT EXISTS fuzzystrmatch;
CREATE EXTENSION IF NOT EXISTS postgis_tiger_geocoder;

CREATE TABLE aoi
(
    id   BIGSERIAL PRIMARY KEY,
    name text,
    area geometry('Polygon', 4326)
);

CREATE TABLE region
(
    id   BIGSERIAL primary key,
    name text,
    area geometry('Polygon', 4326)
);

COMMIT;