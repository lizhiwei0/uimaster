/* null */
/* auto generated constructor */
function org_shaolin_bmdp_analyzer_form_StatsDatePieChart(json)
{
    var prefix = (typeof(json) == "string") ? json : json.prefix; 
    var pieUI = new UIMaster.ui.chart
    ({
        ui: elementList[prefix + "pieUI"]
    });

    var okbtn = new UIMaster.ui.button
    ({
        ui: elementList[prefix + "okbtn"]
    });

    var actionPanel = new UIMaster.ui.panel
    ({
        ui: elementList[prefix + "actionPanel"]
        ,items: []
        ,subComponents: [prefix + "okbtn"]
    });

    var fieldPanel = new UIMaster.ui.panel
    ({
        ui: elementList[prefix + "fieldPanel"]
        ,items: []
        ,subComponents: [prefix + "pieUI"]
    });

    var Form = new UIMaster.ui.panel
    ({
        ui: elementList[prefix + "Form"]
        ,items: [pieUI,okbtn,fieldPanel,actionPanel]
    });

    Form.pieUI=pieUI;

    Form.okbtn=okbtn;

    Form.fieldPanel=fieldPanel;

    Form.pieUI=pieUI;

    Form.actionPanel=actionPanel;

    Form.okbtn=okbtn;

    Form.user_constructor = function()
    {
        /* Construct_FIRST:org_shaolin_bmdp_analyzer_form_StatsDatePieChart */
        /* Construct_LAST:org_shaolin_bmdp_analyzer_form_StatsDatePieChart */
    };

    Form.Cancel = org_shaolin_bmdp_analyzer_form_StatsDatePieChart_Cancel;

    Form.invokeDynamicFunction = org_shaolin_bmdp_analyzer_form_StatsDatePieChart_invokeDynamicFunction;

    Form.__entityName="org.shaolin.bmdp.analyzer.form.StatsDatePieChart";

    Form.init();
    return Form;
};

    /* EventHandler Functions */
/* Other_Func_FIRST:org_shaolin_bmdp_analyzer_form_StatsDatePieChart */
/* Other_Func_LAST:org_shaolin_bmdp_analyzer_form_StatsDatePieChart */

    /* auto generated eventlistener function declaration */
    function org_shaolin_bmdp_analyzer_form_StatsDatePieChart_Cancel(eventsource,event) {/* Gen_First:org_shaolin_bmdp_analyzer_form_StatsDatePieChart_Cancel */
        var o = this;
        var UIEntity = this;

        // cal ajax function. 

        UIMaster.triggerServerEvent(UIMaster.getUIID(eventsource),"cancelDetail-20151205-183328",UIMaster.getValue(eventsource),o.__entityName);
    }/* Gen_Last:org_shaolin_bmdp_analyzer_form_StatsDatePieChart_Cancel */


    /* auto generated eventlistener function declaration */
    function org_shaolin_bmdp_analyzer_form_StatsDatePieChart_invokeDynamicFunction(eventsource,event) {/* Gen_First:org_shaolin_bmdp_analyzer_form_StatsDatePieChart_invokeDynamicFunction */
        var o = this;
        var UIEntity = this;

        new UIMaster.ui.dialog({dialogType: UIMaster.ui.dialog.CONFIRM_DIALOG,message:'Continue?',messageType:UIMaster.ui.dialog.Warning,optionType:UIMaster.ui.dialog.YES_NO_OPTION,title:'',height:150,width:300,handler: function() {

        // cal ajax function. 

        UIMaster.triggerServerEvent(UIMaster.getUIID(eventsource),event,UIMaster.getValue(eventsource),o.__entityName);

        
        }
        }).open();
    }/* Gen_Last:org_shaolin_bmdp_analyzer_form_StatsDatePieChart_invokeDynamicFunction */


