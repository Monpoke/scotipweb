package scotip.app.dao.model;

import scotip.app.model.Company;
import scotip.app.model.Module;
import scotip.app.model.Switchboard;

/**
 * Created by Pierre on 13/05/2016.
 */
public interface ModuleDao {
    Module findByIdAndCompany(int parentId, Company company);

    Module saveModule(Module module);

    void remove(Module module);

    void updatePhoneKeyFrom(Switchboard switchboard, int from, int to);
}
