import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Controller {
    private RecordStorage storage = new FileRecordStorage();
    private Scanner scanner = new Scanner(System.in);
    private List<Record> records;

    public Controller() {
        records = storage.loadRecords();
    }

    private void printAllRecords(){
        for (Record record : records) {
            System.out.println(record.toString());
        }
    }

    private void addRecord(){
        System.out.println("Введите ваши планы на сегодня:");
        String note = scanner.nextLine().trim();
        Record record = new Record(note, LocalDate.now());
        records.add(record);
        storage.saveRecords(record);
    }

    private void findRecordsByDate(){
        System.out.println("Введите дату в формате yyyy-MM-dd:");
        String dateString = scanner.nextLine().trim();
        try {
            LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            List<Record> foundRecords = records.stream()
                    .filter(record -> record.getDate().equals(date))
                    .collect(Collectors.toList());

            if (foundRecords.isEmpty()) {
                System.out.println("Записей за " + dateString + " не найдено");
            } else {
                System.out.println("Найденные записи за " + dateString + " :");
                for (Record record : foundRecords) {
                    System.out.println(record.toString());
                }
            }
        }
        catch (Exception e){
            System.out.println("Неверный формат даты");
        }
    }

    public void run(){
        while(true){
            System.out.println("Введите команду: ");
            String command = scanner.nextLine().trim();
            switch (command) {
                case "#read" -> printAllRecords();
                case "#write" -> addRecord();
                case "#find" -> findRecordsByDate();
                case "#exit" -> {
                    return;
                }
                default -> System.out.println("Неверная команда. Попробуйте снова");
            }
        }
    }
}
