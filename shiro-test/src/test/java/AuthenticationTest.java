import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * Created by Sean on 2019/7/9.
 */
public class AuthenticationTest {

    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    public void addUser(){
        simpleAccountRealm.addAccount("sean","123456");
    }

    @Test
    public void testAuthentication(){
        addUser();
        //1、构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);


        //2、主题提交认证,shiro工具类获取主题

        //设置securityManager环境
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();


        UsernamePasswordToken token = new UsernamePasswordToken("sean","123456");
        subject.login(token);

        System.out.println("是否认证 isAuthenticated >>>>>"+subject.isAuthenticated());

        subject.logout();
        System.out.println("是否认证 isAuthenticated >>>>>"+subject.isAuthenticated());
    }
}
