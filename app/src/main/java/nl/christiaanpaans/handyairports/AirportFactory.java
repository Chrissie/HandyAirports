package nl.christiaanpaans.handyairports;

import java.util.ArrayList;

public class AirportFactory {

    private static final String DATABASE_NAME = "airports.sqlite";
    private AirportsDatabase airportsDatabase;

    private ArrayList<Airports> airports = new ArrayList<Airports>();
    private static AirportFactory instance = null;

    private AirportFactory() {

    }

    public static AirportFactory getInstance() {
        if( instance == null ) {
            instance = new AirportFactory();
        }
        return instance;
    }

    public ArrayList<Airports> getAirports() {
        return airports;
    }
}