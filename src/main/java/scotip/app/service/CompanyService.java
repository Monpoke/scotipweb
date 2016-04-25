package scotip.app.service;

import org.springframework.stereotype.Service;
import scotip.app.model.Company;

/**
 * Created by Pierre on 24/04/2016.
 */

public interface CompanyService {

    Company findById(int id);
    Company findByMail(String mail);

}
