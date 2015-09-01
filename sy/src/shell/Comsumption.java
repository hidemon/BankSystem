package shell;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import info.CreditInfo;
import info.QuesInfo;

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

public class Comsumption extends Shell {
	private Text text;
	private Text text_2;
	private Text text_3;
	private Text text_1;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			Comsumption shell = new Comsumption(display);
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
	
	public void setStu( CreditInfo info ){					//
		text.setText(info.id);	
		text_1.setText(info.available);
	}
	/**
	 * Create the shell.
	 * @param display
	 */
	
	public Comsumption(Display display) {
		super(display, SWT.SHELL_TRIM);
		setBackgroundImage(ResourceManager.getPluginImage("sy", "pic/rBACE1JXm_ugSfr5AAHvdi33HwE751.jpg"));
		setBackgroundMode(SWT.INHERIT_FORCE);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateformatter=formatter.format(new Date(System.currentTimeMillis()));
		
		text = new Text(this, SWT.BORDER);
		text.setBounds(193, 51, 103, 26);
		
		text_2 = new Text(this, SWT.BORDER);
		text_2.setBounds(193, 145, 103, 26);
		text_2.setText(dateformatter);
		
		text_3 = new Text(this, SWT.BORDER);
		text_3.setBounds(193, 197, 103, 26);
		
		Label label = new Label(this, SWT.NONE);
		label.setBounds(91, 54, 76, 20);
		label.setText("\u4FE1\u7528\u5361\u53F7\uFF1A");
		
		Label label_1 = new Label(this, SWT.NONE);
		label_1.setBounds(91, 148, 76, 20);
		label_1.setText("\u65E5\u671F\uFF1A");
		
		Label label_2 = new Label(this, SWT.NONE);
		label_2.setBounds(91, 203, 76, 20);
		label_2.setText("\u6D88\u8D39\u91D1\u989D\uFF1A");
		
		Button button = new Button(this, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String vx0=text.getText().trim();//信用卡号	
				String vx=text_1.getText().trim();//可用额度
				Double ava = Double.parseDouble(vx);
				String vx1=text_2.getText().trim();//date
				String vx2=text_3.getText().trim();//输入消费金额
			
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
				Double quchu = Double.parseDouble(vx2);
				if(quchu > ava){
					MessageDialog.openInformation((Shell) getParent(), "系统消息","消费金额大于可用额度！");
					return;
				}
				ava = ava - quchu;
				DB1 db= new DB1();
				LinkedList<HashMap<String, String>> list2 = db.queryToList("select * from id where id_id = 3");		
				int idnum = Integer.parseInt(list2.get(0).get("value"));		
				idnum++;
				db.update("update id set value="+idnum+" where id_id =3");
				db.update("insert into credit_record(idcredit_card,consumption_t,date,volume,idrecord) values('"+vx0+"',1,'"+dateformatter+"',"+vx2+","+idnum+")");
				db.update("update creditcard set available_money="+ava+" where idCreditCard='"+vx0+"'");
				db.close();
				close();
			}
		});
		button.setBounds(69, 248, 98, 30);
		button.setText("\u786E\u5B9A");
		
		Button button_1 = new Button(this, SWT.NONE);
		button_1.setBounds(283, 248, 98, 30);
		button_1.setText("\u53D6\u6D88");
		
		text_1 = new Text(this, SWT.BORDER);
		text_1.setBounds(193, 100, 103, 26);
		
		Label label_3 = new Label(this, SWT.NONE);
		label_3.setBounds(91, 103, 76, 20);
		label_3.setText("\u53EF\u7528\u989D\u5EA6\uFF1A");
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("\u5237\u5361\u6D88\u8D39");
		setSize(450, 335);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
