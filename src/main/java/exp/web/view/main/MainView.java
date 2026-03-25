package exp.web.view.main;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import exp.web.view.lang.LanguageSelector;

@Route("")
@AnonymousAllowed
public class MainView extends VerticalLayout {
    LanguageSelector languageSelector;
    MainAddition mainAddition;
    Text text = new Text("");
    Button goToLogin = new Button("");
    VerticalLayout wrapper = new VerticalLayout();

    public MainView() {
        wrapperInput();

        mainAddition = new MainAddition(this);

        add(wrapper);
    }

    private void wrapperInput() {
        wrapper.setAlignItems(Alignment.CENTER);
        wrapper.add(text, goToLogin);

        setSizeFull(); // 🔥 важно
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
    }
}