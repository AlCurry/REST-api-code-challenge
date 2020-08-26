package com.example.demo;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class UtilityFunctions {

    /*
       method reformatWeatherJSON

       paramters:
       String respBody

       returns:
       JSONobject of the required fields

       From response.body, reformat the json output to match the requirements, eliminating some fields
       and putting the date timestamp in each section

        it should look like this :
                {
                    "weather": [
                            {
                                "timestamp": "1485705600",
                                "temperature": 288.15,
                                "humidity": 85,
                                "temperatureMax": 289.16,
                                "temperatureMin": 280.16
                            }, {
                                "timestamp": "1485705700",
                                "temperature": 288.15,
                                "humidity": 85,
                                "temperatureMax": 289.16,
                                "temperatureMin": 280.16
                            },
                            ...
                            ]
                        }

     */
    static JSONObject reformatWeatherJSON(String respBody ) {


        Object obj = null;
        try {
            obj = new JSONParser().parse(respBody);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JSONArray ja = (JSONArray) obj;
        JSONObject jo = (JSONObject) ja.get(0);

        Iterator it1 = ja.iterator();

        JSONArray ja2 = new JSONArray();
        JSONObject jo2 = new JSONObject();

        while (it1.hasNext()) {
            jo = (JSONObject) it1.next();
            //System.out.println("while DATE from JSON response: " + jo.get("dt"));

            // getting main (where the weather stats we want are)
            Map address = ((Map) jo.get("main"));

            Map m = new LinkedHashMap(5);

            m.put("timestamp", jo.get("dt"));

            Iterator<Map.Entry> it2 = address.entrySet().iterator();

            // use these Map.Entry variables to change order of the fields, other data type casting
            // created errors
            Map.Entry humPair = null;
            Map.Entry maxPair = null;
            Map.Entry minPair = null;

            while (it2.hasNext()) {
                Map.Entry pair = it2.next();

                if (pair.getKey().equals("temp")) {

                    m.put("temperature", pair.getValue());
                } else if (pair.getKey().equals("humidity")) {
                    humPair = pair;
                }
                if (pair.getKey().equals("temp_max")) {
                    maxPair = pair;
                }
                if (pair.getKey().equals("temp_min")) {
                    minPair = pair;
                }
            }

            m.put("humidity", humPair.getValue());
            m.put("temperatureMax", maxPair.getValue());
            m.put("temperatureMin", minPair.getValue());

            ja2.add(m);

        }
        jo2.put("weather", ja2);

        return jo2;
    }
    /*
      method addSquareBracket
          needed to address a bug - details below - this works for now

      parameter:
      String badJSON

      returns:
      String with expected [[[]]]

      According to error messages from agromonitoring.com and experimenting with Postman, this JSON needs another set of
      square brackets around the coordinate values - example with the error below.

      from JSON:
      "coordinates":[[-121.1958,37.6683],[-121.1779,37.6687],[-121.1773,37.6792],[-121.1958,37.6792],[-121.1958,37.6683]]

      error message:
      returned status code 422, with text repeated for each coordinate pair "a number was found where a coordinate array
      should have been found: this needs to be nested more deeply"

      Probably there is a solution defining the Java Objects "coordinates" value differently, in Geometry.java.

      These objects are used when @RequestBody is invoked with the addField method below.

      A few variations did not reveal a better variable declaration or other solution there, so this brute force insertion
      of another set of brackets corrects the problem for now.

    */
    static String addSquareBrackets( String badJSON ) {

        return( badJSON.replace(":[[", ":[[["  ).replace("]]","]]]")) ;
    }



}
