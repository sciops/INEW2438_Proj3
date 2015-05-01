/*
 * The MIT License
 *
 * Copyright 2015 Pivotal Software, Inc..
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package hello;

/**
 *
 * @author Stephen R. Williams
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    //hardcoded database connection parameters
    // JDBC driver name and database URL
    private final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final static String DB_URL = "jdbc:mysql://localhost:3306/mysql";
    //  Database credentials
    private final static String USER = "root";
    private final static String PASS = "password";

    @RequestMapping("/customer/test")
    public Customer customerTest(
            @RequestParam(value = "username", defaultValue = "defaultUsername") String username,
            @RequestParam(value = "password", defaultValue = "defaultPassword") String password,
            @RequestParam(value = "emailAddr", defaultValue = "defaultEmail") String emailAddress
    ) {
        return new Customer(-1, username, password, emailAddress);
    }

    //TODO: get the parameter mappings to work with the given webform
    //TODO: make this method correctly insert data into the database
    @RequestMapping(value = "/helloworld-webapp/customer/customers", method = RequestMethod.POST)
    public Customer customerStore(
            @RequestParam(value = "username", defaultValue = "defaultUsername") String username,
            @RequestParam(value = "password", defaultValue = "defaultPassword") String password,
            @RequestParam(value = "emailAddr", defaultValue = "defaultEmail") String emailAddress
    ) {
        Customer customer = new Customer(-1, username, password, emailAddress);

        return customer;
    }

    @RequestMapping("/customer/report")
    public String customerReport() {
        MyDatabaseConn mdb = new MyDatabaseConn(JDBC_DRIVER, DB_URL, USER, PASS);
        List<Map> table = mdb.runQuery("SELECT * FROM customer");
        List<Customer> list = CustomerController.getCustomerList(table);
        return list.toString();
    }

    public static List<Customer> getCustomerList(List<Map> table) {
        List<Customer> customers = new ArrayList();
        for (Map m : table) {
            int id = (int) m.get("customerID");
            String username = (String) m.get("username");
            String password = (String) m.get("password");
            String emailAddress = (String) m.get("emailAddress");
            Customer customer = new Customer(id, username, password, emailAddress);
            customers.add(customer);
        }
        return customers;
    }

}
