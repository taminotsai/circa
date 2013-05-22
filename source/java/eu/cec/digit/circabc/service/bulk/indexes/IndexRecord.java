package eu.cec.digit.circabc.service.bulk.indexes;

import java.util.List;

public interface IndexRecord {
	public void addIndexEntry(final IndexEntry indexEntry);

	public List<IndexEntry> getIndexEntries();

	public IndexEntry getEntry(final String headerName);

	/* Predefined Index accessor */

	public String getName();

    public String getTitle();

    public String getDescription();

    public String getDocLang();

    public String getAuthor();

    public String getKeywords();

    public String getStatus();

    public String getIssueDate();

    public String getReference();

    public String getExpirationDate();

    public String getSecurityRanking();
    

    public String getAttri1();

    public String getAttri2();

    public String getAttri3();

    public String getAttri4();

    public String getAttri5();

    public String getAttri6();

    public String getAttri7();

    public String getAttri8();

    public String getAttri9();

    public String getAttri10();


    public String getAttri11();

    public String getAttri12();

    public String getAttri13();

    public String getAttri14();

    public String getAttri15();

    public String getAttri16();

    public String getAttri17();

    public String getAttri18();

    public String getAttri19();

    public String getAttri20();


        
    public String getTypeDocument();

    public String getTranslator();

    public String getIndexRecordDocLang();

    public String getNoContent();

    public String getOriLang();

    public String getRelTrans();

    public String getOverwrite();

    public int getRowNumber();

    public void setName(final String value);

    public void setTitle(final String value);

    public void setDescription(final String value);

    public void setAuthor(final String value);

    public void setDocLang(final String value);

    public void setKeywords(final String value);

    public void setStatus(final String value);

    public void setIssueDate(final String value);

    public void setReference(final String value);

    public void setExpirationDate(final String value);

    public void setSecurityRanking(final String value);
    
   
    
    public void setAttri1(final String value);
    public void setAttri2(final String value);
    public void setAttri3(final String value);
    public void setAttri4(final String value);
    public void setAttri5(final String value);
    public void setAttri6(final String value);
    public void setAttri7(final String value);
    public void setAttri8(final String value);
    public void setAttri9(final String value);
    public void setAttri10(final String value);
    

    public void setAttri11(final String value);
    public void setAttri12(final String value);
    public void setAttri13(final String value);
    public void setAttri14(final String value);
    public void setAttri15(final String value);
    public void setAttri16(final String value);
    public void setAttri17(final String value);
    public void setAttri18(final String value);
    public void setAttri19(final String value);
    public void setAttri20(final String value);
    
    
    
    

    public void setDynamicProperty(int index  ,final String value );
    
    public String getDynamicProperty(int index);
    
    public void setTypeDocument(final String value);

    public void setTranslator(final String value);

    public void setIndexRecordDocLang(final String value);

    public void setNoContent(final String value);

    public void setOriLang(final String value);

    public void setRelTrans(final String value);

    public void setOverwrite(final String value);

    public String toString();
}
