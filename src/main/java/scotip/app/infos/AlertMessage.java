package scotip.app.infos;

/**
 * Created by Pierre on 23/04/2016.
 */
public class AlertMessage {
    private final String type;
    private final String message;

    /**
     * For view purpose.
     *
     * @param type
     * @param message
     */
    public AlertMessage(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }


    /**
     * Get icon
     * @return
     */
    public String getIcon() {
        switch (getType()) {
            case "success":
                return "check";
            case "danger":
                return "ban";
            case "info":
                return "info";
            case "warning":
                return "warning";
            default:
                return "no";
        }
    }

    /**
     * Get title.
     * @return
     */
    public String getTitle() {
        switch (getType()) {
            case "success":
                return "Success!";
            case "danger":
                return "Error!";
            case "info":
                return "Info!";
            case "warning":
                return "Warning!";
            default:
                return "Hum...";
        }
    }
}
