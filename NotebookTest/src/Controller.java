import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Controller {
    private final Scanner scanner = new Scanner(System.in);
    private final RecordLogic logic = new RecordLogic();

    public void run(){
        while(true){
            System.out.println("Введите команду: ");
            String command = scanner.nextLine().trim();
            switch (command) {
                case "#read" -> logic.printAllRecords();
                case "#write" -> logic.addRecord();
                case "#find" -> logic.findRecordsByDate();
                case "#exit" -> {
                    return;
                }
                default -> System.out.println("Неверная команда. Попробуйте снова");
            }
        }
    }
}
