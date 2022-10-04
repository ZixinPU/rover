package input;

import application.commands.Command;
import application.commands.RoverNavigationCommands;
import application.plateau.Plateau;
import application.rover.Rover;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

import static application.execution.RoversNavigation.controlRoversNavigation;
import static application.execution.RoversNavigation.toStringRoverList;
import static input.InputAutorisation.inputPlateauAutorisation;
import static input.InputAutorisation.inputRoverAutorisation;
import static input.InputConverter.*;

public class InputScanner {

    public static List<String> readInputFile(String fileName) throws URISyntaxException, FileNotFoundException {
        String pathToFile = "C:\\Users\\zixin\\IdeaProjects\\rover\\src\\main\\resources\\" + fileName;
        File file = new File(pathToFile);

        try (Scanner input = new Scanner(file)) {
            Plateau plateau = inputPlateauAutorisation(input.nextLine());
            TreeMap<Integer, RoverNavigationCommands> roversNavigationCommands = new TreeMap<>();
            int order = 1;
            while (input.hasNextLine()) {
                Rover rover = inputRoverAutorisation(input.nextLine());
                List<Command> commands = convertInputToCommandsList(input.nextLine());
                roversNavigationCommands.put(order++, new RoverNavigationCommands(rover, commands));
            }
            List<Rover> roversFinalPosition = controlRoversNavigation(roversNavigationCommands, plateau);
            List<String> roversFinalPositionToString = toStringRoverList(roversFinalPosition);
            return roversFinalPositionToString;
        }
    }

    public static void main(String[] args) throws FileNotFoundException, URISyntaxException {
        String fileName = args[0];
        List<String> roversFinalPositionToString = readInputFile(fileName);
        for(String roverFinalPositionToString : roversFinalPositionToString) {
            System.out.println(roverFinalPositionToString);
        }
    }
}
