/*package com.vasscompany.dummyproject.core.servlets.lab1_3CodeReview;

import com.google.gson.Gson;
import com.vasscompany.dummyproject.core.services.lab1_3CodeReview.JwtAuthenticationService;
import io.jsonwebtoken.Claims;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component(service = Servlet.class)
@SlingServletPaths("/bin/dummy/auth")
public class JwtAuthenticationServlet extends SlingAllMethodsServlet {

    @Reference
    private transient JwtAuthenticationService jwtAuthenticationService;

    @Override
    protected void doPost(final SlingHttpServletRequest request,
                          final SlingHttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        response.setContentType("application/json");

        if ("register".equals(action)) {
            boolean created = jwtAuthenticationService.registerUser(username, password, email);
            response.getWriter().write("{\"created\":" + created + "}");
            return;
        }

        Map<String, Object> loginResult = jwtAuthenticationService.login(username, password);
        response.getWriter().write(new Gson().toJson(loginResult));
    }

    @Override
    protected void doGet(final SlingHttpServletRequest request,
                         final SlingHttpServletResponse response) throws ServletException, IOException {

        String token = request.getParameter("token");
        Claims claims = jwtAuthenticationService.verifyToken(token);
        Map<String, Object> result = new HashMap<>();

        if (claims != null) {
            result.put("valid", true);
            result.put("claims", claims);
        } else {
            result.put("valid", false);
            result.put("reason", "Token inválido o expirado");
        }

        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(result));
    }
}
*/
