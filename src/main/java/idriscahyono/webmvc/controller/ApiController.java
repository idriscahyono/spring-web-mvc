package idriscahyono.webmvc.controller;

import idriscahyono.webmvc.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class ApiController {

    @Autowired
    private ApiService apiService;

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

        return "Success save profile " + name + " to " + path;
    }

}
