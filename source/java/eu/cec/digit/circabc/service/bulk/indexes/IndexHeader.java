package eu.cec.digit.circabc.service.bulk.indexes;

import java.util.List;

public interface IndexHeader {
	public String getHeaderName();
	public List<HeaderValidator> getHeaderValidators();
}
