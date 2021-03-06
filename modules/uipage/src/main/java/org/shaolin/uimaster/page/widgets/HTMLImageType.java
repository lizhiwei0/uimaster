package org.shaolin.uimaster.page.widgets;

import java.io.File;
import java.io.IOException;

import org.shaolin.uimaster.page.HTMLSnapshotContext;
import org.shaolin.uimaster.page.HTMLUtil;
import org.shaolin.uimaster.page.WebConfig;
import org.shaolin.uimaster.page.ajax.Image;
import org.shaolin.uimaster.page.ajax.Layout;
import org.shaolin.uimaster.page.ajax.Widget;
import org.shaolin.uimaster.page.cache.UIFormObject;
import org.shaolin.uimaster.page.javacc.VariableEvaluator;
import org.shaolin.uimaster.page.security.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTMLImageType extends HTMLTextWidgetType
{
    private static final Logger logger = LoggerFactory.getLogger(HTMLImageType.class);

    public HTMLImageType()
    {
    }

    public HTMLImageType(HTMLSnapshotContext context)
    {
        super(context);
    }

    public HTMLImageType(HTMLSnapshotContext context, String id)
    {
        super(context, id);
    }

    @Override
	public void generateBeginHTML(HTMLSnapshotContext context, UIFormObject ownerEntity, int depth) {
		
	}
    
    @Override
    public void generateEndHTML(HTMLSnapshotContext context, UIFormObject ownerEntity, int depth)
    {
        try
        {
            generateWidget(context);
            if (this.getAttribute("isGallery") != null) {
	            String root = (UserContext.isMobileRequest() && UserContext.isAppClient()) 
	        			? WebConfig.getAppResourceContextRoot() : WebConfig.getResourceContextRoot();
	        			
//	        	These files are configurable in runconfig.registry file of each module.
//	        	Because JGallary has a bug on importing these files in multiple time.
//	            Please refer to org_shaolin_vogerp_productmodel runconfig.registry.
//	            HTMLUtil.generateTab(context, depth);
//	            context.generateHTML("<div><link rel=\"stylesheet\" href=\""+root+"/css/jsgallery/font-awesome.min.css\" type=\"text/css\">");
//	            HTMLUtil.generateTab(context, depth);
//	            context.generateHTML("<link rel=\"stylesheet\" href=\""+root+"/css/jsgallery/jgallery.min.css?v=1.5.0\" type=\"text/css\">");
//	            HTMLUtil.generateTab(context, depth);
//	        	context.generateHTML("<script type=\"text/javascript\" src=\""+root+"/js/controls/jsgallery/jgallery.js\"></script>");
//	        	HTMLUtil.generateTab(context, depth);
//	        	context.generateHTML("<script type=\"text/javascript\" src=\""+root+"/js/controls/jsgallery/touchswipe.js\"></script>");
//	        	HTMLUtil.generateTab(context, depth);
//	        	context.generateHTML("<script type=\"text/javascript\" src=\""+root+"/js/controls/jsgallery/tinycolor-0.9.16.min.js\"></script>");
//	        	HTMLUtil.generateTab(context, depth);
	        	
	        	context.generateHTML("<div id=\"");
	        	context.generateHTML(getName());
	            context.generateHTML("\" jwidth=\"");
	            Object w = this.getAttribute("width");
	            if (w == null) {
	            	context.generateHTML("-1");
	            } else {
	            	context.generateHTML(w.toString());
	            }
	            context.generateHTML("\" jheight=\"");
	            Object h = this.getAttribute("height");
	            if (h == null) {
	            	context.generateHTML("-1");
	            } else {
	            	context.generateHTML(h.toString());
	            }
	            context.generateHTML("\">");
	            
	            HTMLUtil.generateTab(context, depth + 1);
	            String path = this.getValue();
	            if (path != null && !path.trim().isEmpty()) {
		            if (path.indexOf(";") != -1) {
		            	String[] images = path.split(";");
		            	for (String i : images) {
		            		String item = root + "/" +  i;
		            		context.generateHTML("<a href=\"" + item + "\"><img src=\"" + item + "\"/></a>");
		            	}
		            } else {
			            File directory = new File(WebConfig.getResourcePath() + path);
			            if (directory.exists()) {
			            	String[] images = directory.list();
			            	for (String i : images) {
			            		String item = root + path + "/" +  i;
			            		context.generateHTML("<a href=\"" + item + "\"><img src=\"" + item + "\"/></a>");
			            	}
			            } else {
			            	context.generateHTML("<a href=\"" + root + path + "\"><img src=\"" + root + path + "\"/></a>");
			            }
		            }
	            }
	            HTMLUtil.generateTab(context, depth);
	            context.generateHTML("</div>");
        	} else {
	            context.generateHTML("<input type=hidden name=\"");
	            context.generateHTML(getName());
	            context.generateHTML("\">");
	            context.generateHTML("<img");
	            context.generateHTML(" src=\"");
	            context.generateHTML(getSrc(context));
	            context.generateHTML("\"");
	            generateAttributes(context);
	            generateEventListeners(context);
	            context.generateHTML(" style=\"cursor:pointer;\"/>");
	            if (this.getAttribute("showWords") != null) {
	            	context.generateHTML("<span>" + this.getAttribute("text") + "</span>");
	            }
        	}
            generateEndWidget(context);
        }
        catch (Exception e)
        {
            logger.error("error. in entity: " + getUIEntityName(), e);
        }
    }

    private String getSrc(HTMLSnapshotContext context)
    {
        return context.getImageUrl(getUIEntityName(), (String) getAllAttribute("src"));
    }

    public void generateAttribute(HTMLSnapshotContext context, String attributeName, Object attributeValue) throws IOException
    {
        if ("src".equals(attributeName))
        {
        }
        else
        {
            super.generateAttribute(context, attributeName, attributeValue);
        }
    }

    /**
     * Whether this component can have editPermission.
     */
    @Override
    public boolean isEditPermissionEnabled()
    {
        return false;
    }
    
	public Widget createAjaxWidget(VariableEvaluator ee) {
		Image image = new Image(getName(), Layout.NULL);

		image.setReadOnly(getReadOnly());
		image.setUIEntityName(getUIEntityName());

		if (getValue() != null && !"".equals(getValue())) {
			image.setSrc(getValue());
		} else {
			image.setSrc((String) getAllAttribute("src"));
		}
		image.setIsGallery(this.getAttribute("isGallery") != null);
		image.setListened(true);
		image.setFrameInfo(getFrameInfo());

		return image;
	}


    private static final long serialVersionUID = 24011835838546883L;
}
