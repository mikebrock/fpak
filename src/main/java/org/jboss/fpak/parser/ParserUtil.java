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
                case '\\':
                    b = stream.read();
                    break;

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

    public static int balancedCapture(String chars, int start, char type) {
        int depth = 1;
        char term = type;
        switch (type) {
            case '[':
                term = ']';
                break;
            case '{':
                term = '}';
                break;
            case '(':
                term = ')';
                break;
        }

        if (type == term) {
            for (start++; start < chars.length(); start++) {
                if (chars.charAt(start) == type) {
                    return start;
                }
            }
        } else {
            for (start++; start < chars.length(); start++) {
                if ((start < chars.length()) && (chars.charAt(start) == '/')) {
                    if (start + 1 == chars.length()) {
                        return start;
                    }
                    if (chars.charAt(start + 1) == '/') {
                        start++;
                        while ((start < chars.length()) && (chars.charAt(start) != '\n'))
                            start++;
                    } else if (chars.charAt(start + 1) == '*') {
                        start += 2;
                        while (start < chars.length()) {
                            switch (chars.charAt(start)) {
                                case '*':
                                    if ((start + 1 < chars.length()) && (chars.charAt(start + 1) == '/')) {
                                        break;
                                    }
                                case '\r':
                                case '\n':
                                    break;
                            }
                            start++;
                        }
                    }
                }
                if (start == chars.length()) {
                    return start;
                }
                if ((chars.charAt(start) == '\'') || (chars.charAt(start) == '"')) {
                    start = captureStringLiteral(chars.charAt(start), chars, start, chars.length());
                } else if (chars.charAt(start) == type) {
                    depth++;
                } else if ((chars.charAt(start) == term) && (--depth == 0)) {
                    return start;
                }
            }
        }

        return start;
    }


    private static int captureStringLiteral(final char type, final String expr, int cursor, int length) {
        while ((++cursor < length) && (expr.charAt(cursor) != type)) {
            if (expr.charAt(cursor) == '\\') {
                cursor++;
            }
        }

        return cursor;
    }
}
