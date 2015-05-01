package hello;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//retained model of controller

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    //RESTful web service with Spring. uses no web.xml
    
    // The example does not specify GET vs. PUT, POST, and so forth, because @RequestMapping maps all HTTP operations by default. 
    //Use @RequestMapping(method=GET) to narrow this mapping.
    //Reference: https://spring.io/guides/gs/rest-service/
    //http://localhost:8080/greeting
    //http://localhost:8080/greeting?name=Steve
    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
}
