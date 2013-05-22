package eu.cec.digit.circabc.web.wai.dialog;

public interface WaiDialogAsync {
	
	public String getFinishAsyncButtonLabel();
	
	public boolean isFinishAsyncButtonDisabled();
	
	public String finishAsync();

	public boolean isFinishAsyncButtonVisible();

}
