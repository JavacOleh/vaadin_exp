package exp.web.view.main;

import com.vaadin.flow.data.provider.ListDataProvider;
import exp.web.entity.Person;

public class MainAddition {
    MainView view;

    public MainAddition(MainView view) {
        this.view = view;
        setupFiltering();
        setupGrid();
    }

    private void setupGrid() {
        view.grid.addColumn(Person::getFirstName).setHeader("First Name");
        view.grid.addColumn(Person::getLastName).setHeader("Last Name");
        view.grid.addColumn(Person::getAge).setHeader("Age");

        view.dataProvider = new ListDataProvider<>(view.peopleRepository.findAll());
        view.grid.setDataProvider(view.dataProvider);

        view.grid.setWidthFull();
        view.grid.setHeight("400px");
    }
    private void setupFiltering() {
        view.filterText.setPlaceholder("Search...");
        view.filterText.setClearButtonVisible(true);

        view.filterText.addValueChangeListener(event -> {
            String filter = event.getValue().trim().toLowerCase();
            if (filter.isEmpty()) {
                view.dataProvider.clearFilters();
            } else {
                view.dataProvider.setFilter(person ->
                        person.getFirstName().toLowerCase().contains(filter) ||
                                person.getLastName().toLowerCase().contains(filter) ||
                                String.valueOf(person.getAge()).contains(filter)
                );
            }
        });
    }
}
