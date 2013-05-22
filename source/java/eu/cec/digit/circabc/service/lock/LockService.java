package eu.cec.digit.circabc.service.lock;


public interface LockService
{
	public void lock(String item );
	public void unlock(String item );
	public boolean isLocked(String item );
}
