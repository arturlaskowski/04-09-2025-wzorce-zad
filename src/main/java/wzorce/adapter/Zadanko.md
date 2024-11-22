# System Obsługi Płatności

## Opis

Aktualnie system jest w fazie przejściowej.  
Istnieje implementacja starszej bramki płatności zwanej **legacy** oraz implementacja nowszej bramki płatności zwanej **new**.

- **Starsza bramka płatności** - obsługuje tylko płatności w PLN.
- **Nowa bramka płatności** - obsługuje płatności w wielu walutach.

System ma chwilowo wspierać oba te rozwiązania, a konfiguracja ma odbywać się za pomocą parametru `payment.system`.

W systemie istnieje klasa [LegacyPaymentService](LegacyPaymentService.java), która nie może być modyfikowana z powodu zależności od innych części systemu, które nie są utrzymywane przez nasz zespół.
Dodatkowo, nie chcemy, aby nowa struktura aplikacji miała jakąkolwiek wiedzę o części Legacy.
W związku z tym zależności do klas Legacy nie powinny występować w [NewPaymentService](NewPaymentService.java) ani w [PaymentController](PaymentController.java) .

## Zadanie

### Implementacja adaptera

Twoim zadaniem jest zaimplementowanie klasy [LegacyPaymentAdapter](LegacyPaymentAdapter.java), tak aby umożliwiła integrację starszej bramki płatności z nową strukturą systemu.
Jeśli w żądaniu wystąpi próba przeprowadzenia transakcji w innej walucie niż PLN dla systemu legacy, powinien zostać rzucony wyjątek [UnsupportedCurrencyException](UnsupportedCurrencyException.java).


### Warunki akceptacji

Implementacja będzie uznana za prawidłową, jeśli testy akceptacyjne będą zielone:
- [LegacyPaymentTest](../../../../test/java/wzorce/adapter/LegacyPaymentTest.java)
- [NewPaymentTest](../../../../test/java/wzorce/adapter/NewPaymentTest.java)
- [RelationshipBetweenClassesTests](../../../../test/java/wzorce/adapter/RelationshipBetweenClassesTests.java)

### Info
- Bean [NewPaymentService](NewPaymentService.java) zostanie utworzony, jeśli parametr `payment.system=new`.
- Bean [LegacyPaymentAdapter](LegacyPaymentAdapter.java) zostanie utworzony, jeśli parametr `payment.system=legacy`.
- Nie musisz nic zmieniać w konfiguracji parametru – jest już to skonfigurowane za pomocą adnotacji `@ConditionalOnProperty` i sprawdzana przez testy.