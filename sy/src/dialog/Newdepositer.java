package dialog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.dialogs.MessageDialog;
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

public class Newdepositer extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_3;
	private Text text_4;
    public  int judgesuccess;
    private Text text_5;
    private Text text_6;
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public Newdepositer(Shell parent, int style) {
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

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setBackgroundMode(SWT.INHERIT_FORCE);
		shell.setBackgroundImage(ResourceManager.getPluginImage("sy", "pic/rBACE1JXm_ugSfr5AAHvdi33HwE751.jpg"));
		shell.setBackground(SWTResourceManager.getColor(175, 238, 238));
		shell.setSize(450, 470);
		shell.setText("\u521B\u5EFA\u65B0\u50A8\u6237");
		
		Button button = new Button(shell, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			String vx=text.getText().trim();		
			Pattern p=Pattern.compile("[0-9]+");
			Matcher m=p.matcher(vx);			
			if(vx.equals("")){
				MessageDialog.openInformation((Shell) getParent(), "1系统消息","输入账号为空！");
				return;
			}
			if(!m.matches()){
				MessageDialog.openInformation((Shell) getParent(), "1系统消息","输入字符非法，只能为数字！");
				return;
			}
			
			String vx1=text_1.getText().trim();			
			Pattern p1=Pattern.compile("[a-zA-Z]+");
			Matcher m1=p1.matcher(vx1);			
			if(vx1.equals("")){
				MessageDialog.openInformation((Shell) getParent(), "2系统消息","输入账号为空！");
				return;
			}
			if(!m1.matches()){
				MessageDialog.openInformation((Shell) getParent(), "2系统消息","输入字符非法，只能为数字！");
				return;
			}
			
			String vx2=text_2.getText().trim();			
			Pattern p2=Pattern.compile("[a-zA-Z]+");
			Matcher m2=p2.matcher(vx2);			
			if(vx2.equals("")){
				MessageDialog.openInformation((Shell) getParent(), "3系统消息","输入账号为空！");
				return;
			}
			if(!m2.matches()){
				MessageDialog.openInformation((Shell) getParent(), "3系统消息","输入字符非法，只能为数字！");
				return;
			}
			
		
			String vx3=text_3.getText().trim();			
			Pattern p3=Pattern.compile("[0-9a-zA-Z]+");
			Matcher m3=p3.matcher(vx3);			
			if(vx3.equals("")){
				MessageDialog.openInformation((Shell) getParent(), "4系统消息","输入账号为空！");
				return;
			}
			if(!m3.matches()){
				MessageDialog.openInformation((Shell) getParent(), "4系统消息","输入字符非法，只能为数字！");
				return;
			}
			
			String vx4=text_4.getText().trim();			
			Pattern p4=Pattern.compile("[0-9]+");
			Matcher m4=p4.matcher(vx4);			
			if(vx4.equals("")){
				MessageDialog.openInformation((Shell) getParent(), "5系统消息","输入账号为空！");
				return;
			}
			if(!m4.matches()){
				MessageDialog.openInformation((Shell) getParent(), "5系统消息","输入字符非法，只能为数字！");
				return;
			}
			//password
			String vx5=text_5.getText().trim();			
			Pattern p5=Pattern.compile("[0-9][0-9][0-9][0-9][0-9][0-9]");
			Matcher m5=p5.matcher(vx5);			
			if(vx5.equals("")){
				MessageDialog.openInformation((Shell) getParent(), "6系统消息","输入账号为空！");
				return;
			}
			if(!m5.matches()){
				MessageDialog.openInformation((Shell) getParent(), "6系统消息","输入字符非法，只能为数字！");
				return;
			}
			String vx6=text_6.getText().trim();					
			if(!vx6.equals(vx5)){
				MessageDialog.openInformation((Shell) getParent(), "7系统消息","密码不匹配！");
				return;
			}
			
			MessageDialog.openInformation((Shell) getParent(), "5系统消息","insert into depositer(iddepositer,first_name,last_name,address,phone) values('"+vx+"','"+vx1+"','"+vx2+"','"+vx3+"','"+vx4+"','"+vx5+"')");
			DB1 db=new DB1();
			judgesuccess = db.update("insert into depositer(iddepositer,first_name,last_name,address,phone,password) values('"+vx+"','"+vx1+"','"+vx2+"','"+vx3+"','"+vx4+"','"+vx5+"')");
			db.close();
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
				shell.dispose();
			}
		});
		button_1.setBackground(SWTResourceManager.getColor(175, 238, 238));
		button_1.setBounds(276, 372, 98, 30);
		button_1.setText("\u53D6\u6D88");
		
		text = new Text(shell, SWT.BORDER);
		text.setBounds(232, 59, 142, 26);
		
		text_1 = new Text(shell, SWT.BORDER);
		text_1.setBounds(232, 110, 142, 26);
		
		text_2 = new Text(shell, SWT.BORDER);
		text_2.setBounds(232, 160, 142, 26);
		
		text_3 = new Text(shell, SWT.BORDER);
		text_3.setBounds(232, 206, 142, 26);
		
		text_4 = new Text(shell, SWT.BORDER);
		text_4.setBounds(232, 259, 142, 26);
		
		Label lblid = new Label(shell, SWT.NONE);
		lblid.setBounds(150, 62, 76, 20);
		lblid.setText("\u65B0\u7528\u6237ID:");
		
		Label label = new Label(shell, SWT.NONE);
		label.setBounds(150, 113, 76, 20);
		label.setText("\u540D\uFF1A");
		
		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setBounds(150, 163, 76, 20);
		label_1.setText("\u59D3\uFF1A");
		
		Label label_2 = new Label(shell, SWT.NONE);
		label_2.setBounds(150, 209, 76, 20);
		label_2.setText("\u4F4F\u5740\uFF1A");
		
		Label label_3 = new Label(shell, SWT.NONE);
		label_3.setBounds(150, 262, 76, 20);
		label_3.setText("\u7535\u8BDD\uFF1A");
		
		Label label_4 = new Label(shell, SWT.NONE);
		label_4.setBounds(99, 307, 98, 20);
		label_4.setText("\u516D\u4F4D\u6570\u5B57\u5BC6\u7801\uFF1A");
		
		text_5 = new Text(shell, SWT.BORDER);
		text_5.setBounds(232, 307, 142, 26);
		
		text_6 = new Text(shell, SWT.BORDER);
		text_6.setBounds(232, 340, 142, 26);
		
		Label label_5 = new Label(shell, SWT.NONE);
		label_5.setBounds(120, 346, 76, 20);
		label_5.setText("\u518D\u6B21\u8F93\u5165\uFF1A");

	}
}
