package ca.sheridancollege.sprint2.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    // These are the fields / user information we collect
    private long userId;
    private String email;
    private String firstName;
    private String lastName;
    private long phone;
    private String secondaryEmail;
    private String province;
    private String city;
    private String postalCode;
    private String encryptedPassword;

}
