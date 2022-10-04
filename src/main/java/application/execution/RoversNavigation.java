package application.execution;

import application.plateau.Plateau;
import application.rover.Rover;
import application.commands.RoverNavigationCommands;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;

import static application.execution.RoverNavigation.controlOneRoverAllNavigation;

public class RoversNavigation {
    public static List<String> toStringRoverList(List<Rover> rovers) {
        return rovers.stream()
                .map(rover ->  rover.getX() + " " + rover.getY() + " " + rover.getOrientation())
                .collect(Collectors.toList());
    }

    public static List<Rover> controlRoversNavigation(TreeMap<Integer, RoverNavigationCommands> roversNavigationCommands,
                                                      Plateau plateau) {
        List<Rover> roversFinalPosition = new ArrayList<>();
        Map<Integer, Pair<Integer, Integer>> roversCoordinate = new HashMap<>();

        roversNavigationCommands.forEach((order, roverNavigationCommand) -> {
            roversCoordinate.put(order, Pair.of(roverNavigationCommand.getRover().getX(), roverNavigationCommand.getRover().getY()));
        });

        roversNavigationCommands.forEach((order, roverNavigationCommand) -> {
                roversFinalPosition.add(controlOneRoverAllNavigation(order, roverNavigationCommand, plateau, roversCoordinate));
        });
        return roversFinalPosition;
    }
}
