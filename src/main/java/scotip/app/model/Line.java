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
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Pierre on 21/04/2016.
 */
@Entity
@Table(name = "line")
public class Line {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "line_id")
    private int lineId;

    @Column(name = "country")
    private String country;

    @Column(name = "regionCode")
    private int regionCode;

    @Column(name = "number", unique = true)
    private String number;

    @Column(name = "shared")
    private boolean shared = true;

    // This end is not the owner. It's the inverse of the User.roles association
    @OneToMany(mappedBy = "line")
    private Set<Switchboard> switchboards = new HashSet<>();

    // IF NOT SHARED
    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;


    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public int getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(int regionCode) {
        this.regionCode = regionCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Format a number in international format
     * @return
     */
    public String formatNumber() {

        String nb = "" + getNumber();

        char firstChar = nb.charAt(0);

        // delete the first character
        nb = nb.substring(1);
        String fin = "";
        for (int i = 0, totalLength= nb.length(); i < totalLength; i++) {
        fin += nb.charAt(i);
            if(i%2==0 && totalLength-i > 2){
                fin+=".";
            }
        }

        return "+" + getRegionCode() + "(" + firstChar + ")"+fin;
    }

    public Set<Switchboard> getSwitchboards() {
        return switchboards;
    }

    public void setSwitchboards(Set<Switchboard> switchboards) {
        this.switchboards = switchboards;
    }

    public Line(){
    }

    public Line(int regionCode, String number, boolean shared) {
        this.regionCode = regionCode;
        this.number = number;
        this.shared = shared;
    }


}
