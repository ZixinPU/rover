package application.commands;

import application.rover.Rover;

import java.util.List;

public class RoverNavigationCommands {
    private final Rover rover;
    private final List<Command> commands;

    public RoverNavigationCommands(Rover rover, List<Command> commands) {
        this.rover = rover;
        this.commands = commands;
    }

    public Rover getRover() {
        return rover;
    }

    public List<Command> getCommands() {
        return commands;
    }

    @Override
    public String toString() {
        return "RoverNavigationCommands{rover=" + rover + ", commands='" + commands + "}";
    }
}
