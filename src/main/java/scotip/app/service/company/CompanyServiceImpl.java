/*
 * Copyright (c) 2016. Pierre BOURGEOIS
 *
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deal in the Software without restriction,
 *  including without limitation the rights to use, copy, modify, merge,
 *  publish, distribute, sublicense, and/or sell copies of the Software, and
 *  to permit persons to whom the Software is furnished to do so, subject
 *  to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

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
    public Company update(Company c){return dao.update(c);}

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

    @Override
    public void refresh(Company company) {
        dao.refresh(company);
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
