package exp.web.view.auth.register;

import com.vaadin.flow.component.notification.Notification;
import exp.web.config.StaticData;
import exp.web.view.lang.LanguageSelector;

import java.text.MessageFormat;

import static exp.web.config.StaticData.authEndPoint;

public class RegisterAddition {
    RegisterView view;

    public RegisterAddition(RegisterView view) {
        this.view = view;

        view.languageSelector = new LanguageSelector(view.languageSelectorGravity, lang -> {
            switch (lang.toLowerCase()) {
                case StaticData.ENGLISH -> {
                    view.nameField.setLabel("Login");
                    view.passwordField.setLabel("Password");
                    view.doRegister.setText("Sign up");
                    view.notificationText = "Successfully registered";
                    view.goToLogin.setText("Authorization");
                }

                case StaticData.RUSSIAN -> {
                    view.nameField.setLabel("Логин");
                    view.passwordField.setLabel("Пароль");
                    view.doRegister.setText("Зарегистрироваться");
                    view.notificationText = "Успешно зарегистрировано";
                    view.goToLogin.setText("Авторизация");
                }

                case StaticData.UKRAINIAN -> {
                    view.nameField.setLabel("Логін");
                    view.passwordField.setLabel("Пароль");
                    view.doRegister.setText("Зареєструватися");
                    view.notificationText = "Успішно зареєстровано";
                    view.goToLogin.setText("Авторизація");
                }
            }
        });

        view.goToLogin.addClickListener(e ->
                view.getUI().ifPresent(ui -> ui.navigate(authEndPoint)));

        // Кнопка
        view.doRegister.addSingleClickListener(
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
