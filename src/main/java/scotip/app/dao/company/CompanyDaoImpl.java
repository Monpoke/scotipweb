package scotip.app.dao.company;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import scotip.app.dao.AbstractDao;
import scotip.app.model.Company;

@Transactional
@Repository("companyDao")
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


    @Override
    public void refresh(Company company) {
        getSession().refresh(company);
    }


}