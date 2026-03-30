package exp.web.view.auth;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import exp.web.repository.UserRepository;
import exp.web.view.lang.LanguageSelector;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.context.SecurityContextRepository;

import static exp.web.config.StaticData.authEndPoint;

@AnonymousAllowed
@Route(authEndPoint)
public class LoginView extends VerticalLayout {
    SecurityContextRepository securityContextRepository;
    AuthenticationManager authManager;
    UserRepository userRepository;
    LoginAddition loginAddition;

    LanguageSelector languageSelector;
    String notificationText = "";

    HorizontalLayout header = new HorizontalLayout(); // Хедер
    VerticalLayout content = new VerticalLayout(); // Контент страницы

    TextField nameField = new TextField("");
    PasswordField passwordField = new PasswordField("");
    FormLayout formLayout = new FormLayout();
    Button doLogin = new Button("");

    HorizontalLayout hWrapper = new HorizontalLayout(); // Центрирование формы

    public LoginView(UserRepository userRepository,
                     AuthenticationManager authManager,
                     SecurityContextRepository securityContextRepository) {
        this.securityContextRepository = securityContextRepository;
        this.userRepository = userRepository;
        this.authManager = authManager;
        loginAddition = new LoginAddition(this);

        setSizeFull();
        setPadding(false);
        setSpacing(false);

        // 🔹 Header
        header.setWidthFull();
        header.setPadding(true);
        header.setJustifyContentMode(JustifyContentMode.END);
        header.add(languageSelector);

        // 🔹 Контент
        content.setSizeFull();
        content.setJustifyContentMode(JustifyContentMode.CENTER);
        content.setAlignItems(Alignment.CENTER);

        wrapperInput();

        add(header, content); // Добавляем header сверху, контент ниже
        expand(content); // Контент занимает остальное пространство
    }

    private void wrapperInput() {
        formLayout.setWidth("400px");
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
        formLayout.add(nameField, passwordField, doLogin);

        hWrapper.setWidthFull();
        hWrapper.setJustifyContentMode(JustifyContentMode.CENTER);
        hWrapper.add(formLayout);

        content.add(hWrapper); // Контент страницы — форма
    }
}