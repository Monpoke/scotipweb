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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pierre on 21/04/2016.
 */
@Entity
@Table(name = "switchboard")
public class Switchboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sid")
    protected int sid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    protected Company company;

    @Column(name = "phoneCodeAccess")
    protected int phoneCodeAccess;

    @Column(name = "name")
    protected String name;

    @Column(name = "description")
    protected String description;


    @Column(name = "enabled")
    protected boolean enabled = true;

    @Column(name = "deleted")
    protected boolean deleted = false;


    @ManyToOne(fetch = FetchType.EAGER)
    protected Line line;


    @OneToMany(mappedBy = "switchboard", cascade = {CascadeType.ALL})
    @OrderBy(value = "phoneKey")
    protected List<Module> modules = new ArrayList<>();


    @OneToMany(mappedBy = "switchboard", cascade = {CascadeType.ALL})
    protected List<CallLog> callLogs = new ArrayList<>();

    @OneToMany(mappedBy = "switchboard", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    protected List<MohGroup> mohGroups = new ArrayList<>();

    @OneToMany(mappedBy = "switchboard", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    protected List<Queue> queues = new ArrayList<>();


    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }


    public int getPhoneCodeAccess() {
        return phoneCodeAccess;
    }

    public void setPhoneCodeAccess(int phoneCodeAccess) {
        this.phoneCodeAccess = phoneCodeAccess;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public List<CallLog> getCallLogs() {
        return callLogs;
    }

    public void setCallLogs(List<CallLog> callLogs) {
        this.callLogs = callLogs;
    }

    public List<MohGroup> getMohGroups() {
        return mohGroups;
    }

    public void setMohGroups(List<MohGroup> mohGroups) {
        this.mohGroups = mohGroups;
    }

    public List<Queue> getQueues() {
        return queues;
    }

    public void setQueues(List<Queue> queues) {
        this.queues = queues;
    }

    public String getCallsTotalDuration(){

        int total = 0;

        List<CallLog> callLogs = getCallLogs();
        for (CallLog call :
                callLogs) {
            total += call.getDuration();
        }


        int hours = (int)Math.floor(total / 3600);
        int minutes = (int)Math.floor((total / 60) % 60);
        int seconds = total % 60;

        String s = "";
        if(hours > 0){
            s = hours +"H";
        }

        if(minutes > 0){
            s = minutes +"M";
        }

        s+=seconds+"S";

        return s;
    }

    public Switchboard() {

    }

}