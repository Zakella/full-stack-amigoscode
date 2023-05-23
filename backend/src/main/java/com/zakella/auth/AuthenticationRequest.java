package com.zakella.auth;

public record AuthenticationRequest(
        String username,
        String password
) {
}