package exp.web.view.auth.login;


import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinServletResponse;
import exp.util.CookieUtil;
import exp.web.config.StaticData;
import exp.web.view.lang.LanguageSelector;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

import static exp.web.config.StaticData.mainEndPoint;
import static exp.web.config.StaticData.registerEndPoint;

public class LoginAddition {
    LoginView view;

    public LoginAddition(LoginView view) {
        this.view = view;

        view.languageSelector = new LanguageSelector(view.languageSelectorGravity, lang -> {
            switch (lang.toLowerCase()) {
                case StaticData.ENGLISH -> {
                    view.nameField.setLabel("Login");
                    view.passwordField.setLabel("Password");
                    view.doLogin.setText("Sign in");
                    view.notificationText = "Successfully logged in";
                    view.goToRegister.setText("Registration");
                }

                case StaticData.RUSSIAN -> {
                    view.nameField.setLabel("Логин");
                    view.passwordField.setLabel("Пароль");
                    view.doLogin.setText("Авторизоваться");
                    view.notificationText = "Успешно авторизовано";
                    view.goToRegister.setText("Регистрация");
                }

                default -> {
                    view.nameField.setLabel("Логін");
                    view.passwordField.setLabel("Пароль");
                    view.doLogin.setText("Авторизуватися");
                    view.notificationText = "Успішно авторизовано";
                    view.goToRegister.setText("Реєстрація");
                }
            }
        });

        view.goToRegister.addClickListener(e ->
                view.getUI().ifPresent(ui -> ui.navigate(registerEndPoint)));

        view.doLogin.addClickListener(event -> doLogin());

        view.add(view.languageSelector);
    }

    private void doLogin() {
        String username = view.nameField.getValue();
        String password = view.passwordField.getValue();

        try {
            var token = new UsernamePasswordAuthenticationToken(username, password);
            var auth = view.authManager.authenticate(token);

            // Створюємо новий security context і кладемо туди authentication
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(auth);
            SecurityContextHolder.setContext(context);

            // Беремо поточний request/response Vaadin і явно зберігаємо context в HTTP session
            HttpServletRequest request =
                    ((VaadinServletRequest) VaadinServletRequest.getCurrent()).getHttpServletRequest();

            HttpServletResponse response =
                    ((VaadinServletResponse) VaadinServletResponse.getCurrent()).getHttpServletResponse();

            request.getSession(true); // гарантуємо наявність сесії
            view.securityContextRepository.saveContext(context, request, response);

            Notification.show(view.notificationText);

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
}
