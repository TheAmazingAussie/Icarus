package org.alexdev.icarus.web.util;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.DefaultCookie;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import io.netty.handler.codec.http.cookie.ServerCookieEncoder;

import java.util.Set;

public class CookieUtil {

    public static boolean exists(FullHttpRequest req, String name) {
        Cookie cookie = get(req, name);
        return cookie != null;
    }

    public static Cookie get(FullHttpRequest req, String name) {

        String cookieString = req.headers().get(HttpHeaderNames.COOKIE);

        if (cookieString != null && cookieString.length() > 0) {
            Set<Cookie> cookies = ServerCookieDecoder.LAX.decode(cookieString);

            for (Cookie cookie : cookies) {

                if (name.equalsIgnoreCase(cookie.name())){
                    return cookie;
                }
            }
        }

        return null;
    }

    public static Cookie set(FullHttpResponse req, String name, String value) {

        HttpHeaders httpHeaders = req.headers();

        Cookie cookie = new DefaultCookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setMaxAge(10);

        httpHeaders.add(HttpHeaderNames.SET_COOKIE, ServerCookieEncoder.LAX.encode(cookie));
        return cookie;
    }
}
