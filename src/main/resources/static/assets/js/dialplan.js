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




        $('[data-upload]').off().click(function (e) {
            e.preventDefault();
            var formData = new FormData($('#uploadForm')[0]);

            $.ajax({
                url: '/u/module/upload/'+data.module.mid,  //Server script to process data
                type: 'POST',
                xhr: function () {  // Custom XMLHttpRequest
                    var myXhr = $.ajaxSettings.xhr();
                    if (myXhr.upload) { // Check if upload property exists
                        myXhr.upload.addEventListener('progress', progressHandlingFunction, false); // For handling the progress of the upload
                    }
                    return myXhr;
                },
                //Ajax events
                success: function(data){
                    console.log(data);
                    if(data==="ok"){
                        alert('success');
                    }
                },
                error: function(err){
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
        $("[name='libraryFile']").val(val);

        var split = ("" + val).split(",");
        var allFiles = "library/" + split.join('&library/');


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
    $("[data-set='moduleCode']").text(data.module.mid);

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


    $('[data-save]').off().click(function (e) {

        // dialplan id
        // module id

        // typemodel
        // libraryFile
        console.log(data);
        var data_post = {
            model: $("[name='moduleType']").val(),
            libraryFile: $("[name='libraryFile']").val(),
            canSkipFile: ($("[name='canSkip']").is(':checked')) ? 1 : 0,
            moduleId: data.module.mid,
        };


        $.post("/u/module/update/" + data_post.moduleId, data_post).success(function (data) {
            console.log(data);
            if (data === "ok") {
                location.reload();
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