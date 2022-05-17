package main;
import java.util.*;
public class SimpleCipher
{
public static String encrypt(String s)
 {
	String re="";
	Random r=new Random();
	int key=r.nextInt(10000)+200;
	for(int k=0;k<s.length();++k)
	{
	char ch=s.charAt(k);
	ch+=key;
	re+=ch;
	}
 return (re+(char)(key));
 }
public static String decrypt(String s)
 {
  if(s.isEmpty())
  {return "";}
  String re="";	
  int key=s.charAt(s.length()-1);
  for(int k=0;k<s.length()-1;++k)
  {
	  char ch=(char)(s.charAt(k)-key);
	  re+=ch;
  }
return re;
 }
public static String shuffle(String s)
{
String re="";
for(int k=0;k<s.length();++k)
{
char ch=s.charAt(k);
if(k%2==0)
{
ch++;	
}
else
{
ch--;	
}
re+=ch;
}
int pos=0;
while(pos==0)
{pos=new Random().nextInt(re.length()-1);}
String sp=re.substring(0,pos);
String pp=re.substring(pos);
sp=new StringBuffer(sp).reverse().toString();
re=sp+pp;
return re+(char)(pos);
}
public static String unshuffle(String s)
{
	int kindex=s.charAt(s.length()-1);
	s=s.substring(0,s.length()-1);
	String sp=s.substring(0, kindex);
	String pp=s.substring(kindex);
	sp=new StringBuffer(sp).reverse().toString();
	s=sp+pp;
	String re="";
	for(int k=0;k<s.length();++k)
	{
	char ch=s.charAt(k);
	if(k%2==0)
	{
	ch--;	
	}
	else
	{
	ch++;	
	}
	re+=ch;
	}
	return re;
}
public static String doEncryption(String s)
{
s=performPadding(s);
String re=SimpleCipher.shuffle(s);
re=SimpleCipher.encrypt(re);
return re;
}
public static String doDecryption(String s)
{
String re=SimpleCipher.decrypt(s);
re=SimpleCipher.unshuffle(re);
return re;
}
public static String performPadding(String s)
{
while(s.length()<4)	
{s+=" ";}	
return s;
}
public static void main(String[] args)
{
Scanner sc=new Scanner(System.in);
String s=sc.nextLine();
sc.close();
String en=SimpleCipher.doEncryption(s);
System.out.println("Encrypted: "+en);
System.out.println("Decrypted: "+SimpleCipher.doDecryption(en));
}
}
