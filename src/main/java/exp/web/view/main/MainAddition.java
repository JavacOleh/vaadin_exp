package exp.web.view.main;

import exp.web.config.StaticData;
import exp.web.view.lang.LanguageSelector;

import static exp.web.config.StaticData.authEndPoint;
import static exp.web.config.StaticData.registerEndPoint;

public class MainAddition {
    MainView view;

    public MainAddition(MainView view) {
        this.view = view;

        view.goToLogin.addClickListener(e ->
                view.getUI().ifPresent(ui -> ui.navigate(authEndPoint)));

        view.languageSelector = new LanguageSelector(null, lang -> {
            switch (lang.toLowerCase()) {
                case StaticData.ENGLISH -> {
                    view.text.setText("Experiment");
                    view.goToLogin.setText("Log in");
                }

                case StaticData.RUSSIAN -> {
                    view.text.setText("Експеримент");
                    view.goToLogin.setText("Авторизация");
                }

                case StaticData.UKRAINIAN -> {
                    view.text.setText("Експеримент");
                    view.goToLogin.setText("Авторизація");
                }
            }
        });
    }
}
