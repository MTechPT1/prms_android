package sg.edu.nus.iss.phoenix.schedule.android.delegate;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import sg.edu.nus.iss.phoenix.schedule.android.controller.ReviewSelectScheduleProgramController;
import sg.edu.nus.iss.phoenix.schedule.android.entity.ProgramSlot;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * RetrieveScheduleDelegateTest test for RetrieveScheduleDelegate
 */
@RunWith(MockitoJUnitRunner.class)
public class RetrieveScheduleDelegateTest {

    String EXPECTED_WEEK_VALUE = "39";
    String EXPECTED_DURATION = "30";

    private static final String TAG = RetrieveScheduleDelegate.class.getName();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
        retrieveScheduleDelegate = null;
    }

    @Mock
    ReviewSelectScheduleProgramController reviewSelectScheduleProgramController;

    @Mock
    JSONObject reader;

    @InjectMocks
    RetrieveScheduleDelegate retrieveScheduleDelegate = new RetrieveScheduleDelegate(reviewSelectScheduleProgramController);

    @Test
    public void setScheduleFromReponse() throws Exception {
        when(reader.getString("assignedBy")).thenReturn("pointyhead");
        when(reader.getString("duration")).thenReturn("30");
        when(reader.getString("presenterId")).thenReturn("pointyhead");
        when(reader.getString("producerId")).thenReturn("pointyhead");
        when(reader.getString("programName")).thenReturn("create1");
        when(reader.getString("programSlotId")).thenReturn("52");
        when(reader.getString("startDate")).thenReturn("2018-09-27 00:00:00.0");
        when(reader.getString("weekId")).thenReturn("39");

        ProgramSlot programSlot = retrieveScheduleDelegate.setProgramSlotFromReponse(reader);
        assertEquals(new Integer(EXPECTED_WEEK_VALUE), new Integer(programSlot.getWeekId()));
        assertEquals(new Integer(EXPECTED_DURATION), new Integer(programSlot.getDuration()));

    }

}
