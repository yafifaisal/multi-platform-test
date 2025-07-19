package api.helpers;

import java.util.HashMap;
import java.util.Map;

public class ContextStore {
    private static final Map<String, Object> store = new HashMap<>();

    public static void save(String key, Object value) {
        store.put(key, value);
    }

    public static Object get(String key) {
        return store.get(key);
    }

    public static String getAsString(String key) {
        Object value = get(key);
        return value != null ? value.toString() : null;
    }

    public static void clear() {
        store.clear();
    }

    public static Map<String, Object> getAll() {
        return store;
    }
}
