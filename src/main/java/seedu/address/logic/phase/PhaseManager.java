package seedu.address.logic.phase;

import seedu.address.logic.commands.Command;

/**
 * Used to manage the phase the app is in: LOBBY, QUIZ, RESULTS and its transitions between phases
 */
public class PhaseManager {


    Phase phase;

    private PhaseManager(Phase phase) {
        this.phase = phase;
    }

    public PhaseManager() {
        this(Phase.LOBBY);
    }

    /**
     * Get the next phase resulting from executing this command and make the transition to that
     * phase.
     *
     * @param command {@code Command} which we should get the next phase from
     */
    public void doTransition(Command command) {
        assert (command.nextPhase != null);
        this.phase = command.nextPhase;
    }

    /**
     * Checks if command can be executed / is valid in the current phase.
     *
     * @param command {@code Command} which should be checked
     * @return whether command is allowed to be executed in current phase
     */
    public boolean commandAllowed(Command command) {
        return command.allowedPhases.contains(phase);
    }

    public Phase getPhase() {
        return phase;
    }
}
