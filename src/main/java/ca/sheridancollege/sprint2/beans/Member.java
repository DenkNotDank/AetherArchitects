package ca.sheridancollege.sprint2.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Member {
    //Due to the limitations of Java, we need to create a new object "Member" to handle the
    //SQL inner join data between the SEC_USERS and USER_MEMBERSHIPS table.
    private long userId;
    private String email;
    private String firstName;
    private String lastName;
    private long phone;
    private String secondaryEmail;
    private String province;
    private String city;
    private String postalCode;
    private int accountEnabled;
    private String membershipID;
    private String paid;
    private String paidDate;
    private long roleId;
    private Boolean mailOpted;

}
