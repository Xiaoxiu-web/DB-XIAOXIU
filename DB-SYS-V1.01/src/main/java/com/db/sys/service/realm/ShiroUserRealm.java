package com.db.sys.service.realm;

import java.util.HashSet;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.db.sys.dao.SysMenuDao;
import com.db.sys.dao.SysRoleMenuDao;
import com.db.sys.dao.SysUserDao;
import com.db.sys.dao.SysUserRoleDao;
import com.db.sys.entity.SysUser;

@Service
public class ShiroUserRealm extends AuthorizingRealm{
	
	private Logger log=LoggerFactory.getLogger(ShiroUserRealm.class);
	
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	@Autowired
	private SysMenuDao sysMenuDao;
	
	
	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		HashedCredentialsMatcher cMatcher = new HashedCredentialsMatcher();
		cMatcher.setHashAlgorithmName("MD5");
		cMatcher.setHashIterations(1);
		super.setCredentialsMatcher(cMatcher);
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken Token=(UsernamePasswordToken)token;
		String username = Token.getUsername();
		SysUser user = sysUserDao.findUserByUserName(username);
		if(user==null)throw new UnknownAccountException();
		if(user.getValid()==0)throw new LockedAccountException();
		ByteSource salt = ByteSource.Util.bytes(user.getSalt());
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getPassword(),salt,getName());
		log.debug("log_执行数据库登录用户查询查询");
		return info;
	}
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SysUser user = (SysUser)principals.getPrimaryPrincipal();
		Integer userId = user.getId();
		
		List<Integer> URoleIds = sysUserRoleDao.findRoleIdsByUserId(userId);
		if(URoleIds==null||URoleIds.size()==0)throw new AuthorizationException();
		Integer[] array= {};
		List<Integer> menuIds = sysRoleMenuDao.findMenuIdsByRoleIds(URoleIds.toArray(array));
		if(menuIds==null||menuIds.size()==0)throw new AuthorizationException();
		List<String> permissions = sysMenuDao.findPermissions(menuIds.toArray(array));
		
		HashSet<String> set = new HashSet<>();
		for (String str : permissions) {
			if(!StringUtils.isEmpty(str)) {
				set.add(str);
			}
		}
		System.out.println(set.toString());
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(set);
		log.debug("log_执行数据库用户权限查询");
		return info;
	}

}
