import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileRecordStorage implements RecordStorage{
    private static final String FILE_NAME = "file.txt";

    @Override
    public List<Record> loadRecords(){
        List<Record> recordList = new ArrayList<>();
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] notes = line.split(" ", 2);
                LocalDate date = LocalDate.parse(notes[0]);
                String note = notes[1];
                recordList.add(new Record(note, date));
            }
            bufferedReader.close();
        }
        catch (IOException e){
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
        return recordList;
    }

    @Override
    public void saveRecords(Record record){
        try{
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(record.getDate().toString() + " " + record.getNote());
            bufferedWriter.newLine();
            bufferedWriter.close();
        }
        catch (IOException e){
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }
}
