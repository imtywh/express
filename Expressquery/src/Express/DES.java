package Express;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DES {


public static byte[] desEncrypt(byte[] message, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

		return cipher.doFinal(message);
	}

public static byte[] desDecrypt(byte[] message, String key) throws Exception {

		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
		return cipher.doFinal(message);
	}

//*********************************************************************************

	public static String encrypt(String input,String key) throws Exception {
		if(key.length()==8)
			return base64Encode(desEncrypt(input.getBytes(),key));
		else
		return base64Encode(desEncrypt(input.getBytes(),IVinit(key)));
	}

	public static String decrypt(String input,String key) throws Exception {
		byte[] result = base64Decode(input);
		if(key.length()==8) 
			return new String(desDecrypt(result,key));
		else
		return new String(desDecrypt(result,IVinit(key)));
	}

	public static String base64Encode(byte[] s) {
		if (s == null)
			return null;
		BASE64Encoder b = new sun.misc.BASE64Encoder();
		return b.encode(s);
	}

	public static byte[] base64Decode(String s) throws IOException {
		if (s == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] b = decoder.decodeBuffer(s);
		return b;
	}
	
    public static String  gettext( String a , String b , String s)  //取出中间文本
    { 
	  Pattern pattern2 = Pattern.compile(a+"(.*?)"+b);
	  Matcher matcher2 = pattern2.matcher(s);	  
	  if(matcher2.find()){
		  return matcher2.group(1);
	  } else {return null; }
    }
    
	public static String IVinit(String a) {
		a = a.substring(5);
		System.out.println(a);
		return a;
	}
}
