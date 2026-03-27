package exp.web.view.main;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import exp.web.entity.Person;
import exp.web.repository.PeopleRepository;
import jakarta.annotation.security.RolesAllowed;

import static exp.web.config.StaticData.mainEndPoint;

@Route(mainEndPoint)
@RolesAllowed("ROLE_ADMIN")
public class MainView extends VerticalLayout {
    VerticalLayout wrapper = new VerticalLayout();
    Grid<Person> grid = new Grid<>(Person.class, false);
    ListDataProvider<Person> dataProvider;
    TextField filterText = new TextField();
    PeopleRepository peopleRepository;
    MainAddition addition;

    public MainView(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;

        setupLayout();

        addition = new MainAddition(this);

        add(wrapper);
    }

    private void setupLayout() {
        wrapper.setAlignItems(Alignment.CENTER);
        wrapper.add(filterText, grid);

        setSizeFull(); // 🔥 важно
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
    }
}
