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

## Zadanie 2
W naszej branży wzorce projektowe często ewoluują, przyjmując nowe formy.
Tak jest również z wzorcem Observer, który może być rozwinięty do podejścia Publish-Subscribe.
W tym podejściu Publisher nie informuje bezpośrednio odbiorców, lecz wysyła wiadomość do message brokera,
a z niego wiadomość jest pobierana przez zainteresowanych subskrybentów.
To podejście umożliwia jeszcze większe rozluźnienie powiązań między nadawcą a odbiorcą i często jest wykorzystywane,
na przykład w architekturze rozproszonej.
Aby zaimplementować to podejście, skorzystamy nie z message brokera,
ale z pamięci aplikacji i gotowego mechanizmu dostarczanego przez Spring, jakim jest `@EventListener`.
Pamiętajmy, że domyślnie działa on w komunikacji synchronicznej.
Przechodząc na komunikację asynchroniczną, bezpieczniej jest korzystać z mechanizmu, który fizycznie przechowuje wiadomości, a nie trzyma ich w cache.

Twoim zadaniem jest przejście od wzorca Observer do podejścia Publish-Subscribe, czyli nadawca wiadomości nie ma bezpośredniego powiązania z odbiorcami.
Zaimplementuj to, wykorzystując mechanizm `ApplicationEventPublisher` i `@EventListener` od Spring.

### Warunki akceptacji
Jeśli zaimplementujesz to zadanie prawidłowo, testy będą zielone:
- [ProjectServiceTest](../../../../test/java/wzorce/observer/ProjectServiceTest.java)