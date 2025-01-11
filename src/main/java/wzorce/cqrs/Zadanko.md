# Zarządzanie Kuponami

System ma za zadanie umożliwiać wykonywanie operacji na kuponach.

## Obecne Funkcjonalności

### Tworzenie Kuponu
- **Endpoint:** `POST /coupons`
- **Opis:** Tworzy nowy kupon.
- **Dane:** `{"nominalValue": "nominał"}`

### Użycie Kuponu
- **Endpoint:** `POST /coupons/{id}/use`
- **Opis:** Umożliwia wykorzystanie kuponu o podanym ID.
- **Parametry:** `id`

### Dezaktywacja Kuponu
- **Endpoint:** `POST /coupons/{id}/deactivate`
- **Opis:** Dezaktywuje kupon o podanym ID.
- **Parametry:** `id`

### Pobieranie Szczegółów Kuponu
- **Endpoint:** `GET /coupons/{couponId}`
- **Opis:** Pobiera szczegóły kuponu o podanym ID.
- **Parametry:** `couponId`

Wszystkie te funkcjonalności są pokryte testami, więc nie musisz ich ręcznie wywoływać.
Jeśli jednak wolisz testować za pomocą Postmana, tu jest kolekcja do zaimportowania: [PostmanCollection](../../../../../src/main/resources/static/cqrs/Coupon.postman_collection.json).
## Zad 1

Zaimplementuj wzorzec architektoniczny CQRS, wykorzystując wzorzec Mediator, tak aby
[CouponController](CouponController.java) tworzył komendy (command) i wysyłał je do mediatora. 
Mediator ma za zadanie delegować te komendy do odpowiednich command handlerów. 
Przykład implementacji znajdziesz w projekcie `wzorce` na branchu `cqrs-command-handler`.





Jeśli Ci się uda testy będą zielone [CouponAcceptanceZad1Test](../../../../test/java/wzorce/cqrs/CouponAcceptanceZad1Test.java).

