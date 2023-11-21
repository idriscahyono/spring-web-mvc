package idriscahyono.webmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LivenessController {

    @RequestMapping(path = "/ping")
    public void liveness(HttpServletResponse response) throws IOException{
        response.getWriter().println("PONG");
    }
}
