package dawid;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class RealmQuestManager {
    private static final String FILE_NAME = "quests.json";
    private List<Quest> quests;
    private long nextId;
    private ObjectMapper objectMapper;

    public RealmQuestManager() {
        this.quests = new ArrayList<>();
        this.nextId = 1;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        loadFromFile();
    }

    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("Brak questów, tworzę nową listę");
            return;
        }

        try {
            List<Quest> loadedQuests = objectMapper.readValue(file, new TypeReference<List<Quest>>() {
            });
            if (loadedQuests != null && !loadedQuests.isEmpty()) {
                quests = loadedQuests;
                nextId = quests.stream()
                        .mapToLong(Quest::getId)
                        .max()
                        .orElse(0) + 1;
                System.out.println("Wczytano " + quests.size() + " zadań z pliku.");
            }
        } catch (IOException e) {
            System.out.println("Błąd podczas wczytywania pliku: " + e.getMessage());
        }
    }

    private void addQuest(String title, int rewardCoins) {
        Quest quest = new Quest(nextId++, title, rewardCoins);
        quests.add(quest);
        System.out.println("Dodano zadanie: " + quest);
    }

    private void saveFile() {
        try {
            objectMapper.writeValue(new File(FILE_NAME), quests);
            System.out.println("Zapisano " + quests.size() + " zadań do pliku " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Błąd podczas zapisywania do pliku: " + e.getMessage());
        }
    }

    private void completeQuest(long id) {
        Optional<Quest> questOpt = quests.stream()
                .filter(q -> q.getId() == id)
                .findFirst();

        if (questOpt.isPresent()) {
            Quest quest = questOpt.get();
            if (quest.isCompleted()) {
                System.out.println("Zadanie #" + id + " jest już ukończone!");
            } else {
                quest.setCompleted(true);
                System.out.println("Ukończono zadanie: " + quest);
            }
        } else {
            System.out.println("Błąd: Zadanie o ID " + id + " nie istnieje.");
        }
    }

    private void listQuests(String filter) {
        List<Quest> filteredQuests;
        switch (filter.toLowerCase()) {
            case "completed":
                filteredQuests = quests.stream()
                        .filter(Quest::isCompleted)
                        .collect(Collectors.toList());
                System.out.println("=== UKOŃCZONE ZADANIA ===");
                break;
            case "pending":
                filteredQuests = quests.stream()
                        .filter(q -> !q.isCompleted())
                        .collect(Collectors.toList());
                System.out.println("=== OCZEKUJĄCE ZADANIA ===");
                break;
            default:
                filteredQuests = quests;
                System.out.println("=== WSZYSTKIE ZADANIA ===");
                break;
        }

        if (filteredQuests.isEmpty()) {
            System.out.println("Brak zadań do wyświetlenia.");
        } else {
            filteredQuests.forEach(System.out::println);
        }
        System.out.println();
    }

    private void printHelp() {
        System.out.println("Dostępne komendy:");
        System.out.println("  ADD <tytuł> <nagroda_coins>  - Dodaj nowe zadanie");
        System.out.println("  COMPLETE <id>                - Oznacz zadanie jako ukończone");
        System.out.println("  LIST [completed|pending]     - Wyświetl zadania");
        System.out.println("  SAVE                         - Zapisz zadania do pliku");
        System.out.println("  EXIT                         - Zapisz i wyjdź z aplikacji");
        System.out.println("  HELP                         - Wyświetl tę pomoc\n");
    }

    private void handleCommand(String input) {
        String[] allWords = input.trim().split("\\s+");
        String command = allWords[0].toUpperCase();

        try {
            switch (command) {
                case "ADD":
                    if (allWords.length < 3) {
                        System.out.println("Błąd: Użyj: ADD <tytuł> <nagroda_coins>");
                        break;
                    }
                    String rewardStr = allWords[allWords.length - 1];
                    try {
                        int rewardCoins = Integer.parseInt(rewardStr);
                        if (rewardCoins <= 0) {
                            System.out.println("Błąd: Nagroda musi być większa niż 0.");
                            break;
                        }
                        String title = String.join(" ",
                                Arrays.copyOfRange(allWords, 1, allWords.length - 1)
                        );
                        addQuest(title, rewardCoins);
                    } catch (NumberFormatException e) {
                        System.out.println("Błąd: Nagroda musi być liczbą całkowitą.");
                    }
                    break;
                case "COMPLETE":
                    if (allWords.length < 2) {
                        System.out.println("Błąd: Użyj: COMPLETE <id>");
                        break;
                    }
                    try {
                        long id = Long.parseLong(allWords[1]);
                        completeQuest(id);
                    } catch (NumberFormatException e) {
                        System.out.println("Błąd: ID musi być liczbą.");
                    }
                    break;

                case "LIST":
                    String filter = allWords.length > 1 ? allWords[1] : "all";
                    listQuests(filter);
                    break;

                case "SAVE":
                    saveFile();
                    break;

                case "EXIT":
                    System.out.println("Zapisywanie i zamykanie aplikacji...");
                    saveFile();
                    System.out.println("Do zobaczenia, bohaterze!");
                    System.exit(0);
                    break;
                case "HELP":
                    printHelp();
                    break;
                default:
                    System.out.println("Nieznana komenda: " + command);
                    break;
            }
        } catch (Exception e) {
            System.out.println("Wystąpił błąd: " + e.getMessage());
        }
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("REALM QUEST MANAGER");

        printHelp();

        while (true) {
            System.out.print(">> ");
            String input = scanner.nextLine();
            if (input.trim().isEmpty()) {
                continue;
            }
            handleCommand(input);
        }
    }
}
