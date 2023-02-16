package com.example.config;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.model.Article;
import com.example.model.Systemadmin;
import com.example.service.SystemadminService;

@Service
public class DetailService implements UserDetailsService{
	
	@Autowired
	private SystemadminService systemadminService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Systemadmin systemadmin=systemadminService.getSystemadmin(username);

        if (systemadmin != null) {  
        	List<Systemadmin> roles=new ArrayList<>();
        	roles.add(systemadmin);
            return new org.springframework.security.core.userdetails.User(systemadmin.getAdminaccount()
                    , systemadmin.getAdminpassword(),
                    roles.stream()
                            .map((role) -> new SimpleGrantedAuthority(role.getLevel()))
                            .collect(Collectors.toList()));
        } else {
            throw new UsernameNotFoundException("Invalid email or password");
        }
	}

}
