

You should install and config in your local machine: Java; MongoDB and Maven. 
Below the specific version.

- `java version "1.8.0_192 | Java(TM) SE Runtime Environment (build 1.8.0_192-b12) `
- `MongoDB shell version v4.0.9`
- `Apache Maven 3.6.0`

Install Eclipse:
- `Eclipse Java EE IDE for Web Developers.`

Execute below command in java-quiz folder
- `mvn clean install`

To run mongodb in MACOS run the below command. If you are using Windows or Linux please check documentation.
-  `./mongod --config /usr/local/etc/mongod.conf`
-  Mongo should be running in localhost:27017

To run the Java Application:
- Import the project into eclipse as a `existent maven projects`.
- Make sure that **application.properties** is pointing to your localhost
- Execute the JavaquizApplication class as a Java application

To run the application locally you need first insert at least 5 questions in mongodb.

To insert the questions, go to http://localhost:8005/questions/list. Login is needed. 
Insert a username and password into MongoDB: 

db.admin.insert({
      "username" : "enter-username",
      "password" : "enter-password"});

Password needs to be bcrypted. To generate a bcrypted password: https://www.bcryptcalculator.com/. 

Local Address: **http://localhost:8005/**

Any questions/suggestion please let me know
J.JÚNIOR
