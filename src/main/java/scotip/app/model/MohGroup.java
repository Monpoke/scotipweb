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
import java.util.List;

/**
 * Created by Pierre on 21/04/2016.
 */
@Entity
@Table(name = "moh_group")
public class MohGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mohgroup_id")
    private int groupId;

    @Column(name = "group_name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "switchboard_id", nullable = false)
    protected Switchboard switchboard;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, mappedBy = "group")
    protected List<MohFile> files;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupdId) {
        this.groupId = groupdId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Switchboard getSwitchboard() {
        return switchboard;
    }

    public void setSwitchboard(Switchboard switchboard) {
        this.switchboard = switchboard;
    }

    public List<MohFile> getFiles() {
        return files;
    }

    public void setFiles(List<MohFile> files) {
        this.files = files;
    }

    public MohGroup() {

    }

    public MohGroup(Switchboard switchboard) {
        this.switchboard = switchboard;
    }

}
