package sg.edu.nus.iss.phoenix.schedule.android.ui;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import sg.edu.nus.iss.phoenix.createuser.android.entity.User;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.schedule.android.entity.ProgramSlot;

import static org.junit.Assert.assertEquals;

public class ScheduleProgramAdapterTest implements ScheduleProgramAdapter.ModifyScheduleListener{

    private ScheduleProgramAdapter mSPAdapter;

    @Before
    public void setUp() throws Exception {

        RadioProgram rp1 = new RadioProgram("Symphony92.4", "Symphony", "30");
        RadioProgram rp2 = new RadioProgram("Yes93.3", "Best", "30");
        User presenter = new User();
        presenter.setProducer(true);
        presenter.setPresenter(true);
        presenter.setUserId("Presenter");
        presenter.setUserName("Presenter");
        presenter.setJoinDate("1 Jan");
        User producer = new User();
        producer.setProducer(true);
        producer.setPresenter(true);
        producer.setUserId("Producer");
        producer.setUserName("Producer");
        producer.setJoinDate("1 Jan");
        ProgramSlot ps = new ProgramSlot(1, rp1, presenter, producer, 30, "WK", "1200", 1);
        ProgramSlot ps2 = new ProgramSlot(2, rp2, presenter, producer, 60, "WK", "1200", 2);
        ArrayList<ProgramSlot> pslist = new ArrayList<>();
        pslist.add(ps);
        pslist.add(ps2);

        mSPAdapter = new ScheduleProgramAdapter(new ReviewSelectScheduleProgramScreen(), pslist, this);
    }

    @After
    public void tearDown() throws Exception {
        mSPAdapter = null;
    }

    @Test
    public void test(){
        assertEquals("ID1",1, mSPAdapter.programSlots.get(0).getId());
        assertEquals("ID2",2, mSPAdapter.programSlots.get(1).getId());

        assertEquals("RadioProgramName1","Symphony92.4", mSPAdapter.programSlots.get(0).getRadioProgram().getRadioProgramName());
        assertEquals("RadioProgramName2","Yes93.3", mSPAdapter.programSlots.get(1).getRadioProgram().getRadioProgramName());
        assertEquals("RadioProgramDescription1","Symphony", mSPAdapter.programSlots.get(0).getRadioProgram().getRadioProgramDescription());
        assertEquals("RadioProgramDescription2","Best", mSPAdapter.programSlots.get(1).getRadioProgram().getRadioProgramDescription());
        assertEquals("RadioProgramDuration1","30", mSPAdapter.programSlots.get(0).getRadioProgram().getRadioProgramDuration());
        assertEquals("RadioProgramDuration2","30", mSPAdapter.programSlots.get(1).getRadioProgram().getRadioProgramDuration());

        assertEquals("PresenterID1","Presenter", mSPAdapter.programSlots.get(0).getPresenter().getUserId());
        assertEquals("PresenterID2","Presenter", mSPAdapter.programSlots.get(1).getPresenter().getUserId());
        assertEquals("PresenterName1","Presenter", mSPAdapter.programSlots.get(0).getPresenter().getUserName());
        assertEquals("PresenterName2","Presenter", mSPAdapter.programSlots.get(1).getPresenter().getUserName());
        assertEquals("PresenterJoinDate1","1 Jan", mSPAdapter.programSlots.get(0).getPresenter().getJoinDate());
        assertEquals("PresenterJoinDate2","1 Jan", mSPAdapter.programSlots.get(1).getPresenter().getJoinDate());

        assertEquals("ProducerID1","Producer", mSPAdapter.programSlots.get(0).getProducer().getUserId());
        assertEquals("ProducerID2","Producer", mSPAdapter.programSlots.get(1).getProducer().getUserId());
        assertEquals("ProducerName1","Producer", mSPAdapter.programSlots.get(0).getProducer().getUserName());
        assertEquals("ProducerName2","Producer", mSPAdapter.programSlots.get(1).getProducer().getUserName());
        assertEquals("ProducerJoinDate1","1 Jan", mSPAdapter.programSlots.get(0).getProducer().getJoinDate());
        assertEquals("ProducerJoinDate2","1 Jan", mSPAdapter.programSlots.get(1).getProducer().getJoinDate());

        assertEquals("Duration1",30, mSPAdapter.programSlots.get(0).getDuration());
        assertEquals("Duration2",60, mSPAdapter.programSlots.get(1).getDuration());

        assertEquals("AssignedBy1","WK", mSPAdapter.programSlots.get(0).getAssignedBy());
        assertEquals("AssignedBy2","WK", mSPAdapter.programSlots.get(1).getAssignedBy());

        assertEquals("StartTime1","1200", mSPAdapter.programSlots.get(0).getStartTime());
        assertEquals("StartTime2","1200", mSPAdapter.programSlots.get(1).getStartTime());

        assertEquals("WeekId1",1, mSPAdapter.programSlots.get(0).getWeekId());
        assertEquals("WeekId2",2, mSPAdapter.programSlots.get(1).getWeekId());

    }

    @Override
    public void editSchedule(int position) {

    }

    @Override
    public void copySchedule(int position) {

    }

    @Override
    public void deleteSchedule(int position) {

    }
}