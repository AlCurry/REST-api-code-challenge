/*

   Coding Challenge - develop single endpoint RESTful API with Java 11, Spring Boot, and Docker
   Al Curry      August 20, 2020

   Geojon objects from this repo were used as a guide.
   https://github.com/ngageoint/simple-features-geojson-java/tree/master/src/main/java/mil/nga/sf/geojson

*/

package com.example.demo;

import com.fasterxml.jackson.annotation.JsonTypeId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Field {
    private String name;
    Geo_json Geo_jsonObject;


    // Getter Methods

    public String getName() {
        return name;
    }

    public Geo_json getGeo_json() {
        return Geo_jsonObject;
    }

    // Setter Methods

    public void setName(String name) {
        this.name = name;
    }

    public void setGeo_json(Geo_json geo_jsonObject) {
        this.Geo_jsonObject = geo_jsonObject;
    }
}
