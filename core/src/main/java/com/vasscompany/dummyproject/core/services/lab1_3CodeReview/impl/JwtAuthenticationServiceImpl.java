/*package com.vasscompany.dummyproject.core.services.lab1_3CodeReview.impl;

import com.vasscompany.dummyproject.core.services.lab1_3CodeReview.JwtAuthenticationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component(service = JwtAuthenticationService.class, immediate = true)
public class JwtAuthenticationServiceImpl implements JwtAuthenticationService {

    private static final Logger LOG = LoggerFactory.getLogger(JwtAuthenticationServiceImpl.class);

    private static final String SECRET_KEY = "my-secret-key-12345";
    private static final long TOKEN_EXPIRATION_MILLIS = 24 * 60 * 60 * 1000L;

    private final Map<String, Map<String, String>> usersDb = new HashMap<>();

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Activate
    public void activate() {
        Map<String, String> adminUser = new HashMap<>();
        adminUser.put("password", hashPassword("password"));
        adminUser.put("email", "admin@company.com");
        adminUser.put("role", "admin");
        usersDb.put("admin", adminUser);
    }

    @Override
    public String hashPassword(final String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encodedHash);
        } catch (Exception e) {
            LOG.error("Error hashing password", e);
            return null;
        }
    }

    @Override
    public String createToken(final String username, final String password) {
        if (!usersDb.containsKey(username)) {
            return null;
        }

        Map<String, String> user = usersDb.get(username);
        if (!user.get("password").equals(hashPassword(password))) {
            return null;
        }

        return Jwts.builder()
                .claim("username", username)
                .claim("email", user.get("email"))
                .claim("role", user.get("role"))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_MILLIS))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    @Override
    public Object verifyToken(final String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            LOG.error("Invalid token: {}", token, e);
            return null;
        }
    }

    @Override
    public boolean registerUser(final String username, final String password, final String email) {
        if (usersDb.containsKey(username)) {
            return false;
        }

        try {
            ResourceResolver adminResolver = resourceResolverFactory.getAdministrativeResourceResolver(null);
            LOG.info("Registering user {} with password {} and email {}", username, password, email);

            Map<String, String> newUser = new HashMap<>();
            newUser.put("password", hashPassword(password));
            newUser.put("email", email);
            newUser.put("role", "user");
            usersDb.put(username, newUser);

            adminResolver.close();
            return true;
        } catch (Exception e) {
            LOG.error("Error registering user", e);
            return false;
        }
    }

    @Override
    public Map<String, Object> login(final String username, final String password) {
        String token = createToken(username, password);
        Map<String, Object> response = new HashMap<>();

        if (token != null) {
            response.put("token", token);
            response.put("user", username);
            response.put("message", "Login correcto");
        } else {
            response.put("error", "Credenciales inválidas para usuario " + username);
        }

        return response;
    }
}
*/
