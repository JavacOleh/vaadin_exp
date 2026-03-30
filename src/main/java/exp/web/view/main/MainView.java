package exp.web.view.main;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.Route;
import exp.web.entity.Person;
import exp.web.repository.PeopleRepository;
import exp.web.view.lang.LanguageSelector;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.web.context.SecurityContextRepository;

import static exp.web.config.StaticData.mainEndPoint;

@Route(mainEndPoint)
@RolesAllowed("ROLE_ADMIN")
public class MainView extends VerticalLayout {
    VerticalLayout content = new VerticalLayout();
    HorizontalLayout header = new HorizontalLayout();
    VerticalLayout form_wrapper = new VerticalLayout(); // форма
    LanguageSelector languageSelector;
    public ListDataProvider<Person> dataProvider;
    public Grid<Person> grid = new Grid<>(Person.class, false);
    public TextField filterText = new TextField();
    public Button doLogout = new Button("");
    public Button doNewPerson = new Button("");
    public Grid.Column<Person> firstNameColumn;
    public Grid.Column<Person> lastNameColumn;
    public Grid.Column<Person> ageColumn;
    public Grid.Column<Person> actionsColumn;
    public PeopleRepository peopleRepository;
    SecurityContextRepository securityContextRepository;
    MainAddition addition;

    public MainView(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;

        setupGridAndSearch();
        addition = new MainAddition(this);

        // 🔹 HEADER
        header.setWidthFull();
        header.setJustifyContentMode(JustifyContentMode.END);
        header.add(languageSelector, doLogout);
        header.setSpacing(true);

        // 🔹 CONTENT (растягивается на оставшееся пространство)
        content.setSizeFull(); // занять всё оставшееся место
        content.setJustifyContentMode(JustifyContentMode.CENTER); // центр по вертикали
        content.setAlignItems(Alignment.CENTER); // центр по горизонтали
        content.add(form_wrapper);

        // 🔹 ROOT (MainView)
        setSizeFull();
        add(header, content);
        expand(content); // content растянется и позволит вертикально центрировать wrapper
    }

    private void setupGridAndSearch() {
        form_wrapper.setAlignItems(Alignment.CENTER); // центрируем элементы формы по горизонтали
        form_wrapper.add(filterText, grid);
    }
}
