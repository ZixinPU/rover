package application.rover;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Orientation {
    N,
    E,
    S,
    W;

    public static List<String> getPossibleOrientations() {
        return Arrays.stream(Orientation.values())
                .map(orientation -> {
                    return orientation.toString();
                }).collect(Collectors.toList());
    }
}
