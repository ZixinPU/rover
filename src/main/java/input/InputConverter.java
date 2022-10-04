package input;

import application.commands.Command;
import application.plateau.Plateau;
import application.rover.Orientation;
import application.rover.Rover;

import java.util.ArrayList;
import java.util.List;

import static input.InputAutorisation.inputCommandAutorisation;

public class InputConverter {
    public static Plateau convertInputToPlateau(String plateauLine) {
        String[] plateau = plateauLine.split(" ");
        int height = Integer.parseInt(plateau[0]);
        int width = Integer.parseInt(plateau[1]);
        return new Plateau(height, width);
    }

    public static Rover convertInputToRover(String roverLine) {
        String[] roverPosition = roverLine.split(" ");
        int X = Integer.parseInt(roverPosition[0]);
        int Y = Integer.parseInt(roverPosition[1]);
        Orientation orientation = Orientation.valueOf(roverPosition[2]);
        return new Rover(X, Y, orientation);
    }

    public static List<Command> convertInputToCommandsList(String commandsLine) {
        String[] commands = commandsLine.split("");
        List<Command> commandList = new ArrayList<>();
        for(String command : commands) {
            commandList.add(inputCommandAutorisation(command));
        }
        return commandList;
    }

    public static Command convertInputToCommand(String command) {
        return Command.valueOf(command);
    }
}
