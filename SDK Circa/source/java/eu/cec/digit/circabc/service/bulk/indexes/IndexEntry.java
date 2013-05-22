package eu.cec.digit.circabc.service.bulk.indexes;

public interface IndexEntry {
	public String getHeaderName();
	public String getValue();
	public void setValue(final String value);
}
