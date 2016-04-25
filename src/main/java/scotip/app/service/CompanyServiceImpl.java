package scotip.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scotip.app.dao.CompanyDao;
import scotip.app.model.Company;

/**
 * Created by Pierre on 24/04/2016.
 */
@Service("companyService")
@Transactional
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyDao dao;

    @Override
    public Company findById(int id) {
        return dao.findById(id);
    }

    @Override
    public Company findByMail(String mail) {
        return dao.findByMail(mail);
    }
}
