package exp.web.view.main.actions;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.function.ValueProvider;
import exp.util.CookieUtil;
import exp.web.entity.Person;
import exp.web.view.lang.LanguageSelector;
import exp.web.view.main.MainView;
import exp.web.view.main.lang.MainLangManager;

public class MainActionsAddition {
    public static ValueProvider<Person, HorizontalLayout> getActionsColumn(MainView view) {
        return person -> {
            // Создаем кнопки
            var editBtn = new Button("✏️");
            editBtn.getStyle().set("cursor", "pointer");
            editBtn.addClickListener(e -> openEditDialog(person, view, false));

            var deleteBtn = new Button("🗑️");
            deleteBtn.getStyle().set("cursor", "pointer");
            deleteBtn.addClickListener(e -> {
                view.peopleRepository.delete(person);
                view.dataProvider.getItems().remove(person);
                view.dataProvider.refreshAll();
            });

            var layout = new HorizontalLayout(editBtn, deleteBtn);
            // ВАЖНО: растягиваем layout на всю ширину колонки
            layout.setWidthFull();

            // Центрируем содержимое горизонтально
            layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

            // Вертикальное выравнивание кнопок по центру
            layout.setAlignItems(FlexComponent.Alignment.CENTER);
            return layout;
        };
    }

    public static void openEditDialog(Person person, MainView view, boolean isNew) {
        var dialog = new Dialog();
        dialog.setCloseOnOutsideClick(false); // запрещаем закрытие кликом вне
        var lang = CookieUtil.readCookie(LanguageSelector.LANG_KEY);
        var firstNameHeader = MainLangManager.get("firstName", lang);
        var lastNameHeader = MainLangManager.get("lastName", lang);
        var ageHeader = MainLangManager.get("age", lang);

        // Поля ввода
        var firstNameField = new TextField(firstNameHeader, person.getFirstName());
        var lastNameField = new TextField(lastNameHeader, person.getLastName());
        var ageField = new TextField(ageHeader, person.getAge() > 0 ? String.valueOf(person.getAge()) : "");

        // Кнопка Save (ОК) по центру внизу
        var saveBtn = new Button("✔", e -> {
            String fName = firstNameField.getValue().trim();
            String lName = lastNameField.getValue().trim();
            String ageStr = ageField.getValue().trim();

            if (isNew && (fName.isEmpty() || lName.isEmpty() || ageStr.isEmpty())) {
                Notification.show("All fields must be filled!", 3000, Notification.Position.BOTTOM_START);
                return;
            }

            Integer age = null;
            if (!ageStr.isEmpty()) {
                try { age = Integer.parseInt(ageStr); }
                catch (NumberFormatException ex) {
                    Notification.show("Age must be a number!", 3000, Notification.Position.BOTTOM_START);
                    return;
                }
            }

            if (!fName.isEmpty()) person.setFirstName(fName);
            if (!lName.isEmpty()) person.setLastName(lName);
            if (age != null) person.setAge(age);

            view.peopleRepository.save(person);
            if (isNew) view.dataProvider.getItems().add(person);

            view.dataProvider.refreshAll();
            dialog.close();

            // Жёсткое обновление F5 после закрытия
            view.getUI().ifPresent(ui -> ui.getPage().executeJs("window.location.reload();"));
        });

        // Кнопка Cancel (x) в правом верхнем углу
        var closeBtn = new Button("✖");
        closeBtn.getStyle()
                .set("padding", "0")
                .set("width", "30px")
                .set("height", "30px")
                .set("border-radius", "50%")
                .set("position", "absolute")
                .set("top", "5px")
                .set("right", "5px")
                .set("min-width", "unset");
        closeBtn.addClickListener(e -> dialog.close());

        // Контент
        var content = new VerticalLayout(firstNameField, lastNameField, ageField);
        content.setPadding(false);
        content.setSpacing(true);
        content.setMargin(false);

        // Размещение кнопки Save по центру снизу
        var footer = new HorizontalLayout(saveBtn);
        footer.setWidthFull();
        footer.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        dialog.add(content);
        dialog.add(closeBtn); // кнопка X сверху справа
        dialog.add(footer);   // кнопка Save снизу по центру

        footer.getStyle().set("margin-top", "20px"); // например, 20px

        dialog.open();
    }
}
