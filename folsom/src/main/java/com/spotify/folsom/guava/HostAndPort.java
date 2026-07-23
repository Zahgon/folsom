/*
 * Copyright (C) 2011 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.spotify.folsom.guava;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.requireNonNull;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Strings;
import com.google.common.net.HostSpecifier;
import com.google.common.net.InetAddresses;
import java.io.Serializable;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

/**
 * An immutable representation of a host and port.
 *
 * <p>Example usage:
 *
 * <pre>
 * HostAndPort hp = HostAndPort.fromString("[2001:db8::1]")
 *     .withDefaultPort(80)
 *     .requireBracketsForIPv6();
 * hp.getHostText();  // returns "2001:db8::1"
 * hp.getPort();      // returns 80
 * hp.toString();     // returns "[2001:db8::1]:80"
 * </pre>
 *
 * <p>Here are some examples of recognized formats:
 *
 * <ul>
 *   <li>example.com
 *   <li>example.com:80
 *   <li>192.0.2.1
 *   <li>192.0.2.1:80
 *   <li>[2001:db8::1] - {@link #getHostText()} omits brackets
 *   <li>[2001:db8::1]:80 - {@link #getHostText()} omits brackets
 *   <li>2001:db8::1 - Use {@link #requireBracketsForIPv6()} to prohibit this
 * </ul>
 *
 * <p>Note that this is not an exhaustive list, because these methods are only concerned with
 * brackets, colons, and port numbers. Full validation of the host field (if desired) is the
 * caller's responsibility.
 *
 * @author Paul Marks
 * @since 10.0
 */
@Beta
@Immutable
@GwtCompatible
public final class HostAndPort implements Serializable {

    /**
     * Magic value indicating the absence of a port number.
     */
    private static final int NO_PORT = -1;

    /**
     * Hostname, IPv4/IPv6 literal, or unvalidated nonsense.
     */
    private final String host;

    /**
     * Validated port number in the range [0..65535], or NO_PORT
     */
    private final int port;

    /**
     * True if the parsed host has colons, but no surrounding brackets.
     */
    private final boolean hasBracketlessColons;

    private HostAndPort(String host, int port, boolean hasBracketlessColons) {
        this.host = host;
        this.port = port;
        this.hasBracketlessColons = hasBracketlessColons;
    }

    public String getHostText() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean hasPort() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public int getPort() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public int getPortOrDefault(int defaultPort) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static HostAndPort fromParts(String host, int port) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static HostAndPort fromHost(String host) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static HostAndPort fromString(String hostPortString) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Parses a bracketed host-port string, throwing IllegalArgumentException if parsing fails.
     *
     * @param hostPortString the full bracketed host-port specification. Post might not be specified.
     * @return an array with 2 strings: host and port, in that order.
     * @throws IllegalArgumentException if parsing the bracketed host-port string fails.
     */
    private static String[] getHostAndPortFromBracketedHost(String hostPortString) {
        int colonIndex = 0;
        int closeBracketIndex = 0;
        checkArgument(hostPortString.charAt(0) == '[', "Bracketed host-port string must start with a bracket: %s", hostPortString);
        colonIndex = hostPortString.indexOf(':');
        closeBracketIndex = hostPortString.lastIndexOf(']');
        checkArgument(colonIndex > -1 && closeBracketIndex > colonIndex, "Invalid bracketed host/port: %s", hostPortString);
        String host = hostPortString.substring(1, closeBracketIndex);
        if (closeBracketIndex + 1 == hostPortString.length()) {
            return new String[] { host, "" };
        } else {
            checkArgument(hostPortString.charAt(closeBracketIndex + 1) == ':', "Only a colon may follow a close bracket: %s", hostPortString);
            for (int i = closeBracketIndex + 2; i < hostPortString.length(); ++i) {
                checkArgument(Character.isDigit(hostPortString.charAt(i)), "Port must be numeric: %s", hostPortString);
            }
            return new String[] { host, hostPortString.substring(closeBracketIndex + 2) };
        }
    }

    public HostAndPort withDefaultPort(int defaultPort) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public HostAndPort requireBracketsForIPv6() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean equals(@Nullable Object other) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Return true for valid port numbers.
     */
    private static boolean isValidPort(int port) {
        return port >= 0 && port <= 65535;
    }

    private static final long serialVersionUID = 0;
}
