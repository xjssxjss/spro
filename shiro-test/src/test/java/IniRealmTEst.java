import org.apache.catalina.realm.JDBCRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;


/**
 * Created by Sean on 2019/7/9.
 */
public class IniRealmTEst {

    @Test
    public void iniRealmTest(){


        JDBCRealm jdbcRealm = new JDBCRealm();

        IniRealm iniRealm = new IniRealm("classpath:user.ini");

        //1、构建SecurityManager 环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();

        //设置realm
        defaultSecurityManager.setRealm(iniRealm);

        //设置securityManager
        SecurityUtils.setSecurityManager(defaultSecurityManager);

        //2、设置主体提交请求

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("sean","123456");
        subject.login(token);

        System.out.println("isAuthenticated>>>>>>>>>>>>"+subject.isAuthenticated());

        subject.checkRole("operUser");

        subject.checkPermission("user:update");
    }
}
