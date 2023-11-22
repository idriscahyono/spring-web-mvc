package idriscahyono.webmvc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

public class ErrorPageController {

    @RequestMapping(path = "/error")
    public ResponseEntity<String> error(HttpServletRequest request){
        Integer status = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String message = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);

        String html = "<html>\n"
                + "<body>\n"
                + "<h1>Hello World</h1>\n"
                + "</body>\n"
                + "</html>";

        return ResponseEntity.status(status).body(html);
    }
}
