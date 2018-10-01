package sg.edu.nus.iss.phoenix.createuser.android.ui;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.phoenix.createuser.android.entity.User;

import static org.junit.Assert.*;

public class MaintainUserAdapterTest {

    private  MaintainUserAdapter maintainUserAdapter;
    private  User user;
    @Before
    public void setUp() throws Exception {

        user = new User();
        user.setPresenter(true);
        user.setProducer(false);
        user.setUserId("changling");
        user.setUserName("ChangLing Liu");
        user.setJoinDate("18 June 2018");
        maintainUserAdapter = new MaintainUserAdapter(new MaintainUserScreen(), user,0);
    }

    @After
    public void tearDown() throws Exception {
        maintainUserAdapter = null;
        user = null;
    }

    @Test
    public void getItemViewType() {
        assertEquals("Current view type should be User Id(0)",0, maintainUserAdapter.getItemViewType(0));
        assertEquals("Current view type should be User Name (1)",1, maintainUserAdapter.getItemViewType(1));
        assertEquals("Current view type should be Password (2)",2, maintainUserAdapter.getItemViewType(2));
        assertEquals("Current view type should be Join Date (3)",3, maintainUserAdapter.getItemViewType(3));
        assertEquals("Current view type should be role (4)",4, maintainUserAdapter.getItemViewType(10));
    }

    @Test
    public void getItem() {
        assertEquals("Current User name should be changling",user.getUserId(),maintainUserAdapter.getItem(0));
        assertEquals("Current User name should be ChangLing Liu",user.getUserName(),maintainUserAdapter.getItem(1));
        assertEquals("Current User name should be 18 June 2018",user.getJoinDate(),maintainUserAdapter.getItem(3));
        assertEquals("Current User name should be Presenter","Presenter",maintainUserAdapter.getItem(4));
        assertEquals("Current User name should be Producer","Producer", maintainUserAdapter.getItem(5));
    }

    @Test
    public void roleSelected(){
        assertEquals(true, maintainUserAdapter.roleSelected(0));
        assertEquals(true, maintainUserAdapter.roleSelected(2));
       assertEquals(true, maintainUserAdapter.roleSelected(3));
       assertEquals(true, maintainUserAdapter.roleSelected(4));
       assertEquals(false, maintainUserAdapter.roleSelected(5));
    }

    @Test
    public void didSelectCheckbox(){
        maintainUserAdapter.didSelectCheckbox(4);
        assertEquals(false, user.isPresenter());

        maintainUserAdapter.didSelectCheckbox(5);
        assertEquals(true, user.isProducer());
    }

}