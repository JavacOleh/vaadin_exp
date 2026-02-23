package exp.web.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import static exp.web.config.StaticData.authEndPoint;

@Route("")
public class MainView extends VerticalLayout {

    public MainView() {
        Button goToForm = new Button("Перейти к форме",
                e -> getUI().ifPresent(ui -> ui.navigate(authEndPoint)));
        add(goToForm);
    }
}