/*

   Coding Challenge - develop single endpoint RESTful API with Java 11, Spring Boot, and Docker
   Detailed pdf description in this repository.


   Al Curry
   August 20, 2020

 */

package com.example.demo;

import java.lang.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.time.Duration;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@SpringBootApplication
@RestController
public class ApiController {

    public static void main(String[] args) {
        SpringApplication.run(ApiController.class, args);
    }

    private static final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10)).build();

    final String agroAPIKey = "76f93c352795168ce2274f98d37e4b33";

    @PostMapping("/fields")
    public @ResponseBody
    ResponseEntity<String> addField() {

        System.out.println("Data in hard-coded json file for now");

        System.out.println("POST begin ***");

        try {

            final String fullURL = "http://api.agromonitoring.com/agro/1.0/polygons" + "?appid=" + agroAPIKey;
            System.out.println("fullURL : " + fullURL);

            String jsonFilename = "fieldInput.json";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(fullURL))
                    .header("Content-Type", "application/json")
                    //.POST(ofInputStream(() -> getClass().getResourceAsStream("/some-data.json")))
                    .POST(BodyPublishers.ofFile(Paths.get(jsonFilename)))
                    .build();

            // http://api.agromonitoring.com/agro/1.0/polygons/5f3a3123df8070000761076c?appid=76f93c352795168ce2274f98d37e4b33
           HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // print status code
            System.out.println("http status : " + response.statusCode());

            // print response body
            //System.out.println(response.body());

            ObjectMapper mapper = new ObjectMapper();
            Object jsonObject = mapper.readValue(response.body(), Object.class);

            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject));

            //return new ResponseEntity<String>("PUT Response : " + id + "\n" + response.body() +"\n", HttpStatus.OK);

            return new ResponseEntity<String>("POST status code : " + response.statusCode() + "\nResponse : " + "\n" +
                    mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject) +"\n", HttpStatus.OK);

        } catch (final IOException e) {
            e.printStackTrace();
        } catch (final InterruptedException ie) {
            ie.printStackTrace();
        }


        // return below seems required, without it a compiler error occurs, yet in testing
        // so far code does not get to this point - investigate later, time permitting
        System.out.println("to return 1");
        return new ResponseEntity<String>(HttpStatus.OK);

    }

    @GetMapping("/fields/{id}")
    public @ResponseBody
    ResponseEntity<String> getByFieldId(@PathVariable final String id) {

        System.out.println("field id value is : " + id);

        System.out.println("GET begin ***");

        try {
            final String fullURL = "http://api.agromonitoring.com/agro/1.0/polygons/" + id + "?appid=" + agroAPIKey;

            HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(fullURL))
                    .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                    .build();

            // http://api.agromonitoring.com/agro/1.0/polygons/5f3a3123df8070000761076c?appid=76f93c352795168ce2274f98d37e4b33
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // print status code
            System.out.println("http status : " + response.statusCode());

            // print response body
            //System.out.println(response.body());

            ObjectMapper mapper = new ObjectMapper();
            Object jsonObject = mapper.readValue(response.body(), Object.class);

            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject));

            //return new ResponseEntity<String>("GET Response : " + id + "\n" + response.body() +"\n", HttpStatus.OK);
            return new ResponseEntity<String>("GET Response - field id : " + id + "\n" +
                    mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject) +"\n", HttpStatus.OK);

        } catch (final IOException e) {
            e.printStackTrace();
        } catch (final InterruptedException ie) {
            ie.printStackTrace();
        }

        // return below seems required, without it a compiler error occurs, yet in testing
        // so far code does not get to this point - investigate later, time permitting
        return new ResponseEntity<String>(HttpStatus.OK);

    }

    @DeleteMapping("/fields/{id}")
    public @ResponseBody
    ResponseEntity<String> delete(@PathVariable final String id) {

        System.out.println("field id value is : " + id);

        System.out.println("DELETE begin ***");

        try {
            final String fullURL = "http://api.agromonitoring.com/agro/1.0/polygons/" + id + "?appid=" + agroAPIKey;

            HttpRequest request = HttpRequest.newBuilder().DELETE().uri(URI.create(fullURL))
                    .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                    .build();

            // http://api.agromonitoring.com/agro/1.0/polygons/5f3a3123df8070000761076c?appid=76f93c352795168ce2274f98d37e4b33
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // print status code
            System.out.println("http status : " + response.statusCode());

            // print response body
            System.out.println(response.body());

            return new ResponseEntity<String>("DELETE field id : " + id + " status code : " + response.statusCode() + response.body() +"\n", HttpStatus.OK);

        } catch (final IOException e) {
            e.printStackTrace();
        } catch (final InterruptedException ie) {
            ie.printStackTrace();
        }

        // return below seems required, without it a compiler error occurs, yet in testing
        // so far code does not get to this point - investigate later, time permitting
        return new ResponseEntity<String>(HttpStatus.OK);

    }

    @PutMapping("/fields/{id}")
    public @ResponseBody
    ResponseEntity<String> updateField(@PathVariable final String id) {

        System.out.println("field id value is : " + id);
        System.out.println("Data in hard-coded json file for now");

        System.out.println("PUT begin ***");

        try {

            final String fullURL = "http://api.agromonitoring.com/agro/1.0/polygons/" + id + "?appid=" + agroAPIKey;

            String jsonFilename = "fieldUpdate.json";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(fullURL))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofFile(Paths.get(jsonFilename)))
                    .build();

            // http://api.agromonitoring.com/agro/1.0/polygons/5f3a3123df8070000761076c?appid=76f93c352795168ce2274f98d37e4b33
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            Object jsonObject = mapper.readValue(response.body(), Object.class);

            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject));

            // print status code
            System.out.println("http status : " + response.statusCode());

            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject));

            return new ResponseEntity<String>("PUT Response - field id : " + id + " status code : " + response.statusCode() + "\n" +
                    mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject) +"\n", HttpStatus.OK);


        } catch (final IOException e) {
            e.printStackTrace();
        } catch (final InterruptedException ie) {
            ie.printStackTrace();
        }

        // return below seems required, without it a compiler error occurs, yet in testing
        // so far code does not get to this point - investigate later, time permitting
        return new ResponseEntity<String>(HttpStatus.OK);

    }

    @GetMapping("/fields/{id}/weather")
    public @ResponseBody
    ResponseEntity<String> getWeatherByFieldId(@PathVariable final String id) {

        System.out.println("Weather field id is : " + id);

        System.out.println("GET begin ***");

        /*
        URL format to get weather -- agromonitoring.com api - format from Daniel's email
        https://samples.openweathermap.org/agro/1.0/weather/history?polyid={{POLIYGON_ID}}&start={{startDate}}&end={{endDate}}&appid={{API_ID}}

        With data, this works in POSTMAN :
        GET https://samples.openweathermap.org/agro/1.0/weather/history?polyid=5f3a3123df8070000761076c&start=1485703465&end=1485780512&appid=76f93c352795168ce2274f98d37e4b33

        Dates are in unix format, number of seconds since Jan 01 1970.
        */

        /*
            to get start and end dates  - requirement is for last seven days
            get current date in unix format
            subtract seven days -- find a function for this, return unix format
            possibly start with a standard format, subtract 7, convert both to unix
        */
        try {
            final String fullURL = "http://api.agromonitoring.com/agro/1.0/polygons/" + id + "?appid=" + agroAPIKey;

            HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(fullURL))
                    .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                    .build();

            // http://api.agromonitoring.com/agro/1.0/polygons/5f3a3123df8070000761076c?appid=76f93c352795168ce2274f98d37e4b33
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // print status code
            System.out.println("http status : " + response.statusCode());

            // print response body
            System.out.println(response.body());

            return new ResponseEntity<String>("GET Response : " + id + "\n" + response.body() +"\n", HttpStatus.OK);


        } catch (final IOException e) {
            e.printStackTrace();
        } catch (final InterruptedException ie) {
            ie.printStackTrace();
        }

        // return below seems required, without it a compiler error occurs, yet in testing
        // so far code does not get to this point - investigate later, time permitting
        System.out.println("to return 2");
        return new ResponseEntity<String>(HttpStatus.OK);

    }

}