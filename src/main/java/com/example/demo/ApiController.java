/*

   Coding Challenge - develop single endpoint RESTful API with Java 11, Spring Boot, and Docker
   Detailed pdf description in this repository.


   Al Curry
   August 20, 2020

 */

package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class ApiController {

    public static void main(String[] args) {
        SpringApplication.run(ApiController.class, args);
    }



    private static final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10)).build();

    @Value("${api.key}")
    private String agroApiKey;

    @PostMapping("/fields")
    public @ResponseBody
    ResponseEntity<?> addField(@RequestBody Field field ) {

        System.out.println("POST begin ***");

        try {

            var objectMapper = new ObjectMapper();
            String reqBodyStr = objectMapper.writeValueAsString(field);

            reqBodyStr = UtilityFunctions.addSquareBrackets(reqBodyStr);

            final String fullURL = "http://api.agromonitoring.com/agro/1.0/polygons" + "?appid=" + agroApiKey;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(fullURL))
                    .header("Content-Type", "application/json")
                    .POST(BodyPublishers.ofString(reqBodyStr))
                    .build();

            // http://api.agromonitoring.com/agro/1.0/polygons/5f3a3123df8070000761076c?appid=76f93c352795168ce2274f98d37e4b33
           HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // print status code
            System.out.println("http status : " + response.statusCode());

            ObjectMapper mapper = new ObjectMapper();
            Object jsonObject = mapper.readValue(response.body(), Object.class);

            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject));

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
            final String fullURL = "http://api.agromonitoring.com/agro/1.0/polygons/" + id + "?appid=" + agroApiKey;

            HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(fullURL))
                    .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                    .build();

            // http://api.agromonitoring.com/agro/1.0/polygons/5f3a3123df8070000761076c?appid=76f93c352795168ce2274f98d37e4b33
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // print status code
            System.out.println("http status : " + response.statusCode());

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
            final String fullURL = "http://api.agromonitoring.com/agro/1.0/polygons/" + id + "?appid=" + agroApiKey;

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
    ResponseEntity<String> updateField(@RequestBody Field field, @PathVariable final String id) {

        System.out.println("field id value is : " + id);
        System.out.println("Field name, new value after update : " + field.getName());
        System.out.println("PUT begin ***");

        try {

            var objectMapper = new ObjectMapper();
            String reqBodyStr = objectMapper.writeValueAsString(field);

            final String fullURL = "http://api.agromonitoring.com/agro/1.0/polygons/" + id + "?appid=" + agroApiKey;

            //String jsonFilename = "fieldUpdate.json";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(fullURL))
                    .header("Content-Type", "application/json")
                    //.PUT(BodyPublishers.ofFile(Paths.get(jsonFilename)))
                    .PUT(BodyPublishers.ofString(reqBodyStr))
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
    ResponseEntity<String> getWeatherByFieldId(@PathVariable final String id) throws Exception {

        System.out.println("Weather field id is : " + id);

        /*
        URL format to get weather -- agromonitoring.com api - format from Daniel's email
        https://samples.openweathermap.org/agro/1.0/weather/history?polyid={{POLIYGON_ID}}&start={{startDate}}&end={{endDate}}&appid={{API_ID}}

        With data, this works in POSTMAN :
        GET https://samples.openweathermap.org/agro/1.0/weather/history?polyid=5f3a3123df8070000761076c&start=1485703465&end=1485780512&appid=76f93c352795168ce2274f98d37e4b33

        Dates are in unix format, number of seconds since Jan 01 1970.
        */

        /*
            to get start and end dates  - requirement is for "last seven days"
            assumed to mean today and the previous 7 days
            get current date in unix format
            subtract seven days -  probably with a standard format, subtract 7, convert both to unix
        */

        Date now = new Date();
        long ut3 = now.getTime() / 1000L;
        System.out.println("today's date : " + now + "   unix foramt : " + ut3);
        Date dateBefore = new Date(now.getTime() - 7 * 24 * 3600 * 1000L ); // Subtract 7 days
        long ut3sub7 = ut3 - 7 * 24 * 3600;
        System.out.println("7 days ago   : " + dateBefore + "   unix foramt : " + ut3sub7);

        try {
            final String fullURL = "https://samples.openweathermap.org/agro/1.0/weather/history?polyid=" + id + "&start=" + ut3sub7 + "&end=" + ut3 +  "&appid=" + agroApiKey;

            /* NOTE: this appid key is preceded by "&", while the other agromonitoring.com identifier is preceded by "?".
               The value of the appid key is the same.

               DATE RANGE issues - in my testing on with the IDE, the samples openweathermap web page, and with
               postman, the start and end dates don't appear work.  To me it looks like the output is always
               for 8 times from 1485702000 to 1485727200, January 29 2017, from 3 pm to 10 pm, without regard to
               the start and end date values passed in.
            */

            HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(fullURL))
                    .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // print status code
            System.out.println("http status : " + response.statusCode());

            // print response body
            ObjectMapper mapper = new ObjectMapper();
            Object jsonObject = mapper.readValue(response.body(), Object.class);

            JSONObject requiredFieldsJSON = new JSONObject();

            requiredFieldsJSON = UtilityFunctions.reformatWeatherJSON(response.body());

            //System.out.println(requiredFieldsJSON.toJSONString());

            System.out.println("Results from openweathermap:");
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject));
            System.out.println("Reformatted by this API:");
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requiredFieldsJSON));

            return new ResponseEntity<String>("GET weather Response - field id : " + id + " status code : " + response.statusCode() + "\n" +
                    mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requiredFieldsJSON) +"\n", HttpStatus.OK);

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