package scotip.app.service.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scotip.app.dao.company.CompanyDao;
import scotip.app.dto.CompanyDto;
import scotip.app.exceptions.EmailExistsException;
import scotip.app.model.Company;

/**
 * Created by Pierre on 24/04/2016.
 */
@Service("companyService")
@Transactional
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyDao dao;

    // ENCODER
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Company findById(int id) {
        return dao.findById(id);
    }

    @Override
    public Company findByMail(String mail) {
        return dao.findByMail(mail);
    }


    @Override
    public Company registerNewCompany(CompanyDto companyDto) throws EmailExistsException {

        if (emailExists(companyDto.getContactMail())) {
            throw new EmailExistsException("There is an account with that email adress: " +
                    companyDto.getContactMail());
        }

        final Company company = new Company();

        // Copy all fields
        company.setName(companyDto.getName());
        company.setAddress(companyDto.getAddress());
        company.setAddress2(companyDto.getAddress2());
        company.setCity(companyDto.getCity());
        company.setPostcode(companyDto.getPostcode());
        company.setCountry(companyDto.getCountry());
        company.setPhoneNumber(companyDto.getPhoneNumber());
        company.setContactName(companyDto.getContactName());
        company.setContactMail(companyDto.getContactMail());
        company.setContactPhone(companyDto.getContactPhone());


        company.setPassword(getPasswordEncoder().encode(companyDto.getPassword()));


        return dao.saveCompany(company);
    }

    @Override
    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    /**
     * Check if the email is already registered
     *
     * @param contactMail
     * @return
     */
    private boolean emailExists(String contactMail) {
        final Company byMail = dao.findByMail(contactMail);
        return byMail != null;
    }


}
