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

        $("#moduleConfig [data-edition]").show();

        var data = getData(this);
        updateModalWithData(data);

        $('#moduleConfig').modal('show');

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
    $(".moduleParentName").text("#"+data.module.mid);
    // foreach sur settings
    $("#moduleConfig_configs_settings").html("");
    $.each(data.module.settings, function (key, value) {
        var keyName;
        switch (key) {
            case "file":
                keyName = "File"
                break;
            default:
                keyName = "Unknown";
        }

        $("#moduleConfig_configs_settings").append("<strong>" + keyName + ":</strong> \"" + value + "\"<br />");
    });
}


function createNewModule(data){
    $(".moduleParentName").text("#"+data.module.mid);
    $("#createNewModuleButton").off().on('click', function(){

        $("#submods_"+data.module.mid).append('<li>Test</li>');

        $("#configuration").html("");
        $("#dialplan").jOrgChart({
            chartElement: "#configuration",
            dragAndDrop: true
        });

        $("#moduleCreate").modal('hide');
    });
}