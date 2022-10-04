package input;

import application.commands.Command;
import application.plateau.Plateau;
import application.rover.Rover;
import exception.CannotInputException;

import java.util.List;

import static application.commands.Command.getPossibleCommands;
import static application.rover.Orientation.getPossibleOrientations;
import static input.InputConverter.*;

public class InputAutorisation {

    public static Plateau inputPlateauAutorisation(String plateauLine) {
        String[] plateau = plateauLine.split(" ");
        int X = Integer.parseInt(plateau[0]);
        int Y = Integer.parseInt(plateau[1]);

        if(X <= 0 || Y <= 0) {
            throw new CannotInputException("Plateau's height or width can not be 0 or negative.");
        }
        return convertInputToPlateau(plateauLine);
    }

    public static Rover inputRoverAutorisation(String roverLine) {
        String[] rover = roverLine.split(" ");
        int X = Integer.parseInt(rover[0]);
        int Y = Integer.parseInt(rover[1]);
        String orientation = rover[2];
        List<String> possibleOrientations = getPossibleOrientations();

        if(X < 0 || Y < 0) {
            throw new CannotInputException("Rover's longitude or latitude can not be negative.");
        } else if(!possibleOrientations.contains(orientation)) {
            throw new CannotInputException("Rover's orientation can only be N, S, E, W.");
        }
        return convertInputToRover(roverLine);
    }

    public static Command inputCommandAutorisation(String command) {
        List<String> possibleCommands = getPossibleCommands();

        if(!possibleCommands.contains(command)) {
            throw new CannotInputException("Command can only be M, L, R.");
        }
        return convertInputToCommand(command);
    }
}
