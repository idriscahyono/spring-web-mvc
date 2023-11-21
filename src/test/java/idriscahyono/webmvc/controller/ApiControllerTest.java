package idriscahyono.webmvc.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void helloGuest() throws Exception {
        mockMvc.perform(
                get("/hello")
        ).andExpectAll(
                status().isOk(),
                content().string(Matchers.containsString("Hello Guest"))
        );
    }

    @Test
    void helloName() throws Exception{
        mockMvc.perform(
                get("/hello").queryParam("name", "Idris")
        ).andExpectAll(
                status().isOk(),
                content().string(Matchers.containsString("Hello Idris"))
        );
    }

    @Test
    void date() throws Exception {
        mockMvc.perform(
                get("/date").queryParam("date", "2023-11-21")
        ).andExpectAll(
                status().isOk(),
                content().string(Matchers.containsString("Date : 20231121"))
        );
    }

    @Test
    void orderProduct() throws Exception {
        mockMvc.perform(
                get("/orders/1/products/2")
        ).andExpectAll(
                status().isOk(),
                content().string(Matchers.containsString("Order : 1 Product : 2"))
        );
    }

}