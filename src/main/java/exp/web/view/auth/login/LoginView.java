package exp.web.view.auth.login;

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
    final String languageSelectorGravity = "top-right";
    TextField nameField = new TextField("");
    PasswordField passwordField = new PasswordField("");
    FormLayout formLayout = new FormLayout();
    Button doLogin = new Button("");
    Button goToRegister = new Button("");
    HorizontalLayout hWrapper = new HorizontalLayout(); // Горизонтальная обёртка для центрирования формы
    LanguageSelector languageSelector;
    String notificationText = "";

    public LoginView(UserRepository userRepository,
                     AuthenticationManager authManager,
                     SecurityContextRepository securityContextRepository) {
        this.securityContextRepository = securityContextRepository;
        this.userRepository = userRepository;
        this.authManager = authManager;
        // Корневой VerticalLayout на весь экран
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER); // вертикальное центрирование

        loginAddition = new LoginAddition(this);

        wrapperInput();
    }

    private void wrapperInput() {
        formLayout.setWidth("400px"); // ширина формы
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1)); // одна колонка
        formLayout.add(nameField, passwordField, doLogin, goToRegister);
        formLayout.setColspan(doLogin, 1);

        // горизонтальное центрирование
        hWrapper.setWidthFull(); // растягиваем на всю ширину
        hWrapper.setJustifyContentMode(JustifyContentMode.CENTER);
        hWrapper.add(formLayout);

        add(hWrapper);
    }
}