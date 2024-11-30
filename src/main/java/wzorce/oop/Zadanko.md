# Zarządzanie Kartami Płatniczymi

System ma za zadanie umożliwiać wykonywanie operacji na kartach płatniczych.
Aplikacja jest już po Proof of Concept (PoC) i przyjęła się dobrze, teraz nadszedł czas na refaktoryzację, aby w przyszłości łatwiej było ją rozwijać.

## Obecne Funkcjonalności

### Tworzenie Karty Płatniczej
- **Endpoint:** `POST /payment-cards`
- **Opis:** Tworzy nową kartę płatniczą.
- **Dane:** `{"cardNumber": "numer_karty", "initialBalance": "początkowe_saldo"}`

### Aktywacja Karty
- **Endpoint:** `POST /payment-cards/{cardId}/activation`
- **Opis:** Aktywuje kartę o podanym ID.
- **Parametry:** `cardId`

### Blokowanie Karty
- **Endpoint:** `POST /payment-cards/{cardId}/block`
- **Opis:** Blokuje kartę o podanym ID.
- **Parametry:** `cardId`

### Wpłata na Kartę
- **Endpoint:** `POST /payment-cards/{cardId}/deposit`
- **Opis:** Dokonuje wpłaty na kartę o podanym ID.
- **Parametry:** `cardId`
- **Dane:** `{"amount": "kwota"}`

### Wypłata z Karty
- **Endpoint:** `POST /payment-cards/{cardId}/withdrawal`
- **Opis:** Dokonuje wypłaty z karty o podanym ID.
- **Parametry:** `cardId`
- **Dane:** `{"amount": "kwota"}`

### Pobieranie Szczegółów Karty
- **Endpoint:** `GET /payment-cards/{cardId}`
- **Opis:** Pobiera szczegóły karty o podanym ID.
- **Parametry:** `cardId`


Wszystkie te funkcjonalności są pokryte testami, więc nie musisz ich ręcznie wywoływać.
Jeśli jednak wolisz testować za pomocą Postmana, tu jest kolekcja do zaimportowania: [PostmanCollection](../../../../../src/main/resources/static/oop/PaymentCard.postman_collection.json).

## Cel

Testy akceptacyjne na najwyższym poziomie (wywołujące REST API) znajdują się w pliku: [PaymentCardAcceptanceTest](../../../../test/java/wzorce/oop/PaymentCardAcceptanceTest.java).

Po refaktoryzacji obserwowalne zachowania aplikacji powinny zostać niezmienne, testy powinny być nadal zielone.
Testy akceptacyjne na najwyższym poziomie gwarantują bezpieczną refaktoryzację kodu.

## Wytyczne Refaktoryzacji

### 1. Separacja logiki biznesowej od procesowej
- **Zadanie:** Uprość serwisy, przenosząc logikę domenową tam, gdzie znajdują się dane. Obiekty domenowe powinny posiadać metody z jasno zdefiniowanymi intencjami, które zmieniają stan obiektu, a nie settery.
- **Testy:** Pamiętaj o napisaniu jednostkowych testów dla logiki domenowej. Testy te powinny działać niezależnie od frameworka Spring.