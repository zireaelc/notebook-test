import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

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

    public void statistics(){
        System.out.println("Статистика использования записной книжки: ");

        System.out.println("Общее количество записей: " + records.size());

        Map<LocalDate, Long> recordsByDate = new HashMap<>();

        for (RecordModel record : records) {
            LocalDate date = record.getDate();
            recordsByDate.put(date, recordsByDate.getOrDefault(date, 0L));
        }
        LocalDate mostActiveDay = null;
        long maxCount = 0;
        for (Map.Entry<LocalDate, Long> entry : recordsByDate.entrySet())
            if (entry.getValue() > maxCount) {
                mostActiveDay = entry.getKey();
                maxCount = entry.getValue();
            }
        if (mostActiveDay != null)
            System.out.println("Самый активный день: " + mostActiveDay +
                    " (" + recordsByDate.get(mostActiveDay) + ")");
        else
            System.out.println("Мало данных для подсчёта самого активного дня");

        if (recordsByDate.size() > 0)
            System.out.println("Среднее количество записей в день: " +
                    (double)records.size() / recordsByDate.size());
        else
            System.out.println("Мало данных для подсчёта среднего количества записей в день");
    }
}
