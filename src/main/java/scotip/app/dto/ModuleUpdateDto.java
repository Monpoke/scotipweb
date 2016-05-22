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

package scotip.app.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import scotip.app.validation.PasswordMatches;
import scotip.app.validation.ValidEmail;
import scotip.app.validation.ValidLibraryFiles;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * Created by Pierre on 26/04/2016.
 */

public class ModuleUpdateDto {

    @NotBlank(message = "Company name can't be empty.")
    private String model;

    @NotNull
    @ValidLibraryFiles
    private String libraryFile;

    @NotNull(message = "Please provide a description")
    @Length(max = 40, message = "The description could contains 40 maximum characters.")
    private String description;

    /**
     * @todo SECURITY BREACH: have to check 0 and -1
     */
    @Min(value=-1, message="Phone key cannot be under 1")
    @Max(value=9, message="Phone key cannot be greater than 9")
    private int phoneKey;

    @NotNull(message="Skip file can't be null")
    private boolean canSkipFile;

    @ValidLibraryFiles
    private Map<String, String> files;

    @Min(value=1, message = "Module id should be provided.")
    private int moduleId;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLibraryFile() {
        return libraryFile;
    }

    public void setLibraryFile(String libraryFile) {
        this.libraryFile = libraryFile;
    }

    public boolean isCanSkipFile() {
        return canSkipFile;
    }

    public void setCanSkipFile(boolean canSkipFile) {
        this.canSkipFile = canSkipFile;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPhoneKey() {
        return phoneKey;
    }

    public void setPhoneKey(int phoneKey) {
        this.phoneKey = phoneKey;
    }

    public Map<String, String> getFiles() {
        return files;
    }

    public void setFiles(Map<String, String> files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "ModuleUpdateDto{" +
                "model='" + model + '\'' +
                ", libraryFile='" + libraryFile + '\'' +
                ", canSkipFile='" + canSkipFile + '\'' +
                ", moduleId=" + moduleId +
                '}';
    }
}
