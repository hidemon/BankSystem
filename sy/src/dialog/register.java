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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import db.DB1;

public class register extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_3;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public register(Shell parent, int style) {
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
		shell.setSize(450, 300);
		shell.setText("\u6CE8\u518C\u65B0\u7528\u6237");
		
		Button button = new Button(shell, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String vx=text.getText().trim();
				
				Pattern p=Pattern.compile("[0-9][0-9][0-9][0-9][0-9]");
				Matcher m=p.matcher(vx);			
				if(vx.equals("")){
					MessageDialog.openInformation((Shell) getParent(), "系统消息","输入账号为空！");
					return;
				}
				if(!m.matches()){
					MessageDialog.openInformation((Shell) getParent(), "系统消息","输入字符非法，只能为5位数字");
					return;
				}
				
				String vx1=text_1.getText().trim();			
				Pattern p1=Pattern.compile("[0-9a-zA-Z]+");
				Matcher m1=p1.matcher(vx1);			
				if(vx1.equals("")){
					MessageDialog.openInformation((Shell) getParent(), "系统消息","输入账号为空！");
					return;
				}
				if(!m1.matches()){
					MessageDialog.openInformation((Shell) getParent(), "系统消息","输入字符非法，只能为数字！");
					return;
				}
				
				String vx2=text_2.getText().trim();			
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
				if(!vx2.equals(vx1)){
				MessageDialog.openInformation((Shell) getParent(), "系统消息","密码不匹配！");
				return;
				}
			
				String vx3=text_3.getText().trim();			
				Pattern p3=Pattern.compile("[0-9]");
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
				String in_value=text.getText().replaceAll(" ", "");
				System.out.println("insert into manager(num,password,type) values("+vx+",'"+vx2+"',"+vx3+")");
				db.update("insert into manager(num,password,type) values("+vx+",'"+vx2+"',"+vx3+")");
				db.close();
				shell.dispose();
			}
		});
		button.setBounds(91, 199, 98, 56);
		button.setText("\u786E\u5B9A\u6CE8\u518C\uFF01");
		
		Button button_1 = new Button(shell, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
		button_1.setBounds(282, 199, 98, 56);
		button_1.setText("\u53D6\u6D88");
		
		text = new Text(shell, SWT.BORDER);
		text.setBounds(225, 23, 73, 26);
		
		text_1 = new Text(shell, SWT.BORDER);
		text_1.setBounds(225, 64, 73, 26);
		
		text_2 = new Text(shell, SWT.BORDER);
		text_2.setBounds(225, 108, 73, 26);
		
		text_3 = new Text(shell, SWT.BORDER);
		text_3.setBounds(225, 140, 73, 26);
		
		Label lblid = new Label(shell, SWT.NONE);
		lblid.setBounds(143, 26, 76, 20);
		lblid.setText("\u5DE5\u53F7ID");
		
		Label label = new Label(shell, SWT.NONE);
		label.setBounds(143, 67, 76, 20);
		label.setText("\u5BC6\u7801");
		
		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setBounds(106, 111, 98, 20);
		label_1.setText("\u518D\u6B21\u8F93\u5165\u5BC6\u7801");
		
		Label label_2 = new Label(shell, SWT.NONE);
		label_2.setBounds(143, 143, 76, 20);
		label_2.setText("\u7C7B\u578B");

	}
}
