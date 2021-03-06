/* null */
/* auto generated constructor */
function org_shaolin_bmdp_analyzer_form_ClientDBInfo(json)
{
    var prefix = (typeof(json) == "string") ? json : json.prefix; 
    var idUI = new UIMaster.ui.hidden
    ({
        ui: elementList[prefix + "idUI"]
    });

    var jdbcClassUILabel = new UIMaster.ui.label
    ({
        ui: elementList[prefix + "jdbcClassUILabel"]
    });

    var jdbcClassUI = new UIMaster.ui.textfield
    ({
        ui: elementList[prefix + "jdbcClassUI"]
    });

    var urlUILabel = new UIMaster.ui.label
    ({
        ui: elementList[prefix + "urlUILabel"]
    });

    var urlUI = new UIMaster.ui.textfield
    ({
        ui: elementList[prefix + "urlUI"]
    });

    var userNameUILabel = new UIMaster.ui.label
    ({
        ui: elementList[prefix + "userNameUILabel"]
    });

    var userNameUI = new UIMaster.ui.textfield
    ({
        ui: elementList[prefix + "userNameUI"]
    });

    var passwordUILabel = new UIMaster.ui.label
    ({
        ui: elementList[prefix + "passwordUILabel"]
    });

    var passwordUI = new UIMaster.ui.passwordfield
    ({
        ui: elementList[prefix + "passwordUI"]
    });

    var okbtn = new UIMaster.ui.button
    ({
        ui: elementList[prefix + "okbtn"]
    });

    var cancelbtn = new UIMaster.ui.button
    ({
        ui: elementList[prefix + "cancelbtn"]
    });

    var actionPanel = new UIMaster.ui.panel
    ({
        ui: elementList[prefix + "actionPanel"]
        ,items: []
        ,subComponents: [prefix + "okbtn",prefix + "cancelbtn"]
    });

    var fieldPanel = new UIMaster.ui.panel
    ({
        ui: elementList[prefix + "fieldPanel"]
        ,items: []
        ,subComponents: [prefix + "idUI",prefix + "jdbcClassUILabel",prefix + "jdbcClassUI",prefix + "urlUILabel",prefix + "urlUI",prefix + "userNameUILabel",prefix + "userNameUI",prefix + "passwordUILabel",prefix + "passwordUI"]
    });

    var Form = new UIMaster.ui.panel
    ({
        ui: elementList[prefix + "Form"]
        ,items: [idUI,jdbcClassUILabel,jdbcClassUI,urlUILabel,urlUI,userNameUILabel,userNameUI,passwordUILabel,passwordUI,okbtn,cancelbtn,fieldPanel,actionPanel]
    });

    Form.idUI=idUI;

    Form.jdbcClassUILabel=jdbcClassUILabel;

    Form.jdbcClassUI=jdbcClassUI;

    Form.urlUILabel=urlUILabel;

    Form.urlUI=urlUI;

    Form.userNameUILabel=userNameUILabel;

    Form.userNameUI=userNameUI;

    Form.passwordUILabel=passwordUILabel;

    Form.passwordUI=passwordUI;

    Form.okbtn=okbtn;

    Form.cancelbtn=cancelbtn;

    Form.fieldPanel=fieldPanel;

    Form.idUI=idUI;

    Form.jdbcClassUILabel=jdbcClassUILabel;

    Form.jdbcClassUI=jdbcClassUI;

    Form.urlUILabel=urlUILabel;

    Form.urlUI=urlUI;

    Form.userNameUILabel=userNameUILabel;

    Form.userNameUI=userNameUI;

    Form.passwordUILabel=passwordUILabel;

    Form.passwordUI=passwordUI;

    Form.actionPanel=actionPanel;

    Form.okbtn=okbtn;

    Form.cancelbtn=cancelbtn;

    Form.user_constructor = function()
    {
        /* Construct_FIRST:org_shaolin_bmdp_analyzer_form_ClientDBInfo */
        /* Construct_LAST:org_shaolin_bmdp_analyzer_form_ClientDBInfo */
    };

    Form.Save = org_shaolin_bmdp_analyzer_form_ClientDBInfo_Save;

    Form.Cancel = org_shaolin_bmdp_analyzer_form_ClientDBInfo_Cancel;

    Form.invokeDynamicFunction = org_shaolin_bmdp_analyzer_form_ClientDBInfo_invokeDynamicFunction;

    Form.__entityName="org.shaolin.bmdp.analyzer.form.ClientDBInfo";

    Form.init();
    return Form;
};

    /* EventHandler Functions */
/* Other_Func_FIRST:org_shaolin_bmdp_analyzer_form_ClientDBInfo */
/* Other_Func_LAST:org_shaolin_bmdp_analyzer_form_ClientDBInfo */

    /* auto generated eventlistener function declaration */
    function org_shaolin_bmdp_analyzer_form_ClientDBInfo_Save(eventsource,event) {/* Gen_First:org_shaolin_bmdp_analyzer_form_ClientDBInfo_Save */

        // cal ajax function. 

        UIMaster.triggerServerEvent(UIMaster.getUIID(eventsource),"saveDetail-20151205-183328",UIMaster.getValue(eventsource),this.__entityName);

        var UIEntity = this;
    }/* Gen_Last:org_shaolin_bmdp_analyzer_form_ClientDBInfo_Save */


    /* auto generated eventlistener function declaration */
    function org_shaolin_bmdp_analyzer_form_ClientDBInfo_Cancel(eventsource,event) {/* Gen_First:org_shaolin_bmdp_analyzer_form_ClientDBInfo_Cancel */

        // cal ajax function. 

        UIMaster.triggerServerEvent(UIMaster.getUIID(eventsource),"cancelDetail-20151205-183328",UIMaster.getValue(eventsource),this.__entityName);

        var UIEntity = this;
    }/* Gen_Last:org_shaolin_bmdp_analyzer_form_ClientDBInfo_Cancel */


    /* auto generated eventlistener function declaration */
    function org_shaolin_bmdp_analyzer_form_ClientDBInfo_invokeDynamicFunction(eventsource,event) {/* Gen_First:org_shaolin_bmdp_analyzer_form_ClientDBInfo_invokeDynamicFunction */

        // cal ajax function. 

        UIMaster.triggerServerEvent(UIMaster.getUIID(eventsource),event,UIMaster.getValue(eventsource),this.__entityName);

        var UIEntity = this;
    }/* Gen_Last:org_shaolin_bmdp_analyzer_form_ClientDBInfo_invokeDynamicFunction */



