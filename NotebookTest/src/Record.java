import java.time.LocalDate;

public class Record {
    private String note;
    private LocalDate date;

    public Record(String note, LocalDate date){
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
