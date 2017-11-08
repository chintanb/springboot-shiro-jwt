package com.stormpath.shiro.samples.springboot.common.configuration;

import com.stormpath.shiro.samples.springboot.common.dao.UserDAO;
import com.stormpath.shiro.samples.springboot.common.dao.UserEntity;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by cbarad on 2017-11-07.
 */
public class MaaSSecurityRealm extends AuthorizingRealm {

    private UserDAO userDAO;

    public MaaSSecurityRealm(UserDAO userDAO) {
        super(new AllowAllCredentialsMatcher());
        this.userDAO = userDAO;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        Set roles = new HashSet<>();
        Set permissions = new HashSet<>();

//        Collection<User> principalsList = principals.byType(User.class);
//        for (User user : principalsList) {
//            roles.addAll(user.getRolesName());
//            for (Role role : user.getRoleList()) {
//                for (Iterator iterator = role.getPermissionList().iterator(); iterator.hasNext(); ) {
//                    Permission permission = (Permission)iterator.next();
//                    permissions.add(new WildcardPermission(permission.getPermissionname()));
//                }
//            }
//        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
        info.setRoles(roles);
        info.setObjectPermissions(permissions);

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upat = (UsernamePasswordToken) token;
        UserEntity user = userDAO.findByUsername(upat.getUsername()).orElse(null);
        if(user != null && BCrypt.checkpw(String.valueOf(upat.getPassword()), user.getPassword())) {
            return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        }
        else {
            throw new AuthenticationException("Invalid username/password combination!");
        }
    }
}
