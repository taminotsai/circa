package eu.cec.digit.circabc.service.bulk.indexes;

import java.io.File;
import java.io.IOException;
import java.util.List;

import eu.cec.digit.circabc.service.bulk.indexes.message.ValidationMessage;

public interface IndexService {
	public static final  String INDEX_FILE = "index.txt";

	public IndexHeaders getIndexHeaders();

	public void getIndexRecords(final File indexFile, final List<IndexRecord> indexRecords, final List<ValidationMessage> messages) throws IOException;

	public void generateIndexRecords(final File indexFile, final List<IndexRecord> indexRecords) throws IOException;

	public interface Headers {
		public final String NAME = "NAME";
		public final String TITLE = "TITLE";
		public final String DESCRIPTION = "DESCRIPTION";
		public final String AUTHOR = "AUTHOR";
		public final String KEYWORDS = "KEYWORDS";
		public final String STATUS = "STATUS";
		public final String ISSUE_DATE = "ISSUE DATE";
		public final String REFERENCE = "REFERENCE";
		public final String EXPIRATION_DATE = "EXPIRDATE";
		public final String SECURITY_RANKING = "SECRANK";
		public final String ATTRIPREFIX = "ATTRI";
		public final String ATTRI1 = "ATTRI1";
		public final String ATTRI2 = "ATTRI2";
		public final String ATTRI3 = "ATTRI3";
		public final String ATTRI4 = "ATTRI4";
		public final String ATTRI5 = "ATTRI5";
		public final String ATTRI6 = "ATTRI6";
		public final String ATTRI7 = "ATTRI7";
		public final String ATTRI8 = "ATTRI8";
		public final String ATTRI9 = "ATTRI9";
		public final String ATTRI10 = "ATTRI10";
		public final String ATTRI11 = "ATTRI11";
		public final String ATTRI12 = "ATTRI12";
		public final String ATTRI13 = "ATTRI13";
		public final String ATTRI14 = "ATTRI14";
		public final String ATTRI15 = "ATTRI15";
		public final String ATTRI16 = "ATTRI16";
		public final String ATTRI17 = "ATTRI17";
		public final String ATTRI18 = "ATTRI18";
		public final String ATTRI19 = "ATTRI19";
		public final String ATTRI20 = "ATTRI20";
		public final String TYPE_DOCUMENT = "TYPE";
		public final String TRANSLATOR = "TRANSLATOR";
		public final String DOC_LANG = "LANG";
		public final String NO_CONTENT = "NOCONTENT";
		public final String ORI_LANG = "ORILANG";
		public final String REL_TRANS = "RELTRANS";
		public final String OVERWRITE = "OVERWRITE";
	};
}
