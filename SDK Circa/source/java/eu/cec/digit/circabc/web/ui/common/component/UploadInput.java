/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.ui.common.component;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * Warning: this solution doesn't work without javascript
 *
 * @author Yanick Pignot
 */
public class UploadInput extends org.alfresco.web.ui.common.component.UploadInput
{
    private String onSubmit;

    @Override
    public void encodeBegin(FacesContext context) throws IOException
    {
         final ResponseWriter writer = context.getResponseWriter();
         final String path = context.getExternalContext().getRequestContextPath();

         writer.write("<script type='text/javascript' src='");
         writer.write(path);
         writer.write("/scripts/upload_helper.js'></script>\n");

         writer.write("<script type='text/javascript'>");
         
         writer.write("var contextpath ='" + path + "';\n");
         writer.write("var fullpath = window.location.pathname;\n");
         writer.write("if (!(fullpath.substr(0, contextpath.length) === contextpath ))\n");
         writer.write("{\n");
         writer.write("contextpath ='';\n");
         writer.write("}\n");
                  
         writer.write("function handle_upload(target)\n");
         writer.write("{\n");
         writer.write("handle_upload_helper(target, '', upload_complete, contextpath)\n");
         writer.write("}\n");

         writer.write("function upload_complete(id, path, filename)\n");
         writer.write("{\n");
         writer.write("var schema_file_input = document.getElementById('" + getFramework() + ":" + getId() + "');\n");
         writer.write("schema_file_input.value = filename;\n");
         writer.write("showWaitProgress();\n");
         writer.write("schema_file_input.form.submit();\n");
         writer.write("}\n");
         writer.write("</script>\n");

         final String onChange = "javascript:"+ getSafeOnSubmit() + "handle_upload(this)" ;

         writer.write("\n<input id='" + getFramework() + ":file-input' contentEditable='false' type='file' size='35' name='alfFileInput' onchange='" + onChange + "'/>");
    }

    public Object saveState(FacesContext context)
    {
        Object[] values = new Object[2];
        values[0] = super.saveState(context);
        values[1] = onSubmit;
        return values;
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object[] values = (Object[]) state;
        super.restoreState(context, values[0]);
        onSubmit = (String) values[1];
    }

    public String getOnSubmit()
    {
        return onSubmit;
    }

    public void setOnSubmit(String onSubmit)
    {
          this.onSubmit = onSubmit;
    }

    private String getSafeOnSubmit()
    {
        final String action = getOnSubmit();
		if(action != null && action.trim().length() > 0)
        {
        	boolean appendEnd = action.trim().endsWith(";") == false;
        	
        	return action + (appendEnd ? ";" : "");
        }
        else
        {
        	return "";
        }
    }
}
