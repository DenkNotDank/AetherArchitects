package ca.sheridancollege.sprint2.database;

import ca.sheridancollege.sprint2.beans.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class DatabaseAccess {
//    @Autowired
//    @Qualifier("H2-DataSource")
//    DataSource h2DataSource;
//
//    @Autowired
//    @Qualifier("H2JDBC")
//    public NamedParameterJdbcTemplate jdbc;

    @Autowired
    @Qualifier("RDS-DataSource")
    DataSource remoteDataSource;

    @Autowired
    @Qualifier("RemoteJDBC")
    public NamedParameterJdbcTemplate jdbc;


    public User findUserAccount(String email) {
        System.out.println(email);
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String q = "Select * from sec_user where email = :email";
        parameters.addValue("email", email);

        ArrayList<User> users = (ArrayList<User>) jdbc.query(q, parameters,
                new BeanPropertyRowMapper<User>(User.class));

        if (users.size() > 0) {
            System.out.println(users.get(0));
            return users.get(0);
        }
        return null;
    }

    public User findUserPassword(String email) {
        System.out.println(email);
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String q = "Select encryptedPassword from sec_user where email = :email";
        parameters.addValue("email", email);

        ArrayList<User> users = (ArrayList<User>) jdbc.query(q, parameters,
                new BeanPropertyRowMapper<User>(User.class));

        if (users.size() > 0) {
            System.out.println(users.get(0));
            return users.get(0);
        }
        return null;
    }

    public List<String> getRolesById(long userId) {
        ArrayList<String> roles = new ArrayList<>();

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String q = "Select user_role.userid, sec_role.rolename"
                + " From user_role, sec_role"
                + " Where user_role.roleId = sec_role.roleId"
                + " And userId = :userId";
        parameters.addValue("userId", userId);

        List<Map<String, Object>> rows = jdbc.queryForList(q, parameters);
        for (Map<String, Object> row : rows)
            roles.add((String) row.get("roleName"));

        return roles;
    }

    public BCryptPasswordEncoder passworEncoder() {
        return new BCryptPasswordEncoder();
    }

    public void createNewUser(String email, String firstName, String lastName, long phone, String secondaryEmail,
            String province, String city, String postalCode, String password) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String q = "Insert into SEC_USER (email, firstName, lastName, phone, secondaryEmail, province," +
                "city,postalCode ,encryptedPassword, accountEnabled)"
                + " values (:email, :firstName, :lastName, :phone, :secondaryEmail, :province," +
                ":city, :postalCode, :password, 1)";
        parameters.addValue("email", email);
        parameters.addValue("firstName", firstName);
        parameters.addValue("lastName", lastName);
        parameters.addValue("phone", phone);
        parameters.addValue("secondaryEmail", secondaryEmail);
        parameters.addValue("province", province);
        parameters.addValue("city", city);
        parameters.addValue("postalCode", postalCode);
        parameters.addValue("password", passworEncoder().encode(password));
        jdbc.update(q, parameters);
    }

    public boolean updateUserLogin(String password, String email) {
        try {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            String q = "UPDATE SEC_USER "
                    + " SET (encryptedPassword) = :password"
                    + " where email = :email";

            parameters.addValue("password", passworEncoder().encode(password));
            parameters.addValue("email", email);
            jdbc.update(q, parameters);
            return true;
        }
        catch(Exception e){
            System.out.println("An issue has occurred in updating the user login");
            return false;
        }
    }

    public boolean updateUserEmail(String email, String newEmail) {

        try {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            String q = "UPDATE SEC_USER SET email = :newEmail WHERE email = :email";
            parameters.addValue("newEmail", newEmail);
            parameters.addValue("email", email);
            int isUpdated = jdbc.update(q, parameters);
            if (isUpdated == 1) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error Updating email " + e.getMessage());
        }
        return false;
    }

    public boolean updateUserInfo(String email, String firstName, String lastName, long phone, String province,
            String city, String postalCode, String secondaryEmail) {
        try {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            String q = "UPDATE SEC_USER SET firstName = :firstName, lastName = :lastName , phone = :phone , province = :province, city = :city,postalCode = :postalCode,secondaryEmail= :secondaryEmail  WHERE email = :email ";

            parameters.addValue("email", email); // using email to identify the user
            parameters.addValue("firstName", firstName);
            parameters.addValue("lastName", lastName);
            parameters.addValue("phone", phone);
            parameters.addValue("province", province);
            parameters.addValue("city", city);
            parameters.addValue("postalCode", postalCode);
            parameters.addValue("secondaryEmail", secondaryEmail);
            int isUpdated = jdbc.update(q, parameters);
            if (isUpdated == 1) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error updating user info: " + e.getMessage());
            return false;
        }
        return false;
    }

    public void addRole(long userId, long roleId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String q = "Insert into USER_ROLE (userId, roleId) values (:userId, :roleId)";
        parameters.addValue("userId", userId);
        parameters.addValue("roleId", roleId);
        jdbc.update(q, parameters);
    }

    /**
     * @param id      - The unique identifier number assigned to the page content
     * @param content - The stringified html content
     * @return - False=No errors and page content saved. True=something went wrong.
     */
    public boolean saveContent(long id, String content) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String q = "UPDATE CONTENT SET contentBody = :content WHERE contentId = :id";
        parameters.addValue("content", content);
        parameters.addValue("id", id);
        int returnValue = jdbc.update(q, parameters);
        if (returnValue > 0) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * @param id - The unique identifier number assigned to the page content
     * @return - The stringified html content for the particular section
     */
    public String getContent(long id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String q = "Select * from CONTENT where contentId = :id";
        parameters.addValue("id", id);

        // Queries always have a chance of returning more than 1 row so we'll use
        // ArrayList to store results
        ArrayList<Content> contents = (ArrayList<Content>) jdbc.query(q, parameters,
                new BeanPropertyRowMapper<Content>(Content.class));

        // Check that our arrayList actually contains some results
        if (contents.size() > 0) {
//            System.out.println(contents.get(0));

            // String inside of the Content object at index 0 will contain our html
            return contents.get(0).getContentBody();
        }
        return null;
    }

    public boolean deleteUser(String email) {
        try {
            // First, get the user ID
            User user = findUserAccount(email);
            if (user == null) {
                throw new RuntimeException("User not found.");
            }
            long userId = user.getUserId(); // Ensures User class has getUserId method

            System.out.println(email);
            // Delete related roles
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            String deleteRolesQuery = "DELETE FROM USER_ROLE WHERE userId = :userId";
            parameters.addValue("userId", userId);
            int rolesDeleted = jdbc.update(deleteRolesQuery, parameters);

            // Delete the related memebrship info
            parameters = new MapSqlParameterSource();
            String deleteMembershipsQuery = "DELETE FROM USER_MEMBERSHIPS WHERE userId = :userId";
            parameters.addValue("userId", userId);
            int membershipsDeleted = jdbc.update(deleteMembershipsQuery, parameters);

            // Delete the user
            String deleteUserQuery = "DELETE FROM SEC_USER WHERE email = :email";
            parameters = new MapSqlParameterSource();
            parameters.addValue("email", email);
            int usersDeleted = jdbc.update(deleteUserQuery, parameters);

            if (rolesDeleted > 0 && membershipsDeleted > 0 && usersDeleted > 0) {
                System.out.println("User with email " + email + " deleted successfully.");
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error deleting user: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public void updateUserMembership(long userID, int membershipID, boolean paid, Date paidDate) {
        System.out.println("Updating user membership " + userID + " MembershipId " + membershipID);
        try {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("userID", userID);

            String checkQuery = "SELECT COUNT(*) FROM user_memberships WHERE userID = :userID";
            int count = jdbc.queryForObject(checkQuery, parameters, Integer.class);

            if (count > 0) {
                String updateQuery = "UPDATE user_memberships SET membershipID = :membershipID, paid = :paid, paidDate = :paidDate WHERE userID = :userID";
                parameters.addValue("membershipID", membershipID);
                parameters.addValue("paid", paid);
                parameters.addValue("paidDate", paidDate);
                jdbc.update(updateQuery, parameters);
                System.out.println("User membership updated successfully for userID: " + userID);
            } else {
                String insertQuery = "INSERT INTO user_memberships(userID, membershipID, paid, paidDate) VALUES (:userID, :membershipID, :paid, :paidDate)";
                parameters.addValue("membershipID", membershipID);
                parameters.addValue("paid", paid);
                parameters.addValue("paidDate", paidDate);
                jdbc.update(insertQuery, parameters);
                System.out.println("User membership inserted successfully for userID: " + userID);
            }
        } catch (Exception e) {
            System.out.println("Error updating user membership: " + e.getMessage());
            e.printStackTrace();
        }

    }


    public List<Member> getAllMembersInfo() {
        String query = "SELECT SEC_USER.userId, SEC_USER.email, SEC_USER.firstName, " +
                "SEC_USER.lastName, SEC_USER.phone,SEC_USER.secondaryEmail, SEC_USER.province, " +
                "SEC_USER.city, SEC_USER.postalCode, SEC_USER.accountEnabled, " +
                "USER_MEMBERSHIPS.membershipID, USER_MEMBERSHIPS.paid, USER_MEMBERSHIPS.paidDate " +
                "FROM SEC_USER LEFT JOIN USER_MEMBERSHIPS ON SEC_USER.UserId = USER_MEMBERSHIPS.userID;";
        ArrayList<Member> members = (ArrayList<Member>) jdbc.query(query,
                new BeanPropertyRowMapper<Member>(Member.class));
        if (members.size() > 0) {
            return members;
        }
        return null;
    }

    // method to retrieve the membershipID
    public String getUserMembership(long userID) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();

        String q = "SELECT membershipID FROM user_memberships WHERE userID = :userID";
        parameters.addValue("userID", userID);
        try {
            Integer count = jdbc.queryForObject(q, parameters, Integer.class);
            if (count != null) {
                if (count.equals(1)) {
                    return "Alumni";
                } else if (count.equals(2)) {
                    return "General";
                } else if (count.equals(3)) {
                    return "Professional";
                } else {
                    return "None";
                }

            }
            return "None";
        } catch (Exception e) {
            return "None";
        }
    }

    public List<Member> getFilteredList(boolean free, boolean basic, boolean premium,
                                    boolean paid, boolean unpaid, boolean admin,
                                    boolean user, boolean suspended, boolean notSuspended
    , boolean secondary) {

        String q = "SELECT email, secondaryEmail FROM SEC_USER u " +
                "INNER JOIN USER_ROLE r " +
                "ON u.userId = r.userId " +
                "LEFT JOIN USER_MEMBERSHIPS m " +
                "ON m.userId = r.userId ";

        boolean where = false;
        boolean and = false;



        if (free || basic || premium || paid || unpaid) {
            q += "WHERE ";
            where =true;
            //System.out.println("where is " + where + " in f b p p u");
            if (free) {
                //System.out.println("free is true");
                //Include basic members
                q += "membershipId = 1 ";
                and = true;
            }
            if (basic) {
                //System.out.println("basic is true");
                //Include basic members
                if(and){
                    q+="AND ";
                }
                q += "membershipId = 2 ";
                and = true;
            }
            if (premium) {
                //System.out.println("premium is true");
                //Include premium members
                if(and){
                    q+="AND ";
                }
                q += "membershipId = 3 ";
                and = true;
            }
            if (paid) {
                //System.out.println("paid is true");
                //Include paid members
                if(and){
                    q+="AND ";
                }
                q += "paid = TRUE ";
                and = true;
            }
            if (unpaid) {
                //System.out.println("unpaid is true");
                //Include unpaid members
                if(and){
                    q+="AND ";
                }
                q += "paid = FALSE ";
                and = true;
            }
        }

        if (admin || user) {
            if(!where){
                q+="WHERE ";
                where = true;
            }
            //System.out.println("where is " + where + " in a u");

        if (admin) {
            //System.out.println("admin is true");
            //Include admins
            if(and){
                q+="AND ";
            }
            q += "roleId = 1 ";
            and = true;
        }
        if (user) {
            //System.out.println("user is true");
            //Include users
            if(and){
                q+="AND ";
            }
            q += "roleId = 2 ";
            and = true;
        }
    }

       if(suspended || notSuspended) {

           if(!where){
               q+="WHERE ";
               where = true;
           }
           //System.out.println("where is " + where + " in s n");

            if (suspended) {
                //System.out.println("suspended is true");
                //Include suspended accounts
                if(and){
                    q+="AND ";
                }
                q += "accountEnabled = FALSE ";
                and = true;
            }
            if (notSuspended) {
                //System.out.println("notSuspended is true");
                //Include non suspended members
                if(and){
                    q+="AND ";
                }
                q += "accountEnabled = TRUE ";
                and = true;
            }
        }

       if(secondary){
           if(!where){
               q+="WHERE ";
               where = true;
           }
           if(and){
               q+="AND ";
           }
           q += "secondaryEmail IS NOT NULL";
       }


        ArrayList<Member> emails = (ArrayList<Member>) jdbc.query(q,
                new BeanPropertyRowMapper<Member>(Member.class));

        return emails;

    }



    public List<String> getAllEmails(){
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String q = "(SELECT email FROM SEC_USER) UNION (SELECT secondaryEmail FROM SEC_USER WHERE secondaryEmail IS NOT NULL)";
        try{
            List<String> allEmails = (List<String>) jdbc.query(q, parameters,
                    new BeanPropertyRowMapper<String>(String.class));
            return allEmails;
        }
        catch(Exception e){
            System.out.println("There was an error in retrieving all the user emails.");
        }
        return null;
    }



}
