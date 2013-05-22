package eu.cec.digit.circabc.aspect;

public class DisableNotificationThreadLocal {

	private static class ThreadLocalBoolean extends ThreadLocal<Boolean> {
	    public Boolean initialValue() {
	      return Boolean.FALSE ;
	    }
	  }
	  private static ThreadLocalBoolean flag = new ThreadLocalBoolean();

	  public void remove() {
		  flag.remove();
	  }

	  public void set(Boolean value) {
		  flag.set(value);
	  }

	  public Boolean get() {
	    return flag.get();
	  }
}
