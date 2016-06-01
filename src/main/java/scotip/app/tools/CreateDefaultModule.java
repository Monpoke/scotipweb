/*
 * Copyright (c) 2016. Pierre BOURGEOIS
 *
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deal in the Software without restriction,
 *  including without limitation the rights to use, copy, modify, merge,
 *  publish, distribute, sublicense, and/or sell copies of the Software, and
 *  to permit persons to whom the Software is furnished to do so, subject
 *  to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

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
            modulePlayback(toReturn);
        }
    }

    private static void modulePlayback(Module toReturn) {
        toReturn.getSettings().put("file","hello-world");
    }
}
