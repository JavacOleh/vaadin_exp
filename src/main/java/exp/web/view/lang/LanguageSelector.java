package exp.web.view.lang;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import exp.web.config.StaticData;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static exp.util.CookieUtil.readCookie;
import static exp.util.CookieUtil.saveCookie;

//TODO: Нужна абстракция(Например LangSelectorStylizer, LangAddition)
public class LanguageSelector extends HorizontalLayout {
    public static final List<String> languages =
            Arrays.asList(
                    StaticData.ENGLISH,
                    StaticData.RUSSIAN,
                    StaticData.UKRAINIAN
            );
    public static final String LANG_KEY = "language";

    public LanguageSelector(Consumer<String> onLanguageChange) {
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
                saveCookie(LANG_KEY, lang);
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

        globeButton.addClickListener(e -> {
            boolean shown = "block".equals(dropdown.getStyle().get("display"));
            if (!shown) {
                // Определяем сторону окна
                globeButton.getElement().executeJs("""
                            const rect = this.getBoundingClientRect();
                            const width = window.innerWidth;
                            const dropdown = this.nextElementSibling;
                            if(rect.left + dropdown.offsetWidth > width) {
                                dropdown.style.left = 'auto';
                                dropdown.style.right = '0px';
                            } else {
                                dropdown.style.left = '0px';
                                dropdown.style.right = 'auto';
                            }
                        """);
            }
            dropdown.getStyle().set("display", shown ? "none" : "block");
        });

        // Считываем куку
        String savedLang = readCookie(LANG_KEY);
        savedLang = languages.contains(savedLang)
                ? savedLang
                : "";

        onLanguageChange.accept(savedLang);
    }
}