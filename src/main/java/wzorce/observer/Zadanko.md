# Zarządzanie projektami
## Opis

Akcja wykonana w domenie projektu może spowodować różne operacje w innych domenach. 
Aktualnie domena projektu musi być świadoma wszystkich tych operacji i je inicjować. 
Jest to problematyczne, ponieważ domeną projektu zarządza inny zespół niż zespoły zarządzające pozostałymi domenami.
W obecnej implementacji zespół zarządzający domeną projektu musi ciągle wprowadzać zmiany na prośby od innych zespołów, aby inicjować operacje w ich domenach.

## Zadanie 1

Aby rozwiązać ten problem, należy zastosować wzorzec Obserwator i odwrócić zależność, 
tak aby inne domeny obserwowały zmian w domenie projektu i mogły same decydować, jaką funkcjonalność mają wykonać.

### Warunki akceptacji
Jeśli prawidłowo zaimplementujesz to zadanie, zachowanie aplikacji się nie zmieni, ale domena projektu nie będzie uzależniona od innych domen.
Jeśli ci się uda, testy będą zielone:
- [ProjectServiceTest](../../../../test/java/wzorce/observer/ProjectServiceTest.java)
