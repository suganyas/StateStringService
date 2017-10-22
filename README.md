# StateStringService

#Required :

- Maven
- Java 8

#Tools and DB Used:
- DynamoDB 
- Swagger
- Unit Tests: Junit, Mockito

#Assumptions and considerations :

* Since cookies were not allowed to use I have used UUID generator of java library to generate userId
* I have used Dynamo DB since the application can be easily deployed in AWS cloud
* Right now the configurations in the application.properties ar pointing to local but cna be made production ready jusy by updating the url and keys
* Reagrding : POST /chars - adds the character/s to the string state, e.g. with JSON input {“character”:”a”,”amount”:3} adds “aaa” to the state string : I assumed that the characters are added at end

#PreRequisites :

Download the Dynamodb JAR for setting up the DB in local from the link : http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.html

Extract the Jar and run the command :java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jarsharedDb

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

#Start Application:
Go to the root directory . Run the command
```
mvn spring-boot:run
```
You can see the end points in Swagger Docs: localhost:8080/swagger-ui.html

#Run UnitTests:
Go to the root directory . Run the command
```
mvn verify
```