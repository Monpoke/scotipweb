package scotip.app.tools;

import scotip.app.model.Module;
import scotip.app.model.ModuleModel;

/**
 * Created by Pierre on 13/05/2016.
 */
public abstract  class CreateDefaultModule {

    public static Module create(ModuleModel moduleModel, Module parentModule) {
        Module toReturn = new Module();

        toReturn.setSwitchboard(parentModule.getSwitchboard());

        // model and parent
        toReturn.setModuleModel(moduleModel);
        toReturn.setModuleParent(parentModule);

        // provisioning
        provisioning(toReturn,moduleModel);

        return toReturn;
    }

    /**
     *
     * @param toReturn
     * @param moduleModel
     */
    private static void provisioning(Module toReturn, ModuleModel moduleModel) {
        if(moduleModel.getSlug().equals("playback")){
            module_playback(toReturn);
        }
    }

    private static void module_playback(Module toReturn) {
        toReturn.getSettings().put("file","hello-world");
    }
}
