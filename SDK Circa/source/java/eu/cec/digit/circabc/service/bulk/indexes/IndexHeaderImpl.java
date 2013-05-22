package eu.cec.digit.circabc.service.bulk.indexes;

import java.util.List;

public class IndexHeaderImpl implements IndexHeader {
	private String headerName;
	private List<HeaderValidator> headerValidators;

	public IndexHeaderImpl(final String headerName, final List<HeaderValidator> headerValidators) {
		this.headerName = headerName;
		this.headerValidators = headerValidators;
	}

	public String getHeaderName() {
		return headerName;
	}

	public List<HeaderValidator> getHeaderValidators() {
		return headerValidators;
	}
}
