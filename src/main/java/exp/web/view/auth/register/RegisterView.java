package exp.web.view.auth.register;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import exp.web.view.lang.LanguageSelector;
import lombok.Getter;

import static exp.web.config.StaticData.registerEndPoint;

@AnonymousAllowed
@Route(registerEndPoint)
@Getter
public class RegisterView extends VerticalLayout {
    RegisterAddition registerAddition;
    final String languageSelectorGravity = "top-right";
    TextField nameField = new TextField("");
    PasswordField passwordField = new PasswordField("");
    FormLayout formLayout = new FormLayout();
    Button doRegister = new Button("");
    Button goToLogin = new Button("");
    HorizontalLayout hWrapper = new HorizontalLayout();
    LanguageSelector languageSelector;
    String notificationText = "";

    public RegisterView() {
        // Корневой VerticalLayout на весь экран
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER); // вертикальное центрирование

        registerAddition = new RegisterAddition(this);

        wrapperInput();
    }

    private void wrapperInput() {
        formLayout.setWidth("400px"); // ширина формы
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1)); // одна колонка
        formLayout.add(nameField, passwordField, doRegister, goToLogin);
        formLayout.setColspan(doRegister, 1);

        // горизонтальное центрирование
        hWrapper.setWidthFull(); // растягиваем на всю ширину
        hWrapper.setJustifyContentMode(JustifyContentMode.CENTER);
        hWrapper.add(formLayout);

        add(hWrapper);
    }
}