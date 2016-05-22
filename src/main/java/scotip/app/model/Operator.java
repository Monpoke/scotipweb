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

package scotip.app.model;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by svevia on 05/05/2016.
 */

@Entity
@Table(name = "operator")
public class Operator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oid")
    protected int oid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id", nullable = false)
    protected Company company;

    @Column(name = "name", unique = true)
    protected String name;

    @Column(name = "password")
    protected String password;

    @Column(name = "skype")
    protected boolean skype = true;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "queue_operator", joinColumns = {@JoinColumn(name = "operator_id")}, inverseJoinColumns = {@JoinColumn(name = "queue_id")})
    protected List<Queue> queues;


    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Queue> getQueues() {
        return queues;
    }

    public void setQueues(List<Queue> queues) {
        this.queues = queues;
    }

    public boolean isSkype() {
        return skype;
    }

    public void setSkype(boolean skype) {
        this.skype = skype;
    }

    public Operator() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Operator operator = (Operator) o;

        if (getOid() != operator.getOid()) return false;
        if (getName() != null ? !getName().equals(operator.getName()) : operator.getName() != null) return false;
        return getPassword() != null ? getPassword().equals(operator.getPassword()) : operator.getPassword() == null;

    }

    @Override
    public int hashCode() {
        int result = getOid();
        result = 31 * result + (getCompany() != null ? getCompany().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Operator{" +
                "oid=" + oid +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public HashMap<String, Object> getPublicData() {

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("oid", oid);
        properties.put("skype", skype);
        properties.put("name",name);

        return properties;
    }
}

