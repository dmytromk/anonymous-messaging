package org.anonmes.messenger.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FunctionalUtils {
    public static Stream<Integer> range(int fromInclusive, int toExclusive) {
        List<Integer> result = new ArrayList<>();
        for (int i = fromInclusive; i < toExclusive; i++) {
            result.add(i);
        }
        return result.stream();
    }
    public static Stream<Integer> range(int toExclusive) {
        return range(0, toExclusive);
    }
}
