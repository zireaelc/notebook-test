import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.crypto.SecretKey;

public class FileRecordStorage implements RecordStorage{
    private static final String FILE_NAME = "file.txt";
    private static final String KEY_FILE = "key.txt";
    private SecretKey secretKey;

    public FileRecordStorage() {
        try {
            File keyFile = new File(KEY_FILE);
            if (!keyFile.exists()) {
                this.secretKey = Encryption.generateKey();
                try (FileWriter keyWriter = new FileWriter(KEY_FILE)) {
                    keyWriter.write(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
                }
            } else {
                try (BufferedReader keyReader = new BufferedReader(new FileReader(keyFile))) {
                    String keyString = keyReader.readLine();
                    this.secretKey = Encryption.getKeyFromString(keyString);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при генерации или загрузке ключа: " + e.getMessage(), e);
        }
    }

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
                String decryptedLine = Encryption.decrypt(line, secretKey);
                String[] notes = decryptedLine.split(" ", 2);
                LocalDate date = LocalDate.parse(notes[0]);
                String note = notes[1];
                recordList.add(new Record(note, date));
            }
            bufferedReader.close();
        }
        catch (Exception e){
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
            String data = record.getDate().toString() + " " + record.getNote();
            String encryptedData = Encryption.encrypt(data, secretKey);
            bufferedWriter.write(encryptedData);
            bufferedWriter.newLine();
            bufferedWriter.close();
        }
        catch (Exception e){
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }
}
