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

import com.google.gson.Gson;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Pierre on 21/04/2016.
 */
@Entity
@Table(name = "call_logs")
public class CallLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "callid")
    private int callid;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "switchboard_id", nullable = false)
    protected Switchboard switchboard;

    @Column(name="caller_number")
    protected String caller_number;

    @Column(name="timestamp")
    protected String timestamp;

    @Column(name="duration")
    protected int duration = 0;

    @Column(name="finished")
    protected boolean finished = false;

    @ManyToOne(fetch = FetchType.LAZY)
    protected Operator replyer;

    @ElementCollection()
    @CollectionTable(name="call_logs_actions", joinColumns = @JoinColumn(name = "calllog_id"))
    List<String> actions = new ArrayList<>();

    @ElementCollection()
    @CollectionTable(name="call_logs_variables", joinColumns=@JoinColumn(name="calllog_id"))
    @Column(name="variables")
    protected Map<String, String> variables = new HashMap<>();



    public int getCallid() {
        return callid;
    }

    public void setCallid(int callid) {
        this.callid = callid;
    }

    public Switchboard getSwitchboard() {
        return switchboard;
    }

    public void setSwitchboard(Switchboard switchboard) {
        this.switchboard = switchboard;
    }

    public String getCaller_number() {
        return caller_number;
    }

    public void setCaller_number(String caller_number) {
        this.caller_number = caller_number;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public List<String> getActions() {
        return actions;
    }

    public void setActions(List<String> actions) {
        this.actions = actions;
    }

    public Map<String, String> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, String> variables) {
        this.variables = variables;
    }

    public Operator getReplyer() {
        return replyer;
    }

    public void setReplyer(Operator replyer) {
        this.replyer = replyer;
    }


    public String infosData(){
        HashMap<String,Object> infos = new HashMap<>();

        infos.put("callerNb",getCaller_number());
        infos.put("date",getTimestamp());
        infos.put("duration",getDuration());
        infos.put("variables",getVariables());
        infos.put("actions",getActions());

        return new Gson().toJson(infos);
    }
}
