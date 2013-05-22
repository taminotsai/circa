package eu.cec.digit.circabc.web.servlet;

import org.alfresco.enterprise.repo.management.MBeanSupport;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource
public class UploadFileServletConfigMBean extends MBeanSupport

{
private UploadFileServletConfig uploadFileServletConfig;

public void setUploadFileServletConfig(UploadFileServletConfig uploadFileServletConfig)
{
	this.uploadFileServletConfig = uploadFileServletConfig;
}

public UploadFileServletConfig getUploadFileServletConfig()
{
	return uploadFileServletConfig;
}

@ManagedAttribute(description = "Set the max file upload size in megabatyes , -1 means unlimited ")
public void setMaxSizeInMegaBytes(Integer maxSizeInMegaBytes) {
	if (maxSizeInMegaBytes != null) {
		uploadFileServletConfig.setMaxSizeInMegaBytes(maxSizeInMegaBytes);
	}
}

@ManagedAttribute(description = "Get the max file upload size in megabatyes , -1 means unlimited ")
public Integer getMaxSizeInMegaBytes() {
	return uploadFileServletConfig.getMaxSizeInMegaBytes();
}


}
