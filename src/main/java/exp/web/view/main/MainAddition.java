package exp.web.view.main;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import exp.util.AuthUtil;
import exp.web.entity.Person;
import exp.web.view.main.actions.MainActionsAddition;
import exp.web.view.lang.LanguageSelector;
import exp.web.view.main.lang.MainViewLocalization;

public class MainAddition {
    MainView view;

    public MainAddition(MainView view) {
        this.view = view;
        view.doLogout.addClickListener(c -> AuthUtil.doLogout(view, view.securityContextRepository));

        setupFiltering();
        setupGrid();

        view.languageSelector = new LanguageSelector(l -> MainViewLocalization.onChange(l, view));
    }

    private void setupGrid() {
        var people = view.peopleRepository.findAll();
        view.dataProvider = new ListDataProvider<>(people);
        view.grid.setDataProvider(view.dataProvider);

        view.grid.setWidthFull();
        view.grid.setHeight("400px");

        view.hashTagIndColumn = view.grid.addColumn(person -> people.indexOf(person) + 1)
                .setHeader("#")
                .setTextAlign(ColumnTextAlign.CENTER);

        view.firstNameColumn = view.grid.addColumn(Person::getFirstName)
                .setHeader("First Name")
                .setTextAlign(ColumnTextAlign.CENTER);

        view.lastNameColumn = view.grid.addColumn(Person::getLastName)
                .setHeader("Last Name")
                .setTextAlign(ColumnTextAlign.CENTER);

        view.ageColumn = view.grid.addColumn(Person::getAge)
                .setHeader("Age")
                .setTextAlign(ColumnTextAlign.CENTER);

        view.actionsColumn = view.grid.addComponentColumn(MainActionsAddition.getActionsColumn(view))
                .setHeader(new HorizontalLayout(new Span("Actions")) {{
                    setWidthFull();
                    setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
                }});

        var filterWrapper = new HorizontalLayout();
        filterWrapper.setWidthFull();
        filterWrapper.setSpacing(true);
        filterWrapper.setPadding(false);

        // Фильтр занимает всю доступную ширину
        view.filterText.setWidthFull();

        view.doNewPerson.setId("open_dialog_create_person_button_id");

        view.doNewPerson.addClickListener(e -> {
            Person person = new Person();
            MainActionsAddition.openEditDialog(person, view, true);
        });

        filterWrapper.add(view.filterText, view.doNewPerson);
        filterWrapper.expand(view.filterText); // фильтр растягивается, кнопка занимает только свою ширину

        // Убираем старое добавление фильтра в layout, теперь добавляем filterWrapper
        view.form_wrapper.removeAll();
        view.form_wrapper.add(filterWrapper, view.grid);
    }

    private void setupFiltering() {
        view.filterText.setClearButtonVisible(true);
        view.filterText.setWidthFull();
        view.filterText.setValueChangeMode(ValueChangeMode.EAGER);

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
