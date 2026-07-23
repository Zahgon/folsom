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

import com.spotify.folsom.MemcacheStatus;
import com.spotify.folsom.client.OpCode;
import com.spotify.folsom.client.Request;
import com.spotify.folsom.client.Utils;
import com.spotify.folsom.guava.HostAndPort;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import java.io.IOException;
import java.nio.ByteBuffer;

public class SetRequest extends BinaryRequest<MemcacheStatus> implements com.spotify.folsom.client.SetRequest {

    private final OpCode opcode;

    private final byte[] value;

    private final int ttl;

    private final long cas;

    private final int flags;

    public SetRequest(final OpCode opcode, final byte[] key, final byte[] value, final int ttl, final long cas) {
        this(opcode, key, value, ttl, cas, 0);
    }

    public SetRequest(final OpCode opcode, final byte[] key, final byte[] value, final int ttl, final long cas, final int flags) {
        super(key);
        this.opcode = opcode;
        this.value = value;
        this.ttl = ttl;
        this.cas = cas;
        this.flags = flags;
    }

    @Override
    public ByteBuf writeRequest(final ByteBufAllocator alloc, final ByteBuffer dst) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Request<MemcacheStatus> duplicate() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static ByteBuf toBufferWithValue(final ByteBufAllocator alloc, ByteBuffer dst, byte[] value) {
        ByteBuf buffer = toBuffer(alloc, dst, value.length);
        buffer.writeBytes(value);
        return buffer;
    }

    @Override
    public void handle(final BinaryResponse replies, final HostAndPort server) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public byte[] getValue() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public OpCode getOpCode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean withCas() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
