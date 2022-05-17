package frontend;
import javax.swing.*;

import main.SimpleCipher;

import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.*;
public final class EncrypterFrontEnd
{
	private static String session_psswd;
	private static String abt_sessions="Session passwords allow you to save time while using the app\n"
			+ "If the user sets a session password, all encryptions and decryptions will be performed with the sessions password provided\n"
			+ "until the app is closed, or the session password is changed or removed.\n"
			+ "To set a session password, click on the \"set session password\" option and enter the desired password.\n"
			+ "To remove a session password, click on the \"remove session password\" option.\n"
			+ "To view the current session password, click the \"view current session password\" option.";
	private static String setPsswd="Would you like to password lock your encryption?\nThis will"
			+ " prevent other users of this app from decrypting this message\n "
			+ "unless they have your password.\n"
			+ "If you'd like to skip this step, click cancel or close this dialog";
	private static String askPsswd="This message is password protected\n"
			+ "To view the message, please enter the password for this encryption";
	
	public static final String VIEW="main_panel.png";
	public static final String CCP="cutcopy.png";
	public static final String CONTACT="javakingxi@gmail.com";
	public static final String WORK="https://github.com/nikhil-RGB";
	public static final String ABOUT="https://linktr.ee/nikhil_n67";
	public static final String RULES="<html>Enter text into the first textbox and press on the encrypt<br>"
			+ "button to encrypt it. To decrypt it, switch between encryption and decryption modes via the option on the menu bar<br>"
			+ "You can use ctrl+c/v/x OR the buttons provided for copy paste and cut actions on the selected text in the text area.<br>"
			+ "You can try storing encrypted text in a file/data base and decrypt it later on via the same application.<br>"
			+ "Setting a password for your encryptions gives you further privacy, since other users of the app can't<br>"
			+ "decrypt your text without your permission. Also see: Session Passwords.<br>"
			+ "Happy Encryption!";
	public static final String path="icon.png";
	public static final ImageIcon img=new ImageIcon(CCP);
	public static final boolean isLoneApplication=true;
	private CipherMode mode;
	protected JMenuBar mb;
	protected Box b;
	protected JButton encrypt;
	protected JButton copy_enc;
	protected JButton cut_enc;
	protected JButton paste_enc;
	protected JButton copy_dec;
	protected JButton cut_dec;
	protected JButton paste_dec;
	protected JScrollPane scp;
	protected JScrollPane scp2;
	protected JTextArea main_text;
	protected JTextArea cipher_text;
	{   
		mode=CipherMode.ENCRYPT;
		main_text=new JTextArea(50,50);
		main_text.setBackground(Color.BLACK);
		main_text.setForeground(Color.WHITE);
		main_text.setCaretColor(Color.GREEN);
		main_text.setText("Enter Plain text to Encrypt");
		main_text.setFont(new Font(Font.SANS_SERIF,Font.BOLD,14));
		scp=new JScrollPane(main_text);
		main_text.setLineWrap(true);
        main_text.setWrapStyleWord(true);
        encrypt=new JButton("Encrypt");
        encrypt.setForeground(Color.CYAN);
        encrypt.setFont(new Font("algerian",Font.BOLD,19));
        
        encrypt.setToolTipText("Click here to Encrypt / Decrypt text above!");
        
        copy_enc=new JButton("Copy");
        copy_enc.setBackground(Color.CYAN);
        cut_enc=new JButton("Cut");
        cut_enc.setBackground(Color.CYAN);
        paste_enc=new JButton("Paste");
        paste_enc.setBackground(Color.CYAN);
        cipher_text=new JTextArea(50,50);
        scp2=new JScrollPane(cipher_text);
        cipher_text.setLineWrap(true);
        cipher_text.setWrapStyleWord(true);
        cipher_text.setText("Encrypted Text will be shown here");
        cipher_text.setFont(new Font(Font.SANS_SERIF,Font.BOLD,14));
        cipher_text.setBackground(Color.BLACK);
        cipher_text.setForeground(Color.WHITE);
        cipher_text.setCaretColor(Color.GREEN);
        copy_dec=new JButton("Copy");
        copy_dec.setBackground(Color.CYAN);
        cut_dec=new JButton("Cut");
        cut_dec.setBackground(Color.CYAN);
        paste_dec=new JButton("Paste");
        paste_dec.setBackground(Color.CYAN);
        ActionListener cutCopyPaste=(ev)->
        {
        	JButton src=(JButton)(ev.getSource());
        	if(src==copy_enc)
        	{main_text.copy();}
        	else if(src==cut_enc)
        	{main_text.cut();}
        	else if(src==paste_enc)
        	{main_text.paste();}
        	else if(src==copy_dec)
        	{cipher_text.copy();}
        	else if(src==cut_dec)
        	{cipher_text.cut();}
        	else if(src==paste_dec)
        	{cipher_text.paste();}
        };
        copy_enc.addActionListener(cutCopyPaste);
        copy_dec.addActionListener(cutCopyPaste);
        cut_dec.addActionListener(cutCopyPaste);
        cut_enc.addActionListener(cutCopyPaste);
        paste_enc.addActionListener(cutCopyPaste);
        paste_dec.addActionListener(cutCopyPaste);
        ActionListener enc=(ev)->
        {   
        	Runnable obj=()->
        	{
        	//edit in dialog for password here
        	String text=main_text.getText();
        	String res="";
        	if(mode.equals(CipherMode.ENCRYPT))
        	{
        	String password="";
        	boolean skip_init=false;
        	if(EncrypterFrontEnd.session_psswd!=null)
        	{
        		String options[]= {"Yes","No,custom password","No,no password"};
        	    int response=JOptionPane.showOptionDialog(null,"A session password was detected, would you like\n"
        	    		+ " to encrypt this message with the session password?\n"
        	    		+ "Current sessions password: "+EncrypterFrontEnd.session_psswd,"Use Session Password?" ,JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        	    if(response==0)
        	    {
        	    	skip_init=true;
        	    	password=EncrypterFrontEnd.session_psswd;
        	    }
        	    else if(response==2)
        	    {
        	    	skip_init=true;
        	    	password=null;
        	    }
        	 }
        	
        	if(!skip_init)
        	{
        	do 
        	{ 
        	password=JOptionPane.showInputDialog(null,setPsswd);
        	if((password!=null)&&(!EncrypterFrontEnd.isValidPassword(password)))
        	{
        		JOptionPane.showMessageDialog(null,"Invalid Password\n"
        				+ "Passwords can't have spaces and can't be empty","Error", JOptionPane.ERROR_MESSAGE);
        	}
        	}
        	while((password!=null)&&(!EncrypterFrontEnd.isValidPassword(password)));
        	}
        	
        	
        	String add_line="";
        	 //If there's a password which is entered, then the first line 
        	 //of the encrypted message will
        	 //be an encrypted form of: true <password>.
        	 //In all other cases, the line will be: false
        	 if(password==null)
        	 {
        		 add_line=""+false;
        	 }
        	 else
        	 {
        		 add_line=true+" "+password;
        	 }
        	 add_line=SimpleCipher.encrypt(add_line);
        	 res=encryptSentences(text);
        	 cipher_text.setText(add_line+"\n"+res);
        	}
        	else
        	{
        		ArrayList<String> sentences=splitSentence(text);
        		String control=SimpleCipher.decrypt(sentences.get(0));
        		Scanner reader=new Scanner(control);
        		if(!reader.hasNextBoolean())
        		{
        			JOptionPane.showMessageDialog(null,"This message has either been tampered with, or was encrypted with\nan older version of this application.\nThe message cannot be decrypted for security reasons.","Error",JOptionPane.ERROR_MESSAGE);
        			reader.close();
        			return;
        		}
        		boolean flag=reader.nextBoolean();
        		USER_PASSWORD_INPUT:
        		if(flag)
        		{
        			String psswd=reader.next();
        			boolean skip_inp=psswd.equals(EncrypterFrontEnd.session_psswd);
        			if(skip_inp)
        			{
        				break USER_PASSWORD_INPUT;
        			}
        			else if(EncrypterFrontEnd.session_psswd!=null)
        			{
        				JOptionPane.showMessageDialog(null,"Sessions Password does not match required password\n"
        						+ "Please input the password this message was encrypted with\nto continue.");
        			}
        			String userp="";
        			do
        			{
        			userp=JOptionPane.showInputDialog(askPsswd);
        			if(userp==null)
        			{
        				JOptionPane.showMessageDialog(null,"Operation Cancelled");
        				reader.close();
        				return;
        			}
        			if(!userp.equalsIgnoreCase(psswd))
        			{
        				JOptionPane.showMessageDialog(null,"Wrong password, Try again","Incorrect Password",JOptionPane.ERROR_MESSAGE, null);
        			}
        			}
        			while(!userp.equalsIgnoreCase(psswd));
        		}
        		reader.close();
        		sentences.remove(0);
        		text="";
        		for(String inp:sentences)
        		{
        			text+=inp+"\n";
        		}
        		res=decryptSentences(text);
        		cipher_text.setText(res);
        	}
        	};
        	SwingUtilities.invokeLater(obj);	
        };
        encrypt.addActionListener(enc);
        
	}
	public void setUpLayout()
	{
		//b=Box.createVerticalBox();
		b=new PictureBox(BoxLayout.PAGE_AXIS,VIEW);
		
		Box enc_text=new PictureBox(BoxLayout.PAGE_AXIS,VIEW);
	
		enc_text.add(scp);
		enc_text.add(Box.createRigidArea(new Dimension(10,20)));
		
		Box enc_controls=new PictureBox(BoxLayout.LINE_AXIS,CCP);
		enc_controls.add(encrypt);
		enc_controls.add(Box.createHorizontalStrut(15));
		enc_controls.add(cut_enc);
		enc_controls.add(copy_enc);
		enc_controls.add(paste_enc);
		enc_text.add(enc_controls);
		
		Box dec_text=new PictureBox(BoxLayout.PAGE_AXIS,VIEW);
		
		dec_text.add(scp2);
		dec_text.add(Box.createRigidArea(new Dimension(10,20)));
		Box dec_controls=new PictureBox(BoxLayout.LINE_AXIS,CCP);
		JLabel lab1=new JLabel("Tools");
		lab1.setForeground(Color.CYAN);
		lab1.setFont(new Font("algerian",Font.BOLD,19));
        dec_controls.add(lab1);
        dec_controls.add(Box.createHorizontalStrut(15));
        dec_controls.add(cut_dec);
        dec_controls.add(copy_dec);
        dec_controls.add(paste_dec);
        dec_text.add(dec_controls);
        
        b.add(enc_text);
        b.add(Box.createVerticalStrut(20));
        b.add(dec_text);
        b.add(Box.createVerticalStrut(20));
	}
	public void setUpMenuBar()
	{
		mb=new JMenuBar();
		//additional menu option-Session Password
		JMenu session=new JMenu("Set/Remove Session Password");
		JMenuItem set=new JMenuItem("Set Session Password");
		JMenuItem remove= new JMenuItem("Remove Session Password");
		JMenuItem view=new JMenuItem("View Session Password");
		JMenuItem abt=new JMenuItem("About session passwords");
		ActionListener active=(ae)->
		{
		 Object source=ae.getSource();
		 if(source==set)
		 {   String psswd="";
		    do
		    {
			 psswd=JOptionPane.showInputDialog("Please enter the session password you'd like to set: ");
		     if((!isValidPassword(psswd))&&(psswd!=null))
		      {
		    	 JOptionPane.showMessageDialog(null,"Invalid Password\n"
		    	 		+ "Please note that passwords cannot have spaces and cannot be empty");
		      }
		      else 
		      {break;}		     
		     }
		     while(true);
		  if(psswd==null)
		  {return;}
		  EncrypterFrontEnd.session_psswd=psswd;
		 }
		 else if(source==remove)
		 {   
			 EncrypterFrontEnd.session_psswd=null;
			 JOptionPane.showMessageDialog(null,"If a Session password was initialized,\nit has been removed");
		 }
		 else if(source==abt)
		 {
			 JOptionPane.showMessageDialog(null, abt_sessions);
		 }
		 else
		 {
			 String mssg="";
			 if(EncrypterFrontEnd.session_psswd==null)
			 {
				 mssg="No Session Password has been set";
			 }
			 else
			 {
				 mssg="Session Password is: "+EncrypterFrontEnd.session_psswd;
			 }
			 JOptionPane.showMessageDialog(null,mssg);
		 }
		};
		set.addActionListener(active);
		remove.addActionListener(active);
		abt.addActionListener(active);
		view.addActionListener(active);
		session.add(set);
		session.add(remove);
		session.add(view);
		session.add(abt);
		mb.add(session);
		
		JMenu change=new JMenu("Switch Encryption/Decryption Mode");
		change.setToolTipText("<html>Clicking on this Option will allow you to switch between<br>"
				+ "encrypting and decrypting text via the Shuffle Cipher");
		final JMenuItem sw=new JMenuItem("Switch from Encryption to Decryption Mode");
		ActionListener ar= (ev)->
		{  
			Runnable er=()->{
			if(encrypt.getText().equals("Encrypt"))
			{
				mode=CipherMode.DECRYPT;
				encrypt.setText("Decrypt");
				sw.setText("Switch from Decryption to Encryption Mode");
				main_text.setText("Enter Encrypted text here");
				cipher_text.setText("Plain text will be shown here");
			}
			else
			{
				mode=CipherMode.ENCRYPT;
				encrypt.setText("Encrypt");
				sw.setText("Switch from Encryption to Decryption Mode");
				main_text.setText("Enter Plain text here");
				cipher_text.setText("Encrypted text will be shown here");
			}
			};
			SwingUtilities.invokeLater(er);
		};
		sw.addActionListener(ar);
		change.add(sw);
		mb.add(change);
	  }
	public static void main(String[] args)
	{
		Runnable obj=new Runnable(){
			public void run()
			{
		try 
		{
			Thread.sleep(1500);
		} 
		catch (InterruptedException e) 
		{
		//Nothing required here	
		}
		JFrame frm=new JFrame("Nikhil's Shuffle Cipher!");
		frm.setSize(650, 650);
		frm.setResizable(false);
		setIcon(frm);
		JMenu more=new JMenu("More/Help");
		 final JMenuItem info=new JMenuItem("About Developer");
	     final JMenuItem work=new JMenuItem("More from this Developer");
	     final JMenuItem contact=new JMenuItem("Contact developer");
	     final JMenuItem help=new JMenuItem("How to use");
	     more.add(help);
	     more.add(contact);
	     more.add(work);
	     more.add(info);
	    ActionListener ar=(ev)->
	    {
	    	JMenuItem src=(JMenuItem)(ev.getSource());
	    	Desktop tb=Desktop.getDesktop();
	    	if(src==contact)
	    	{
	    		try {
					tb.mail(new URI("mailto:"+CONTACT));
				    }
	    		catch (Throwable e) 
	    		{
				JOptionPane.showMessageDialog(null,"Can't open mailing app","Oops!",JOptionPane.ERROR_MESSAGE);	
				}
              return;	    	
	    	}
	    	else if(src==help)
	    	{
	    	JOptionPane.showMessageDialog(null,RULES);
	    	return;
	    	}
	    	String s="";
	    	if(src==info)
	    	{s=ABOUT;}
	    	else if(src==work)
	    	{s=WORK;}
	    	try
	    	{
				tb.browse(new URI(s));
			} 
	    	catch (IOException | URISyntaxException e) 
	    	{
			JOptionPane.showMessageDialog(null,"Couldn't open dev-site","Oops!",JOptionPane.ERROR_MESSAGE);	
			}
	    };
	    info.addActionListener(ar);
	    work.addActionListener(ar);
	    help.addActionListener(ar);
	    contact.addActionListener(ar);
	    //back here
		EncrypterFrontEnd obj=new EncrypterFrontEnd();
		obj.setUpLayout();
		obj.setUpMenuBar();
		obj.mb.add(more);
		frm.add(obj.b);
		frm.setJMenuBar(obj.mb);
		if(!isLoneApplication)
		{frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);}
		else
		{
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		frm.setVisible(true);
		performCrop(obj.encrypt,img);
		obj.encrypt.setHorizontalTextPosition(SwingConstants.CENTER);
		obj.encrypt.setBorder(BorderFactory.createLineBorder(Color.CYAN,4));
			}
		};
		SwingUtilities.invokeLater(obj);
	}
	public static ArrayList<String> splitSentence(String s)
	{
	 ArrayList<String> sentences=new ArrayList<>(0);
	 Scanner reader=new Scanner(s);
	 while(reader.hasNextLine())
	 {
		 sentences.add(reader.nextLine());
	 }
	 reader.close();
	 return sentences;
	}
	
	public static String encryptSentences(String s)
	{
		String rr="";
		ArrayList<String> sen=splitSentence(s);
		for(int k=0;k<sen.size();++k)
		{
			String re=sen.get(k);
			rr+=SimpleCipher.doEncryption(re)+"\n";
			
		}
		
		return rr;
	}
	public static String decryptSentences(String s)
	{
		String rr="";
		ArrayList<String> sen=splitSentence(s);
		for(int k=0;k<sen.size();++k)
		{
			String re=sen.get(k);
			if(re.length()==0)
			{continue;}
			rr+=SimpleCipher.doDecryption(re)+"\n";
		
		}
		
		return rr;
		
	}
	public static void setIcon(JFrame frm)
	{   
		try
		{
		ImageIcon img=new ImageIcon(EncrypterFrontEnd.path);
	    frm.setIconImage(img.getImage());
		}
	    catch(Throwable obj)
		{
	    JOptionPane.showMessageDialog(null,"Unable to locate Icon","Oops!",JOptionPane.ERROR_MESSAGE);
		}
	}

public static class PictureBox extends Box
{
private static final long serialVersionUID = -7785584961772213825L;
Image ps;
	public PictureBox(int axis,String filepath) 
	{
		super(axis);
		ps=new ImageIcon(filepath).getImage();
	}
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(ps==null) {return;}
		g.drawImage(ps,0,0,this.getWidth()+1,this.getHeight()+1,null);
	}

}
public static void performCrop(JButton b,ImageIcon oh)
{
Image ba=oh.getImage();
ba=ba.getScaledInstance(b.getWidth(),b.getHeight(),Image.SCALE_SMOOTH);
b.setIcon(new ImageIcon(ba));
}
//This method checks if the password entered by the user is valid or not
//returns true in all cases where the password is valid, and false in all other cases
public static boolean isValidPassword(String password)
{
if(password==null)
{
return false;	
}
if(password.contains(" ")||password.isEmpty())
{
return false;	
}
return true;
}
//This method sets and changes the session password status, session password will be changed to inpuuted value
//except null or empty String, in which case session password value will be set to9 null.
public void setSessionPassword(String psswd)
{
 if(psswd==null||psswd.isEmpty())
 {
	 EncrypterFrontEnd.session_psswd=null;
	 return;
 }
 EncrypterFrontEnd.session_psswd=psswd;	
}

}
