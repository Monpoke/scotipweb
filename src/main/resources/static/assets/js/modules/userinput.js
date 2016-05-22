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
function ModUserInput(rootElem, data) {
    this.rootElem = rootElem;
    this.data = data;
    this.common = new ModCommon(rootElem, data);

    this.messages();
    this.module();

    this.fillFields();
}

ModUserInput.prototype.messages = function () {
    var customMessages = [
        {
            msg: "Description message",
            name: "message"
        },
        {
            msg: "Invalid input",
            name: "inputError"
        }
    ];
    this.common.messages(customMessages);
};


ModUserInput.prototype.module = function () {
    $(this.rootElem).append("<h2>User input</h2>");

    // operator select
    $(this.rootElem).append("<label for='variable'>Variable name:</label><br />" +
        "<input id='variable' name='variable' class='form-control' /><br/>");

    $(this.rootElem).append("<label for='numberFormatMin'>Characters number:</label><br />" +
        "<input type='number' id='numberFormatMin' placeholder='Min char' name='numberFormatMin' min='1' max='25' value='1' size='2'/> to " +
        "<input type='number' id='numberFormatMax' placeholder='Max char' name='numberFormatMax' min='1' max='25' value='8' size='2' /><br /><br/>");

    $(this.rootElem).append("<label for='numberFormat'>URI check:</label> <a href='/help#userinputCheck' target='_blank'>Help?</a><br />" +
        "<input type='text' id='urlCheck' placeholder='URL' name='urlCheck' class='form-control'/>");

};

/**
 * To fill some fields.
 */
ModUserInput.prototype.fillFields = function () {
    if (typeof this.data.module.settings.variable != 'undefined') {
        $('[name="variable"]').val(this.data.module.settings.variable);
    }

    if (typeof this.data.module.settings.numberFormatMin != 'undefined') {
        $('[name="numberFormatMin"]').val(this.data.module.settings.numberFormatMin);
    }
    if (typeof this.data.module.settings.numberFormatMax != 'undefined') {
        $('[name="numberFormatMax"]').val(this.data.module.settings.numberFormatMax);
    }

    if (typeof this.data.module.settings.uri != 'undefined') {
        $('[name="urlCheck"]').val(this.data.module.settings.uri);
    }


};