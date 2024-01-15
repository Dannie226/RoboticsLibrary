package frc.team4272.globals;

import java.util.Set;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * Custom state command.
 * A State can only ever require one subsystem. the reason for this is to
 * prevent coupling between subsystems.
 */
public abstract class State<T extends Subsystem> extends Command {
    protected final T requiredSubsystem;

    public State(T subsystem) {
        requiredSubsystem = subsystem;
    }

    @Override
    public Set<Subsystem> getRequirements() {
        return Set.of(requiredSubsystem);
    }
}