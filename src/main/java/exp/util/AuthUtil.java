package exp.util;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinServletResponse;
import exp.web.config.StaticData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;

import java.util.Objects;

import static exp.web.config.StaticData.mainEndPoint;

public class AuthUtil {
    public static void doLogin(AuthenticationManager authManager,
                               Component view,
                               String username,
                               String password,
                               String notificationText,
                               SecurityContextRepository securityContextRepository) {

        try {
            var token = new UsernamePasswordAuthenticationToken(username, password);
            var auth = authManager.authenticate(token);

            // Створюємо новий security context і кладемо туди authentication
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(auth);
            SecurityContextHolder.setContext(context);

            // Беремо поточний request/response Vaadin і явно зберігаємо context в HTTP session
            HttpServletRequest request =
                    VaadinServletRequest.getCurrent().getHttpServletRequest();

            HttpServletResponse response =
                    VaadinServletResponse.getCurrent().getHttpServletResponse();

            request.getSession(true); // гарантуємо наявність сесії
            securityContextRepository.saveContext(context, request, response);

            Notification.show(notificationText);

            boolean isAdmin = auth.getAuthorities().stream()
                    .anyMatch(a -> Objects.equals(a.getAuthority(), "ROLE_ADMIN"));

            if (isAdmin) {
                view.getUI().ifPresent(ui -> ui.navigate(mainEndPoint));
            } else {
                Notification.show("Нет прав администратора");
            }

        } catch (Exception ex) {
            Notification.show("Неверный логин или пароль");
        }
    }

    public static void doLogout(Component view, SecurityContextRepository securityContextRepository) {
        HttpServletRequest request =
                VaadinServletRequest.getCurrent().getHttpServletRequest();

        HttpServletResponse response =
                VaadinServletResponse.getCurrent().getHttpServletResponse();

        // Очистити security context
        SecurityContextHolder.clearContext();

        // Інваліднути HTTP сесію
        request.getSession(false).invalidate();

        // Очистити context у репозиторії
        if (securityContextRepository != null)
            securityContextRepository.saveContext(
                    SecurityContextHolder.createEmptyContext(),
                    request,
                    response
            );

        view.getUI().ifPresent(ui ->
                ui.getPage().executeJs(
                        "window.history.replaceState(null, null, window.location.href);" +
                                "window.onpageshow = function(event) { if (event.persisted) { window.location.reload(); } };"
                )
        );
        // Редірект (наприклад, на логін)
        //view.getUI().ifPresent(ui -> ui.navigate(StaticData.authEndPoint));
    }
}
