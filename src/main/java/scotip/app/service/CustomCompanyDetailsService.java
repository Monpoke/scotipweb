package scotip.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scotip.app.model.Company;
import scotip.app.model.CompanyProfile;
import scotip.app.model.CompanyProfileType;

import java.util.ArrayList;
import java.util.List;

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


        return new org.springframework.security.core.userdetails.User(user.getContactMail(), user.getPassword(),
                user.getState().equals("Active"), true, true, true, getGrantedAuthorities(user));
    }


    private List<GrantedAuthority> getGrantedAuthorities(Company user){
        List<GrantedAuthority> authorities = new ArrayList<>();


        /*
        for(CompanyProfile companyProfile : user.getCompanyProfiles()){
            System.out.println("UserProfile : "+ companyProfile);
           // authorities.add(new SimpleGrantedAuthority("ROLE_"+companyProfile.getType()));
            authorities.add(new SimpleGrantedAuthority("ROLE_COMPANY"));
        }*/

        authorities.add(new SimpleGrantedAuthority("ROLE_COMPANY"));

        System.out.print("authorities :"+authorities);
        return authorities;
    }

}