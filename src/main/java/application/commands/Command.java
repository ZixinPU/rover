package application.commands;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Command {
    M,
    L,
    R;

    public static List<String> getPossibleCommands() {
        return Arrays.stream(Command.values())
                .map(command -> {
                    return command.toString();
                }).collect(Collectors.toList());
    };
}
