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
package com.spotify.folsom.client.binary;

import com.spotify.folsom.MemcacheAuthenticationException;
import com.spotify.folsom.MemcacheStatus;
import com.spotify.folsom.client.OpCode;
import com.spotify.folsom.client.Request;
import com.spotify.folsom.guava.HostAndPort;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class NoopRequest extends BinaryRequest<Void> {

    // Keys have to be valid, so pick the key "X" even though we will never actually use it.
    private static final byte[] DUMMY_KEY = "X".getBytes(StandardCharsets.US_ASCII);

    public NoopRequest() {
        super(DUMMY_KEY);
    }

    @Override
    public ByteBuf writeRequest(final ByteBufAllocator alloc, final ByteBuffer dst) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Request<Void> duplicate() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void handle(final BinaryResponse replies, final HostAndPort server) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
