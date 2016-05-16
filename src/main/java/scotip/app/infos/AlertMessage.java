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
