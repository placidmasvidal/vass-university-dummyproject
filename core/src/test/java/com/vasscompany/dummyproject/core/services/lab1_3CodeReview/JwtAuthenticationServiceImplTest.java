/*package com.vasscompany.dummyproject.core.services.lab1_3CodeReview;

import com.vasscompany.dummyproject.core.services.lab1_3CodeReview.impl.JwtAuthenticationServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtAuthenticationServiceImplTest {

    private final JwtAuthenticationServiceImpl service = new JwtAuthenticationServiceImpl();

    @Test
    void testCreateTokenSuccess() {
        service.activate();
        String token = service.createToken("admin", "password");
        assertNotNull(token);
    }

    @Test
    void testCreateTokenInvalidUser() {
        service.activate();
        String token = service.createToken("nonexistent", "password");
        assertNull(token);
    }

    @Test
    void testVerifyTokenSuccess() {
        service.activate();
        String token = service.createToken("admin", "password");
        assertNotNull(service.verifyToken(token));
    }

    @Test
    void testRegisterUser() {
        service.activate();
        boolean result = service.registerUser("newuser", "password123", "new@test.com");
        assertTrue(result);
    }
}
*/
