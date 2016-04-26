package scotip.app.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import scotip.app.dto.CompanyDto;
import scotip.app.exceptions.EmailExistsException;
import scotip.app.model.Company;
import sun.security.util.Password;

/**
 * Created by Pierre on 24/04/2016.
 */

public interface CompanyService {

    Company findById(int id);
    Company findByMail(String mail);
    Company registerNewCompany(CompanyDto companyDto) throws EmailExistsException;

    PasswordEncoder getPasswordEncoder();
}
