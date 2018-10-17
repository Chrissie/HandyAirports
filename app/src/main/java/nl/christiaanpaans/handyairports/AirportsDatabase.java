package nl.christiaanpaans.handyairports;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Airports.class}, version = 2)
public abstract class AirportsDatabase extends RoomDatabase {
    public abstract AirportsInterface daoAccess();
}
