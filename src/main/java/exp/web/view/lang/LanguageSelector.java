package exp.web.view.lang;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.server.VaadinService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class LanguageSelector extends HorizontalLayout {

    public static final List<String> languages = Arrays.asList("English", "Русский", "Українська");

    public LanguageSelector(String gravity, Consumer<String> onLanguageChange) {
        setPadding(false);
        setSpacing(false);

        // Кнопка с планетой
        Button globeButton = new Button("\uD83C\uDF10");
        globeButton.getStyle().set("cursor", "pointer");

        // Выпадающий список
        Div dropdown = new Div();
        dropdown.getStyle().set("display", "none");
        dropdown.getStyle().set("position", "absolute");
        dropdown.getStyle().set("top", "100%");
        dropdown.getStyle().set("background", "white");
        dropdown.getStyle().set("border", "1px solid gray");
        dropdown.getStyle().set("z-index", "1000");

        for (String lang : languages) {
            Button langButton = new Button(lang, e -> {
                saveLanguageCookie(lang);
                onLanguageChange.accept(lang);
                dropdown.getStyle().set("display", "none");
            });
            langButton.getStyle().set("display", "block");
            dropdown.add(langButton);
        }

        Div wrapper = new Div();
        wrapper.getStyle().set("position", "relative");
        wrapper.add(globeButton, dropdown);
        add(wrapper);

        // Позиционирование по краю экрана
        getStyle().set("position", "fixed");
        getStyle().set("z-index", "1000");
        if (gravity.equalsIgnoreCase("top-left")) {
            getStyle().set("top", "10px");
            getStyle().set("left", "10px");
        } else {
            getStyle().set("top", "10px");
            getStyle().set("right", "10px");
        }

        globeButton.addClickListener(e -> {
            boolean shown = "block".equals(dropdown.getStyle().get("display"));
            if (!shown) {
                // Определяем сторону окна
                globeButton.getElement().executeJs(
                        "const rect = this.getBoundingClientRect();" +
                                "const width = window.innerWidth;" +
                                "const dropdown = this.nextElementSibling;" +
                                "if(rect.left + dropdown.offsetWidth > width) {" +
                                " dropdown.style.left = 'auto'; dropdown.style.right = '0px';" +
                                "} else {" +
                                " dropdown.style.left = '0px'; dropdown.style.right = 'auto';" +
                                "}"
                );
            }
            dropdown.getStyle().set("display", shown ? "none" : "block");
        });

        // Считываем куку
        String savedLang = readLanguageCookie();
        if (savedLang != null && languages.contains(savedLang)) {
            onLanguageChange.accept(savedLang);
        }
    }

    private void saveLanguageCookie(String lang) {
        var response = VaadinService.getCurrentResponse();
        if (response instanceof HttpServletResponse httpResponse) {
            Cookie cookie = new Cookie("language", lang);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24 * 365);
            httpResponse.addCookie(cookie);
        }
    }

    private String readLanguageCookie() {
        var request = VaadinService.getCurrentRequest();
        if (request != null) {
            var cookies = request.getCookies();
            if (cookies != null) {
                for (var c : cookies) {
                    if ("language".equals(c.getName())) {
                        return c.getValue();
                    }
                }
            }
        }
        return null;
    }
}