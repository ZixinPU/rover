package application.execution;

import application.commands.Command;
import application.plateau.Plateau;
import application.rover.Rover;
import application.commands.RoverNavigationCommands;
import exception.CannotMoveException;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import static application.commands.Command.*;
import static application.rover.Orientation.E;
import static application.rover.Orientation.N;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RoversNavigationTest {
    private Rover rover1 = new Rover(1,2, N);
    private Rover rover2 = new Rover(3,3, E);
    private List<Command> rover1Commands = Arrays.asList(L, M, L, M, L, M, L, M, M);
    private List<Command> rover2Commands = Arrays.asList(M, M, R, M, M, R, M, R, R, M);
    private Plateau plateau = new Plateau(5, 5);

    @Test
    public void should_convert_rovers_final_position_list_to_string_list() {
        //Given
        List<Rover> roversFinalPositionList = Arrays.asList(new Rover(1, 3, N), new Rover(5, 1, E));

        // When
        List<String> roversFinalPositionStringList = RoversNavigation.toStringRoverList(roversFinalPositionList);

        // Then
        assertThat(roversFinalPositionStringList).hasSize(2);
        assertThat(roversFinalPositionStringList).isEqualTo(Arrays.asList("1 3 N", "5 1 E"));
    }

    @Test
    public void should_return_rovers_final_position_list_after_all_commands() {
        // Given
        TreeMap<Integer, RoverNavigationCommands> roversNavigationCommand = new TreeMap<>();
        roversNavigationCommand.put(1, new RoverNavigationCommands(rover1, rover1Commands));
        roversNavigationCommand.put(2, new RoverNavigationCommands(rover2, rover2Commands));

        // When
        List<Rover> roversFinalPosition = RoversNavigation.controlRoversNavigation(roversNavigationCommand, plateau);

        // Then
        assertThat(roversFinalPosition).hasSize(2);
        assertThat(roversFinalPosition.get(0).getX()).isEqualTo(1);
        assertThat(roversFinalPosition.get(0).getY()).isEqualTo(3);
        assertThat(roversFinalPosition.get(0).getOrientation()).isEqualTo(N);
        assertThat(roversFinalPosition.get(1).getX()).isEqualTo(5);
        assertThat(roversFinalPosition.get(1).getY()).isEqualTo(1);
        assertThat(roversFinalPosition.get(1).getOrientation()).isEqualTo(E);
    }

    @Test
    public void should_throw_exception_when_plateau_space_is_not_enough() {
        // Given
        Rover roverForExceptionTest = new Rover(5,5, N);
        TreeMap<Integer, RoverNavigationCommands> roversNavigationCommand = new TreeMap<>();
        roversNavigationCommand.put(1, new RoverNavigationCommands(rover1, rover1Commands));
        roversNavigationCommand.put(2, new RoverNavigationCommands(roverForExceptionTest, rover2Commands));

        // When
        assertThatThrownBy(() -> {
            RoversNavigation.controlRoversNavigation(roversNavigationCommand, plateau);
        }).isInstanceOf(CannotMoveException.class)
                .hasMessage("Space not enough for rover 2 navigation.");
    }

    @Test
    public void should_throw_exception_when_duplicate_coordinate() {
        // Given
        Rover roverForExceptionTest = new Rover(0,2, N);
        TreeMap<Integer, RoverNavigationCommands> roversNavigationCommand = new TreeMap<>();
        roversNavigationCommand.put(1, new RoverNavigationCommands(rover1, rover1Commands));
        roversNavigationCommand.put(2, new RoverNavigationCommands(roverForExceptionTest, rover2Commands));

        // When
        assertThatThrownBy(() -> {
            RoversNavigation.controlRoversNavigation(roversNavigationCommand, plateau);
        }).isInstanceOf(CannotMoveException.class)
                .hasMessage("Rover 1 can not move because of a duplicate coordinate.");
    }
}