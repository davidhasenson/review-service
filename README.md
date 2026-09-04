# Review Service

Del av ett mikrotjänstsystem (3 tjänster totalt) för ett bokningssystem. Den här tjänsten äger **recensioner** av rum.

## Vad tjänsten gör

- Skapa, hämta och ta bort recensioner (`/api/reviews`).
- En recension är knuten till ett rum via `roomId` och innehåller recensentens namn, betyg (`rating`), fritext (`reviewText`) och datum (`reviewDate`).
- Hämta alla recensioner för ett specifikt rum (`GET /api/reviews/room/{id}`).
- Till skillnad från `customer-service` och `pensionat-app` gör tjänsten **inga anrop till de andra tjänsterna** — den validerar t.ex. inte att `roomId` faktiskt existerar i `pensionat-app`, utan lagrar bara ID:t som en referens.

### Endpoints i korthet

| Metod | Path | Beskrivning | Kräver JWT |
|---|---|---|---|
| GET | `/api/reviews/{id}` | Hämta en recension | Nej |
| GET | `/api/reviews/room/{id}` | Hämta alla recensioner för ett rum | Nej |
| POST | `/api/reviews` | Skapa en recension | Ja |
| DELETE | `/api/reviews/{id}` | Ta bort en recension | Ja |

## Hur tjänsten pratar med andra

- **JWT**: Precis som `pensionat-app` litar tjänsten på samma delade `JWT_SECRET` som `customer-service` genererar tokens med. `JwtFilter` läser `Authorization: Bearer <token>`-headern och sätter en autentiserad användare i security-kontexten om token är giltig — ingen ytterligare koll görs mot `customer-service`.
- Tjänsten anropas **inte** av och anropar **inte** själv någon av de andra tjänsterna via REST. Kopplingen till resten av systemet är enbart via delad autentisering (JWT) och den logiska referensen `roomId` som pekar mot ett rum i `pensionat-app`.

## Konfiguration (miljövariabler)

```
DB_URL=jdbc:mysql://localhost:3306/review_db
DB_USERNAME=root
DB_PASSWORD=<ditt-db-lösenord>
JWT_SECRET=<samma hemlighet som i customer-service och pensionat-app>
```

Tjänsten lyssnar på port **8082** (`server.port=8082`).

> **Obs:** `JWT_SECRET` måste vara identisk i alla tre tjänster.

## Starta tjänsten

> Precis som för `pensionat-app` saknas en `Dockerfile`/`docker-compose.yml` bland filerna som delats för det här repot. Säg till om du vill ha ett förslag i samma stil som `customer-service`s multi-stage build.

### Lokalt utan Docker

Kräver Java 17, Maven och en lokal MySQL-instans.

```bash
./mvnw spring-boot:run
```

Se till att `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` och `JWT_SECRET` finns tillgängliga, t.ex. via en `.env`-fil (läses automatiskt med `dotenv-java`).

## Starta hela systemet

För att köra alla tre tjänster tillsammans behövs en gemensam `docker-compose.yml` på systemnivå. Se README för `customer-service` för hur `customer-service` och `pensionat-app` kopplas ihop; `review-service` läggs till på samma sätt men behöver bara:

- En egen MySQL-databas för recensioner.
- Samma `JWT_SECRET` som de andra två tjänsterna.
- Ingen `BASE_URL` till någon annan tjänst, eftersom den inte gör några utgående anrop.

## Teknisk stack

- Java 17, Spring Boot (Web, Data JPA, Security, Validation)
- MySQL 8
- JWT (jjwt) för autentisering
