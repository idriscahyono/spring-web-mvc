package idriscahyono.webmvc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import idriscahyono.webmvc.model.CreatePersonRequest;
import idriscahyono.webmvc.model.HelloRequest;
import idriscahyono.webmvc.model.HelloResponse;
import idriscahyono.webmvc.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class ApiController {

    @Autowired
    private ApiService apiService;

    @Autowired
    private ObjectMapper objectMapper;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyMMdd");

    @GetMapping(path = "/hello")
    public void user(@RequestParam(name = "name", required = false) String name, HttpServletResponse response) throws IOException{
        String responseBody = apiService.hello(name);
        response.getWriter().println(responseBody);
    }

    @GetMapping(path = "/date")
    @ResponseBody
    public String date(@RequestParam(name = "date", required = false) Date date) throws IOException {
        return "Date : " + dateFormat.format(date);
    }

    @GetMapping(path = "/orders/{orderId}/products/{productId}")
    @ResponseBody
    public String order(@PathVariable("orderId") String orderId, @PathVariable("productId") String productId){
        return "Order : " + orderId + " Product : " + productId;
    }


    @PostMapping(path = "/upload/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public String upload(@RequestParam(name = "name") String name,
                         @RequestParam(name = "profile") MultipartFile profile) throws IOException {
        Path path = Path.of("upload/" + profile.getOriginalFilename());
        // Files.write(path, profile.getBytes());
        profile.transferTo(path);

        return "Success save profile " + name + " to " + path.getFileName();
    }

    @PostMapping(
            path = "/body/hello",
            consumes =  MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public String body(@RequestBody String requestBody) throws JsonProcessingException {
        HelloRequest request = objectMapper.readValue(requestBody, HelloRequest.class);
        HelloResponse response = new HelloResponse();
        response.setHello("Hello " + request.getName());

        return objectMapper.writeValueAsString(response);
    }

    @PostMapping(path = "/auth/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public ResponseEntity<String>basicAuth(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "password") String password
    ){
        if ("wrong".equals(username) && "wrong".equals(password)){
            return ResponseEntity.status(HttpStatus.OK).body("OK");
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("KO");
        }
    }

    @PostMapping(path = "/person", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public ResponseEntity<StringBuilder> createPerson(
            @ModelAttribute CreatePersonRequest request
    ){
        return ResponseEntity.status(HttpStatus.OK).body(
                new StringBuilder("Success create person " +
                        request.getFirstName() + " " +
                        request.getLastName() + " " +
                        request.getPhone() + " " +
                        request.getEmail() + " with address " +
                        request.getAddress().getStreet() + " " +
                        request.getAddress().getCity() + " " +
                        request.getAddress().getCountry() + " " +
                        request.getAddress().getPostalCode() + " with social medias " +
                        request.getSocialMedias() + " with hobbies " +
                        request.getHobbies()
                )
        );
    }

    @PostMapping(
            path = "/api/person",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public CreatePersonRequest createPersonApi(@RequestBody @Valid CreatePersonRequest request){
        return request;
    }

}