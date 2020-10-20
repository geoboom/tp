package seedu.address.logic.phase;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PhaseManagerTest {
    @Test
    public void assertInitialPhase_equalsLobby() {
        PhaseManager phaseManager = new PhaseManager();
        assertEquals(phaseManager.getPhase(), Phase.LOBBY);
    }
}