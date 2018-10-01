package sg.edu.nus.iss.phoenix.schedule.android.delegate;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.List;

import sg.edu.nus.iss.phoenix.schedule.android.controller.ReviewSelectScheduleProgramController;
import sg.edu.nus.iss.phoenix.schedule.android.entity.ProgramSlot;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/*
 * Created by neelima on 25/9/2018.
 */

@RunWith(MockitoJUnitRunner.class)
public class RetrieveScheduleDelegateTest {

    String EXPECTED_WEEK_VALUE = "39";
    String EXPECTED_CONTENT_TYPE = "";
    String EXPECTED_STATUS_CODE_SUCCESS = "500";

    private static final String TAG = RetrieveScheduleDelegate.class.getName();



    @Before
    public void setUp() throws Exception {
        //MockitoAnnotations.initMocks(this);
        //retrieveScheduleDelegate = new RetrieveScheduleDelegate(new ReviewSelectScheduleProgramController());
    }

    @After
    public void tearDown() throws Exception {
        retrieveScheduleDelegate = null;
    }



    @Mock
    ReviewSelectScheduleProgramController reviewSelectScheduleProgramController;

    @Mock
    JSONObject reader;

   //@Mock
   //JSONArray rpArray;

    @InjectMocks
    RetrieveScheduleDelegate retrieveScheduleDelegate = new RetrieveScheduleDelegate(reviewSelectScheduleProgramController);

    @Test
    public void doInBackground() throws Exception {
        String[] params= new String[2];
        params[0] = "39";
        params[1] = "2018";
       /* String PRMS_BASE_URL_SCHEDULE_PROGRAM = "http://58.182.85.189:8080/phoenix/rest/schedule";
when(mockUri.parse(PRMS_BASE_URL_SCHEDULE_PROGRAM)).thenReturn(mockUri);
        String response = retrieveScheduleDelegate.doInBackground(params);
        // Verify the response obtained matches the values we expect.
        JSONObject jsonObjectFromResponse = new JSONObject(response);
        Log.v(TAG, String.valueOf(jsonObjectFromResponse));
        Log.v(TAG, String.valueOf(jsonObjectFromResponse.get("psList")));*/

        //assertEquals(EXPECTED_RESPONSE_VALUE, jsonObjectFromResponse.get("psList"));
        //assertEquals(EXPECTED_CONTENT_TYPE, jsonObjectFromResponse.get("Headers").get("Content-Type"));
        //assertEquals(EXPECTED_STATUS_CODE_SUCCESS, response.getStatusCode());
    }

    @Test
    public void setScheduleFromReponse() throws Exception {
       // HashMap<String,String> map = new HashMap();
       // map.put("assignedBy","pointyhead");
        //map.put("duration","1");
       // String key=null;
        //'presenterId':'pointyhead','producerId':'pointyhead','programName':'create1','programSlotId':52,'startDate':'2018-09-27 00:00:00.0','weekId':39}]}";


        //rpArray.put("{'assignedBy':'pointyhead','duration':1,'presenterId':'pointyhead','producerId':'pointyhead','programName':'create1','programSlotId':52,'startDate':'2018-09-27 00:00:00.0','weekId':39}");
        when(reader.getString("assignedBy")).thenReturn("pointyhead");
        when(reader.getString("duration")).thenReturn("1");
        when(reader.getString("presenterId")).thenReturn("pointyhead");
        when(reader.getString("producerId")).thenReturn("pointyhead");
        when(reader.getString("programName")).thenReturn("create1");
        when(reader.getString("programSlotId")).thenReturn("52");
        when(reader.getString("startDate")).thenReturn("2018-09-27 00:00:00.0");
        when(reader.getString("weekId")).thenReturn("39");

        ProgramSlot programSlot = retrieveScheduleDelegate.setProgramSlotFromReponse(reader);
        assertEquals(new Integer(EXPECTED_WEEK_VALUE), new Integer(programSlot.getWeekId()));
    }

}
