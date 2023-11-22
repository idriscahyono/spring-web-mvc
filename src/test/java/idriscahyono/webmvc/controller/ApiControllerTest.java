package idriscahyono.webmvc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import idriscahyono.webmvc.model.CreatePersonRequest;
import idriscahyono.webmvc.model.CreateSocialMediaRequest;
import idriscahyono.webmvc.model.HelloRequest;
import idriscahyono.webmvc.model.HelloResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private ObjectMapper objectMapper;

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

    @Test
    void uploadFile() throws Exception {
        mockMvc.perform(
                multipart("/upload/file")
                        .file(new MockMultipartFile("profile", "image.jpg", "image/jpg",
                                getClass().getResourceAsStream("/images/image.jpg")))
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("name", "Idris")
        ).andExpectAll(
                status().isOk(),
                content().string(Matchers.containsString("Success save profile Idris to image.jpg"))
        );
    }

    @Test
    void bodyHello() throws Exception {
        HelloRequest request = new HelloRequest();
        request.setName("Idris");
        mockMvc.perform(
                post("/body/hello")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk(),
                header().string(HttpHeaders.CONTENT_TYPE, Matchers.containsString(MediaType.APPLICATION_JSON_VALUE))
        ).andExpect(result -> {
            String responseBody = result.getResponse().getContentAsString();
            HelloResponse helloResponse = objectMapper.readValue(responseBody, HelloResponse.class);
            Assertions.assertEquals("Hello Idris", helloResponse.getHello());
        });
    }

    @Test
    void basicAuthSuccess() throws Exception {
        mockMvc.perform(
                post("/auth/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "wrong")
                        .param("password", "wrong")
        ).andExpectAll(
                status().isOk(),
                content().string(Matchers.containsString("OK"))
        );
    }

    @Test
    void basicAuthFailed() throws Exception {
        mockMvc.perform(
                post("/auth/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "uName")
                        .param("password", "psw")
        ).andExpectAll(
                status().isUnauthorized(),
                content().string(Matchers.containsString("KO"))
        );
    }

    @Test
    void createPerson() throws Exception {
        mockMvc.perform(
                post("/person")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("firstName", "Idris")
                        .param("lastName", "Cahyono")
                        .param("email", "idris@idriscahyono.com")
                        .param("phone", "+62895338083588")
                        .param("address.street", "Indonesia")
                        .param("address.city", "Indonesia")
                        .param("address.country", "Indonesia")
                        .param("address.postalCode", "Indonesia")
                        .param("socialMedias[0].name", "Instagram")
                        .param("socialMedias[0].location", "Malaysia")
                        .param("socialMedias[1].name", "Twitter")
                        .param("socialMedias[1].location", "Singapore")
                        .param("hobbies[0]", "GYM")
                        .param("hobbies[1]", "Fishing")

        ).andExpectAll(
                status().isOk(),
                content().string(Matchers.containsString("Success create person Idris Cahyono +62895338083588 idris@idriscahyono.com with address Indonesia Indonesia Indonesia Indonesia"))
        );
    }

    @Test
    void createPersonApi() throws Exception {
        CreatePersonRequest request = new CreatePersonRequest();
        request.setFirstName("Idris");
        request.setLastName("Cahyono");
        request.setEmail("idris@idriscahyono.com");
        request.setPhone("+62895338083588");
        request.setHobbies(List.of("GYM", "Coding"));
        request.setSocialMedias(new ArrayList<>());
        request.getSocialMedias().add(new CreateSocialMediaRequest("Instagram", "instagram.com/idriscahyonoo"));
        request.getSocialMedias().add(new CreateSocialMediaRequest("Faceboook", "facebook.com/idriscahyonoo"));


        String jsonRequest = objectMapper.writeValueAsString(request);
        mockMvc.perform(
                post("/api/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
        ).andExpectAll(
                status().isOk(),
                content().json(jsonRequest)
        );
    }

    @Test
    void createPersonApiValidationError() throws Exception {
        CreatePersonRequest request = new CreatePersonRequest();
        request.setLastName("Cahyono");
        request.setPhone("+62895338083588");
        request.setHobbies(List.of("GYM", "Coding"));
        request.setSocialMedias(new ArrayList<>());
        request.getSocialMedias().add(new CreateSocialMediaRequest("Instagram", "instagram.com/idriscahyonoo"));
        request.getSocialMedias().add(new CreateSocialMediaRequest("Faceboook", "facebook.com/idriscahyonoo"));


        String jsonRequest = objectMapper.writeValueAsString(request);
        mockMvc.perform(
                post("/api/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
        ).andExpectAll(
                status().isBadRequest(),
                content().string(Matchers.containsString("Validation Error : "))
        );
    }
}
