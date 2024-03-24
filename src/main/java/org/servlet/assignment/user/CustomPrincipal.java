package org.servlet.assignment.user;

import lombok.Getter;

import java.security.Principal;
import java.util.Objects;

public class CustomPrincipal implements Principal {

    private final String username;
    private final String role;

    public CustomPrincipal(String username, String role) {
        this.username = Objects.requireNonNull(username, () -> "Principal name is required");
        this.role = Objects.requireNonNull(role, () -> "Principal role is required");
    }

    @Override
    public String getName() {
        return username;
    }

    public boolean isRole(String role) {
        return this.role.equalsIgnoreCase(role);
    }

}
