package invoice.xr.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import invoice.xr.model.InvoiceUserInfo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private InvoiceUserInfoService invoiceUserInfoService;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		InvoiceUserInfo userInfo = invoiceUserInfoService.getUserInfoByUserName(userName);
		GrantedAuthority authority = new SimpleGrantedAuthority(userInfo.getRole());
		return new User(userInfo.getUserName(), userInfo.getPassword(), Arrays.asList(authority));
	}
}

