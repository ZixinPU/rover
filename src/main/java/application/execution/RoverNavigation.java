package application.execution;

import application.commands.Command;
import application.commands.RoverNavigationCommands;
import application.plateau.Plateau;
import application.rover.Rover;
import exception.CannotMoveException;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;

import static application.commands.Command.M;
import static application.execution.CommandExecution.*;
import static org.apache.commons.lang3.tuple.Pair.of;

public class RoverNavigation {

    public static Rover controlOneRoverAllNavigation(int order, RoverNavigationCommands roverNavigationCommands,
                                                     Plateau plateau, Map<Integer, Pair<Integer, Integer>> roversCoordinate) {
        Rover rover = roverNavigationCommands.getRover();
        for(Command command : roverNavigationCommands.getCommands()) {
            controlRoverNavigationAutorisation(order, rover, plateau, command, roversCoordinate);
        }
        return rover;
    }

    public static Rover controlRoverNavigationAutorisation(int order, Rover rover, Plateau plateau, Command command,
                                                           Map<Integer, Pair<Integer, Integer>> roversCoordinate) {
        int plateauHeight = plateau.getHeight();
        int plateauWidth = plateau.getWidth();

        controlOneRoverOnceNavigation(rover, command);
        int x = rover.getX();
        int y = rover.getY();
        Pair<Integer, Integer> roverCoordinate = of(x, y);

        if(x > plateauHeight || x < 0 || y > plateauWidth || y < 0) {
            throw new CannotMoveException("Space not enough for rover " + order + " navigation.");
        } else if(command.equals(M) && roversCoordinate.containsValue(roverCoordinate)) {
            throw new CannotMoveException("Rover " + order + " can not move because of a duplicate coordinate.");
        }
        roversCoordinate.replace(order, roverCoordinate);

        return rover;
    }

    public static Rover controlOneRoverOnceNavigation(Rover rover, Command command) {
        int x = rover.getX();
        int y = rover.getY();

        switch (command) {
            case M:
                executeCommandM(rover);
                break;
            case L:
                executeCommandL(rover);
                break;
            case R:
                executeCommandR(rover);
                break;
        }
        return rover;
    }
}
