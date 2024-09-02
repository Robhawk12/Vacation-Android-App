package demo.robertjohnson.livedatavacation.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity(tableName = "vacations")
public class Vacation {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String hotel;
    private String startDate;
    private String endDate;

    public Vacation(int id, String name, String hotel, String startDate, String endDate) {
        this.id = id;
        this.name = name;
        this.hotel = hotel;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Vacation() {
    }
}
