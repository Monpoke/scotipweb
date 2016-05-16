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
import scotip.app.model.Company;
import scotip.app.model.Line;
import scotip.app.model.Switchboard;
import scotip.app.validation.PasswordMatches;
import scotip.app.validation.UniquePhoneAccessCode;
import scotip.app.validation.ValidEmail;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by Pierre on 26/04/2016.
 */

@UniquePhoneAccessCode
public class SwitchboardDto {

    @NotBlank(message = "The service name cannot be blank")
    private String name;

    @NotBlank(message = "The service description should be provided.")
    private String description;


    @NotNull(message = "The service code should be an integer.")
    @Range(min = 50, max = 9999, message = "The code service should be between 50 and 9999.")
    private Integer phoneCodeAccess;

    @NotNull(message = "The shared line is invalid.")
    private Line sharedLine;


    // NO COMMENT, BECAUSE SET BY THE SOFTWARE
    private Company company;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPhoneCodeAccess() {
        return phoneCodeAccess;
    }

    public void setPhoneCodeAccess(Integer phoneCodeAccess) {
        this.phoneCodeAccess = phoneCodeAccess;
    }

    public Line getSharedLine() {
        return sharedLine;
    }

    public void setSharedLine(Line sharedLine) {
        this.sharedLine = sharedLine;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
