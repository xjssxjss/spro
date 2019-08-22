import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * Created by Sean on 2019/7/9.
 */
public class MyRealm extends AuthorizingRealm{

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        return null;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        //1、从主体传过来的认证信息中，获得用户名
        String userName = (String)authenticationToken.getPrincipal();

        //2、通过用户名到数据库获取凭证
        String passWord = getPassWordByUserName(userName);
        if(null == passWord){
            return null;
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo("sean",passWord,"myRealm");
        return authenticationInfo;
    }

    private String getPassWordByUserName(String userName){
        return "123456";
    }
}
