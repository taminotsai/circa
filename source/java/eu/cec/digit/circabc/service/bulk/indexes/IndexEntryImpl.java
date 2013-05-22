package eu.cec.digit.circabc.service.bulk.indexes;

public class IndexEntryImpl implements IndexEntry {

	private String headerName;
	private String value;

	public IndexEntryImpl(final String headerName, final String value) {
		this.headerName = headerName;
		this.value = value;
	}

	public String getHeaderName() {
		return headerName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

}
