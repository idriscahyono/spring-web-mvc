package idriscahyono.webmvc.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiControllerIntegrationTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void helloGuest(){
        String response = restTemplate.getForEntity("http://localhost:" + port + "/hello", String.class).getBody();
        Assertions.assertNotNull(response);
        Assertions.assertEquals("Hello Guest", response.trim());
    }

    @Test
    void helloName(){
        String response = restTemplate.getForEntity("http://localhost:" + port + "/hello?name=Idris", String.class).getBody();
        Assertions.assertNotNull(response);
        Assertions.assertEquals("Hello Idris", response.trim());
    }

}
