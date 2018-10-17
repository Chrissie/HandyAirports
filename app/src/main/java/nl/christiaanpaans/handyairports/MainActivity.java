package nl.christiaanpaans.handyairports;

import android.app.SearchManager;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.fstyle.library.helper.AssetSQLiteOpenHelperFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private class DatabaseAccessor {
        private static final String DATABASE_NAME = "airports.sqlite";
        private AirportsDatabase airportsDatabase;
        private List<Airports> myAirportsClone = new ArrayList<>();
        private AirportsAdapter adapter;

        public DatabaseAccessor(AirportsAdapter adapter) {
            this.adapter = adapter;
            airportsDatabase = buildDatabase();
        }

        private AirportsDatabase buildDatabase() {
            final File dbFile = getApplicationContext().getDatabasePath(DATABASE_NAME);

            if (!dbFile.exists()) {
//            copyDatabaseFile(dbFile.getAbsolutePath());
                Log.e("DB_NOT_FOUND", "Did not find any database, what to do now?");
            }

            return Room.databaseBuilder(getApplicationContext(),
                    AirportsDatabase.class, DATABASE_NAME).
                    openHelperFactory(new AssetSQLiteOpenHelperFactory())
                    .build();
        }

        public void retrieveAirports() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    AirportsInterface access = airportsDatabase.daoAccess();
                    MainActivity.allAirports = (ArrayList<Airports>) access.getAirports();
                    AirportFactory.getInstance().getAirports().clear();
                    AirportFactory.getInstance().getAirports().addAll(MainActivity.allAirports);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }).start();
        }
    }

    private RecyclerView recyclerView;
    private AirportsAdapter adapter;
    public static ArrayList<Airports> allAirports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new AirportsAdapter(this);
        recyclerView = findViewById(R.id.airportsRecycler);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        new DatabaseAccessor(adapter).retrieveAirports();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterAirports(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterAirports(newText);
                return true;
            }
        });
        return true;
    }

    public void filterAirports(String filterString) {
        ArrayList<Airports> airports = AirportFactory.getInstance().getAirports();
        airports.clear();
        
        if(filterString.isEmpty()){
            airports.addAll(allAirports);
        } else{
            filterString = filterString.toLowerCase();
            for(Airports port: allAirports){
                if(port.getName().toLowerCase().contains(filterString)
                        || port.getIcao().toLowerCase().contains(filterString)
                        || port.getMunicipality().toLowerCase().contains((filterString)))
                {
                    airports.add(port);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}