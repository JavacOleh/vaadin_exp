package exp.web.view.main.lang;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import exp.web.view.main.MainView;

public class MainViewLocalization {
    public static void onChange(String lang, MainView view) {
        var firstNameHeader = MainLangManager.get("firstName", lang);
        var lastNameHeader = MainLangManager.get("lastName", lang);
        var ageHeader = MainLangManager.get("age", lang);
        var actionsHeader = MainLangManager.get("actions", lang);
        var searchPlaceHolder = MainLangManager.get("search", lang);
        var logoutText = MainLangManager.get("logout", lang);
        var doNewPersonText = MainLangManager.get("create", lang);

        view.firstNameColumn.setHeader(firstNameHeader);
        view.lastNameColumn.setHeader(lastNameHeader);
        view.ageColumn.setHeader(ageHeader);
        // Component колонка: header как HorizontalLayout с Span по центру
        view.actionsColumn.setHeader(new HorizontalLayout(new Span(actionsHeader)) {{
            setWidthFull();
            setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        }});

        view.filterText.setPlaceholder(searchPlaceHolder + "...");

        view.doNewPerson.setText(doNewPersonText);

        view.doLogout.setText(logoutText);
    }
}
