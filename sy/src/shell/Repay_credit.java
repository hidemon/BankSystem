package shell;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import info.CreditInfo;
import info.QuesInfo;
import info.RepaycreditInfo;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import db.DB1;

public class Repay_credit extends Shell {
	private Text text_idc;
	private Text text_date;
	private Text text_money;
    private int type;
    private ResultSet rs;
    private Text text_idr;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			Repay_credit shell = new Repay_credit(display);
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
	
	public void setStu( RepaycreditInfo info ){					//
		text_idr.setText(info.idrecord);
		text_idc.setText(info.idcard);		
		text_date.setText(info.date);
		text_money.setText(info.volume);
		type = Integer.parseInt(info.type);
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
	
	public Repay_credit(Display display) {
		super(display, SWT.SHELL_TRIM);
		setBackgroundImage(ResourceManager.getPluginImage("sy", "pic/rBACE1JXm_ugSfr5AAHvdi33HwE751.jpg"));
		setBackgroundMode(SWT.INHERIT_FORCE);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateformatter=formatter.format(new Date(System.currentTimeMillis()));
		
		text_idc = new Text(this, SWT.BORDER);
		text_idc.setBounds(193, 64, 103, 26);
		
		text_date = new Text(this, SWT.BORDER);
		text_date.setBounds(193, 114, 103, 26);
		text_date.setText(dateformatter);
		
		text_money = new Text(this, SWT.BORDER);
		text_money.setBounds(193, 162, 103, 26);
		
		Label label = new Label(this, SWT.NONE);
		label.setBounds(91, 67, 76, 20);
		label.setText("\u4FE1\u7528\u5361\u53F7\uFF1A");
		
		Label label_1 = new Label(this, SWT.NONE);
		label_1.setBounds(91, 117, 76, 20);
		label_1.setText("\u65E5\u671F\uFF1A");
		
		Label label_2 = new Label(this, SWT.NONE);
		label_2.setBounds(91, 165, 76, 20);
		label_2.setText("\u6D88\u8D39\u91D1\u989D\uFF1A");
		
		Button button = new Button(this, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String vx0=text_idc.getText().trim();//信用卡号									
				String vx1=text_date.getText().trim();//date
				String vx2=text_money.getText().trim();//还款金额
				Double volume = Double.parseDouble(vx2);
				String vx_idr=text_idr.getText().trim();
				Pattern p1=Pattern.compile("[0-9]+");
				Matcher m1=p1.matcher(vx2);			
				if(vx2.equals("")){
					MessageDialog.openInformation((Shell) getParent(), "系统消息","输入账号为空！");
					return;
				}
				if(!m1.matches()){
					MessageDialog.openInformation((Shell) getParent(), "系统消息","输入字符非法，只能为数字！");
					return;
				}
				
				DB1 db= new DB1();
				float period=1;
				try {
				rs=db.query("select * from credit_record where idrecord = "+vx_idr+"");
				rs.next();
				Date date1=rs.getDate(3);
				Date date2=new Date(System.currentTimeMillis());
				period = daysBetween(date1,date2);
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			LinkedList<HashMap<String, String>> list1 = db.queryToList("select * from creditcard where idCreditCard = "+vx0+"");
			int time = Integer.parseInt(list1.get(0).get("interestfree_period"));
			double interest_rate = Double.parseDouble(list1.get(0).get("interest"));
			double interest = 0;
			if(type==1){
				if(period<=time)
					interest = volume * interest_rate;
				else
					interest = 0;
			}else{
				interest = volume  * (interest_rate+0.003);
			}
			interest += volume;	
			LinkedList<HashMap<String, String>> list2 = db.queryToList("select * from id where id_id = 3");		
			int idnum = Integer.parseInt(list2.get(0).get("value"));
			
			idnum++;
			db.update("update id set value="+idnum+" where id_id =3");
			db.update("insert into credit_record(idcredit_card,consumption_t,date,volume,idrecord) values('"+vx0+"',3,'"+dateformatter+"',"+interest+","+idnum+")");
				db.update("update creditcard set available_money=available_money + "+vx2+" where idCreditCard='"+vx0+"'");
				db.update("update credit_record set consumption_t = 4 where idrecord="+vx_idr+"");
				db.close();
				close();
			
			}
		});
		button.setBounds(69, 248, 98, 30);
		button.setText("\u786E\u5B9A");
		
		Button button_1 = new Button(this, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				close();
			}
		});
		button_1.setBounds(283, 248, 98, 30);
		button_1.setText("\u53D6\u6D88");
		
		text_idr = new Text(this, SWT.BORDER);
		text_idr.setBounds(193, 20, 73, 26);
		
		Label label_3 = new Label(this, SWT.NONE);
		label_3.setBounds(91, 23, 76, 20);
		label_3.setText("\u8BB0\u5F55\u53F7\uFF1A");
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("\u8FD8\u6B3E");
		setSize(450, 335);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
