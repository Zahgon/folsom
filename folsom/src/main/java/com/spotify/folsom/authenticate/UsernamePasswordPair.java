package com.spotify.folsom.authenticate;

import static java.util.Objects.requireNonNull;

public class UsernamePasswordPair {

    private final String username;

    private final String password;

    public UsernamePasswordPair(final String username, final String password) {
        this.username = requireNonNull(username);
        this.password = requireNonNull(password);
    }

    public AsciiAuthenticator getAsciiAuthenticator() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public PlaintextAuthenticator getPlainTextAuthenticator() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
