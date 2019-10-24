package com.sprint.dash.auth.service;

import com.sprint.dash.entities.auth.DashAuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DashUserDetailService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        DashAuthUser authUser = new DashAuthUser("dash", passwordEncoder.encode("dash"), true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("user:add"));

//        BeanUtils.copyProperties(systemUser,authUser);
        return authUser;
    }

}
