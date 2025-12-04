# Realm Quest Manager 🎮

Prosty menedżer zadań (questów) dla graczy - aplikacja konsolowa w Javie.

## 🚀 Jak uruchomić aplikację

### Wymagania
- Java 8 lub nowsza
- Maven 3.x

### Kompilacja i uruchomienie

```bash
# Skompiluj projekt
mvn clean compile

# Uruchom aplikację
mvn exec:java
```

Aplikacja uruchomi się w terminalu i będzie gotowa do przyjmowania komend.

## 🎯 Dostępne komendy

| Komenda | Opis |
|---------|------|
| `ADD <tytuł> <nagroda>` | Dodaj nowe zadanie |
| `COMPLETE <id>` | Oznacz zadanie jako ukończone |
| `LIST` | Wyświetl wszystkie zadania |
| `LIST completed` | Wyświetl tylko ukończone zadania |
| `LIST pending` | Wyświetl tylko oczekujące zadania |
| `SAVE` | Zapisz zadania do pliku |
| `EXIT` | Zapisz i wyjdź z aplikacji |

## 💡 Przykładowa sesja

```
> ADD pokonaj smoka 150
Dodano zadanie: id=1, title='pokonaj smoka', rewardCoins=150, completed=false

> ADD nagraj TikToka 50
Dodano zadanie: id=2, title='nagraj TikToka', rewardCoins=50, completed=false

> LIST

=== WSZYSTKIE ZADANIA ===
id=1, title='pokonaj smoka', rewardCoins=150, completed=false
id=2, title='nagraj TikToka', rewardCoins=50, completed=false

> COMPLETE 1
Ukończono zadanie: id=1, title='pokonaj smoka', rewardCoins=150, completed=true

> LIST completed

=== UKOŃCZONE ZADANIA ===
id=1, title='pokonaj smoka', rewardCoins=150, completed=true

> SAVE
Zapisano 2 zadań do pliku quests.json

> EXIT
Zapisywanie i zamykanie aplikacji...
Zapisano 2 zadań do pliku quests.json
Do zobaczenia, bohaterze!
```