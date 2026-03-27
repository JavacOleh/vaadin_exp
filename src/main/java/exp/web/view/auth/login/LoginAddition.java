package exp.web.view.auth.login;


import com.vaadin.flow.component.notification.Notification;
import exp.web.config.StaticData;
import exp.web.view.lang.LanguageSelector;

import java.text.MessageFormat;

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

        view.doLogin.addSingleClickListener(
                event ->
                        Notification.show(MessageFormat.format("""
                                {2}:
                                - "{0}"
                                - "{1}"
                                """, view.nameField.getValue(), view.passwordField.getValue(), view.notificationText))
        );

        view.add(view.languageSelector); // добавляем прямо в корень VerticalLayout
    }
}
