import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class RecordLogic {
    private final RecordStorage storage = new FileRecordStorage();
    private final Scanner scanner = new Scanner(System.in);
    private final List<RecordModel> records;

    public RecordLogic(){
        records = storage.loadRecords();
    }

    public void printAllRecords(){
        for (RecordModel record : records) {
            System.out.println(record.toString());
        }
    }

    public void addRecord(){
        System.out.println("Введите ваши планы на сегодня:");
        String note = scanner.nextLine().trim();
        RecordModel record = new RecordModel(note, LocalDate.now());
        records.add(record);
        storage.saveRecords(record);
    }

    public void findRecordsByDate(){
        System.out.println("Введите дату в формате yyyy-MM-dd:");
        String dateString = scanner.nextLine().trim();
        try {
            LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            List<RecordModel> foundRecords = records.stream()
                    .filter(record -> record.getDate().equals(date))
                    .toList();

            if (foundRecords.isEmpty()) {
                System.out.println("Записей за " + dateString + " не найдено");
            } else {
                System.out.println("Найденные записи за " + dateString + " :");
                for (RecordModel record : foundRecords) {
                    System.out.println(record.toString());
                }
            }
        }
        catch (Exception e){
            System.out.println("Неверный формат даты");
        }
    }
}
