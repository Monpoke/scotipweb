package scotip.app.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import scotip.app.model.Company;

import java.io.Serializable;

@Repository("userDao")
public class CompanyDaoImpl extends AbstractDao<Integer, Company> implements CompanyDao {

    public Company findById(int id) {
        return getByKey(id);
    }

    public Company findByMail(String mail) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("ContactMail", mail));
        return (Company) crit.uniqueResult();
    }

    @Override
    public Company saveCompany(Company company) {
        company.setId((int)getSession().save(company));
        return company;
    }


}