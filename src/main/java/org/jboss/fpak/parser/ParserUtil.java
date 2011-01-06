package org.jboss.fpak.parser;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Mike Brock .
 */
public abstract class ParserUtil {
    public static String captureTo(InputStream stream, int to) throws IOException {
        StringBuilder sb = new StringBuilder();
        int b;
        while ((b = stream.read()) != -1) {
            if (b == '{') sb.append('{').append(captureCurlyBlock(stream, false)).append('}');
            else if (b == to) return sb.toString().trim();
            else sb.append((char) b);
        }

        throw new RuntimeException("expected character '"
                + ((char) to) + "': ..." + sb.toString() + " <<");
    }

    public static String captureCurlyBlock(InputStream stream) throws IOException {
        return captureCurlyBlock(stream, true);
    }

    public static String captureCurlyBlock(InputStream stream, boolean startsWith) throws IOException {
        StringBuilder sb = new StringBuilder();

        int b = stream.read();
        if (b != '{') {
            if (startsWith) {
                throw new RuntimeException("expected '{'");
            } else {
                sb.append((char) b);
            }
        }
        int nest = 1;

        Capture:
        while ((b = stream.read()) != -1) {
            switch (b) {
                case '{':
                    ++nest;
                    break;
                case '}':
                    if (--nest == 0) break Capture;
                    break;
            }
            sb.append((char) b);
        }

        if (nest != 0) {
            throw new RuntimeException("unclosed { ... }");
        }

        return sb.toString().trim();
    }
}
