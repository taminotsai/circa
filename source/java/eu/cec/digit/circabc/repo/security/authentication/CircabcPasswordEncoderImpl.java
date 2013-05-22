package eu.cec.digit.circabc.repo.security.authentication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.alfresco.repo.security.authentication.MD4PasswordEncoderImpl;

import sun.misc.BASE64Encoder;

public class CircabcPasswordEncoderImpl extends MD4PasswordEncoderImpl {

	public CircabcPasswordEncoderImpl() {
		super();
	}

	@Override
	public boolean isPasswordValid(String encPass, String rawPass, Object salt)
	{

		if ( isAlgorithhmPresent(encPass) )
        {
			String algorithm = getAlgorithm(encPass);
			if ( algorithm.equals("SHA") || algorithm.equals("MD5") )
			{
				String pass1 = encPass;
			    String pass2 = this.encodePassword(algorithm , rawPass, salt);
			    return pass1.equals(pass2);
			}
			else  
			{
				throw new IllegalArgumentException("Not supported algorithm : " +algorithm );
			}
	    }
		else
		{
			return super.isPasswordValid(encPass, rawPass, salt);
		}
	}

	private String getAlgorithm(String encPass) {
		return encPass.substring( encPass.indexOf( '{' ) + 1, encPass.indexOf( '}' ) );
	}

	private boolean isAlgorithhmPresent(String encPass) {
		return encPass.indexOf( '{' ) == 0 && encPass.indexOf( '}' ) > 0;
	}
	
	public String encodePassword( String rawPass, Object salt)
	{
		return super.encodePassword(rawPass, salt);
		
	}
	
	private String encodePassword(String  algorithm, String rawPass, Object salt)
    {
		String result = null ;
		MessageDigest md;
		try {
			md = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("No such algorithm : " +algorithm  , e);
		}
		md.update(rawPass.getBytes());
        byte[] bytes = md.digest();
        
        
        // Print out value in Base64 encoding
        BASE64Encoder base64encoder = new BASE64Encoder();
        String hash = base64encoder.encode(bytes);        
        result = "{"+algorithm+"}"+ hash;
		return result;
    }

}
