package demo.robertjohnson.livedatavacation.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
@Entity(tableName = "excursions")
public class Excursion {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String date;
    private int vacaID;

    public Excursion(int id, String name, String date, int vacaID) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.vacaID = vacaID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getVacaID() {
        return vacaID;
    }

    public void setVacaID(int vacaID) {
        this.vacaID = vacaID;
    }
}
