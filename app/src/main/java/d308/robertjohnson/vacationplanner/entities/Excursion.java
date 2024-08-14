package d308.robertjohnson.vacationplanner.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "excursions")
public class Excursion {

    @PrimaryKey(autoGenerate = true)
    private int excursionID;
    private String exursionName;
    private int vacationID;

    public Excursion(int excursionID, String exursionName, int productID) {
        this.excursionID = excursionID;
        this.exursionName = exursionName;
        this.vacationID = productID;
    }

    public int getExcursionID() {
        return excursionID;
    }

    public void setExcursionID(int excursionID) {
        this.excursionID = excursionID;
    }

    public String getExursionName() {
        return exursionName;
    }

    public void setExursionName(String exursionName) {
        this.exursionName = exursionName;
    }

    public int getProductID() {
        return vacationID;
    }

    public void setProductID(int vacationID) {
        this.vacationID = vacationID;
    }
}
