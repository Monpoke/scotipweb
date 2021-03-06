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

package scotip.app.service.module;

import scotip.app.dto.ModuleDto;
import scotip.app.dto.ModuleUpdateDto;
import scotip.app.exceptions.OperationException;
import scotip.app.model.Company;
import scotip.app.model.Module;

/**
 * Created by Pierre on 13/05/2016.
 */
public interface ModuleService {
    Module findByIdAndCompany(int id, Company currentCompany);

    Module createNewModule(int parentId, String modelSlug, Company currentCompany) throws Exception;

    Module save(Module module);

    Module removeModule(int parentId, Company currentCompany) throws OperationException;

    void saveUpdate(Module module, ModuleDto moduleUpdateDto);
}
