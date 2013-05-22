package eu.cec.digit.circabc.service.dynamic.authority;

import java.util.List;
import java.util.Map;

import org.alfresco.service.namespace.QName;

public class TransgressCutHeritance extends AbstractTransgressCutHeritance {

	public TransgressCutHeritance() {
		super();
	}

	/**
	 * @param mandatoryAspect
	 *            the node that has been evaluated need to have specified aspect
	 */
	@Override
	public void setMandatoryAspects(final Map<String, String> mandatoryAspects) {

		for (Map.Entry<String, String> elem : mandatoryAspects.entrySet()) {
			this.mandatoryAspects.put(QName.createQName(elem.getKey()), QName
					.createQName(elem.getValue()));
		}
	}

	/**
	 * List of permissions where the dynamic authority has competence for
	 *
	 * @param permissions
	 *            list of permissions
	 */
	@Override
	public void setRequiredFor(final List<String> permissions) {
		this.permissions.addAll(permissions);
	}

	@Override
	public final void setCircabcService(final String circabcService) {
		this.circabcService = circabcService;
	}

}