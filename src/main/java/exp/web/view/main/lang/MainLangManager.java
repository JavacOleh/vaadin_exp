package exp.web.view.main.lang;

import exp.web.config.StaticData;

import java.util.Map;

public class MainLangManager {
    private static final Map<String, Map<String, String>> TEXTS = Map.of(
            "firstName", Map.of(
                    StaticData.ENGLISH, "First Name",
                    StaticData.RUSSIAN, "Имя",
                    StaticData.UKRAINIAN, "Ім'я"
            ),
            "lastName", Map.of(
                    StaticData.ENGLISH, "Last Name",
                    StaticData.RUSSIAN, "Фамилия",
                    StaticData.UKRAINIAN, "Прізвище"
            ),
            "age", Map.of(
                    StaticData.ENGLISH, "Age",
                    StaticData.RUSSIAN, "Возраст",
                    StaticData.UKRAINIAN, "Вік"
            ),
            "actions", Map.of(
                    StaticData.ENGLISH, "Actions",
                    StaticData.RUSSIAN, "Действия",
                    StaticData.UKRAINIAN, "Дії"
            ),
            "search", Map.of(
                    StaticData.ENGLISH, "Search",
                    StaticData.RUSSIAN, "Поиск",
                    StaticData.UKRAINIAN, "Пошук"
            ),
            "logout", Map.of(
                    StaticData.ENGLISH, "Logout",
                    StaticData.RUSSIAN, "Выход",
                    StaticData.UKRAINIAN, "Вихід"
            ),
            "create", Map.of(
                    StaticData.ENGLISH, "Create",
                    StaticData.RUSSIAN, "Создать",
                    StaticData.UKRAINIAN, "Створити"
            )
    );

    public static String get(String key, String lang) {
        // Если язык не задан или нет перевода, возвращаем украинский как default
        return TEXTS.getOrDefault(key, Map.of())
                .getOrDefault(lang, TEXTS.getOrDefault(key, Map.of()).get(StaticData.UKRAINIAN));
    }
}