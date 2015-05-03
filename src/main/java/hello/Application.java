package hello;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 INEW 2438 Project 3 Assignment
 Stephen R. Williams

 REST service using Spring
 This class and main method use SpringApplication.run to set up the service without web.xml
 Reference: https://spring.io/guides/gs/rest-service/

 JDBC connection to MySQL database. If building with Maven, use the following dependency in pom.xml
 <dependency>
 <groupId>mysql</groupId>
 <artifactId>mysql-connector-java</artifactId>
 <version>5.1.6</version>
 </dependency>

 Assignment text:
 Create a DataBase with a table Table name Customer. The table should have columns at least with user name, and email address.
 Use the web-form provided under Chapter 4 - Database Programming folder to capture user input.
 Create a REST Service that use @POST, and @FormParam to capture the data sent by the web form and insert the data into the Customer table.
 Create a REST Service that use @GET to return all the data stored in the Customer table.
 */
@SpringBootApplication
public class Application {

    //hardcoded database connection parameters
    // JDBC driver name and database URL
    private final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final static String DB_URL = "jdbc:mysql://localhost:3306/mysql";
    //  Database credentials
    private final static String USER = "root";
    private final static String PASS = "password";

    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        testDB();
    }

    public static void testDB() {
        //database test, outputs all customer records.
        MyDatabaseConn mdb = new MyDatabaseConn(JDBC_DRIVER, DB_URL, USER, PASS);
        List<Map<String, Object>> table = mdb.runQuery("SELECT * FROM customer");
        List<Customer> customers = CustomerController.getCustomerList(table);
        for (Customer c : customers) {
            System.out.println(c.toString());
        }
    }
}
