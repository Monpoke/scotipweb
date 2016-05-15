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

        $("#moduleConfig_configs_settings").append("<strong>" + keyName + ":</strong> \"" + value + "\"<br />");
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