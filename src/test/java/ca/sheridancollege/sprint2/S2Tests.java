package ca.sheridancollege.sprint2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import ca.sheridancollege.sprint2.beans.Content;
import ca.sheridancollege.sprint2.beans.User;
import ca.sheridancollege.sprint2.controllers.ContentController;
import ca.sheridancollege.sprint2.database.DatabaseAccess;
import org.springframework.ui.Model;


@AutoConfigureMockMvc
@SpringBootTest
class S2Tests {

    Model model = new Model() {
        @Override
        public Model addAttribute(String attributeName, Object attributeValue) {
            return null;
        }

        @Override
        public Model addAttribute(Object attributeValue) {
            return null;
        }

        @Override
        public Model addAllAttributes(Collection<?> attributeValues) {
            return null;
        }

        @Override
        public Model addAllAttributes(Map<String, ?> attributes) {
            return null;
        }

        @Override
        public Model mergeAttributes(Map<String, ?> attributes) {
            return null;
        }

        @Override
        public boolean containsAttribute(String attributeName) {
            return false;
        }

        @Override
        public Object getAttribute(String attributeName) {
            return null;
        }

        @Override
        public Map<String, Object> asMap() {
            return null;
        }
    };

    @Test
    public void testContent() {
        long id = 1;
        String contentBody = "Test content";
        Content content = new Content(id, contentBody);

        assertEquals(id, content.getContentId());
        assertEquals(contentBody, content.getContentBody());
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

    @Test
    public void testContentController() {
        DatabaseAccess databaseAccess = mock(DatabaseAccess.class);
        ContentController controller = new ContentController();
        controller.da = databaseAccess;

        Content content = new Content(1, "Test content");

        controller.saveContent(content, model);

        verify(databaseAccess).saveContent(1, "Test content");
    }

    @Test
    public void testSaveContent() {
        NamedParameterJdbcTemplate jdbc = mock(NamedParameterJdbcTemplate.class);
        DatabaseAccess databaseAccess = new DatabaseAccess();
        databaseAccess.jdbc = jdbc;

        long id = 1;
        String contentBody = "Test content";

        databaseAccess.saveContent(id, contentBody);

        verify(jdbc).update(anyString(), any(MapSqlParameterSource.class));
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

//    @Test
//    public void testGetRegisterPage() throws Exception {
//        mockMvc.perform(get("/register"))
//                .andExpect(status().isOk());
//    }

    @Autowired
    private DatabaseAccess databaseAccess;

    @Test
    public void testFindUserAccount() {
        String email = "admin@email.com";
        assertNotNull(databaseAccess.findUserAccount(email));
    }

    @Test
    public void testGetRolesById() {
        NamedParameterJdbcTemplate jdbc = mock(NamedParameterJdbcTemplate.class);
        DatabaseAccess databaseAccess = new DatabaseAccess();
        databaseAccess.jdbc = jdbc;

        long userId = 1;
        List<String> expectedRoles = new ArrayList<>();
        expectedRoles.add("ROLE_USER");

        // Simulate the rows returned by the query
        List<Map<String, Object>> rows = new ArrayList<>();
        Map<String, Object> row = new HashMap<>();
        row.put("roleName", "ROLE_USER");
        rows.add(row);

        // Configure the mock behavior
        when(jdbc.queryForList(anyString(), any(SqlParameterSource.class)))
                .thenReturn(rows);

        // Call the method under test
        List<String> actualRoles = databaseAccess.getRolesById(userId);

        // Verify the result
        assertEquals(expectedRoles, actualRoles);
    }


    @Test
    public void testPasswordEncoder() {
        BCryptPasswordEncoder expectedEncoder = mock(BCryptPasswordEncoder.class);
        DatabaseAccess databaseAccess = new DatabaseAccess() {
            @Override
            public BCryptPasswordEncoder passworEncoder() {
                return expectedEncoder;
            }
        };

        BCryptPasswordEncoder actualEncoder = databaseAccess.passworEncoder();

        assertEquals(expectedEncoder, actualEncoder);
    }

}
