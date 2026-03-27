package exp.web.view.start;

import exp.util.CookieUtil;
import exp.web.config.StaticData;

import static exp.web.config.StaticData.authEndPoint;
import static exp.web.view.lang.LanguageSelector.LANG_KEY;

public class StartAddition {
    StartView view;

    public StartAddition(StartView view) {
        this.view = view;

        view.goToLogin.addClickListener(e ->
                view.getUI().ifPresent(ui -> ui.navigate(authEndPoint)));

        var lang = CookieUtil.readCookie(LANG_KEY);
        switch (lang.toLowerCase()) {
            case StaticData.ENGLISH -> {
                view.text.setText("Experiment");
                view.goToLogin.setText("Log in");
            }

            case StaticData.RUSSIAN -> {
                view.text.setText("Експеримент");
                view.goToLogin.setText("Авторизация");
            }

            default -> {
                view.text.setText("Експеримент");
                view.goToLogin.setText("Авторизація");
            }
        }
    }
}
