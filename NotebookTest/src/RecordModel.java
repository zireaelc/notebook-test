import java.time.LocalDate;

public class RecordModel {
    private String note;
    private LocalDate date;

    public RecordModel(String note, LocalDate date){
        this.note = note;
        this.date = date;
    }

    public String getNote(){
        return note;
    }

    public LocalDate getDate(){
        return date;
    }

    @Override
    public String toString(){
        return date + " " + note;
    }
}
