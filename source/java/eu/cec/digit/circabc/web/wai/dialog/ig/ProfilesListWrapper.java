package eu.cec.digit.circabc.web.wai.dialog.ig;

import java.io.Serializable;
import java.util.List;

public class ProfilesListWrapper implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1188993444330837226L;

	private String profileName;
	private List<String> igs;

	private ProfilesListWrapper() {

	}

	public ProfilesListWrapper(final String profileName, final List<String> igs) {
		this.profileName = profileName;
		this.igs = igs;
	}

	public String getProfileName() {
		return this.profileName;
	}

	public List<String> getIgs() {
		return this.igs;
	}
}
