package org.servlet.assignment.user.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.servlet.assignment.user.CustomPrincipal;

import java.security.Principal;
import java.util.Optional;

public final class AuthRequestWrapper extends HttpServletRequestWrapper {

    private final HttpServletRequest request;
    private final CustomPrincipal principal;

    public AuthRequestWrapper(HttpServletRequest request) {
        super(request);
        this.request = request;
        this.principal = Optional.ofNullable(request.getSession(false))
                .filter(e -> e.getAttribute("user") != null)
                .map(session -> (CustomPrincipal) session.getAttribute("user"))
                .orElse(null);
    }

    @Override
    public String getRemoteUser() {
        if (principal != null) {
            return principal.getName();
        }
        return request.getRemoteUser();
    }

    @Override
    public Principal getUserPrincipal() {
        if (principal != null) {
            return principal;
        }
        return request.getUserPrincipal();
    }

    @Override
    public boolean isUserInRole(String role) {
        if (principal != null) {
            return principal.isRole(role);
        }
        return request.isUserInRole(role);
    }
}
