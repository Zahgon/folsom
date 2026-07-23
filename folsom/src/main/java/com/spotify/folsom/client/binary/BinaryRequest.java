/*
 * Copyright (c) 2014-2018 Spotify AB
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

import com.spotify.folsom.client.AbstractRequest;
import com.spotify.folsom.client.OpCode;
import com.spotify.folsom.guava.HostAndPort;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ThreadLocalRandom;

public abstract class BinaryRequest<V> extends AbstractRequest<V> {

    protected static final int HEADER_SIZE = 24;

    protected static final byte MAGIC_NUMBER = (byte) 0x80;

    protected final int opaque;

    protected BinaryRequest(final byte[] key) {
        super(key);
        opaque = (ThreadLocalRandom.current().nextInt() << 8) & 0xFFFFFF00;
    }

    public void writeHeader(final ByteBuffer dst, final OpCode opCode, final int extraLength, final int valueLength, final long cas) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected ResponsePacket handleSingleReply(BinaryResponse replies) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void handle(final Object response, final HostAndPort server) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected abstract void handle(BinaryResponse response, HostAndPort server) throws IOException;
}
