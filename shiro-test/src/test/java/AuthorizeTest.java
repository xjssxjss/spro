import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Sean on 2019/7/9.
 * 授权
 */
public class AuthorizeTest {

    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void addUser(){
        simpleAccountRealm.addAccount("sean","123456","admin","");
    }

    @Test
    public void authorizeTest(){
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);

        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("sean","123456");

        subject.login(token);

        System.out.println("isAuthenticated>>>>>>>>>>>>>" + subject.isAuthenticated());
        //check role
        subject.checkRole("admin");
    }
}
