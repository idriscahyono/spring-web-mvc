package idriscahyono.webmvc.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApiServiceImplTest {

    @Autowired
    private ApiService apiService;

    @Test
    void hello(){
        assertEquals("Hello Guest", apiService.hello((null)));
        assertEquals("Hello Idris", apiService.hello("Idris"));
    }

}