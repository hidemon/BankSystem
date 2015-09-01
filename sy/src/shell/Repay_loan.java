package shell;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import info.QuesInfo;


import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import db.DB1;

import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.swt.widgets.Combo;

public class Repay_loan extends Shell {
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_3;	
	private Statement stmt;
	private Connection conn;
	private ResultSet rs;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			Repay_loan shell = new Repay_loan(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setStu( QuesInfo info ){					//
		text.setText(info.num);
		text_1.setText(info.password);
		
		
	}
	public static int daysBetween(Date date1,Date date2) {   
        Calendar cal = Calendar.getInstance();    
        cal.setTime(date1);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(date2);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));     
    } 
	/**
	 * Create the shell.
	 * @param display
	 */
	public Repay_loan(Display display) {
		super(display, SWT.SHELL_TRIM);
		setBackgroundMode(SWT.INHERIT_FORCE);
		setBackgroundImage(ResourceManager.getPluginImage("sy", "pic/rBACE1JXm_ugSfr5AAHvdi33HwE751.jpg"));
		
		text = new Text(this, SWT.BORDER);
		text.setBounds(194, 20, 73, 26);
		
		text_1 = new Text(this, SWT.BORDER);
		text_1.setBounds(194, 74, 73, 26);
		
		text_2 = new Text(this, SWT.BORDER);
		text_2.setBounds(194, 127, 73, 26);
		
		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String vx0=text.getText().trim();//贷款号 idloan
				String vx1=text_1.getText().trim();//贷户账号 loaner_id
				String vx2=text_2.getText().trim();//输入密码
				System.out.println(text_1.getText());				
				Pattern p2=Pattern.compile("[0-9a-zA-Z]+");
				Matcher m2=p2.matcher(vx2);			
				if(vx2.equals("")){
					MessageDialog.openInformation((Shell) getParent(), "系统消息","输入账号为空！");
					return;
				}
				if(!m2.matches()){
					MessageDialog.openInformation((Shell) getParent(), "系统消息","输入字符非法，只能为数字！");
					return;
				}
				
				String vx3=text_3.getText().trim();//还款金额				
				Pattern p3=Pattern.compile("[0-9]+");
				Matcher m3=p3.matcher(vx3);			
				if(vx3.equals("")){
					MessageDialog.openInformation((Shell) getParent(), "系统消息","输入账号为空！");
					return;
				}
				if(!m3.matches()){
					MessageDialog.openInformation((Shell) getParent(), "系统消息","输入字符非法，只能为数字！");
					return;
				}
				
				
						       
				DB1 db=new DB1();
				
				//获取数据库中储户设置的密码并校验
				LinkedList<HashMap<String, String>> list = db.queryToList("select * from depositer where iddepositer = '"+vx1+"'");
				String pass = list.get(0).get("password");
				if(!vx2.equals(pass)){
					MessageDialog.openInformation((Shell) getParent(), "系统消息","密码不匹配！！");
					return;
				}
				
				
				//获取原来该条储蓄的信息，包括何种类型存款，用于计算利率
				LinkedList<HashMap<String, String>> list1 = db.queryToList("select * from loan where idloan = "+vx0+"");
				float period=1;
			
				try {
				rs=db.query("select * from deposit where iddeposit = "+vx0+"");
				rs.next();
				Date date1=rs.getDate(3);
				Date date2=new Date(System.currentTimeMillis());
				period = daysBetween(date1,date2);
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
				String type_loan = list1.get(0).get("loan_type");
				String date_loan = list1.get(0).get("date");
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String dateformatter=formatter.format(new Date(System.currentTimeMillis()));
				
				
				double interest = Double.parseDouble(list1.get(0).get("volume"));//calculate利率
                int time = Integer.parseInt(list1.get(0).get("time"));  //贷款期限
				if(type_loan.equals("3")){
					LinkedList<HashMap<String, String>> list2 = db.queryToList("select * from interest_rate where interestid = 6");
					double interest_rate_current = Double.parseDouble(list2.get(0).get("value"));
					interest = interest * interest_rate_current * period/30;
				}else if(type_loan.equals("1")){
					if(period > time * 365){
					LinkedList<HashMap<String, String>> list2 = db.queryToList("select * from interest_rate where interestid = 4");
					double interest_rate_current = Double.parseDouble(list2.get(0).get("value"));		
					interest = interest * interest_rate_current * 1.5 * period/30;
					}else{
						LinkedList<HashMap<String, String>> list2 = db.queryToList("select * from interest_rate where interestid = 4");
						double interest_rate_current = Double.parseDouble(list2.get(0).get("value"));		
						interest = interest * interest_rate_current * period/30;
					}
				}else if(type_loan.equals("2")){
					if(period > time * 365){
					LinkedList<HashMap<String, String>> list2 = db.queryToList("select * from interest_rate where interestid = 5");
					double interest_rate_current = Double.parseDouble(list2.get(0).get("value"));		
					interest = interest * interest_rate_current * 1.5 * period/30;
					}else{
						LinkedList<HashMap<String, String>> list2 = db.queryToList("select * from interest_rate where interestid = 5");
						double interest_rate_current = Double.parseDouble(list2.get(0).get("value"));		
						interest = interest * interest_rate_current * period/30;
					}
				}
				db.update("update loan set volume = volume + "+interest+" where idloan = "+vx0+"");
				//贷款从活期储蓄里扣除
				LinkedList<HashMap<String, String>> list_deposit = db.queryToList("select * from deposit where depositer_id = '"+vx1+"' and type = 1");
		        int i = 0;
		        //JOptionPane.showConfirmDialog(null,""+i ,"i", JOptionPane.YES_NO_OPTION);
		        double value = 0;
		        if(type_loan.equals("2")){         //住房贷款分多次还清
		        value = Double.parseDouble(vx3);
		       // JOptionPane.showConfirmDialog(null,""+value ,"value", JOptionPane.YES_NO_OPTION);
		      
		        while(i<list_deposit.size())
				{ 
		        	String id = list_deposit.get(i).get("iddeposit");
		        	JOptionPane.showConfirmDialog(null,list_deposit.get(i).get("volume") ,"活期存款余额", JOptionPane.YES_NO_OPTION);	
					if(value < Double.parseDouble(list_deposit.get(i).get("volume"))){
			            
						db.update("update deposit set volume = volume-"+value+" where iddeposit = "+id+"");
						value=0;
						break;
					}else{
						db.update("update deposit set volume = 0 where iddeposit = "+id+"");
						value = value - Double.parseDouble(list_deposit.get(i).get("volume"));						
						i++;
					}
					
				}
		        db.update("update loan set volume = volume - "+vx3+" + "+value+"  where idloan = "+vx0+"");
		        }else{                 //助学和自助贷款一次还清
		        	list1 = db.queryToList("select * from loan where idloan = "+vx0+"");
		        	value =Double.parseDouble(list1.get(0).get("volume"));
			        while(i<list_deposit.size())
					{ 
			        	String id = list_deposit.get(i).get("iddeposit");
						if(value < Double.parseDouble(list_deposit.get(i).get("volume"))){
				            
							db.update("update deposit set volume = volume-"+value+" where iddeposit = "+id+"");
							value=0;
							break;
						}else{
							db.update("update deposit set volume = 0 where iddeposit = "+id+"");
							value = value - Double.parseDouble(list_deposit.get(i).get("volume"));						
							i++;
						}
						
					}
			        db.update("update loan set volume =  "+value+"  where idloan = "+vx0+"");
		        }
		        if(value>0.001){
		        	JOptionPane.showConfirmDialog(null,"未还上的剩余金额："+value ,"余额不足！", JOptionPane.YES_NO_OPTION);
		        }
		        	
				JOptionPane.showConfirmDialog(null,vx3 ,"vx3", JOptionPane.YES_NO_OPTION);	
				JOptionPane.showConfirmDialog(null,""+interest ,"interest", JOptionPane.YES_NO_OPTION);
				
				db.close();
				close();
			}
		});
		btnNewButton.setBounds(66, 257, 98, 30);
		btnNewButton.setText("\u786E\u5B9A");
		
		Button btnNewButton_1 = new Button(this, SWT.NONE);
		btnNewButton_1.setBounds(260, 257, 98, 30);
		btnNewButton_1.setText("\u53D6\u6D88");
		
		Label label = new Label(this, SWT.NONE);
		label.setBounds(88, 23, 76, 20);
		label.setText("\u8D37\u6B3E\u53F7");
		
		Label label_1 = new Label(this, SWT.NONE);
		label_1.setBounds(88, 77, 76, 20);
		label_1.setText("\u8D37\u6B3E\u6237\u8D26\u6237");
		
		Label label_2 = new Label(this, SWT.NONE);
		label_2.setBounds(88, 130, 76, 20);
		label_2.setText("\u8D26\u6237\u5BC6\u7801");
		
		Label label_3 = new Label(this, SWT.NONE);
		label_3.setBounds(88, 175, 76, 20);
		label_3.setText("\u8FD8\u6B3E\u91D1\u989D");
		
		text_3 = new Text(this, SWT.BORDER);
		text_3.setBounds(194, 172, 73, 26);
		
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("\u8D37\u6B3E\u754C\u9762");
		setSize(450, 369);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
