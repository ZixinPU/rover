package application.execution;

import application.commands.Command;
import application.commands.RoverNavigationCommands;
import application.plateau.Plateau;
import application.rover.Rover;
import exception.CannotMoveException;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static application.commands.Command.L;
import static application.commands.Command.M;
import static application.execution.RoverNavigation.*;
import static application.rover.Orientation.N;
import static application.rover.Orientation.W;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RoverNavigationTest {
    private Rover rover = new Rover(1, 2, N);
    private Rover roverForNoSpaceExceptionTest = new Rover(5, 5, N);
    private Rover roverForDuplicateCoordinateExceptionTest = new Rover(1, 3, N);
    private Plateau plateau = new Plateau(5, 5);

    @Test
    public void should_return_rover_final_position_after_one_command() {
        // Given
        Command command = L;

        // When
        Rover roverFinalPosition = controlOneRoverOnceNavigation(rover, command);

        // Then
        assertThat(roverFinalPosition.getX()).isEqualTo(1);
        assertThat(roverFinalPosition.getY()).isEqualTo(2);
        assertThat(roverFinalPosition.getOrientation()).isEqualTo(W);
    }

    @Test
    public void should_throw_exception_when_plateau_space_is_not_enough_after_one_command() {
        // Given
        Command command = M;
        int order = 1;

        // When
        assertThatThrownBy(() -> {
            controlRoverNavigationAutorisation(order, roverForNoSpaceExceptionTest, plateau, command, getRoversCoordinate());
        }).isInstanceOf(CannotMoveException.class)
          .hasMessage("Space not enough for rover 1 navigation.");
    }

    @Test
    public void should_throw_exception_when_a_duplicate_coordinate_after_one_command() {
        // Given
        Command command = M;
        int order = 1;

        // When
        assertThatThrownBy(() -> {
            controlRoverNavigationAutorisation(order, roverForDuplicateCoordinateExceptionTest, plateau, command, getRoversCoordinate());
        }).isInstanceOf(CannotMoveException.class)
                .hasMessage("Rover 1 can not move because of a duplicate coordinate.");
    }

    @Test
    public void should_return_rover_final_position_after_all_command() {
        // Given
        List<Command> commands = Arrays.asList(L, M, L, M, L, M, L, M, M);
        int order = 1;
        RoverNavigationCommands roverNavigationCommands = new RoverNavigationCommands(rover, commands);

        // When
        Rover roverFinalPosition = controlOneRoverAllNavigation(order, roverNavigationCommands, plateau, getRoversCoordinate());

        // Then
        assertThat(roverFinalPosition.getX()).isEqualTo(1);
        assertThat(roverFinalPosition.getY()).isEqualTo(3);
        assertThat(roverFinalPosition.getOrientation()).isEqualTo(N);
    }

    @Test
    public void should_throw_exception_when_plateau_space_is_not_enough() {
        // Given
        List<Command> commands = Arrays.asList(L, M, L, M, L, M, L, M, M);
        int order = 1;
        RoverNavigationCommands roverNavigationCommands = new RoverNavigationCommands(roverForNoSpaceExceptionTest, commands);

        // When
        assertThatThrownBy(() -> {
            controlOneRoverAllNavigation(order, roverNavigationCommands, plateau, getRoversCoordinate());
        }).isInstanceOf(CannotMoveException.class)
                .hasMessage("Space not enough for rover 1 navigation.");
    }

    @Test
    public void should_throw_exception_when_a_duplicate_coordinate_is_not_enough() {
        // Given
        List<Command> commands = Arrays.asList(L, M, L, M, L, M, L, M, M);
        int order = 1;
        RoverNavigationCommands roverNavigationCommands = new RoverNavigationCommands(roverForDuplicateCoordinateExceptionTest, commands);

        // When
        assertThatThrownBy(() -> {
            controlOneRoverAllNavigation(order, roverNavigationCommands, plateau, getRoversCoordinate());
        }).isInstanceOf(CannotMoveException.class)
                .hasMessage("Rover 1 can not move because of a duplicate coordinate.");
    }

    public Map<Integer, Pair<Integer, Integer>> getRoversCoordinate() {
        Map<Integer, Pair<Integer, Integer>> roversCoordinate = new HashMap<>();
        roversCoordinate.put(1, Pair.of(1, 4));
        return roversCoordinate;
    }
}