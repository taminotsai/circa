package eu.cec.digit.circabc.myfaces.application.jsp;


import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LifeCycleListener implements PhaseListener {

    /**
	 *
	 */
	private static final Log logger = LogFactory.getLog(LifeCycleListener.class);
	private static final long serialVersionUID = -4197163591373990516L;

	public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }

    public void beforePhase(PhaseEvent event) {
    	if (logger.isDebugEnabled())
    	{
    		logger.debug("START PHASE " + event.getPhaseId());
    	}
    }

    public void afterPhase(PhaseEvent event) {
        if (logger.isDebugEnabled())
    	{
    		logger.debug("END PHASE " + event.getPhaseId());
    	}
    }

}
