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
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import db.DB1;

import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.ResourceManager;

public class Withdraw_depo extends Shell {
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
			Withdraw_depo shell = new Withdraw_depo(display);
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
	public Withdraw_depo(Display display) {
		super(display, SWT.SHELL_TRIM);
		setBackgroundMode(SWT.INHERIT_FORCE);
		setBackgroundImage(ResourceManager.getPluginImage("sy", "pic/rBACE1JXm_ugSfr5AAHvdi33HwE751.jpg"));
		
		text = new Text(this, SWT.BORDER);
		text.setBounds(224, 23, 73, 26);
		
		text_1 = new Text(this, SWT.BORDER);
		text_1.setBounds(224, 74, 73, 26);
		
		text_2 = new Text(this, SWT.BORDER);
		text_2.setBounds(224, 130, 73, 26);
		
		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String vx0=text.getText().trim();//储蓄号 iddeposit
				String vx1=text_1.getText().trim();//储户账号 depositer_id
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
				
				String vx3=text_3.getText().trim();//取款金额				
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
				LinkedList<HashMap<String, String>> list1 = db.queryToList("select * from deposit where iddeposit = "+vx0+"");
				float period=1;
				/*try {
					String url = "jdbc:mysql://localhost:3306/bank_db2";
					String username = "root";
					String password = "1234";
					Class.forName("com.mysql.jdbc.Driver");
					conn = DriverManager.getConnection(url, username, password);
					stmt = conn.createStatement();
					
					rs=stmt.executeQuery("select * from deposit where iddeposit = "+vx0+"");
					rs.next();
					Date date1=rs.getDate(3);
					Date date2=new Date(System.currentTimeMillis());
					period = daysBetween(date1,date2);
					JOptionPane.showConfirmDialog(null,""+period ,"新的储户111", JOptionPane.YES_NO_OPTION);
					rs.close();
					conn.close();
					stmt.close();	
				} catch (ClassNotFoundException e2) {
					e2.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}*/
				try {
				rs=db.query("select * from deposit where iddeposit = "+vx0+"");
				rs.next();
				Date date1=rs.getDate(3);
				Date date2=new Date(System.currentTimeMillis());
				period = daysBetween(date1,date2);
				JOptionPane.showConfirmDialog(null,""+period ,"相隔多少天", JOptionPane.YES_NO_OPTION);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
				String type_deposit = list1.get(0).get("type");
				String date_deposit = list1.get(0).get("date");
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String dateformatter=formatter.format(new Date(System.currentTimeMillis()));
				
				JOptionPane.showConfirmDialog(null,date_deposit+dateformatter ,"新的储户", JOptionPane.YES_NO_OPTION);
				double interest = Double.parseDouble(list1.get(0).get("volume"));//calculate利率
				if(type_deposit.equals("1")){
					LinkedList<HashMap<String, String>> list2 = db.queryToList("select * from interest_rate where interestid = 1");
					double interest_rate_current = Double.parseDouble(list2.get(0).get("value"));
					interest = interest * interest_rate_current * period/30;
				}else if(type_deposit.equals("2")){
					if(period > 5 * 365){
					LinkedList<HashMap<String, String>> list2 = db.queryToList("select * from interest_rate where interestid = 2");
					double interest_rate_current = Double.parseDouble(list2.get(0).get("value"));		
					interest = interest * interest_rate_current * period/30;
					}else{
						LinkedList<HashMap<String, String>> list2 = db.queryToList("select * from interest_rate where interestid = 1");
						double interest_rate_current = Double.parseDouble(list2.get(0).get("value"));		
						interest = interest * interest_rate_current * period/30;
					}
				}else if(type_deposit.equals("3")){
					if(period > 365){
					LinkedList<HashMap<String, String>> list2 = db.queryToList("select * from interest_rate where interestid = 3");
					double interest_rate_current = Double.parseDouble(list2.get(0).get("value"));		
					interest = interest * interest_rate_current * period/30;
					}else{
						LinkedList<HashMap<String, String>> list2 = db.queryToList("select * from interest_rate where interestid = 1");
						double interest_rate_current = Double.parseDouble(list2.get(0).get("value"));		
						interest = interest * interest_rate_current * period/30;
					}
				}
				JOptionPane.showConfirmDialog(null,vx3 ,"vx3", JOptionPane.YES_NO_OPTION);	
				JOptionPane.showConfirmDialog(null,""+interest ,"interest", JOptionPane.YES_NO_OPTION);
				db.update("update deposit set volume = volume+"+interest+"-"+vx3+" where iddeposit = "+vx0+"");
				db.close();
				close();
			}
		});
		btnNewButton.setBounds(66, 213, 98, 30);
		btnNewButton.setText("\u786E\u5B9A");
		
		Button btnNewButton_1 = new Button(this, SWT.NONE);
		btnNewButton_1.setBounds(261, 213, 98, 30);
		btnNewButton_1.setText("\u53D6\u6D88");
		
		Label label = new Label(this, SWT.NONE);
		label.setBounds(88, 23, 76, 20);
		label.setText("\u50A8\u84C4\u53F7");
		
		Label label_1 = new Label(this, SWT.NONE);
		label_1.setBounds(88, 77, 76, 20);
		label_1.setText("\u50A8\u6237\u8D26\u6237");
		
		Label label_2 = new Label(this, SWT.NONE);
		label_2.setBounds(88, 130, 76, 20);
		label_2.setText("\u8D26\u6237\u5BC6\u7801");
		
		Label label_3 = new Label(this, SWT.NONE);
		label_3.setBounds(88, 175, 76, 20);
		label_3.setText("\u53D6\u6B3E\u91D1\u989D");
		
		text_3 = new Text(this, SWT.BORDER);
		text_3.setBounds(224, 175, 73, 26);
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("\u53D6\u6B3E\u754C\u9762");
		setSize(450, 300);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
