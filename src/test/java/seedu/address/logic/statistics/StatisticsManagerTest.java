package seedu.address.logic.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class StatisticsManagerTest {

    @Test
    void test_newStatisticsManagerShouldHaveOneLoginEvent() {
        StatisticsManager sm = new StatisticsManager();
        assertEquals(sm.getStatistics().getEventLog().size(), 1);
        assertEquals(sm.getStatistics().getEventLog().get(0).getEventType(), EventType.LOGIN);
    }

    @Test
    void testGetLastLogin_shouldBeNull_ifEventsHas1Login() {
        StatisticsManager sm = new StatisticsManager();
        assertNull(sm.getLastLogin());
    }

    @Test
    void testGetLastLoginString_shouldBePlaceholder_ifEventsHas1Login() {
        StatisticsManager sm = new StatisticsManager();
        assertEquals(sm.getLastLoginString(), "None - first login.");
    }


    @Test
    void testGetLastLogin_shouldBeCorrect_ifEventsHas2Logins() {
        StatisticsManager sm = new StatisticsManager();
        Event event1 = sm.getStatistics().getEventLog().get(0);
        sm.getStatistics().registerAppLogout();
        sm.getStatistics().registerAppLogin();
        assertEquals(sm.getLastLogin(), event1.getLocalDateTime());
    }

    @Test
    void testGetLastLogin_shouldBeSecondLatestLogin_ifEventsHasSomeLoginLogoutEvents() {
        StatisticsManager sm = new StatisticsManager();
        Event event1 = sm.getStatistics().getEventLog()
                .get(sm.getStatistics().getEventLog().size() - 1);
        sm.getStatistics().registerAppLogout();
        sm.getStatistics().registerAppLogin();
        for (int i = 0; i < 10000; ++i) {
            ; // simulate blocking
        }
        Event event2 = sm.getStatistics().getEventLog()
                .get(sm.getStatistics().getEventLog().size() - 1);
        sm.getStatistics().registerAppLogout();
        for (int i = 0; i < 10000; ++i) {
            ; // simulate blocking
        }
        sm.getStatistics().registerAppLogin();
        Event event3 = sm.getStatistics().getEventLog()
                .get(sm.getStatistics().getEventLog().size() - 1);
        for (int i = 0; i < 10000; ++i) {
            ; // simulate blocking
        }

        assertEquals(sm.getLastLogin(), event2.getLocalDateTime());
    }

    @Test
    void testGetLastLogin_shouldBeSecondLatestLogin_ifEventsHasMoreRandomEvents() {
        StatisticsManager sm = new StatisticsManager();
        Event event1 = sm.getStatistics().getEventLog()
                .get(sm.getStatistics().getEventLog().size() - 1);
        sm.getStatistics().registerAppLogout();
        sm.getStatistics().registerAppLogin();
        for (int i = 0; i < 10000; ++i) {
            ; // simulate blocking
        }
        Event event2 = sm.getStatistics().getEventLog()
                .get(sm.getStatistics().getEventLog().size() - 1);
        sm.getStatistics().registerAppLogout();
        for (int i = 0; i < 10000; ++i) {
            ; // simulate blocking
        }
        sm.getStatistics().registerAppLogin();
        Event event3 = sm.getStatistics().getEventLog()
                .get(sm.getStatistics().getEventLog().size() - 1);
        for (int i = 0; i < 10000; ++i) {
            ; // simulate blocking
        }
        sm.getStatistics().registerAppLogout();
        sm.getStatistics().registerAppLogout();
        sm.getStatistics().registerAppLogout();
        sm.getStatistics().registerAppLogout();
        sm.getStatistics().registerAppLogout();
        sm.getStatistics().registerAppLogout();
        sm.getStatistics().registerAppLogout();
        sm.getStatistics().registerAppLogout();
        sm.getStatistics().registerAppLogout();
        sm.getStatistics().registerAppLogout();
        sm.getStatistics().registerAppLogout();
        sm.getStatistics().registerAppLogout();
        sm.getStatistics().registerAppLogout();
        sm.getStatistics().registerAppLogout();
        sm.getStatistics().registerAppLogout();
        sm.getStatistics().registerAppLogout();
        sm.getStatistics().registerAppLogin();
        Event correctEvent = sm.getStatistics().getEventLog()
                .get(sm.getStatistics().getEventLog().size() - 1);
        sm.getStatistics().registerAppLogout();
        sm.getStatistics().registerAppLogout();
        sm.getStatistics().registerAppLogout();
        sm.getStatistics().registerAppLogout();
        sm.getStatistics().registerAppLogin();

        assertEquals(sm.getLastLogin(), correctEvent.getLocalDateTime());
    }
}
