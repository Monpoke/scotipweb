package scotip.app.service.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    /**
     * Override the default method. Load by email.
     *
     * @param email
     * @return
     * @throws UsernameNotFoundException
     */
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        Company user = companyService.findByMail(email);

        if (user == null) {
            System.out.println("Company email not found");
            throw new UsernameNotFoundException("Company not found");
        }

        return user;
    }

}