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

package scotip.app.converter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import scotip.app.exceptions.ModuleModelNotFoundException;
import scotip.app.model.Module;
import scotip.app.model.ModuleModel;
import scotip.app.model.Operator;
import scotip.app.service.moduleModel.ModuleModelService;
import scotip.app.service.operator.OperatorService;

/**
 * Created by Pierre on 29/04/2016.
 */
@Component
public class ModuleModelConverter implements Converter<Object, ModuleModel> {

    @Autowired
    ModuleModelService moduleModelService;


    @Override
    public ModuleModel convert(Object element) {
        ModuleModel operator = null;

        try {
            operator = moduleModelService.getEnabledModuleBySlug(((String) element));
        } catch (ModuleModelNotFoundException e) {
            e.printStackTrace();
        }

        return operator;
    }
}
