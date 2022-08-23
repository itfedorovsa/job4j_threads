package ru.job4j.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        Base check = memory.computeIfPresent(model.getId(), (k, v) -> {
            if (v.getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            Base rsl = new Base(model.getId(), model.getVersion() + 1);
            rsl.setName(model.getName());
            return rsl;
        });
        return check != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId(), model);
    }

    public Base get(int id) {
        return memory.get(id);
    }

    public List<Base> getAll() {
        return new ArrayList<>(memory.values());
    }
}
