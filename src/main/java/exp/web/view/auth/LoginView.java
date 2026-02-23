package exp.web.view.auth;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import exp.web.view.lang.LanguageSelector;
import lombok.Getter;

import static exp.web.config.StaticData.authEndPoint;

@AnonymousAllowed
@Route(authEndPoint)
@Getter
public class LoginView extends VerticalLayout {
    private static final String languageSelectorGravity = "top-right";
    TextField nameField = new TextField("Имя");
    PasswordField passwordField = new PasswordField("Пароль");
    FormLayout formLayout = new FormLayout();
    Button saveButton = new Button("Авторизоваться");
    HorizontalLayout hWrapper = new HorizontalLayout();
    LanguageSelector languageSelector;

    public LoginView() {
        // Корневой VerticalLayout на весь экран
        setSizeFull();
        setPadding(false);
        setSpacing(false);

        // Создаём форму
        formLayout.setWidth("400px"); // ширина формы
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1)); // одна колонка

        // Поля
        nameField.setWidthFull();
        passwordField.setWidthFull();
        saveButton.setWidthFull();

        // Кнопка
        saveButton.addSingleClickListener(
                event ->
                        Notification.show(
                                "Сохранено: "
                                        + nameField.getValue()
                                        + " (" + passwordField.getValue() + ")")
        );

        // в конструкторе AuthView
        languageSelector = new LanguageSelector(languageSelectorGravity, lang -> {
            switch (lang.toLowerCase()) {
                case "english" -> {
                    nameField.setLabel("Login");
                    passwordField.setLabel("Password");
                    saveButton.setText("Sign in");
                }

                case "русский" -> {
                    nameField.setLabel("Логин");
                    passwordField.setLabel("Пароль");
                    saveButton.setText("Авторизоваться");
                }

                case "українська" -> {
                    nameField.setLabel("Логін");
                    passwordField.setLabel("Пароль");
                    saveButton.setText("Авторизуватися");
                }
            }
        });
        add(languageSelector); // добавляем прямо в корень VerticalLayout


        formLayout.add(nameField, passwordField, saveButton);
        formLayout.setColspan(saveButton, 1);

        // Горизонтальная обёртка для центрирования формы
        hWrapper.setWidthFull(); // растягиваем на всю ширину
        hWrapper.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER); // горизонтальное центрирование
        hWrapper.add(formLayout);

        // Добавляем в корневой VerticalLayout с вертикальным центрированием
        add(hWrapper);
        setAlignItems(FlexComponent.Alignment.CENTER); // горизонтальное центрирование VerticalLayout (не обязательно, но безопасно)
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER); // вертикальное центрирование
    }
}