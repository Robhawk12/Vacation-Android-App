package d308.robertjohnson.vacationplanner.data;

import androidx.room.Database;

import d308.robertjohnson.vacationplanner.entities.Excursion;
import d308.robertjohnson.vacationplanner.entities.Vacation;

@Database(entities = {Vacation.class, Excursion.class},version = 1,exportSchema = false)
public class vacationDBBuilder {
}
