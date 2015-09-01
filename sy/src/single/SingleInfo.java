package single;

public class SingleInfo {
	public static SingleInfo s;  //单例的一个对象
	public String num;			//学号
	public String name;		//姓名
	public String password;	//密码
	public String power;		//权限
	private SingleInfo(){
		
	}
	/*
	 * 
	 *  返回单例对象
	 *  
	 *  */ 
	static public SingleInfo main(){ //
		if(s==null){
			s=new SingleInfo();
		}
		return s;
	}
}
