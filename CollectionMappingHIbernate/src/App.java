import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.*;

public class App {
	public static void main(String[] args) {
		Configuration cfg=new Configuration();
		cfg.configure();
		SessionFactory session=cfg.buildSessionFactory();
		Session se=session.openSession();
		Hobbies h1=new Hobbies("Cricket1","OutDoor",1);
		Hobbies h2=new Hobbies("Cricket2","OutDoor",1);
		Hobbies h3=new Hobbies("Cricket3","OutDoor",1);
		Hobbies h4=new Hobbies("Cricket4","OutDoor",1);
		
		HashSet<Hobbies> hs=new HashSet<>();
		hs.add(h1);
		hs.add(h2);
		hs.add(h3);
		hs.add(h4);
		Student s1=new Student(1,"Ranjit1",21,hs);
		Student s2=new Student(1,"Ranjit2",21,hs);
		Student s3=new Student(1,"Ranjit3",21,null);
		Transaction tx=se.beginTransaction();
		se.save(h1);
		se.save(h2);
		se.save(h3);
		se.save(h4);
		
		se.save(s1);
		se.save(s2);
		se.save(s3);
		tx.commit();
		se.clear();
		session.close();
		
	}
}
