import java.util.Scanner;

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
                case "#statistics" -> logic.statistics();
                case "#exit" -> {
                    return;
                }
                case "#help" -> {
                    System.out.println("#read - Получить список всех записей");
                    System.out.println("#write - Создать новую запись");
                    System.out.println("#find - Найти записи за конкретный день");
                    System.out.println("#statistics - Получить сведения по использованию записной книжки");
                    System.out.println("#exit - Выход из программы");
                }
                default -> System.out.println("Неверная команда. Для получения списка команд введите #help");
            }
        }
    }
}
