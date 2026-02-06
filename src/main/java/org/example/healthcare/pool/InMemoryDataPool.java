package org.example.healthcare.pool;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InMemoryDataPool<T> {

    private final List<T> data;

    public InMemoryDataPool(List<T> initialData) {
        this.data = new ArrayList<>(initialData);
    }

    public List<T> getAll() {
        return new ArrayList<>(data);
    }

    public List<T> filter(Predicate<T> predicate) {
        return data.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public List<T> sort(Comparator<T> comparator) {
        return data.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public <R> List<R> map(Function<T, R> mapper) {
        return data.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }
}
