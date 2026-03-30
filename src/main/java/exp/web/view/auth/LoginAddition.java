package exp.web.view.auth;

import exp.util.AuthUtil;
import exp.web.config.StaticData;
import exp.web.view.lang.LanguageSelector;
public class LoginAddition {
    LoginView view;

    public LoginAddition(LoginView view) {
        this.view = view;

        view.languageSelector = new LanguageSelector(lang -> {
            switch (lang.toLowerCase()) {
                case StaticData.ENGLISH -> {
                    view.nameField.setLabel("Login");
                    view.passwordField.setLabel("Password");
                    view.doLogin.setText("Sign in");
                    view.notificationText = "Successfully logged in";
                }

                case StaticData.RUSSIAN -> {
                    view.nameField.setLabel("Логин");
                    view.passwordField.setLabel("Пароль");
                    view.doLogin.setText("Авторизоваться");
                    view.notificationText = "Успешно авторизовано";
                }

                default -> {
                    view.nameField.setLabel("Логін");
                    view.passwordField.setLabel("Пароль");
                    view.doLogin.setText("Авторизуватися");
                    view.notificationText = "Успішно авторизовано";
                }
            }
        });

        view.doLogin.addClickListener(event ->
                AuthUtil.doLogin(view.authManager,
                        view,
                        view.nameField.getValue(),
                        view.passwordField.getValue(),
                        view.notificationText,
                        view.securityContextRepository));

        view.add(view.languageSelector);
    }
}
