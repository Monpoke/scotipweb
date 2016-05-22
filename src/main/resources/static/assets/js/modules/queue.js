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
function ModQueue(rootElem, data) {
    this.rootElem = rootElem;
    this.data = data;
    this.common = new ModCommon(rootElem, data);
    this.messages();
    this.module();
}


ModQueue.prototype.messages = function () {
    var customMessages = [
        {
            msg: "All lines are busy",
            name: "unavailable"
        }
    ];
    this.common.messages(customMessages);
};


ModQueue.prototype.module = function () {
    $(this.rootElem).append("<h2>Queue</h2>");


    var queuesOptions = "";
    var queues = $.parseJSON($("[data-queues]").attr('data-queues'));
    for (var i = 0, t = queues.length; i < t; i++) {
        queuesOptions += "<option value='" + queues[i].qid + "'>"+ queues[i].name +"</option>" + "\n";
    }

    // operator select
    $(this.rootElem).append("<label for='queue'>Queue:</label> " +
        "<select id='queue' name='queue'>" + queuesOptions + "</select><br />");

    // QUEUE
    if(typeof this.data.module.queue != 'undefined'){
        $('[name="queue"]').val(this.data.module.queue);
    }

    this.common.moh();
};

