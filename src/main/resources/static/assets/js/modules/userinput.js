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
function ModUserInput(rootElem, data){
    this.rootElem = rootElem;
    this.data = data;
    this.common = new ModCommon(rootElem,data);

    this.messages();
    this.module();
}

ModUserInput.prototype.messages = function () {
    var customMessages = [
        {
            msg: "Description message",
            name: "file"
        },
        {
            msg: "Invalid input",
            name: "invalid"
        }
    ];
    this.common.messages(customMessages);
};


ModUserInput.prototype.module = function(){
    $(this.rootElem).append("<h2>User input</h2>");

    // operator select
    $(this.rootElem).append("<label for='variable'>Variable name:</label><br />" +
        "<input id='variable' name='variable' class='form-control' /><br/>");

    $(this.rootElem).append("<label for='numberFormat'>Characters number:</label><br />" +
        "<input id='numberFormat' placeholder='Min char' name='numberFormat' size='2'/> to " +
        "<input id='numberFormatMax' placeholder='Max char' name='numberFormatMax' size='2' /><br /><br/>");

    $(this.rootElem).append("<label for='numberFormat'>URI check:</label> <a href='/help#userinputCheck' target='_blank'>Help?</a><br />" +
        "<input type='text' id='urlCheck' placeholder='URL' name='urlCheck' class='form-control'/>");

};

