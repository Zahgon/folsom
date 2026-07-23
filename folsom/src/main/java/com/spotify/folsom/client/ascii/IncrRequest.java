/*
 * Copyright (c) 2014-2015 Spotify AB
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.spotify.folsom.client.ascii;

import com.spotify.folsom.MemcacheAuthenticationException;
import com.spotify.folsom.client.Request;
import com.spotify.folsom.client.ascii.AsciiResponse.Type;
import com.spotify.folsom.guava.HostAndPort;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class IncrRequest extends AsciiRequest<Long> {

    private static final byte[] INCR_CMD = "incr ".getBytes(StandardCharsets.US_ASCII);

    private static final byte[] DECR_CMD = "decr ".getBytes(StandardCharsets.US_ASCII);

    private final byte[] operation;

    private final long by;

    private IncrRequest(final byte[] operation, final byte[] key, final long by) {
        super(key);
        this.operation = operation;
        this.by = by;
    }

    public static IncrRequest createIncr(final byte[] key, final long value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static IncrRequest createDecr(final byte[] key, final long value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public ByteBuf writeRequest(final ByteBufAllocator alloc, final ByteBuffer dst) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Request<Long> duplicate() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void handle(final AsciiResponse response, final HostAndPort server) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public long getBy() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public long multiplier() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
