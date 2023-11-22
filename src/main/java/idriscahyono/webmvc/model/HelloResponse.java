package idriscahyono.webmvc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HelloResponse {
    private String hello;

    public void setHello(String hello){
        this.hello = hello;
    }
}
