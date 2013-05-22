package eu.cec.digit.circabc.service.bulk;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.alfresco.service.cmr.repository.NodeRef;

import eu.cec.digit.circabc.service.bulk.indexes.IndexRecord;
import eu.cec.digit.circabc.service.bulk.indexes.message.ValidationMessage;
import eu.cec.digit.circabc.service.bulk.upload.UploadedEntry;
import eu.cec.digit.circabc.service.compress.CompressedEntry;

public interface BulkService {
	public static final String INDEX_DATE_FORMAT = "dd/MM/yyyy";

	public List<UploadedEntry> upload(final NodeRef containerNodeRef, final File compressedFile, final List<ValidationMessage> messages);

	public List<UploadedEntry> upload(final NodeRef containerNodeRef, final File compressedFile, final List<IndexRecord> indexRecords, final List<ValidationMessage> messages);

	public List<IndexRecord> getIndexRecords(final File compressedFile, final List<ValidationMessage> messages) throws IOException;

	public List<CompressedEntry> getCompressedEntries(final NodeRef containerNodeRef, final File compressedFile, final List<ValidationMessage> messages);

	public List<IndexRecord> getMetaData(final List<NodeRef> nodeRefs);

	public void validateEntries(final List<IndexRecord> indexFileEntries, final List<UploadedEntry> uploadedEntries, final List<ValidationMessage> messages);

}
