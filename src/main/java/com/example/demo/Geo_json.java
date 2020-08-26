/*

   Coding Challenge - develop single endpoint RESTful API with Java 11, Spring Boot, and Docker
   Al Curry      August 20, 2020

   Geojon objects from this repo were used as a guide.
   https://github.com/ngageoint/simple-features-geojson-java/tree/master/src/main/java/mil/nga/sf/geojson

*/
package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Geo_json {
    private String type;
    Properties PropertiesObject;
    Geometry GeometryObject;


    // Getter Methods

    public String getType() {
        return type;
    }

    public Properties getProperties() {
        return PropertiesObject;
    }

    public Geometry getGeometry() {
        return GeometryObject;
    }

    // Setter Methods

    public void setType(String type) {
        this.type = type;
    }

    public void setProperties(Properties propertiesObject) {
        this.PropertiesObject = propertiesObject;
    }

    public void setGeometry(Geometry geometryObject) {
        this.GeometryObject = geometryObject;
    }
}
