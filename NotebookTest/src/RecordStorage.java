import java.util.List;

public interface RecordStorage {
    List<RecordModel> loadRecords();
    void saveRecords(RecordModel record);
}
