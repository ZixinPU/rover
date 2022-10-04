package input;

import application.commands.Command;
import application.plateau.Plateau;
import application.rover.Rover;
import exception.CannotInputException;
import org.junit.Test;

import java.util.List;

import static application.commands.Command.L;
import static application.commands.Command.M;
import static application.rover.Orientation.N;
import static input.InputAutorisation.inputPlateauAutorisation;
import static input.InputAutorisation.inputRoverAutorisation;
import static input.InputConverter.convertInputToCommandsList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class InputAutorisationTest {
    @Test
    public void should_convert_input_to_plateau() {
        // Given
        String plateauLine = "5 5";

        // When
        Plateau plateau = inputPlateauAutorisation(plateauLine);

        // Then
        assertThat(plateau.getHeight()).isEqualTo(5);
        assertThat(plateau.getWidth()).isEqualTo(5);
    }

    @Test
    public void should_throw_exception_when_negative_input() {
        // Given
        String plateauLine = "-1 -1";

        // When
        assertThatThrownBy(() -> {
            inputPlateauAutorisation(plateauLine);
        }).isInstanceOf(CannotInputException.class)
                .hasMessage("Plateau's height or width can not be 0 or negative.");
    }

    @Test
    public void should_convert_input_to_rover() {
        // Given
        String roverLine = "1 2 N";

        // When
        Rover rover = inputRoverAutorisation(roverLine);

        // Then
        assertThat(rover.getX()).isEqualTo(1);
        assertThat(rover.getY()).isEqualTo(2);
        assertThat(rover.getOrientation()).isEqualTo(N);
    }

    @Test
    public void should_throw_exception_when_negative_coordinate_input() {
        // Given
        String roverLine = "-1 -1 N";

        // When
        assertThatThrownBy(() -> {
            inputRoverAutorisation(roverLine);
        }).isInstanceOf(CannotInputException.class)
                .hasMessage("Rover's longitude or latitude can not be negative.");
    }

    @Test
    public void should_throw_exception_when_no_possible_orientation_input() {
        // Given
        String roverLine = "1 2 A";

        // When
        assertThatThrownBy(() -> {
            inputRoverAutorisation(roverLine);
        }).isInstanceOf(CannotInputException.class)
                .hasMessage("Rover's orientation can only be N, S, E, W.");
    }

    @Test
    public void should_convert_input_to_commands() {
        // Given
        String commandLine = "LLM";

        // When
        List<Command> commands = convertInputToCommandsList(commandLine);

        // Then
        assertThat(commands).hasSize(3);
        assertThat(commands.get(0)).isEqualTo(L);
        assertThat(commands.get(1)).isEqualTo(L);
        assertThat(commands.get(2)).isEqualTo(M);
    }

    @Test
    public void should_throw_exception_when_no_possible_command_input() {
        // Given
        String commandLine = "MA";

        // When
        assertThatThrownBy(() -> {
            convertInputToCommandsList(commandLine);
        }).isInstanceOf(CannotInputException.class)
                .hasMessage("Command can only be M, L, R.");
    }
}