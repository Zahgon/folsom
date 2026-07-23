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

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.List;

public class AsciiMemcacheDecoder extends ByteToMessageDecoder {

    private static final int MAX_RESPONSE_LINE = 500;

    private final ByteBuffer line = ByteBuffer.allocate(MAX_RESPONSE_LINE);

    private final ByteBuffer token = ByteBuffer.allocate(MAX_RESPONSE_LINE);

    private final Charset charset;

    private boolean valueMode = false;

    private ValueAsciiResponse valueResponse = new ValueAsciiResponse();

    private StatsAsciiResponse statsAsciiResponse = new StatsAsciiResponse();

    private boolean consumed;

    private byte[] key = null;

    private byte[] value = null;

    private long cas = 0;

    private int flags = 0;

    private int valueOffset;

    public AsciiMemcacheDecoder(Charset charset) {
        this.charset = charset;
    }

    @Override
    protected void decode(final ChannelHandlerContext ctx, final ByteBuf buf, final List<Object> out) throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void readNextToken() {
        token.clear();
        while (line.hasRemaining()) {
            byte b = line.get();
            if (b == ' ') {
                break;
            }
            token.put(b);
        }
        token.flip();
    }

    private IOException fail() {
        return new IOException("Unexpected line: " + toString(line));
    }

    private String toString(final ByteBuffer buf) {
        buf.rewind();
        return new String(buf.array(), 0, buf.remaining(), charset);
    }

    private void expect(byte firstChar, final String compareTo) throws IOException {
        if (firstChar != compareTo.charAt(0)) {
            throw fail();
        }
        final int length = compareTo.length();
        if (length != token.remaining() + 1) {
            throw fail();
        }
        for (int i = 1; i < length; i++) {
            if (token.get() != compareTo.charAt(i)) {
                throw fail();
            }
        }
    }

    private long parseLong(byte firstChar, ByteBuffer token) throws IOException {
        // firstChar must be guarantee to be a digit.
        long res = firstChar - '0';
        if (res < 0 || res > 9) {
            throw fail();
        }
        while (token.hasRemaining()) {
            final int digit = token.get() - '0';
            if (digit < 0 || digit > 9) {
                throw fail();
            }
            res *= 10;
            res += digit;
        }
        return res;
    }

    private ByteBuffer readLine(final ByteBuf buf, final int available) throws IOException {
        if (consumed) {
            consumed = false;
            line.clear();
        }
        for (int i = 0; i < available - 1; i++) {
            final byte b = buf.readByte();
            if (b == '\r') {
                if (buf.readByte() == '\n') {
                    consumed = true;
                    line.flip();
                    return line;
                }
                throw new IOException("Expected newline, got something else");
            }
            line.put(b);
        }
        return null;
    }
}
