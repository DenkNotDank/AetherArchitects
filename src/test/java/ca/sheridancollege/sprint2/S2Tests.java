package ca.sheridancollege.sprint2;

import ca.sheridancollege.sprint2.beans.User;
import ca.sheridancollege.sprint2.database.DatabaseAccess;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
class S2Tests {

    @Test
    void contextLoads() {
    }


    @Test
    public void testUser() {

        User user = new User(1L,
                "josh@example.com",
                "Josh", "Abeto", 9055558888L,
                "fakeaccount@example.com",
                "Ontario", "Toronto", "A1B2C3",
                "password");

        assertEquals(1L, user.getUserId());
        assertEquals("josh@example.com", user.getEmail());
        assertEquals("Josh", user.getFirstName());
        assertEquals("Abeto", user.getLastName());
        assertEquals(9055558888L, user.getPhone());
        assertEquals("fakeaccount@example.com", user.getSecondaryEmail());
        assertEquals("Ontario", user.getProvince());
        assertEquals("Toronto", user.getCity());
        assertEquals("A1B2C3", user.getPostalCode());
        assertEquals("password", user.getEncryptedPassword());
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetRegisterPage() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk());
    }

    @Autowired
    private DatabaseAccess databaseAccess;

    @Test
    public void testFindUserAccount() {
        String email = "admin@email.com";
        assertNotNull(databaseAccess.findUserAccount(email));
    }

    @Test
    public void testGetRolesById() {
    }

    @Test
    public void testPasswordEncoder() {
        BCryptPasswordEncoder encoder = databaseAccess.passworEncoder();
        assertNotNull(encoder);
    }


}
