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
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.io.IOException;
import java.util.List;

public class BinaryMemcacheDecoder extends ByteToMessageDecoder {

    private static final byte[] NO_BYTES = new byte[0];

    private static final int BATCH_SIZE = 16;

    private BinaryResponse replies = new BinaryResponse();

    public BinaryMemcacheDecoder() {
    }

    @Override
    protected void decode(final ChannelHandlerContext ctx, final ByteBuf buf, final List<Object> out) throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private IOException fail(final ByteBuf buf, final String message) {
        buf.resetReaderIndex();
        return new IOException(message);
    }
}
