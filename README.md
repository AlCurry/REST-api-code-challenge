### Yara Digital Farming code challenge                                                                   

#### Al Curry  

#### August 26, 2020 
---
<ins>Additonal work, improving code and solving some problems: </ins>
1. Removed hard-coded .json filenames, resolving docker issue for POST and PUT.
1. Defined api.key in application.properties, rather than in java code.
1. Implemented more thorough java object structure to match json for POST request / create function.
1. Reformatted JSON weather output as described in the challenge pdf. 
1. Created methods and moved sections of code to UtilityFunctions.java
1. Tested and added script for testing with HTTPie.
1. Added comments 
1. Gained some familiarity with IntelliJ's debugger and git tools.

#### August 20, 2020 
---
Full description of the challenge is in the file code_challenge.pdf in this repo.  

<ins> Short version : <ins>

Develop a RESTful api to define Fields (as used for farming) and get weather information about them.  Use Java 11, Spring Boot, Docker.  

Followup email questions and answers with Daniel Fernandes in this repo - email18.08.2020.pdf.

Development and most testing (though not enough) done in IntelliJ IDEA.

<ins>Docker commands to build and run the API : </ins>
  
  run in project directory 
  
  ./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=ac101/ydf-api
    
  docker run -p 8080:8080 -t ac101/ydf-api
    
  to store in docker hub : 
  docker push ac101/ydf-api

<ins>Curl commands for testing in repo file apiTestsCurl and below : </ins>

if not obvious, must be customized to data, changing polygon ids

curl http://localhost:8080/fields/5f3a93c1714b52fc1de0e79a

curl -X DELETE http://localhost:8080/fields/5f3ec3aa714b52079ee0eb06

curl -X POST -H "Content-Type: application/json" http://localhost:8080/fields

curl -X PUT -H "Content-Type: application/json" http://localhost:8080/fields/5f3e9170714b52b6c9e0ea29

and
curl http://localhost:8080/fields/5f3e492c714b52b902e0e9f7/weather

<ins>Requirements omitted : </ins>
  
   Field entity model in pdf not implemented.
   Clarification needed about “partial updates” - a PUT to update will allow only the polygon name to be updated.
   For the weather history use case, the JSON output needs to be reformatted to match the requirements.  For now it is printed as extracted.    

<ins> Code concerns :  </ins>

Many sections of the code are repetitive, I know this is not the best.   Ideally it would be made more modular so pieces are not mostly repeated for each Http Request Type - GET, POST, PUT, and DELETE and for pretty printing of JSON. 
    
Also generally concerned about the file structure, likely it is overly simplified.  In researching the problem much more elaborate, multi-file solutions were found - probably required for a more thorough solution.  This would include a java object mapping to the Field structure provided and a database for persistence.  

<ins> Other known issues : </ins>

For now the POST and PUT commands to create and update a Field include JSON that is in flat files, their names hard-coded in the .java code.  Far from optimal.  This produces a file not found error when running in docker, but works correctly in IntelliJ.   Probably better to pass a file name to the API that would include the JSON, or another approach. 

Thank you for reading this far, apologies for so many disclaimers.  Some partially self-imposed time constraints were encountered.  If I could not resolve an issue within 30 or 60 minutes, between researching and implementing solutions, I accepted something that was working, in order to progress toward the finish line.  Part of the challenge was using many tools that I have limited experience with (docker, postman, the agromonitoring api, Intellij IDE, Java 11) and others that it has been more than 3 years since i studied or worked with (Spring Boot, Java in general).   In only a week working on this, my skills improved, or were refreshed - after a bit more exposure to the specific tools, I believe they would continue to improve, especially with access to existing code, development standards, and professional expertise. 
