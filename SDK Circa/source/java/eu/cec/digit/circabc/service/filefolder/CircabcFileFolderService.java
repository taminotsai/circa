package eu.cec.digit.circabc.service.filefolder;

import java.util.List;

import org.alfresco.service.cmr.model.FileInfo;
import org.alfresco.service.cmr.repository.NodeRef;

public interface CircabcFileFolderService
{
	public List<FileInfo> list(NodeRef contextNodeRef);
}
