/*
 * Copyright (c) 2014-2020 Spotify AB
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

import static java.nio.charset.StandardCharsets.US_ASCII;
import com.spotify.folsom.MemcacheAuthenticationException;
import com.spotify.folsom.MemcacheStatus;
import com.spotify.folsom.client.Request;
import com.spotify.folsom.guava.HostAndPort;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Deletes a key with CAS check.
 *
 * <p>Since the ASCII protocol does not have a CAS field for the delete command, we instead use a
 * CAS set with a dummy value and negative exptime, which is guaranteed to immediately expire the
 * key.
 */
public class DeleteWithCasRequest extends AsciiRequest<MemcacheStatus> {

    private static final byte[] CMD_BYTES = "cas ".getBytes(US_ASCII);

    private static final byte[] FLAGS_EXPTIME_LENGTH_BYTES = " 0 -1 1 ".getBytes(US_ASCII);

    private static final byte[] VALUE_BYTES = "\r\na\r\n".getBytes(US_ASCII);

    private final long cas;

    public DeleteWithCasRequest(final byte[] key, final long cas) {
        super(key);
        this.cas = cas;
    }

    @Override
    public ByteBuf writeRequest(final ByteBufAllocator alloc, final ByteBuffer dst) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Request<MemcacheStatus> duplicate() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void handle(final AsciiResponse response, final HostAndPort server) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
