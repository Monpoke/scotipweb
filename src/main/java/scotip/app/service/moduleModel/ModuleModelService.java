package scotip.app.service.moduleModel;

import scotip.app.dto.SwitchboardDto;
import scotip.app.model.Company;
import scotip.app.model.ModuleModel;
import scotip.app.model.Switchboard;

import java.util.List;
import java.util.Map;

/**
 * Created by Pierre on 29/04/2016.
 */
public interface ModuleModelService {

    Map<String, ModuleModel> getModulesBySlugsAndMap(String[] slugs);
}
