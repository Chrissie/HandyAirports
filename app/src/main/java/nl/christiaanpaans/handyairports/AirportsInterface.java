package nl.christiaanpaans.handyairports;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface AirportsInterface {
   @Query("select * from airports order by name")
    List<Airports> getAirports();

   @Query("select count(*) from airports")
    int countAirports();
}
