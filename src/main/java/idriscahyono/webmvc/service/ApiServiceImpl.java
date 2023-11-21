package idriscahyono.webmvc.service;

import org.springframework.stereotype.Service;

@Service
public class ApiServiceImpl implements ApiService{
    @Override
    public String hello(String name) {
        if (name == null){
            return "Hello Guest";
        }else{
            return "Hello " + name;
        }
    }
}
