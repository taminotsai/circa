/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.repo.content.MimetypeMap;
import org.alfresco.util.TempFileProvider;
import org.alfresco.web.app.Application;
import org.alfresco.web.app.servlet.AuthenticationHelper;
import org.alfresco.web.app.servlet.AuthenticationStatus;
import org.alfresco.web.app.servlet.BaseServlet;
import org.alfresco.web.app.servlet.FacesHelper;
import org.alfresco.web.bean.FileUploadBean;
import org.alfresco.web.config.ClientConfigElement;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.config.ConfigService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import eu.cec.digit.circabc.error.CircabcRuntimeException;
import eu.cec.digit.circabc.web.wai.bean.content.CircabcUploadedFile;

/**
 * Servlet that takes a file uploaded via a browser and represents it as an
 * UploadFileBean in the session
 *
 * @author gavinc
 */
public class UploadFileServlet extends BaseServlet
{
   private static final long serialVersionUID = -5482538466491052876L;
   private static final Log logger = LogFactory.getLog(UploadFileServlet.class);
   private ConfigService configService;
   private ServletConfig servletConfig;
   private UploadFileServletConfig config;

   /**
    * @see javax.servlet.GenericServlet#init()
    */
   @Override
   public void init(final ServletConfig sc) throws ServletException
   {
      super.init(sc);
      this.servletConfig = sc;
      final WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(sc.getServletContext());
      this.configService = (ConfigService)ctx.getBean("webClientConfigService");
      this.config = (UploadFileServletConfig)ctx.getBean("fileUploadLimit");
   }

   /**
    * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
    */
   @SuppressWarnings("unchecked")
   protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException
   {
  
	  String uploadId = null;
	  String returnPage = null;
	  String submitCallback = null;

      final RequestContext requestContext = new ServletRequestContext(request);
      boolean isMultipart = ServletFileUpload.isMultipartContent(requestContext);

      try
      {
    	  // check content size
    	  final int maxSizeInBytes = this.config.getMaxSizeInMegaBytes() * 1024 * 1024;
    	  if ( request.getContentLength() >  maxSizeInBytes )
    	  {
    		  throw new CircabcRuntimeException("File is too big to be uploded actual size of request is:  " +
    				  String.valueOf(request.getContentLength()) +
              " maximum allowed size is: " + String.valueOf(maxSizeInBytes) + " bytes." );
    	  }
 
    	  
         final AuthenticationStatus status = servletAuthenticate(request, response);
         if (status == AuthenticationStatus.Failure)
         {
            return;
         }

         if (!isMultipart)
         {
            throw new AlfrescoRuntimeException("This servlet can only be used to handle file upload requests, make" +
                                        "sure you have set the enctype attribute on your form to multipart/form-data");
         }

         if (logger.isDebugEnabled())
            logger.debug("Uploading servlet servicing...");

         final HttpSession session = request.getSession();
         final ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());

         // ensure that the encoding is handled correctly
         upload.setHeaderEncoding("UTF-8");

         final List<FileItem> fileItems = upload.parseRequest(request);

         final CircabcUploadedFile bean = new CircabcUploadedFile();
         for (final FileItem item : fileItems)
         {
            if(item.isFormField())
            {
               final String fieldName = item.getFieldName().toLowerCase();
               if (fieldName.equals("return-page"))
               {
                  returnPage = item.getString();
               }
               else if (fieldName.equals("upload-id"))
               {
                  uploadId = item.getString();
               }
               else if (fieldName.equals("submit-callback"))
               {
            	   submitCallback = item.getString();
               }
               else if(fieldName.length() > 0 && fieldName.equals("submitfile") == false)
               {
            	   // add other submited values in the session
                   bean.addSubmitedProperty(fieldName, item.getString());
               }

            }
            else
            {
               String filename = item.getName();
               if (filename != null && filename.length() != 0)
               {
                  if (logger.isDebugEnabled())
                  {
                     logger.debug("Processing uploaded file: " + filename);
                  }
                  // ADB-41: Ignore non-existent files i.e. 0 byte streams.
                  if (allowZeroByteFiles() == true || item.getSize() > 0)
                  {
	                  // workaround a bug in IE where the full path is returned
	                  // IE is only available for Windows so only check for the Windows path separator
	                  filename = FilenameUtils.getName(filename);
	                  final File tempFile = TempFileProvider.createTempFile("alfresco", ".upload");
	                  item.write(tempFile);
	                  bean.setFile(tempFile);
	                  bean.setFileName(filename);
	                  bean.setFilePath(tempFile.getAbsolutePath());
	                  if (logger.isDebugEnabled())
	                  {
	                     logger.debug("Temp file: " + tempFile.getAbsolutePath() +
	                                  " size " + tempFile.length() +
	                                  " bytes created from upload filename: " + filename);
	                  }
                  }
                  else
                  {
                     if (logger.isWarnEnabled())
                     {
                        logger.warn("Ignored file '" + filename + "' as there was no content, this is either " +
                              "caused by uploading an empty file or a file path that does not exist on the client.");
                     }
                  }
               }
            }
         }

         final Serializable attributeValue;
         final String attributeKey;

         if(submitCallback == null)
         {
        	 // it is an alfresco call.. use alfresco wrapper.
        	 attributeKey = FileUploadBean.getKey(uploadId);

        	 final FileUploadBean alfrescoWrapper = new FileUploadBean();
        	 alfrescoWrapper.setFile(bean.getFile());
        	 alfrescoWrapper.setFileName(bean.getFileName());
        	 alfrescoWrapper.setFilePath(bean.getFilePath());

        	 attributeValue = alfrescoWrapper;
         }
         else
         {
        	 // it is a circabc call.. use circabc wrapper.
        	 attributeKey = CircabcUploadedFile.getKey(uploadId);
        	 attributeValue = bean;
         }

         // examine the appropriate session to try and find the User object
         if (Application.inPortalServer() == false)
         {
        	 session.setAttribute(attributeKey, attributeValue);
         }
         else
         {
            // naff solution as we need to enumerate all session keys until we find the one that
            // should match our User objects - this is weak but we don't know how the underlying
            // Portal vendor has decided to encode the objects in the session
            final Enumeration<String> enumNames = session.getAttributeNames();
            while (enumNames.hasMoreElements())
            {
               final String name = (String)enumNames.nextElement();
               // find an Alfresco value we know must be there...
               if (name.startsWith("javax.portlet.p") && name.endsWith(AuthenticationHelper.AUTHENTICATION_USER))
               {
                  final String key = name.substring(0, name.lastIndexOf(AuthenticationHelper.AUTHENTICATION_USER));
                  session.setAttribute(key + attributeKey, attributeValue);
                  break;
               }
            }
         }

         if(submitCallback != null)
         {
        	 final FacesContext ctx = FacesHelper.getFacesContext(request, response, servletConfig.getServletContext());

        	 final MethodBinding callback = ctx.getApplication().createMethodBinding("#{"+ submitCallback + "}", new Class[] {CircabcUploadedFile.class});
        	 callback.invoke(ctx, new Object[]{bean});
         }

         if (bean.getFile() == null && uploadId != null && logger.isWarnEnabled())
         {
            logger.warn("no file uploaded for upload id: " + uploadId);
         }

         if (returnPage == null || returnPage.length() == 0)
         {
            throw new AlfrescoRuntimeException("return-page parameter has not been supplied");
         }

         if (returnPage.startsWith("javascript:"))
         {
            returnPage = returnPage.substring("javascript:".length());
            // finally redirect
            if (logger.isDebugEnabled())
            {
               logger.debug("Sending back javascript response " + returnPage);
            }
            response.setContentType(MimetypeMap.MIMETYPE_HTML);
            response.setCharacterEncoding("UTF-8");
            final PrintWriter out = response.getWriter();
            out.println("<html><body><script type=\"text/javascript\">");
            out.println(returnPage);
            out.println("</script></body></html>");
            out.close();
         }
         else
         {
            // finally redirect
            if (logger.isDebugEnabled())
            {
               logger.debug("redirecting to: " + returnPage);
            }

            response.sendRedirect(returnPage);
         }
      }
      catch (final Throwable error)
      {
    	  if(logger.isErrorEnabled()) {
				logger.error("Error", error);
    	  }
    	  Application.handleServletError(getServletContext(), (HttpServletRequest)request,
                                        (HttpServletResponse)response, error, logger, returnPage);
      }

      if (logger.isDebugEnabled())
      {
         logger.debug("upload complete");
      }
   }

   public static void resetUploadedFiles(final HttpSession session)
   {
       session.removeAttribute(CircabcUploadedFile.FILE_UPLOAD_BEAN_NAME);
   }

   private boolean allowZeroByteFiles()
   {
      final ClientConfigElement clientConfig = (ClientConfigElement)configService.getGlobalConfig().getConfigElement(
            ClientConfigElement.CONFIG_ELEMENT_ID);
      return clientConfig.isZeroByteFileUploads();
   }

}
