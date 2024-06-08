import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Controller {
    private RecordStorage storage = new FileRecordStorage();
    private Scanner scanner = new Scanner(System.in);
    private List<Record> records;

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

    public void run(){
        while(true){
            records = storage.loadRecords();
            System.out.println("Введите команду: ");
            String command = scanner.nextLine().trim();
            if (command.equals("#read")) {
                printAllRecords();
            } else if (command.equals("#write")) {
                addRecord();
            } else if (command.equals("#exit")) {
                return;
            } else {
                System.out.println("Неверная команда. Попробуйте снова");
            }
        }
    }
}
