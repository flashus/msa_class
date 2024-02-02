package ru.idyachenko.users.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import java.util.UUID;

@Entity
@Table(name = "cities")
public class City {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @Column(name = "city_name", nullable = false)
    private String cityName;

    // Constructors, getters, and setters

    public UUID getId() {
        return id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public City(String cityName) {
        // this.id = UUID.randomUUID();
        this.cityName = cityName;
    }

    public City() {
        // this.id = UUID.randomUUID();
    }

    public City(UUID id, String cityName) {
        this.id = id;
        this.cityName = cityName;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", cityName='" + cityName + '\'' +
                '}';
    }
}
