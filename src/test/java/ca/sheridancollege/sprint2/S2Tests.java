package ca.sheridancollege.sprint2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import ca.sheridancollege.sprint2.beans.Content;
import ca.sheridancollege.sprint2.beans.Member;
import ca.sheridancollege.sprint2.beans.User;
import ca.sheridancollege.sprint2.controllers.AccountController;
import ca.sheridancollege.sprint2.controllers.AdminController;
import ca.sheridancollege.sprint2.controllers.ContentController;
import ca.sheridancollege.sprint2.database.DatabaseAccess;

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

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DatabaseAccess databaseAccess;

    private AccountController accountController;
    private AdminController adminController;

    @Autowired
    private ContentController contentController;

    @BeforeEach
    public void setUp() {
        databaseAccess = mock(DatabaseAccess.class);
        accountController = new AccountController(); // New instance for AccountController
        accountController.da = databaseAccess; // Directly assign the mocked DAO

        adminController = new AdminController(); // New instance for AdminController
        adminController.da = databaseAccess; // Assign the same mocked DAO

        contentController = new ContentController(); // New instance for ContentController
        contentController.da = databaseAccess; // Assign the same mocked DAO
    }

    //
    //
    //
    /************************************************************************************************
     * SPRINT 1 AND 2 TEST CASES
     ************************************************************************************************/
    //
    //
    //

    @Test
    public void testContent() {
        long id = 1;
        String contentBody = "Test content";
        Content content = new Content(id, contentBody);

        assertEquals(id, content.getContentId());
        assertEquals(contentBody, content.getContentBody());
    }

    // @Test
    // public void testUser() {

    //     User user = new User(1L,
    //             "josh@example.com",
    //             "Josh", "Abeto", 9055558888L,
    //             "fakeaccount@example.com",
    //             "Ontario", "Toronto", "A1B2C3",
    //             "password");

    //     assertEquals(1L, user.getUserId());
    //     assertEquals("josh@example.com", user.getEmail());
    //     assertEquals("Josh", user.getFirstName());
    //     assertEquals("Abeto", user.getLastName());
    //     assertEquals(9055558888L, user.getPhone());
    //     assertEquals("fakeaccount@example.com", user.getSecondaryEmail());
    //     assertEquals("Ontario", user.getProvince());
    //     assertEquals("Toronto", user.getCity());
    //     assertEquals("A1B2C3", user.getPostalCode());
    //     assertEquals("password", user.getEncryptedPassword());
    // }

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

    @Autowired
    private DatabaseAccess da;

    @Test
    public void testFindUserAccount() {
        String email = "admin@email.com";
        assertNotNull(da.findUserAccount(email));
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

    //
    //
    //
    /************************************************************************************************
     * SPRINT 3 TEST CASES
     ************************************************************************************************/
    //
    //
    //

    @Test
    public void testGetMyAccountPage_UserExists() {
        // Setup
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        user.setUserId(1L);
        when(databaseAccess.findUserAccount(email)).thenReturn(user);

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn(email);
        SecurityContextHolder.getContext().setAuthentication(auth);

        Model model = mock(Model.class);

        // Call method
        String view = accountController.getMyAccountPage(model);

        // Verify
        assertEquals("myAccount", view);
        verify(model).addAttribute("user", user);
    }

    @Test
    public void testChangeUserInfo_UpdateSuccess() {
        // Setup
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        when(databaseAccess.findUserAccount(email)).thenReturn(user);
        when(databaseAccess.updateUserInfo(anyString(), anyString(), anyString(),
                anyLong(), anyString(), anyString(),
                anyString(), anyString())).thenReturn(true);

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn(email);
        SecurityContextHolder.getContext().setAuthentication(auth);

        RedirectAttributes redirectAttrs = mock(RedirectAttributes.class);

        // Call method
        String view = accountController.changeUserInfo("Josh", "Abeto",
                1234567890L, "", "ON", "Toronto", "A1B2C3", model,
                redirectAttrs);

        // Verify
        assertEquals("redirect:/myAccount", view);
        verify(redirectAttrs).addFlashAttribute("infoUpdated", true);
    }

    @Test
    public void testChangeEmail_Success() {
        // Setup
        String currentEmail = "test@example.com";
        String newEmail = "newemail@example.com";

        when(databaseAccess.updateUserEmail(currentEmail, newEmail)).thenReturn(true);

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn(currentEmail);
        SecurityContextHolder.getContext().setAuthentication(auth);

        RedirectAttributes redirectAttrs = mock(RedirectAttributes.class);
        Model model = mock(Model.class);

        // Call method
        String view = accountController.changeEmail(newEmail, model, redirectAttrs);

        // Verify
        assertEquals("redirect:/myAccount", view);
        verify(redirectAttrs).addFlashAttribute(
                "Message", "Your email has been changed successfully.");

        // Verify that the authentication context has been updated with the new email
        Authentication updatedAuth = SecurityContextHolder.getContext().getAuthentication();
        assertEquals(newEmail, updatedAuth.getName());
    }

    @Test
    public void testChangePassword_Success() {
        // Setup
        String email = "test@example.com";
        String newPassword = "newPassword123";
        String confirmPassword = "newPassword123";
        User user = new User();
        user.setEmail(email);
        user.setEncryptedPassword(new BCryptPasswordEncoder()
                .encode("oldPassword123"));

        when(databaseAccess.findUserAccount(email)).thenReturn(user);
        // Simulate that the new password is different from the current password
        when(databaseAccess.updateUserLogin(newPassword, email)).thenReturn(true);

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn(email);
        SecurityContextHolder.getContext().setAuthentication(auth);

        Model model = mock(Model.class);

        // Call method
        String view = accountController.changePassword(
                newPassword, confirmPassword, model);

        // Verify
        assertEquals("login", view);
        verify(model).addAttribute(
                "message", "Password has been changed successfully!");
    }

    @Test
    public void testDeleteAccount_Success() {
        // Setup
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        when(databaseAccess.findUserAccount(email)).thenReturn(user);
        when(databaseAccess.deleteUser(email)).thenReturn(true);

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn(email);
        SecurityContextHolder.getContext().setAuthentication(auth);

        RedirectAttributes redirectAttrs = mock(RedirectAttributes.class);

        // Call method
        String view = accountController.deleteAccount(redirectAttrs);

        // Verify
        assertEquals("redirect:/", view);
        verify(redirectAttrs).addFlashAttribute("accountDeleted", true);
        verify(databaseAccess).deleteUser(email);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    //
    //
    //
    /************************************************************************************************
     * SPRINT 4 TEST CASES
     ************************************************************************************************/
    //
    //
    //

    @Test
    public void testGetMembersPage() {
        // Setup
        List<Member> members = new ArrayList<>();
        Member member1 = new Member();
        member1.setFirstName("Josh");
        member1.setLastName("Abeto");
        Member member2 = new Member();
        member2.setFirstName("Max");
        member2.setLastName("Denk");

        members.add(member1);
        members.add(member2);

        when(databaseAccess.getAllMembersInfo()).thenReturn(members);
        Model model = mock(Model.class);

        // Call method
        String view = adminController.getMembersPage(model);

        // Verify
        assertEquals("secure/admin/members", view);
        verify(model).addAttribute("members", members);
        verify(databaseAccess).getAllMembersInfo();
    }

    @Test
    public void testChangeUserPassword_Success() {
        // Setup
        String email = "test@example.com";
        String newPassword = "newPassword123";
        String confirmPassword = "newPassword123";

        User user = new User();
        user.setEmail(email);
        user.setEncryptedPassword(new BCryptPasswordEncoder()
                .encode("oldPassword123")); // Simulate current password

        when(databaseAccess.findUserAccount(email)).thenReturn(user);
        when(databaseAccess.updateUserLogin(newPassword, email)).thenReturn(true);

        RedirectAttributes redirectAttrs = mock(RedirectAttributes.class);

        // Call method
        String view = accountController.changeUserPassword(
                email, newPassword, confirmPassword, redirectAttrs);

        // Verify
        assertEquals("redirect:/admin/members", view);
        verify(redirectAttrs).addFlashAttribute(
                "successMessage", "Password changed successfully for user: " + email);
    }

    @Test
    public void testChangeUserPassword_PasswordsDoNotMatch() {
        // Setup
        String email = "test@example.com";
        String newPassword = "newPassword123";
        String confirmPassword = "differentPassword";

        RedirectAttributes redirectAttrs = mock(RedirectAttributes.class);

        // Call method
        String view = accountController.changeUserPassword(
                email, newPassword, confirmPassword, redirectAttrs);

        // Verify
        assertEquals("redirect:/admin/members", view);
        verify(redirectAttrs).addFlashAttribute(
                "errorMessage", "Passwords do not match.");
    }

    @Test
    public void testChangeUserPassword_SamePassword() {
        // Setup
        String email = "test@example.com";
        String newPassword = "oldPassword123"; // Same as current password
        String confirmPassword = "oldPassword123";

        User user = new User();
        user.setEmail(email);
        user.setEncryptedPassword(new BCryptPasswordEncoder()
                .encode(newPassword)); // Current password

        when(databaseAccess.findUserAccount(email)).thenReturn(user);
        RedirectAttributes redirectAttrs = mock(RedirectAttributes.class);

        // Call method
        String view = accountController.changeUserPassword(
                email, newPassword, confirmPassword, redirectAttrs);

        // Verify
        assertEquals("redirect:/admin/members", view);
        verify(redirectAttrs).addFlashAttribute("errorMessage",
                "New password cannot be the same as the current password.");
    }

    @Test
    public void testSelectMembership_Success() {
        // Setup
        String email = "test@example.com";
        String membershipType = "alumni";
        User user = new User();
        user.setEmail(email);
        user.setUserId(1L);

        when(databaseAccess.findUserAccount(email)).thenReturn(user);
        doNothing().when(databaseAccess).updateUserMembership(
                anyLong(), anyInt(), anyBoolean(), any());

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn(email);
        SecurityContextHolder.getContext().setAuthentication(auth);

        RedirectAttributes redirectAttrs = mock(RedirectAttributes.class);

        // Call method

        String view = accountController.selectMembership(membershipType, redirectAttrs);

        // Verify
        assertEquals("redirect:/myAccount", view);
        verify(redirectAttrs).addFlashAttribute(
                "message", "Membership has been updated successfully.");
        verify(databaseAccess).updateUserMembership(
                user.getUserId(), 1, false, null);
    }

    @Test
    public void testSelectMembership_UserNotFound() {
        // Setup
        String email = "test@example.com";
        String membershipType = "alumni";

        when(databaseAccess.findUserAccount(email)).thenReturn(null);

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn(email);
        SecurityContextHolder.getContext().setAuthentication(auth);

        RedirectAttributes redirectAttrs = mock(RedirectAttributes.class);

        // Call method
        String view = accountController.selectMembership(membershipType, redirectAttrs);
        // Verify
        assertEquals("redirect:/myAccount", view);
        verify(redirectAttrs).addFlashAttribute(
                "error", "There was a problem finding your account.");
    }

    @Test
    public void testSelectMembership_UpdateFailure() {
        // Setup
        String email = "test@example.com";
        String membershipType = "general";
        User user = new User();
        user.setEmail(email);
        user.setUserId(1L);

        when(databaseAccess.findUserAccount(email)).thenReturn(user);
        doThrow(new RuntimeException("Update failed")).when(databaseAccess)
                .updateUserMembership(anyLong(), anyInt(),
                        anyBoolean(), any());

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn(email);
        SecurityContextHolder.getContext().setAuthentication(auth);

        RedirectAttributes redirectAttrs = mock(RedirectAttributes.class);

        // Call method
        String view = accountController.selectMembership(membershipType, redirectAttrs);

        // Verify
        assertEquals("redirect:/myAccount", view);
        verify(redirectAttrs).addFlashAttribute(
                "error", "There was a problem updating your account.");
    }

    @Test
    public void testSelectMembership_InvalidMembershipType() {
        // Setup
        String email = "test@example.com";
        String membershipType = "poggers";
        User user = new User();
        user.setEmail(email);
        user.setUserId(1L);

        when(databaseAccess.findUserAccount(email)).thenReturn(user);

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn(email);
        SecurityContextHolder.getContext().setAuthentication(auth);

        RedirectAttributes redirectAttrs = mock(RedirectAttributes.class);

        // Call method
        String view = accountController.selectMembership(membershipType, redirectAttrs);

        // Verify
        assertEquals("redirect:/myAccount", view);
        verify(redirectAttrs).addFlashAttribute(
                "error", "Invalid Membership Type");
        verify(databaseAccess, never())
                .updateUserMembership(anyLong(), anyInt(), anyBoolean(), any());
    }

    //
    //
    //
    /************************************************************************************************
     * SPRINT 5 TEST CASES
     ************************************************************************************************/
    //
    //
    //

    @Test
    public void testMarkPaidSubmit_Success() {
        // Setup
        String paidMemberList = "member1,member2";
        String paidToggle = "true";
        String tier = "gold";
        String datePaid = "2024-11-05";
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

        when(databaseAccess.updatePaidInfo(paidMemberList, Boolean.parseBoolean(paidToggle), tier, datePaid))
                .thenReturn(true);

        // Call method
        String result = accountController.markPaidSubmit(paidMemberList, paidToggle, tier, datePaid,
                redirectAttributes);

        // Verify
        assertEquals("redirect:/admin/members", result);
        assertTrue(redirectAttributes.getFlashAttributes().containsKey("successMessage"));
        assertEquals("User memberships updated successfully.",
                redirectAttributes.getFlashAttributes().get("successMessage"));
    }

    @Test
    public void testMarkPaidSubmit_Failure() {
        // Setup
        String paidMemberList = "invalidMember";
        String paidToggle = "true";
        String tier = "silver";
        String datePaid = "2024-11-05";
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

        // Mocks failure response
        when(databaseAccess.updatePaidInfo(paidMemberList, Boolean.parseBoolean(paidToggle), tier, datePaid))
                .thenReturn(false);

        // Call method
        String result = accountController.markPaidSubmit(paidMemberList, paidToggle, tier, datePaid,
                redirectAttributes);

        // Verify
        assertEquals("redirect:/admin/members", result);
        assertTrue(redirectAttributes.getFlashAttributes().containsKey("errorMessage"));
        assertEquals("Invalid user entered.", redirectAttributes.getFlashAttributes().get("errorMessage"));
    }

    @Test
    public void testTogglePageVisibility_Success() {
        // Setup
        Map<String, Object> payload = new HashMap<>();
        payload.put("contentId", 123L);
        payload.put("isVisible", true);

        when(databaseAccess.updatePageHiddenStatus(123L, true)).thenReturn(true);

        // Call method
        ResponseEntity<String> response = contentController.togglePageVisibility(payload);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Visibility updated successfully", response.getBody());
    }

    @Test
    public void testTogglePageVisibility_MissingParameters() {
        // Setup
        Map<String, Object> payload = new HashMap<>(); // Missing contentId and isVisible

        // Call method
        ResponseEntity<String> response = contentController.togglePageVisibility(payload);

        // Verify
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Missing required parameters.", response.getBody());
    }

    @Test
    public void testTogglePageVisibility_UpdateFailure() {
        // Setup
        Map<String, Object> payload = new HashMap<>();
        payload.put("contentId", 123L);
        payload.put("isVisible", false);

        when(databaseAccess.updatePageHiddenStatus(123L, false)).thenReturn(false);

        // Call method
        ResponseEntity<String> response = contentController.togglePageVisibility(payload);

        // Verify
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to update visibility.", response.getBody());
    }

    @Test
    public void testChangeUserPermissions_Success() {
        // Setup
        long userId = 123L;
        Integer perm = 1;
        RedirectAttributes redirectAttrs = new RedirectAttributesModelMap();

        when(databaseAccess.updateUserPermissions(perm, userId)).thenReturn(true);

        // Call method
        String result = adminController.changeUserPermissions(userId, perm, redirectAttrs);

        // Verify
        assertEquals("redirect:/admin/members", result);
        assertTrue(redirectAttrs.getFlashAttributes().containsKey("successMessage"));
        assertEquals("Permissions successfully changed", redirectAttrs.getFlashAttributes().get("successMessage"));
    }

    @Test
    public void testChangeUserPermissions_Failure() {
        // Setup
        long userId = 123L;
        Integer perm = 1;
        RedirectAttributes redirectAttrs = new RedirectAttributesModelMap();

        when(databaseAccess.updateUserPermissions(perm, userId)).thenReturn(false);

        // Call method
        String result = adminController.changeUserPermissions(userId, perm, redirectAttrs);

        // Verify
        assertEquals("redirect:/admin/members", result);
        assertTrue(redirectAttrs.getFlashAttributes().containsKey("errorMessage"));
        assertEquals("System error: User password not updated", redirectAttrs.getFlashAttributes().get("errorMessage"));
    }
}
