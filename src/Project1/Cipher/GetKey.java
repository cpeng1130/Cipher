package Project1.Cipher;

import java.util.HashMap;
import java.util.Map;

public class GetKey {
	int numcipherlength;// cut the ciphertext to n parts
	int n; // defining  a number to represent n
	static int charlength=26;
	String[] plaintext= new String [charlength];
	static Map<Character,Double> cdmap  = new HashMap<Character,Double>();//  creating a Map cdmap to record the English Letter Frequency Table 
	Map<Integer, char[]> ciphermap= new HashMap<Integer ,char[]>();// creating a Map ciphermap to record every letter in ciphertext.length%n position before translation 
	Map<Integer, char[]> plainmap=new HashMap<Integer,char[]>();// creating a Map plainmap to record the possibly plain text
	Map<Character, Double> fremap= new HashMap<Character,Double>();// creating a Map fremap to get a letter and its frequency
	public char [] letters= new char[charlength];
	int [] count;
	double subfre=0.0;
	public char[] getLetters()// defining a array list to record A-Z;
	{	
		for(char a='A',i=0;a<='Z';a++,i++)
		{
			letters [i]=a;
		}
		return  letters;
	}
	public  static Map<Character, Double>  standardlyFre()// creating a English letter frequency talbe
	{
		cdmap.put('A',8.19 );cdmap.put('B',1.47 );cdmap.put('C',3.83 );cdmap.put('D',3.91 );cdmap.put('E',12.25 );cdmap.put('F',2.26 );cdmap.put('G',1.71 );cdmap.put('H',4.57 );cdmap.put('I',7.10 );cdmap.put('J',0.14 );
		cdmap.put('K',0.41 );cdmap.put('L',3.77 );cdmap.put('M', 3.34);cdmap.put('N',7.06 );cdmap.put('O',7.26 );cdmap.put('P',2.89 );cdmap.put('Q',0.09 );cdmap.put('R',6.85 );cdmap.put('S',6.36 );cdmap.put('T',9.41 );cdmap.put('U',2.58 );cdmap.put('V',1.09 );cdmap.put('W',1.59 );cdmap.put('X',0.21 );cdmap.put('Y',1.58 );cdmap.put('Z',0.06 );
		return cdmap;
	}
	public Map<Integer, char[]> cipherToChar( String str ,int n)//get the letter in cipher.length%n  position and put them in ciphermap
	{
		String[] charstr=new String[n]; // here is to define a string [] charstr to record cipher.length%n  positon's letter
		char[] keybitstochar=str.toCharArray();//
		int x=str.length();
		for(int i=0;i<n;i++)
		{
			charstr[i]="";
		}
		 for(int i=0;i<x;i++)
		 {
			 charstr[i%n]+=keybitstochar[i];
		 }
		 for(int i=0;i<n;i++)
		 {
			 ciphermap.put(i,charstr[i].toCharArray());// let these String [] into Map  ciphermap
		 }
		return ciphermap;
	}
	public String geteveryLinePlainText(int n)// this method is to get key
	{
		String key="";
		for(int i=0;i<n;i++)
		{
			this.getPlainText(i);
			this.getMixPlain(i);
			//this.getMixPlain();
			key=key+this.getAllFre();
		}
		return key;
	}
	public Map<Integer,char[]>  getPlainText(int k)  // this method is to get one of array list of cipher text  to translate to plain text
	{
		char[] charcipher=new char[(this.numcipherlength)];// defining a char [] charcipher to record translation letter
		for(int i=0;i<this.numcipherlength;i++)
		{
			charcipher[i]=0;
		}
		charcipher=ciphermap.get(k);// 
		
		for(int i=0;i<charlength;i++)
			{
			char [] charplain=new char[this.numcipherlength];
			for(int j=0;j<numcipherlength;j++)// get plain letter
				{
					charplain[j]=(char)((((int)charcipher[j]-(int)this.getLetters()[i])+26)%26+65);// here every letter subtract A-Z
				}
				plainmap.put(i, charplain);// here the plain text have 26  kinds of possibility
			}
		return plainmap; 
	}
	public String [] getMixPlain(int k)//this method is used to change the type(plaintext) from char[] to string
	{
		//System.out.println(this.plainmap.size());     26
		//System.out.println(this.getplaintext().size());     26
		for(int i=0;i<this.charlength;i++)
		{
			plaintext[i]=String.valueOf(getPlainText(k).get(i));
		}
		return plaintext;
	}
	public Map<Character,Double> getFre(int k)// get every letter's frequency in a array list of cipher text(before translation)
	{
		Integer [] count= new Integer [26];
		for(int i=0;i<26;i++)
		{
			count[i]=0;
		}
		for(int i=0;i<26;i++)
		{
			for(int j=0;j<this.numcipherlength;j++)
			{
				if((this.letters[i]+"").equals((String.valueOf(this.plaintext[k])).charAt(j)+"")){count[i]++;}
			}
			fremap.put((char)(i+65),(double)(count[i]*100/numcipherlength));  
		}
		return fremap;
	}
	
	public double matchFre()// get every lette's frequency of plain text
	{
		subfre=0;
		double [] everysubfre= new double [charlength];
		for(int i=0;i<charlength;i++)
		{
			everysubfre[i]=0.0;
		}
		for(int i=0;i<this.charlength;i++)
		{
			double x=this.standardlyFre().get((char)(i+65));
			double y=this.fremap.get((char)(i+65));
			everysubfre[i]=Math.abs(x-y);
			subfre+=everysubfre[i];
		}
		return subfre;
	}
	Map<Character,Double>  poskeymap =new HashMap<Character,Double>();// this map is to record every letter and letter's frequency (translated)
	public String getAllFre()// get the most possible letter 
	{
		double minnum=0;int count=0;
		for(char a='A',i=0;a<='Z';a++,i++)
		{
			this.getFre(i);
			this.matchFre();
			poskeymap.put(a,this.matchFre()); 
		}
		minnum=poskeymap.get((char)65);
		for(char a='A';a<'Z';a++)
		{
			if(minnum>poskeymap.get(a))
			{
				minnum=poskeymap.get(a);
				count=(int)(a-65);
			}
		}
		//System.out.println((char)(count+65)+"-----"+poskeymap.get((char)(count+65)));
		return ((char)(count+65)+"");
	 }	
	
	
}
