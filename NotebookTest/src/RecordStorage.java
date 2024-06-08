import java.util.List;

public interface RecordStorage {
    List<Record> loadRecords();
    void saveRecords(Record record);
}
