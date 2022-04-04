package database_classes;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;




public class EncryptionConversion {
	
	private static String key = "aqdeswfrj";
	 
	 
	public static String convert_to_text(String password)  
	{
		String strData="";
		
		try {
			SecretKeySpec skeyspec=new SecretKeySpec(key.getBytes("ISO-8859-1"),"Blowfish");
			Cipher cipher=Cipher.getInstance("Blowfish");
			cipher.init(Cipher.DECRYPT_MODE, skeyspec);
			byte[] decrypted=cipher.doFinal(password.getBytes("ISO-8859-1"));
			strData=new String(decrypted, "ISO-8859-1");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strData;
	}
	
	
	public static String encrypt_text(String password) 
	{
		String strData="";
		

		try {
			SecretKeySpec skeyspec=new SecretKeySpec(key.getBytes("ISO-8859-1"),"Blowfish");
			Cipher cipher=Cipher.getInstance("Blowfish");
			cipher.init(Cipher.ENCRYPT_MODE, skeyspec);
			byte[] encrypted=cipher.doFinal(password.getBytes("ISO-8859-1"));
			strData=new String(encrypted, "ISO-8859-1");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strData;
	}

}
