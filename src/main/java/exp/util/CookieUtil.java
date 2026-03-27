package exp.util;

import com.vaadin.flow.server.VaadinService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {
    public static void saveLanguageCookie(String key, String value) {
        var response = VaadinService.getCurrentResponse();
        if (response instanceof HttpServletResponse httpResponse) {
            if (key != null && value != null) {
                Cookie cookie = new Cookie(key, value);
                cookie.setPath("/");
                cookie.setMaxAge(60 * 60 * 24 * 365);
                httpResponse.addCookie(cookie);
            }
        }
    }

    public static String readCookie(String key) {
        var request = VaadinService.getCurrentRequest();
        if (request != null) {
            var cookies = request.getCookies();
            if (cookies != null) {
                for (var c : cookies) {
                    if (key.equals(c.getName())) {
                        return c.getValue();
                    }
                }
            }
        }
        return "";
    }
}
