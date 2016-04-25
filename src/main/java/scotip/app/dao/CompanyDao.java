package scotip.app.dao;

import scotip.app.model.Company;

/**
 * Created by Pierre on 24/04/2016.
 */
public interface CompanyDao {

    Company findById(int id);
    Company findByMail(String mail);
}
