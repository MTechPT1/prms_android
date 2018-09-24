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
        user.setUserId("11223232");
        user.setUserName("ChangLing Liu");
        maintainUserAdapter = new MaintainUserAdapter(new MaintainUserScreen(), user);
    }

    @After
    public void tearDown() throws Exception {
        maintainUserAdapter = null;
        user = null;
    }

    @Test
    public void getItemViewType() {
        assertEquals(0, maintainUserAdapter.getItemViewType(0));
        assertEquals(1, maintainUserAdapter.getItemViewType(1));
        assertEquals(2, maintainUserAdapter.getItemViewType(2));
        assertEquals(2, maintainUserAdapter.getItemViewType(3));
        assertEquals(2, maintainUserAdapter.getItemViewType(10));
    }

    @Test
    public void getItem() {
       assertEquals(user.getUserName(),maintainUserAdapter.getItem(0));
       assertEquals(user.getJoinDate(),maintainUserAdapter.getItem(1));
       assertEquals("Presenter",maintainUserAdapter.getItem(2));
       assertEquals("Producer", maintainUserAdapter.getItem(3));
    }

    @Test
    public void roleSelected(){
        assertEquals(true, maintainUserAdapter.roleSelected(0));
        assertEquals(true, maintainUserAdapter.roleSelected(2));
        assertEquals(false, maintainUserAdapter.roleSelected(3));
    }

    @Test
    public void didSelectCheckbox(){
        maintainUserAdapter.didSelectCheckbox(2);
        assertEquals(false, user.isPresenter());

        maintainUserAdapter.didSelectCheckbox(3);
        assertEquals(true, user.isProducer());
    }

}