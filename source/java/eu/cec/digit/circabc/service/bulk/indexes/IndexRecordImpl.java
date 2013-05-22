package eu.cec.digit.circabc.service.bulk.indexes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import eu.cec.digit.circabc.service.bulk.indexes.IndexService.Headers;

public class IndexRecordImpl implements IndexRecord {

	private static final String EQUALS_SIGN = "=";

	private int rowNumber;

	final private List<IndexEntry> indexEntries = new ArrayList<IndexEntry>();

	public IndexRecordImpl(final int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public void addIndexEntry(final IndexEntry indexEntry) {
		indexEntries.add(indexEntry);
	}

	public List<IndexEntry> getIndexEntries() {
		return indexEntries;
	}

	public IndexEntry getEntry(final String headerName) {
		for(final IndexEntry indexEntry : indexEntries) {
			if(indexEntry.getHeaderName().equals(headerName)) {
				return indexEntry;
			}
		}
		return null;
	}

	/* Predefined Index accessor */

	private String getGeneric(final String headerName) {
		final IndexEntry indexEntry = getEntry(headerName);
		String value = "";
		if(indexEntry != null) {
			value = indexEntry.getValue();
			if(value == null) {
				value = "";
			}
    	}
		return value;
	}

	private void setGeneric(final String headerName, final String value) {
		IndexEntry indexEntry = getEntry(headerName);
		if(indexEntry != null) {
			indexEntry.setValue(value);
		} else {
			indexEntry = new IndexEntryImpl(headerName, value);
			addIndexEntry(indexEntry);
		}
	}

	public String getName() {
		return getGeneric(Headers.NAME);
    }

	public void setName(final String value) {
		String name;
		if(File.separatorChar == '/') {
			name = value.replace('\\', File.separatorChar);
		} else {
			name = value.replace('/', File.separatorChar);
		}
		setGeneric(Headers.NAME, name);
    }

    public String getTitle() {
    	return getGeneric(Headers.TITLE);
    }

    public void setTitle(final String value) {
		setGeneric(Headers.TITLE, value);
    }

    public String getDescription() {
    	return getGeneric(Headers.DESCRIPTION);
    }

    public void setDescription(final String value) {
		setGeneric(Headers.DESCRIPTION, value);
    }

    public String getAuthor() {
    	return getGeneric(Headers.AUTHOR);
    }

    public void setAuthor(final String value) {
		setGeneric(Headers.AUTHOR, value);
    }

    public String getDocLang() {
    	return getGeneric(Headers.DOC_LANG);
    }

    public void setDocLang(final String value) {
    	setGeneric(Headers.DOC_LANG, value);
    }

    public String getKeywords() {
    	return getGeneric(Headers.KEYWORDS);
    }

    public void setKeywords(final String value) {
    	setGeneric(Headers.KEYWORDS, value);
    }

    public String getStatus() {
    	return getGeneric(Headers.STATUS);
    }

    public void setStatus(final String value) {
    	setGeneric(Headers.STATUS, value);
    }

    public String getIssueDate() {
    	return getGeneric(Headers.ISSUE_DATE);
    }

    public void setIssueDate(final String value) {
    	setGeneric(Headers.ISSUE_DATE, value);
    }

    public String getReference() {
    	return getGeneric(Headers.REFERENCE);
    }

    public void setReference(final String value) {
    	setGeneric(Headers.REFERENCE, value);
    }

    public String getExpirationDate() {
    	return getGeneric(Headers.EXPIRATION_DATE);
    }

    public void setExpirationDate(final String value) {
    	setGeneric(Headers.EXPIRATION_DATE, value);
    }

    public String getSecurityRanking() {
    	return getGeneric(Headers.SECURITY_RANKING);
    }

    public void setSecurityRanking(final String value) {
    	setGeneric(Headers.SECURITY_RANKING, value);
    }

    
    /*
    public String getAttri1() {
    	return getGeneric(Headers.ATTRI1);
    }

    public void setAttri1(final String value) {
    	setGeneric(Headers.ATTRI1, value);
    }

    public String getAttri2() {
    	return getGeneric(Headers.ATTRI2);
    }

    public void setAttri2(final String value) {
    	setGeneric(Headers.ATTRI2, value);
    }

    public String getAttri3() {
    	return getGeneric(Headers.ATTRI3);
    }

    public void setAttri3(final String value) {
    	setGeneric(Headers.ATTRI3, value);
    }

    public String getAttri4() {
    	return getGeneric(Headers.ATTRI4);
    }

    public void setAttri4(final String value) {
    	setGeneric(Headers.ATTRI4, value);
    }

    public String getAttri5() {
    	return getGeneric(Headers.ATTRI5);
    }

    public void setAttri5(final String value) {
    	setGeneric(Headers.ATTRI5, value);
    }

    
    public String getAttri6() {
    	return getGeneric(Headers.ATTRI6);
    }

    public void setAttri6(final String value) {
    	setGeneric(Headers.ATTRI6, value);
    }

    public String getAttri7() {
    	return getGeneric(Headers.ATTRI7);
    }

    public void setAttri7(final String value) {
    	setGeneric(Headers.ATTRI7, value);
    }

    public String getAttri8() {
    	return getGeneric(Headers.ATTRI8);
    }

    public void setAttri8(final String value) {
    	setGeneric(Headers.ATTRI8, value);
    }

    public String getAttri9() {
    	return getGeneric(Headers.ATTRI9);
    }

    public void setAttri9(final String value) {
    	setGeneric(Headers.ATTRI9, value);
    }

    public String getAttri10() {
    	return getGeneric(Headers.ATTRI10);
    }

    public void setAttri10(final String value) {
    	setGeneric(Headers.ATTRI10, value);
    }
    
    public String getAttri11() {
    	return getGeneric(Headers.ATTRI11);
    }

    public void setAttri11(final String value) {
    	setGeneric(Headers.ATTRI11, value);
    }

    public String getAttri12() {
    	return getGeneric(Headers.ATTRI12);
    }

    public void setAttri12(final String value) {
    	setGeneric(Headers.ATTRI12, value);
    }

    public String getAttri13() {
    	return getGeneric(Headers.ATTRI13);
    }

    public void setAttri13(final String value) {
    	setGeneric(Headers.ATTRI13, value);
    }

    public String getAttri14() {
    	return getGeneric(Headers.ATTRI14);
    }

    public void setAttri14(final String value) {
    	setGeneric(Headers.ATTRI14, value);
    }

    public String getAttri15() {
    	return getGeneric(Headers.ATTRI15);
    }

    public void setAttri15(final String value) {
    	setGeneric(Headers.ATTRI15, value);
    }

    
    public String getAttri16() {
    	return getGeneric(Headers.ATTRI16);
    }

    public void setAttri16(final String value) {
    	setGeneric(Headers.ATTRI16, value);
    }

    public String getAttri17() {
    	return getGeneric(Headers.ATTRI17);
    }

    public void setAttri17(final String value) {
    	setGeneric(Headers.ATTRI17, value);
    }

    public String getAttri18() {
    	return getGeneric(Headers.ATTRI18);
    }

    public void setAttri18(final String value) {
    	setGeneric(Headers.ATTRI18, value);
    }

    public String getAttri19() {
    	return getGeneric(Headers.ATTRI19);
    }

    public void setAttri19(final String value) {
    	setGeneric(Headers.ATTRI19, value);
    }

    public String getAttri20() {
    	return getGeneric(Headers.ATTRI20);
    }

    public void setAttri20(final String value) {
    	setGeneric(Headers.ATTRI20, value);
    }
    */
    
    public String getTypeDocument() {
    	return getGeneric(Headers.TYPE_DOCUMENT);
    }

    public void setTypeDocument(final String value) {
    	setGeneric(Headers.TYPE_DOCUMENT, value);
    }

    public String getTranslator() {
    	return getGeneric(Headers.TRANSLATOR);
    }

    public void setTranslator(final String value) {
    	setGeneric(Headers.TRANSLATOR, value);
    }

    public String getIndexRecordDocLang() {
    	return getGeneric(Headers.DOC_LANG);
    }

    public void setIndexRecordDocLang(final String value) {
    	setGeneric(Headers.DOC_LANG, value);
    }

    public String getNoContent() {
    	return getGeneric(Headers.NO_CONTENT);
    }

    public void setNoContent(final String value) {
    	setGeneric(Headers.NO_CONTENT, value);
    }

    public String getOriLang() {
    	return getGeneric(Headers.ORI_LANG);
    }

    public void setOriLang(final String value) {
    	setGeneric(Headers.ORI_LANG, value);
    }

    public String getRelTrans() {
    	return getGeneric(Headers.REL_TRANS);
    }

    public void setRelTrans(final String value) {
    	setGeneric(Headers.REL_TRANS, value);
    }

    public String getOverwrite() {
    	return getGeneric(Headers.OVERWRITE);
    }

    public void setOverwrite(final String value) {
    	setGeneric(Headers.OVERWRITE, value);
    }

	public int getRowNumber() {
		return rowNumber;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		//Row Number
		sb.append("Row Number");
		sb.append(EQUALS_SIGN);
		sb.append(getRowNumber());
		sb.append("\n");
		//Name
		sb.append(Headers.NAME);
		sb.append(EQUALS_SIGN);
		sb.append(getGeneric(Headers.NAME));
		sb.append("\n");
		//Title
		sb.append(Headers.TITLE);
		sb.append(EQUALS_SIGN);
		sb.append(getGeneric(Headers.TITLE));
		sb.append("\n");
		//Description
		sb.append(Headers.DESCRIPTION);
		sb.append(EQUALS_SIGN);
		sb.append(getGeneric(Headers.DESCRIPTION));
		sb.append("\n");
		//Author
		sb.append(Headers.AUTHOR);
		sb.append(EQUALS_SIGN);
		sb.append(getGeneric(Headers.AUTHOR));
		sb.append("\n");
		//DocLang
		sb.append(Headers.DOC_LANG);
		sb.append(EQUALS_SIGN);
		sb.append(getGeneric(Headers.DOC_LANG));
		sb.append("\n");
		//Keywords
		sb.append(Headers.KEYWORDS);
		sb.append(EQUALS_SIGN);
		sb.append(getGeneric(Headers.KEYWORDS));
		sb.append("\n");
		//Keywords
		sb.append(Headers.KEYWORDS);
		sb.append(EQUALS_SIGN);
		sb.append(getGeneric(Headers.KEYWORDS));
		sb.append("\n");
		//Status
		sb.append(Headers.STATUS);
		sb.append(EQUALS_SIGN);
		sb.append(getGeneric(Headers.STATUS));
		sb.append("\n");
		//Issue Date
		sb.append(Headers.ISSUE_DATE);
		sb.append(EQUALS_SIGN);
		sb.append(getGeneric(Headers.ISSUE_DATE));
		sb.append("\n");
		//Reference
		sb.append(Headers.REFERENCE);
		sb.append(EQUALS_SIGN);
		sb.append(getGeneric(Headers.REFERENCE));
		sb.append("\n");
		//Expiration Date
		sb.append(Headers.EXPIRATION_DATE);
		sb.append(EQUALS_SIGN);
		sb.append(getGeneric(Headers.EXPIRATION_DATE));
		sb.append("\n");
		//Security Ranking
		sb.append(Headers.SECURITY_RANKING);
		sb.append(EQUALS_SIGN);
		sb.append(getGeneric(Headers.SECURITY_RANKING));
		sb.append("\n");
		/*
		//Attr1
		sb.append(Headers.ATTRI1);
		sb.append(EQUALS_SIGN);
		sb.append(getGeneric(Headers.ATTRI1));
		sb.append("\n");
		//Attr2
		sb.append(Headers.ATTRI2);
		sb.append(EQUALS_SIGN);
		sb.append(getGeneric(Headers.ATTRI2));
		sb.append("\n");
		//Attr3
		sb.append(Headers.ATTRI3);
		sb.append(EQUALS_SIGN);
		sb.append(getGeneric(Headers.ATTRI3));
		sb.append("\n");
		//Attr4
		sb.append(Headers.ATTRI4);
		sb.append(EQUALS_SIGN);
		sb.append(getGeneric(Headers.ATTRI4));
		sb.append("\n");
		//Attr5
		sb.append(Headers.ATTRI5);
		sb.append(EQUALS_SIGN);
		sb.append(getGeneric(Headers.ATTRI5));
		sb.append("\n");
		*/
		//Translator
		sb.append(Headers.TRANSLATOR);
		sb.append(EQUALS_SIGN);
		sb.append(getGeneric(Headers.TRANSLATOR));
		sb.append("\n");
		//Doc Lang
		sb.append(Headers.DOC_LANG);
		sb.append(EQUALS_SIGN);
		sb.append(getGeneric(Headers.DOC_LANG));
		sb.append("\n");
		//No Content
		sb.append(Headers.NO_CONTENT);
		sb.append(EQUALS_SIGN);
		sb.append(getGeneric(Headers.NO_CONTENT));
		sb.append("\n");
		//Ori Lang
		sb.append(Headers.ORI_LANG);
		sb.append(EQUALS_SIGN);
		sb.append(getGeneric(Headers.ORI_LANG));
		sb.append("\n");
		//Rel Trans
		sb.append(Headers.REL_TRANS);
		sb.append(EQUALS_SIGN);
		sb.append(getGeneric(Headers.REL_TRANS));
		sb.append("\n");
		//Overwrite
		sb.append(Headers.OVERWRITE);
		sb.append(EQUALS_SIGN);
		sb.append(getGeneric(Headers.OVERWRITE));
		sb.append("\n");
		return sb.toString();
	}

	

	public String getDynamicProperty(int index)
	{
		return getGeneric(Headers.ATTRIPREFIX + Integer.toString(index));
	}
	
	

	public String getAttri1()
	{
		return getDynamicProperty(1);
	}

	public String getAttri2()
	{
		return getDynamicProperty(2);
	}

	public String getAttri3()
	{
		return getDynamicProperty(3);
	}

	public String getAttri4()
	{
		return getDynamicProperty(4);
	}

	public String getAttri5()
	{
		return getDynamicProperty(5);
	}

	public String getAttri6()
	{
		return getDynamicProperty(6);
	}

	public String getAttri7()
	{
		return getDynamicProperty(7);
	}

	public String getAttri8()
	{
		return getDynamicProperty(8);
	}

	public String getAttri9()
	{
		return getDynamicProperty(9);
	}

	public String getAttri10()
	{
		return getDynamicProperty(10);
	}

	public String getAttri11()
	{
		return getDynamicProperty(11);
	}

	public String getAttri12()
	{
		return getDynamicProperty(12);
	}

	public String getAttri13()
	{
		return getDynamicProperty(13);
	}

	public String getAttri14()
	{
		return getDynamicProperty(14);
	}

	public String getAttri15()
	{
		return getDynamicProperty(15);
	}

	public String getAttri16()
	{
		return getDynamicProperty(16);
	}

	public String getAttri17()
	{
		return getDynamicProperty(17);
	}

	public String getAttri18()
	{
		return getDynamicProperty(18);
	}

	public String getAttri19()
	{
		return getDynamicProperty(19);
	}

	public String getAttri20()
	{
		return getDynamicProperty(20);
	}

	
	public void setDynamicProperty(int index, String value)
	{
		setGeneric(Headers.ATTRIPREFIX + Integer.toString(index), value);
		
	}
	
	public void setAttri1(String value)
	{
		setDynamicProperty(1, value);
		
	}

	public void setAttri2(String value)
	{
		setDynamicProperty(2, value);
		
	}

	public void setAttri3(String value)
	{
		setDynamicProperty(3, value);
		
	}

	public void setAttri4(String value)
	{
		setDynamicProperty(4, value);
		
	}

	public void setAttri5(String value)
	{
		setDynamicProperty(5, value);
		
	}

	public void setAttri6(String value)
	{
		setDynamicProperty(6, value);
		
	}

	public void setAttri7(String value)
	{
		setDynamicProperty(7, value);
		
	}

	public void setAttri8(String value)
	{
		setDynamicProperty(8, value);
		
	}

	public void setAttri9(String value)
	{
		setDynamicProperty(9, value);
		
	}

	public void setAttri10(String value)
	{
		setDynamicProperty(10, value);
		
	}

	public void setAttri11(String value)
	{
		setDynamicProperty(11, value);
		
	}

	public void setAttri12(String value)
	{
		setDynamicProperty(12, value);
		
	}

	public void setAttri13(String value)
	{
		setDynamicProperty(13, value);
		
	}

	public void setAttri14(String value)
	{
		setDynamicProperty(14, value);
		
	}

	public void setAttri15(String value)
	{
		setDynamicProperty(15, value);
		
	}

	public void setAttri16(String value)
	{
		setDynamicProperty(16, value);
		
	}

	public void setAttri17(String value)
	{
		setDynamicProperty(17, value);
		
	}

	public void setAttri18(String value)
	{
		setDynamicProperty(18, value);
		
	}

	public void setAttri19(String value)
	{
		setDynamicProperty(19, value);
		
	}

	public void setAttri20(String value)
	{
		setDynamicProperty(20, value);
		
	}
}
