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

/**
 * Created by Pierre on 21/05/2016.
 */
function ModCommon(rootElem, data) {
    this.rootElem = rootElem;
    this.data = data;
}

/**
 * Shows custom messages section.
 * @param customMessages
 */
ModCommon.prototype.messages = function (customMessages) {

    $(this.rootElem).append("<h2>Messages</h2>\n" +
        "<table>");

    for (var i = 0, t = customMessages.length; i < t; i++) {
        var cu = customMessages[i];
        $(this.rootElem).append("<tr>" +
            "<td><strong>" + cu.msg + ":</strong> </td>" +
            "<td><input type='text' value='' name='" + cu.name + "' /> <button class='btn btn-primary' data-toggle='modal' data-target='#changeSong'><i class='fa fa-upload'></i> Upload</button></td>" +
            "</tr>");
    }

    $(this.rootElem).append("</table>");

}


ModCommon.prototype.moh = function(){
    // moh select
    $(this.rootElem).append("<label for='moh'>MOH class:</label> " +
        "<select id='moh' name='moh'><option value=''>potter</option><option value=''>nyan</option></select><br/>");

};

