import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.springframework.cglib.core.Local;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp extends JFrame{
	JButton j1,j2,j3,j4,j5;
	public MainApp(String arg0) throws HeadlessException {
		super(arg0);
		// TODO Auto-generated constructor stub
		try(ClassPathXmlApplicationContext ctx=new ClassPathXmlApplicationContext("app.xml")){
			setLayout(new FlowLayout());
			j1=new JButton("MARTHI");
			j2=new JButton("HINDI");
			j3=new JButton("CA");
			j4=new JButton("EN");
			j5=new JButton("China");
			//System.out.println(j1.getActionCommand());
			add(j1);
			add(j2);
			add(j3);
			add(j4);
			add(j5);
			j1.addActionListener((e)->{
				switch(e.getActionCommand()) {
					
					case "MARTHI":
						Locale l=new Locale("hi","IN");
						String s1=ctx.getMessage("str1", null, "msg1",l);
						String s2=ctx.getMessage("str2", null, "msg2",l);
						String s3=ctx.getMessage("str3", null, "msg3",l);
						String s4=ctx.getMessage("str4", null, "msg4",l);
						String s5=ctx.getMessage("str5", null, "msg5",l);
						j1.setText(s1);
						j2.setText(s2);
						j3.setText(s3);
						j4.setText(s4);
						j5.setText(s5);
						
					break;

					case "HINDI":
						Locale l1=new Locale("fr","CA");
						String s11=ctx.getMessage("str1", null, "msg1",l1);
						String s21=ctx.getMessage("str2", null, "msg2",l1);
						String s31=ctx.getMessage("str3", null, "msg3",l1);
						String s41=ctx.getMessage("str4", null, "msg4",l1);
						String s51=ctx.getMessage("str5", null, "msg5",l1);
						j1.setText(s11);
						j2.setText(s21);
						j3.setText(s31);
						j4.setText(s41);
						j5.setText(s51);
						
						
					break;
					

				
			
				}
				
				
			});
			
		}
		
		
	}

	
	
	
	
}
