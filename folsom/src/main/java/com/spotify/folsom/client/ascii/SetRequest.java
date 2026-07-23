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

import static java.nio.charset.StandardCharsets.US_ASCII;
import com.spotify.folsom.MemcacheAuthenticationException;
import com.spotify.folsom.MemcacheStatus;
import com.spotify.folsom.client.OpCode;
import com.spotify.folsom.client.Request;
import com.spotify.folsom.client.Utils;
import com.spotify.folsom.guava.HostAndPort;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.EnumMap;

public class SetRequest extends AsciiRequest<MemcacheStatus> implements com.spotify.folsom.client.SetRequest {

    private static final EnumMap<Operation, byte[]> CMD;

    private static final byte[] NO_FLAGS = " 0 ".getBytes(US_ASCII);

    static {
        CMD = new EnumMap<>(Operation.class);
        CMD.put(Operation.SET, "set ".getBytes(US_ASCII));
        CMD.put(Operation.ADD, "add ".getBytes(US_ASCII));
        CMD.put(Operation.REPLACE, "replace ".getBytes(US_ASCII));
        CMD.put(Operation.APPEND, "append ".getBytes(US_ASCII));
        CMD.put(Operation.PREPEND, "prepend ".getBytes(US_ASCII));
        CMD.put(Operation.CAS, "cas ".getBytes(US_ASCII));
    }

    private final Operation operation;

    private final byte[] value;

    private final int ttl;

    private final long cas;

    private final int flags;

    SetRequest(final Operation operation, final byte[] key, final byte[] value, final int ttl, final long cas, final int flags) {
        super(key);
        this.operation = operation;
        this.value = value;
        this.ttl = ttl;
        this.cas = cas;
        this.flags = flags;
    }

    public static SetRequest casSet(final byte[] key, final byte[] value, final int ttl, final long cas) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static SetRequest casSet(final byte[] key, final byte[] value, final int ttl, final long cas, final int flags) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static SetRequest create(final Operation operation, final byte[] key, final byte[] value, final int ttl) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static SetRequest create(final Operation operation, final byte[] key, final byte[] value, final int ttl, final int flags) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public ByteBuf writeRequest(ByteBufAllocator alloc, ByteBuffer dst) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Request<MemcacheStatus> duplicate() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static ByteBuf toBufferWithValueAndNewLine(final ByteBufAllocator alloc, ByteBuffer dst, byte[] value) {
        ByteBuf buffer = toBuffer(alloc, dst, value.length + NEWLINE_BYTES.length);
        buffer.writeBytes(value);
        buffer.writeBytes(NEWLINE_BYTES);
        return buffer;
    }

    private static byte[] toFlags(int flags) {
        // Optimize for the common case when no flags are set
        if (flags == 0) {
            return NO_FLAGS;
        }
        return (" " + flags + " ").getBytes(US_ASCII);
    }

    @Override
    public void handle(final AsciiResponse response, final HostAndPort server) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public enum Operation {

        SET,
        ADD,
        REPLACE,
        APPEND,
        PREPEND,
        CAS
    }

    @Override
    public byte[] getValue() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public OpCode getOpCode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean withCas() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
