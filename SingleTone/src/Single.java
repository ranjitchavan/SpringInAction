
public class Single {
	private static Single obj=null;
	private Single() {
		
		
	}
	public static Single getInstance() {
		
		if(obj==null)
			obj=new Single();
		return obj;
	}

	public Object clone() {
		return null;	
}

}


