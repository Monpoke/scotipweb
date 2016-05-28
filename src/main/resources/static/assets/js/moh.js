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
 * Created by Pierre on 27/05/2016.
 */

$(function () {

    /**
     * Function to upload a file.
     */
    $('#uploadSong form').submit(function (e) {
        e.preventDefault();
        var formData = new FormData($('form')[0].files);

        console.log($("input[type='file']")[0]);

        formData.append("file", $('input[type=file]')[0].files[0]);
        formData.append("name", $(this).find("[name='name']").val());
        formData.append("sid", $(this).find("[name='sid']").val());
        formData.append("mohid", $(this).find("[name='mohid']").val());


        $.ajax({
            url: '/u/switchboard/' + $(this).find("[name='sid']").val() + '/moh/' + $(this).find("[name='mohid']").val() + '/upload',  //Server script to process data
            type: 'POST',
            xhr: function () {  // Custom XMLHttpRequest
                var myXhr = $.ajaxSettings.xhr();
                if (myXhr.upload) { // Check if upload property exists
                    myXhr.upload.addEventListener('progress', progressHandlingFunction, false); // For handling the progress of the upload
                }
                return myXhr;
            },
            //Ajax events
            success: function (data) {
                console.log(data);
                var da = $.parseJSON(data);

                if (da.status === "ok") {
                    alert('Upload successful!');
                    location.reload();
                }
            },
            error: function (err) {
                console.log(err);
            },
            // Form data
            data: formData,
            //Options to tell jQuery not to process data or worry about content-type.
            cache: false,
            contentType: false,
            processData: false
        });


        function progressHandlingFunction(e) {
            $("#uploadProgress").removeClass('hidden');
            if (e.lengthComputable) {
                $('#uploadProgress').attr({value: e.loaded, max: e.total});
            }
        }
    });


});