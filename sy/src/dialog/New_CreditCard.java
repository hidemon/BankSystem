package dialog;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JOptionPane;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import db.DB1;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.wb.swt.ResourceManager;

public class New_CreditCard extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text text_cardholder;
	private Text text_date;
	private Text text_money;
	private Combo combo;
	private Composite container;
    public  int judgesuccess;
    private Text text_period;
    private Text text_cardid;
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public New_CreditCard(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}
	public static String getRandomString(int length) { //length表示生成字符串的长度
	    String base = "0123456789";   
	    Random random = new Random();   
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i < length; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString();   
	 } 

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		
		shell = new Shell(getParent(), getStyle());
		shell.setBackgroundImage(ResourceManager.getPluginImage("sy", "pic/rBACE1JXm_ugSfr5AAHvdi33HwE751.jpg"));
		shell.setBackgroundMode(SWT.INHERIT_FORCE);
		shell.setBackground(SWTResourceManager.getColor(175, 238, 238));
		shell.setSize(450, 470);
		shell.setText("\u521B\u5EFA\u65B0\u50A8\u6237");
		//获取日期
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateformatter=formatter.format(new Date(System.currentTimeMillis()));
		//JOptionPane.showConfirmDialog(null, dateformatter,"提示:", JOptionPane.YES_NO_OPTION);
		String cardid = getRandomString(10);
		Button button = new Button(shell, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			DB1 db=new DB1();
			
			
			
	         //利率
			LinkedList<HashMap<String, String>> list2 = db.queryToList("select * from interest_rate where interestid=7");		
			double interest = Double.parseDouble(list2.get(0).get("value"));
			
	
			
			
			//贷户身份ID
			String vx1=text_cardholder.getText().trim();			
			Pattern p1=Pattern.compile("[0-9]+");
			Matcher m1=p1.matcher(vx1);			
			if(vx1.equals("")){
				MessageDialog.openInformation((Shell) getParent(), "2系统消息","输入账号为空！");
				return;
			}
			if(!m1.matches()){
				MessageDialog.openInformation((Shell) getParent(), "2系统消息","输入字符非法，只能为数字！");
				return;
			}
			
					
			LinkedList<HashMap<String, String>> list = db.queryToList("select * from depositer where iddepositer = '"+vx1+"'");
			if(list.size()==0){
				System.out.println(db.update("select * from depositer where iddepositer = '"+vx1+"'"));
				JOptionPane.showConfirmDialog(null, "请先创建持卡人信息！！","新的持卡人", JOptionPane.YES_NO_OPTION);
				db.close();
				shell.close();
			};				       				
		    
			//金额大小
			String vx3=text_money.getText().trim();			
			Pattern p3=Pattern.compile("[0-9]+");
			Matcher m3=p3.matcher(vx3);			
			if(vx3.equals("")){
				MessageDialog.openInformation((Shell) getParent(), "4系统消息","输入账号为空！");
				return;
			}
			if(!m3.matches()){
				MessageDialog.openInformation((Shell) getParent(), "4系统消息","输入字符非法，只能为数字！");
				return;
			}
			double vx1_1=Double.parseDouble(vx3);    //vx1_1用double类型保存储蓄金额，检查是否满足要求
		
			
			
			//贷款期限
			String vx5=text_period.getText().trim();			
			Pattern p5=Pattern.compile("[0-9]+");
			Matcher m5=p5.matcher(vx5);			
			if(vx5.equals("")){
				MessageDialog.openInformation((Shell) getParent(), "4系统消息","输入账号为空！");
				return;
			}
			if(!m5.matches()){
				MessageDialog.openInformation((Shell) getParent(), "4系统消息","输入字符非法，只能为数字！");
				return;
			}
			
			String vx=text_cardid.getText().trim();
			System.out.println("insert into creditcard(idCreditCard,user_id,volume,interest,interestfree_period,available_money) values('"+vx+"','"+vx1+"','"+vx3+"',"+interest+","+vx5+","+vx3+")"); 
			judgesuccess = db.update("insert into creditcard(idCreditCard,user_id,volume,interest,interestfree_period,available_money) values('"+vx+"','"+vx1+"','"+vx3+"',"+interest+","+vx5+","+vx3+")");
			db.close();
			JOptionPane.showConfirmDialog(null, "成功创建新的信用卡！卡号："+vx+"身份号："+vx1+"  贷款金额：" + vx3,"成功创建新的信用卡！", JOptionPane.YES_NO_OPTION);
			shell.dispose();
		}
		});
		button.setBackground(SWTResourceManager.getColor(175, 238, 238));
		button.setBounds(77, 372, 98, 30);
		button.setText("\u786E\u5B9A\u6DFB\u52A0\uFF01");
		
		Button button_1 = new Button(shell, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		button_1.setBackground(SWTResourceManager.getColor(175, 238, 238));
		button_1.setBounds(276, 372, 98, 30);
		button_1.setText("\u53D6\u6D88");
		
		text_cardholder = new Text(shell, SWT.BORDER);
		text_cardholder.setBounds(232, 77, 142, 26);
		
		text_date = new Text(shell, SWT.BORDER);
		text_date.setBounds(232, 124, 142, 26);
		text_date.setText(dateformatter);	
		
		text_money = new Text(shell, SWT.BORDER);
		text_money.setBounds(232, 172, 142, 26);
		
		text_period = new Text(shell, SWT.BORDER);
		text_period.setBounds(232, 237, 142, 26);
		
		Label label_5 = new Label(shell, SWT.NONE);
		label_5.setBounds(138, 240, 76, 20);
		label_5.setText("\u514D\u606F\u671F\uFF1A");
		
		Label label_3 = new Label(shell, SWT.NONE);
		label_3.setBounds(138, 175, 76, 20);
		label_3.setText("\u4FE1\u7528\u989D\u5EA6\uFF1A");
		
		Label label_2 = new Label(shell, SWT.NONE);
		label_2.setBounds(138, 127, 76, 20);
		label_2.setText("\u529E\u5361\u65E5\u671F");
		
		Label lblid = new Label(shell, SWT.NONE);
		lblid.setBounds(108, 80, 118, 20);
		lblid.setText("\u4FE1\u7528\u5361\u6237\u4E3BID:");
		
		Label label = new Label(shell, SWT.NONE);
		label.setBounds(138, 296, 76, 20);
		label.setText("\u5361\u53F7:");
		
		text_cardid = new Text(shell, SWT.BORDER);
		text_cardid.setBounds(232, 296, 142, 26);
		text_cardid.setText(cardid);
		

	}
}
