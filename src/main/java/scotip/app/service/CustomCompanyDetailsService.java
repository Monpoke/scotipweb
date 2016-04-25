package scotip.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scotip.app.model.Company;

/**
 * Created by Pierre on 24/04/2016.
 */
@Service("customCompanyDetailsService")
public class CustomCompanyDetailsService implements UserDetailsService {

    @Autowired
    private CompanyService companyService;

    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        Company user = companyService.findByMail(email);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println("TEst password: " + bCryptPasswordEncoder.encode("test"));

        System.out.println("Company email: "+email +" -> " +user);



        if(user==null){
            System.out.println("Company email not found");
            throw new UsernameNotFoundException("Company not found");
        }

        return user;


/*
        return new org.springframework.security.core.userdetails.User(user.getContactMail(), user.getPassword(),
                user.getState().equals("Active"), true, true, true, getGrantedAuthorities(user));*/
    }

}