package eu.cec.digit.circabc.action.config;

import org.alfresco.enterprise.repo.management.MBeanSupport;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource
public class ImportConfigMBean extends MBeanSupport

{
private ImportConfig importConfig;



@ManagedAttribute(description = "Set the max file import size in megabatyes")
public void setMaxSizeInMegaBytes(Integer maxSizeInMegaBytes) {
	if (maxSizeInMegaBytes != null) {
		importConfig.setMaxSizeInMegaBytes(maxSizeInMegaBytes);
	}
}

@ManagedAttribute(description = "Get the max file import size in megabatyes")
public Integer getMaxSizeInMegaBytes() {
	return importConfig.getMaxSizeInMegaBytes();
}

public void setImportConfig(ImportConfig importConfig) {
	this.importConfig = importConfig;
}

public ImportConfig getImportConfig() {
	return importConfig;
}


}
