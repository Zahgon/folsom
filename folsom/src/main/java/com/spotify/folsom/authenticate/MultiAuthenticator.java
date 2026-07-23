package com.spotify.folsom.authenticate;

import com.spotify.folsom.MemcacheAuthenticationException;
import com.spotify.folsom.RawMemcacheClient;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class MultiAuthenticator implements Authenticator {

    private final List<? extends Authenticator> authenticators;

    public MultiAuthenticator(List<? extends Authenticator> authenticators) {
        this.authenticators = authenticators;
        if (authenticators.isEmpty()) {
            throw new IllegalStateException("Must not have an empty list of authenticators");
        }
    }

    @Override
    public CompletionStage<RawMemcacheClient> authenticate(RawMemcacheClient client) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void validate(boolean binary) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
