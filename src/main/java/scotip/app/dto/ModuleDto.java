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
import org.hibernate.validator.constraints.Range;
import scotip.app.model.*;
import scotip.app.validation.UniquePhoneAccessCode;
import scotip.app.validation.ValidLibraryFiles;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Created by Pierre on 26/04/2016.
 */

public class ModuleDto {

    @Length(min = 3, max = 80, message = "The service description should contains between 3 and 80 characters.")
    @NotNull(message = "Module description can't be null.")
    private String moduleDescription;


    // autoconverted with slugs
    @NotNull(message = "The model is invalid...")
    private ModuleModel moduleType;

    private boolean modulePhoneKeyDisable;

    private Integer modulePhoneKey;


    // OPERATOR Mod
    private Operator operator;

    // QUEUE Mod
    private Queue queue;

    // OPERATOR AND QUEUE
    private MohGroup moh;

    // USERINPUT
    private String variable;
    private String numberFormatMin;
    private String numberFormatMax;
    private String urlCheck;


    // RESERVED FIELDS
    private Switchboard switchboard;
    private Company company;
    private int moduleId;

    @ValidLibraryFiles
    private Map<String,String> files;

    public String getModuleDescription() {
        return moduleDescription;
    }

    public void setModuleDescription(String moduleDescription) {
        this.moduleDescription = moduleDescription;
    }

    public ModuleModel getModuleType() {
        return moduleType;
    }

    public void setModuleType(ModuleModel moduleType) {
        this.moduleType = moduleType;
    }

    public boolean isModulePhoneKeyDisable() {
        return modulePhoneKeyDisable;
    }

    public void setModulePhoneKeyDisable(boolean modulePhoneKeyDisable) {
        this.modulePhoneKeyDisable = modulePhoneKeyDisable;
    }

    public String getNumberFormatMin() {
        return numberFormatMin;
    }

    public void setNumberFormatMin(String numberFormatMin) {
        this.numberFormatMin = numberFormatMin;
    }

    public String getNumberFormatMax() {
        return numberFormatMax;
    }

    public void setNumberFormatMax(String numberFormatMax) {
        this.numberFormatMax = numberFormatMax;
    }

    public int getModulePhoneKey() {
        return modulePhoneKey;
    }

    public void setModulePhoneKey(int modulePhoneKey) {
        this.modulePhoneKey = modulePhoneKey;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public void setModulePhoneKey(Integer modulePhoneKey) {
        this.modulePhoneKey = modulePhoneKey;
    }

    public String getUrlCheck() {
        return urlCheck;
    }

    public void setUrlCheck(String urlCheck) {
        this.urlCheck = urlCheck;
    }

    public Switchboard getSwitchboard() {
        return switchboard;
    }

    public void setSwitchboard(Switchboard switchboard) {
        this.switchboard = switchboard;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public MohGroup getMoh() {
        return moh;
    }

    public void setMoh(MohGroup moh) {
        this.moh = moh;
    }

    public Map<String, String> getFiles() {
        return files;
    }

    public void setFiles(Map<String, String> files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "ModuleDto{" +
                "moduleDescription='" + moduleDescription + '\'' +
                ", moduleType=" + moduleType +
                ", modulePhoneKeyDisable=" + modulePhoneKeyDisable +
                ", modulePhoneKey=" + modulePhoneKey +
                ", operator=" + operator +
                ", queue=" + queue +
                ", moh=" + moh +
                ", variable='" + variable + '\'' +
                ", numberFormatMin=" + numberFormatMin +
                ", numberFormatMax=" + numberFormatMax +
                ", urlCheck='" + urlCheck + '\'' +
                ", switchboard=" + switchboard +
                ", company=" + company +
                '}';
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public int getModuleId() {
        return moduleId;
    }
}
