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

        console.log("icic");
        $('#deleteButton').off().click(function (e) {
            if (!confirm('Remove this module?')) {
                return;
            }

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
            console.log("jj");
            if (somethingChanged == true) {
                if (!confirm("Are you sure to change to this type? Previous modifications will be lost!")) {
                    return;
                }
            }
            somethingChanged = false;

            var mod = $(this).val();

            var target = "#dynamicForm";
            $(target).html("")

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
                    new ModUserInput(target, data);
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
        $('#modulePhoneKeyDisable').trigger('change');


        /**
         * Saving changes
         */
        $('#moduleConfig form').off().submit(function (e) {
            e.preventDefault();

            // libraryFile
            var data_post = $(this).serialize();

            $("input").attr('disabled','disabled');

            $.post("/u/module/update/" + data.module.mid, data_post).success(function (dataPost) {
                if (dataPost === "ok") {
                    $("input").attr('disabled','disabled');
                    location.reload();
                } else {
                    $("input").removeAttr('disabled');
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

        $("[data-openLibrary]").off().click(function () {
            var destName = $(this).attr('data-openLibrary');
            $("#songLibraryModal").modal('show');
            /**
             * Choose a library song
             */
            $('[data-choose-library]').click(function (e) {
                var val = $("#songLibrary").val();

                var split = ("" + val).split(",");
                var allFiles = "library/" + split.join('&library/');

                $("#file_" + destName).val(allFiles);
                $("#songLibraryModal").modal('hide');
            });

        });

    });

    $(document).on('click', '[data-btnUpload]', function (e) {
        e.preventDefault();

        var modId = $(this).attr('data-btnUpload');
        var modSlug = $(this).attr('data-name');
        var modMsgId = -1;
        switch (modSlug) {
            case "message":
                modMsgId = 1;
                break;
            case "inputError":
                modMsgId = 2;
                break;
            case "unavailable":
                modMsgId = 3;
                break;
            default:
                alert("Unsupported");
        }

        if (modMsgId == -1) return;

        var code = ("" + modMsgId).length + "" + modMsgId + "" + modId;
        $("#changeSong [data-set='moduleCodeAst']").text(code);


        /**
         * Function to upload a file.
         */
        $('[data-upload]').off().click(function (e) {
            e.preventDefault();
            var formData = new FormData($('#uploadForm')[0]);

            $.ajax({
                url: '/u/module/upload/' + code,  //Server script to process data
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
                        $("[data-fieldname='" + modSlug + "']").val(da.filename);
                        $("#changeSong").modal('hide');
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


        $("#changeSong").modal('show');
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


/**
 * Set Globals data
 * @param data
 */
function updateModalWithData(data) {
    $("[data-set='moduleCode']").text(data.module.mid);

    $("[name='moduleType']").val(data.model.slug);
    $("[name='moduleDescription']").val(data.module.description);
    $("[data-set='moduleCode']").val(data.module.mid);

    if (data.module.root) {
        $("#phoneKeyGroup").hide();
        $("#phoneKeyGroup input").attr('disabled', 'disabled');
    } else {
        $("#phoneKeyGroup").show();
        $("#phoneKeyGroup input").removeAttr('disabled');
        $("input[name='modulePhoneKeyDisable']").prop('checked', data.module.phoneKeyDisabled);
        $("input[name='modulePhoneKey']").val(data.module.phoneKey);
    }


}

/**
 * Create a new module.
 * @param data
 */
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