package eu.cec.digit.circabc.service.bulk.indexes;

import java.util.List;

public interface IndexHeaders {
	public void addHeader(final IndexHeader header);
	public List<IndexHeader> getHeaders();
	public IndexHeader getHeader(final String headerName);
}
