package idriscahyono.webmvc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePersonRequest {
    @NotBlank
    private String firstName;

    private String lastName;

    @NotBlank
    private String email;

    private String phone;

    private CreateAddressRequest address;

    private List<CreateSocialMediaRequest> socialMedias;

    private List<String> hobbies;
}
