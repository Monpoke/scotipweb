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

    $(document).on('click', '.addModule', function (e) {

        var moduleId = $(this).attr('data-module-id') * 1;

        $("#moduleConfig").hide();

        var data = getData(this);

        createNewModule(data);
        $('#moduleCreate').modal('show');

    });

    /**
     * Update a module
     */
    $(document).on('click', '.moduleDescription', function (e) {

        var data = getData(this);
        updateModalWithData(data);

        $('#moduleConfig').modal('show');

        $('#deleteButton').off().click(function (e) {

            $.get("/u/module/delete/" + data.module.mid).success(function (data) {
                    if (data == "ok") {
                        location.reload();
                    }
                    else {
                        alert("Hm. Error! " + data);
                    }
                })
                .fail(function (err) {
                    alert("Error!");
                });
        });

        var somethingChanged = false;

        $(document).on('change', '#moduleConfig input, #moduleConfig select', function () {
            if ($(this).attr('id') === "moduleType") return;
            somethingChanged = true;
        })

        $('#moduleType').off().change(function (e) {
            if (somethingChanged == true) {
                if (!confirm("Are you sure to change to this type? Previous modifications will be lost!")) {
                    return;
                }
            }
            somethingChanged = false;

            var mod = $(this).val();
            var target = "#dynamicForm";
            $(target).html("");
            switch (mod) {
                case "playback":
                    new ModPlayback(target, data);
                    break;
                case "operator":
                    new ModOperator(target, data);
                    break;
                case "queue":
                    new ModQueue(target, data);
                    break;
                case "userinput":
                    new ModUserInput(target,data);
                    break;
            }
        });

        $('#moduleType').trigger('change');

        // for phone key
        $('#modulePhoneKeyDisable').off().change(function (e) {
            if ($("#modulePhoneKeyDisable").is(':checked')) {
                $("#modulePhoneKey").attr('disabled', 'disabled');
            } else {
                $("#modulePhoneKey").removeAttr('disabled');
            }
        });

        /**
         * Function to upload a file.
         */
        $('[data-upload]').off().click(function (e) {
            e.preventDefault();
            var formData = new FormData($('#uploadForm')[0]);

            $.ajax({
                url: '/u/module/upload/' + data.module.mid,  //Server script to process data
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
                    if (data === "ok") {
                        alert('success');
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

    /**
     * Choose a library song
     */
    $(document).on('click', '[data-choose-library]', function (e) {
        var val = $("#songLibrary").val();

        var split = ("" + val).split(",");
        var allFiles = "library/" + split.join('&library/');

        $(".libraryFileGroup").removeClass('hidden');
        $("[name='libraryFile']").val(allFiles);


        $("[data-id='opt_change_file']").html("<strong>Modification into</strong> <em>" + allFiles + "</em>");
        $("#songLibraryModal").modal('hide');
    });


});

/**
 * Get data of module.
 * @param element
 * @returns {{}}
 */
function getData(element) {
    var json = $(element).parent().children('[data-infos]').attr('data-infos');
    try {
        return $.parseJSON(json);
    }
    catch (e) {
        console.error(e);
        return {};
    }
}


function updateModalWithData(data) {
    $("[name='description']").text(data.module.description);
    $("[data-set='moduleCode']").text(data.module.mid);

    console.log(data);
    if (data.module.root) {
        $(".phoneKeyGroup").hide();
    } else {
        $(".phoneKeyGroup").show();
        $("input[name='phoneKey']").val(data.module.phoneKey);
    }

    // foreach sur settings
    $("#moduleConfig_configs_settings").html("");
    $.each(data.module.settings, function (key, value) {
        var keyName;
        switch (key) {
            case "file":
                keyName = "File";

                // replace default selected
                if ($("#songLibrary option[data-path='" + value + "']").length == 1) {
                    $("#songLibrary option[data-path='" + value + "']").attr('')
                }

                break;
            default:
                keyName = "Unknown";
        }

        $("#moduleConfig_configs_settings").append("<strong>" + keyName + ":</strong> \"" + value + "\"<div class='text-warning' data-id='opt_change_" + key + "'></div><br />");
    });


    /**
     * Saving changes
     */
    $('[data-save]').off().click(function (e) {

        // libraryFile
        console.log(data);
        var data_post = {
            model: $("[name='moduleType']").val(),
            libraryFile: $("[name='libraryFile']").val(),
            description: $("[name='description']").val(),
            phoneKey: $("[name='phoneKey']").val() * 1,
            canSkipFile: ($("[name='canSkip']").is(':checked')) ? 1 : 0,
            moduleId: data.module.mid,
        };


        $.post("/u/module/update/" + data_post.moduleId, data_post).success(function (dataPost) {
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


}


function createNewModule(data) {
    $(".moduleParentName").text("#" + data.module.mid);
    $("#createNewModuleButton").off().on('click', function () {

        var mod = $("#moduleTypeCreate").val();

        $.get("/u/module/create/" + data.module.mid + "/" + mod).success(function (data) {
                if (data == "ok") {
                    location.reload();
                }
                else {
                    alert("Hm. Error! " + data);
                }
            })
            .fail(function (err) {

                alert("Error!");
            });


        $("#submods_" + data.module.mid).append('<li>Refreshing...</li>');

        $("#configuration").html("");
        $("#dialplan").jOrgChart({
            chartElement: "#configuration",
            dragAndDrop: true
        });


        $("#moduleCreate").modal('hide');
    });
}