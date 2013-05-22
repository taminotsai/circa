package eu.cec.digit.circabc.repo.module;

import java.util.List;
import java.util.Properties;

import org.alfresco.repo.importer.ImporterBootstrap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ImporterModuleComponent extends org.alfresco.repo.module.ImporterModuleComponent {

	private static final Log logger = LogFactory.getLog(ImporterModuleComponent.class);
	
	
	@Override
	protected void checkProperties() {
		// TODO Auto-generated method stub
		super.checkProperties();
	}

	@Override
	protected void executeInternal() throws Throwable {
		// TODO Auto-generated method stub
		if(logger.isErrorEnabled()) {
			logger.info("Circabc Importing Process starting");
		}
		
		
		super.executeInternal();
		if(logger.isErrorEnabled()) {
			logger.info("Circabc Importing Process finished");
		}
	}

	@Override
	public void setBootstrapView(final Properties bootstrapView) {
		// TODO Auto-generated method stub
		super.setBootstrapView(bootstrapView);
	}

	@Override
	public void setBootstrapViews(final List<Properties> bootstrapViews) {
		// TODO Auto-generated method stub
		super.setBootstrapViews(bootstrapViews);
	}

	@Override
	public void setImporter(final ImporterBootstrap importer) {
		// TODO Auto-generated method stub
		super.setImporter(importer);
	}

}
