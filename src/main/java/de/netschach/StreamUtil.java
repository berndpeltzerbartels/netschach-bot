package de.netschach;

import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class StreamUtil {

    public static <T> Collector<T, ?, T> findOne(Supplier<? extends RuntimeException> exceptionSupplier) {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    switch (list.size()) {
                        case 0:
                            throw exceptionSupplier.get();
                        case 1:
                            return list.get(0);
                        default:
                            throw exceptionSupplier.get();
                    }
                }
        );
    }
}
