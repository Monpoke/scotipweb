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
import java.util.*;

/**
 * Created by svevia on 05/05/2016.
 */

@Entity
@Table(name = "queue")
public class Queue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qid")
    protected int qid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "switchboard_id", nullable = false)
    protected Switchboard switchboard;

    @Column(name = "name")
    protected String name;

    @Column(name = "asteriskName")
    protected String asteriskName;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "queue_operator", joinColumns = {
            @JoinColumn(name = "queue_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "operator_id",
                    nullable = false, updatable = false)})
    protected List<Operator> operators = new ArrayList<>();
    private HashMap<String, Object> publicData;


    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public Switchboard getSwitchboard() {
        return switchboard;
    }

    public void setSwitchboard(Switchboard switchboard) {
        this.switchboard = switchboard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAsteriskName() {
        return asteriskName;
    }

    public void setAsteriskName(String asteriskName) {
        this.asteriskName = asteriskName;
    }

    public List<Operator> getOperators() {
        return operators;
    }

    public void setOperators(List<Operator> operators) {
        this.operators = operators;
    }


    /**
     * Returns true if the current queue contains the operator.
     *
     * @param operator
     * @return
     */
    public boolean hasOperator(Operator operator) {
        return getOperators().contains(operator);
    }

    public HashMap<String, Object> getPublicData() {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("qid", qid);
        properties.put("name", name);
        return properties;
    }
}

