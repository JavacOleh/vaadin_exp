package exp.web.view.start;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("")
@AnonymousAllowed
public class StartView extends VerticalLayout {
    StartAddition startAddition;
    Text text = new Text("");
    Button goToLogin = new Button("");
    VerticalLayout wrapper = new VerticalLayout();

    public StartView() {
        wrapperInput();

        startAddition = new StartAddition(this);

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