package idriscahyono.webmvc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HelloRequest {
    private String name;

    public void setName(String name){
        this.name = name;
    }
}
