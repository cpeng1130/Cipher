package Project1.Cipher;

public class TranslateToPlainText {
	String plain="";
	String ciphere=null; 
	String  key=null;
	public TranslateToPlainText(String ciphere, String  key) //  this method is to input  cipher text and key
	{
		super();
		this.ciphere = ciphere;
		this.key = key;
		return;
	}
	public String  translateCipher()// this method is used to  translate  the cipher text to become a plain text
	{
		int n=key.length();
		for(int i=0;i<ciphere.length();i++)
		{
			plain+=(char)((ciphere.charAt(i)-key.charAt(i%n)+26)%26+65);
		}
		return  plain;
	}
}
