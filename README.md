# StateStringService

# Required :

- Maven
- Java 8

# Tools and DB Used:
- DynamoDB 
- Swagger
- Unit Tests: Junit, Mockito

# Assumptions and considerations :

* Since cookies were not allowed to use I have used UUID generator of java library to generate userId
* I have used Dynamo DB since the application can be easily deployed in AWS cloud
* Right now the configurations in the application.properties are pointing to local but can be made production ready jusy by updating the url and keys
* Regarding : POST /chars - adds the character/s to the string state, e.g. with JSON input {“character”:”a”,”amount”:3} adds “aaa” to the state string : I assumed that the characters are added at end

# Endpoint for Testing:

Have added an end point /addState just for adding a state String for testing purpose

# PreRequisites :

Download the Dynamodb JAR for setting up the DB in local from the link : http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.html

Extract the Jar and run the command : java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar sharedDb

Once the DynamoDB started goto http://localhost:8000/shell and Create a table named User :
```
var params = {
  AttributeDefinitions: [
     {
    AttributeName: "userId", 
    AttributeType: "S"
   }, 
     {
    AttributeName: "stateString", 
    AttributeType: "S"
   }
  ], 
  KeySchema: [
     {
    AttributeName: "userId", 
    KeyType: "HASH"
   }
  ], 
  GlobalSecondaryIndexes: [{
                IndexName: "FactoryIndex",
                KeySchema: [
                    {
                        AttributeName: "userId",
                        KeyType: "HASH"
                    },
                    {
                        AttributeName: "stateString",
                        KeyType: "RANGE"
                    }
                ],
                Projection: {
                    ProjectionType: "ALL"
                },
                ProvisionedThroughput: {
                    ReadCapacityUnits: 1,
                    WriteCapacityUnits: 1
                }
            }],
  ProvisionedThroughput: {
   ReadCapacityUnits: 5, 
   WriteCapacityUnits: 5
  }, 
  TableName: "User"
 };
 dynamodb.createTable(params, function(err, data) {
   if (err) console.log(err, err.stack); // an error occurred
   else     console.log(data);           // successful response
   
 });
 ```

# Start Application:
Go to the root directory . Run the command
```
mvn spring-boot:run
```
You can see the end points in Swagger Docs: http://localhost:8080/swagger-ui.html

# Run UnitTests:
Go to the root directory . Run the command
```
mvn verify
```
# Requirements Completion Status:

Created Java & Spring Boot,  RESTful, JSON-based web service that keeps a String state per person (per browser).
The initial state for new user is empty String ""

# requirements:

* do not use cookies - Done
* each operation logs what it is doing in a log/console, e.g. userID: “ab85c56a”, added: “a”, 2
times - Logged trace, info, error statements
* two different browsers from single computer are two different users - Since cookies could not be used I used UUID which might not change with browser. I did not get the requirement clearly 
* provide an instruction how to install your solution (including dependencies) and start it (on
linux or mac) - provided above

# supported operations: All Done
- GET /state - returns the current state - Done
- GET /sum - sums all numbers in a string, e.g. “5abc141def” returns 146, if there are no numbers return 0 - Done
- GET /chars - shows the current state without numbers, e.g. “5abc141def” returns abcdef - Done
- POST /chars - adds the character/s to the string state, e.g. with JSON input {“character”:”a”,”amount”:3} adds “aaa” to the state string - Done
- DELETE /chars/<character> - deletes the last occurrence of the character in the state string - Done

# web services requirements: All Done
- return 400 if the POST request contains invalid JSON
- character in DELETE has to be a single alphanumeric character, otherwise return 400
- character in POST request has to be just one alphanumeric character and amount a number
from 1 to 9, otherwise return 400
- if the length of the string state will exceed 200 characters after the POST request, do not
change the state and return 400
- wrong url returns 404
- each valid JSON response (200 class) includes a user hash/id

# bonus points: All done
- put your solution on GitHub (personal account)
- write unit tests
- use database to store the state