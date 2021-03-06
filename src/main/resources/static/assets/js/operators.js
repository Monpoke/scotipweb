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

$(function () {

    $("[name='skype']").change(function () {
        if ($(this).val() === "1") {
            $("#passwordArea").hide();
        } else {
            $("#passwordArea").show();
        }
    });
    $("[name='skype']").trigger('change');

    /**
     * Saving changes
     */
    $("#addOperator form").submit(function (e) {
        e.preventDefault();

        var data_post = $("#addOperator form").serialize();


        $.post("./operator/add", data_post).success(function (dataPost) {
            console.log(dataPost);
            if (dataPost === "ok") {
                location.reload();
            } else {
                try {
                    var err = $.parseJSON(dataPost);
                    var alertm = "";

                    for (var i = 0, t = err.length; i < t; i++) {
                        alertm += err[i].defaultMessage + "\n";
                    }

                    alert(alertm);
                } catch (e) {
                    console.log(dataPost);
                }

            }
        }).fail(function (err) {

        });


    });


});