package scotip.app.dao.company;

import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import scotip.app.model.Company;

/**
 * Created by Pierre on 24/04/2016.
 */
public interface CompanyDao {

    Company findById(int id);
    Company findByMail(String mail);
    Company saveCompany(Company company);

    void refresh(Company company);

}
