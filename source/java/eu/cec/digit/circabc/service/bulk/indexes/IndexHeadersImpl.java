package eu.cec.digit.circabc.service.bulk.indexes;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IndexHeadersImpl implements IndexHeaders {
	private static Log logger = LogFactory.getLog(IndexHeadersImpl.class);
	private final List<IndexHeader> headerList = new LinkedList<IndexHeader>();

	public IndexHeadersImpl() {

	}

	public IndexHeadersImpl(final String[] headers) {
		boolean found = false;
		for(final String header : headers) {
			for(final IndexHeader indexHeader : headerList) {
				if(indexHeader.getHeaderName().equals(header)) {
					found = true;
					break;
				}
			}
			if(!found) {
				addHeader(new IndexHeaderImpl(header, null));
			}
		}
	}

	public void addHeader(final IndexHeader header) {
		if(logger.isDebugEnabled()) {
			logger.debug("addHeader:" + header.getHeaderName());
		}
		((LinkedList<IndexHeader>)this.headerList).addLast(header);
	}

	public List<IndexHeader> getHeaders() {

		return this.headerList;
	}

	public IndexHeader getHeader(final String headerName) {
		for(final IndexHeader indexHeader : headerList) {
			if(indexHeader.getHeaderName().equals(headerName)) {
				return indexHeader;
			}
		}
		return null;
	}
}
