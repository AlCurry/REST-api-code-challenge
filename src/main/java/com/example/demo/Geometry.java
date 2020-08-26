/*

   Coding Challenge - develop single endpoint RESTful API with Java 11, Spring Boot, and Docker
   Al Curry      August 20, 2020

   Geojon objects from this repo were used as a guide.
   https://github.com/ngageoint/simple-features-geojson-java/tree/master/src/main/java/mil/nga/sf/geojson

*/
package com.example.demo;

import java.awt.desktop.SystemSleepEvent;
import java.lang.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Geometry {
    private String type;
    private ArrayList<ArrayList<Double>> coordinates;

    // Getter Methods
    public String getType() {
        return type;
    }

    public ArrayList<ArrayList<Double>> getCoordinates () {
        return coordinates;
    }

    // Setter Methods
    public void setType(String type) {
        this.type = type;
    }
    public void setCoordinates(ArrayList<ArrayList<Double>> coordinates) {
        this.coordinates =  coordinates;
    }


}



