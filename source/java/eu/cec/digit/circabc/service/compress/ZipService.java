package eu.cec.digit.circabc.service.compress;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.alfresco.service.cmr.repository.NodeRef;

import eu.cec.digit.circabc.service.bulk.indexes.IndexRecord;
import eu.cec.digit.circabc.service.bulk.indexes.message.ValidationMessage;

public interface ZipService {

	public boolean extract(final File compressedFile, final String fileName, final File outputFile, final List<ValidationMessage> messages);

	public Map<String, NodeRef> extract(final NodeRef libraryNodeRef, final NodeRef destinationNodeRef, final File compressedFile, final List<IndexRecord> indexRecords, final List<ValidationMessage> messages);

	public Map<String, NodeRef> extract(final NodeRef libraryNodeRef, final NodeRef destinationNodeRef, final File compressedFile, final List<String> excludedFileName, final List<IndexRecord> indexRecords, final List<ValidationMessage> messages);

	public List<CompressedEntry> getCompressedEntries(final File compressedFile, final List<ValidationMessage> messages);

	public void addingFileIntoArchive(final File newFile, final File compressedFile);

	public void addingFilesIntoArchive(final List<File> newFiles, final File compressedFile);

	public void addingFileIntoArchive(final NodeRef nodeRef, final File compressedFile);

	public void addingFileIntoArchive(final List<NodeRef> nodeRefs, final File compressedFile);

	public void addingFileIntoArchive(final List<NodeRef> nodeRefs, final File compressedFile, final File indexFile);

	public String getRelativeLibraryPath(final NodeRef nodeRef);
}
