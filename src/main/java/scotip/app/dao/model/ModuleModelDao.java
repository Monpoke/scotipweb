package scotip.app.dao.model;

import scotip.app.model.ModuleModel;

import java.util.List;

/**
 * Created by Pierre on 29/04/2016.
 */
public interface ModuleModelDao {
    ModuleModel getModelBySlug(String slug);

    List<ModuleModel> getModelsWithSlug(String[] slugs);
}
