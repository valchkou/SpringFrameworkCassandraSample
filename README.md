Sample Spring Cassandra Project
==============================

The purpose of this project to demonstrate how to merry [casandra-driver-mapping ](https://github.com/valchkou/cassandra-driver-mapping) add-on with the Spring Framework.  
The project is a small webapp with UI, REST Services and Cassandra DB. 

### You can dive into the code directly:

- [Cassandra Connection Factory](https://github.com/valchkou/SpringFrameworkCassandraSample/blob/master/src/main/java/com/valchkou/sample/dao/CassandraSessionFactory.java)

- [BaseDAO - Spring Bean Wrapper for MappingSession](https://github.com/valchkou/SpringFrameworkCassandraSample/blob/master/src/main/java/com/valchkou/sample/dao/BaseDAO.java)

- [Customized DAO for Messages](https://github.com/valchkou/SpringFrameworkCassandraSample/blob/master/src/main/java/com/valchkou/sample/dao/MessageDAO.java)

- [SpringMVC REST Controller](https://github.com/valchkou/SpringFrameworkCassandraSample/tree/master/src/main/java/com/valchkou/sample/controller)  

- [UI-Simple chat client powered by JQuery Ajax.](https://github.com/valchkou/SpringFrameworkCassandraSample/tree/master/public) 


### Or get the project on your device and run it.
You will need:
- [Apache Maven](http://maven.apache.org/download.cgi)
- Cassandra instance running on your localhost (or change [application.properties](https://github.com/valchkou/SpringFrameworkCassandraSample/blob/master/src/main/java/application.properties)).
- Java 7 and up.

This application baked with [Spring-Boot](http://projects.spring.io/spring-boot). So you don't have to worry about deploying this webapp.  
You just need to navigate the project root and execute command:  
#### mvn spring-boot:run
spring-boot will do all the magic under the hood.
When app is up open few browser Tabs or Windows with [http://localhost:8080](http://localhost:8080) and start chatting.



