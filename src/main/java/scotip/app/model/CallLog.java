package scotip.app.model;

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
    protected int caller_number;

    @Column(name="timestamp")
    protected String timestamp;



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

    public int getCaller_number() {
        return caller_number;
    }

    public void setCaller_number(int caller_number) {
        this.caller_number = caller_number;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
