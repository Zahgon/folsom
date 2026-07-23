package com.spotify.folsom.client.ascii;

import static com.spotify.folsom.MemcacheStatus.UNAUTHORIZED;
import static java.nio.charset.StandardCharsets.US_ASCII;
import com.spotify.folsom.MemcacheStatus;
import com.spotify.folsom.guava.HostAndPort;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class AsciiAuthenticateRequest extends SetRequest {

    private static final byte[] KEY = "ASCII_AUTH".getBytes(StandardCharsets.US_ASCII);

    public AsciiAuthenticateRequest(final String username, final String password) {
        super(Operation.SET, KEY, (username + " " + password).getBytes(US_ASCII), 0, 0, 0);
    }

    @Override
    public void handle(final AsciiResponse response, final HostAndPort server) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
