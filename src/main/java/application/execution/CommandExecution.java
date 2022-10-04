package application.execution;

import application.rover.Rover;

import static application.rover.Orientation.*;

public class CommandExecution {
    public static Rover executeCommandM(Rover rover) {
        switch (rover.getOrientation()) {
            case N:
                moveN(rover);
                break;
            case S:
                moveS(rover);
                break;
            case E:
                moveE(rover);
                break;
            case W:
                moveW(rover);
                break;
        }
        return rover;
    }

    public static Rover moveN(Rover rover) {
        int y = rover.getY();
        rover.setY(y + 1);
        return rover;
    }

    public static Rover moveS(Rover rover) {
        int y = rover.getY();
        rover.setY(y - 1);
        return rover;
    }

    public static Rover moveE(Rover rover) {
        int x = rover.getX();
        rover.setX(x + 1);
        return rover;
    }

    public static Rover moveW(Rover rover) {
        int x = rover.getX();
        rover.setX(x - 1);
        return rover;
    }

    public static Rover executeCommandL(Rover rover) {
        switch (rover.getOrientation()) {
            case N:
                rover.setOrientation(W);
                break;
            case S:
                rover.setOrientation(E);
                break;
            case E:
                rover.setOrientation(N);
                break;
            case W:
                rover.setOrientation(S);
                break;
        }
        return rover;
    }

    public static Rover executeCommandR(Rover rover) {
        switch (rover.getOrientation()) {
            case N:
                rover.setOrientation(E);
                break;
            case S:
                rover.setOrientation(W);
                break;
            case E:
                rover.setOrientation(S);
                break;
            case W:
                rover.setOrientation(N);
                break;
        }
        return rover;
    }
}
