package nl.christiaanpaans.handyairports;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.ArrayList;

@Entity(tableName = "airports")
public class Airports {

    @NonNull
    @PrimaryKey
    private String icao;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "longitude")
    private Double longitude;

    @ColumnInfo(name = "latitude")
    private Double latitude;

    @ColumnInfo(name = "elevation")
    private Double elevation;

    @ColumnInfo(name = "iso_country")
    private String iso_country;

    @ColumnInfo(name = "municipality")
    private String municipality;

    public Airports() {}

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIso_country() {
        return iso_country;
    }

    public void setIso_country(String iso_country) {
        this.iso_country = iso_country;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public Double getLongitude() {
        return longitude;

    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getElevation() {
        return elevation;
    }

    public void setElevation(Double elevation) {
        this.elevation = elevation;
    }

    @Override
    public String toString() {
        return "Airports{" +
                "icao='" + icao + '\'' +
                ", name='" + name + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", elevation=" + elevation +
                ", iso_country='" + iso_country + '\'' +
                ", municipality='" + municipality + '\'' +
                '}';
    }
}
