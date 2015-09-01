package shell;

import info.QuesInfo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.ui.PlatformUI;

import db.DB1;

public class Amend_depo extends Shell {
	private Text text;
	private Text text_1;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			Amend_depo shell = new Amend_depo(display);
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
	
	/*
	 * 
	 *  /在shell中恢复修改之前的内容
	 *  
	 *  
	 *  */
	public void setStu( QuesInfo info ){					//
		text.setText(info.num);
		text_1.setText(info.password);
		
		
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public Amend_depo(Display display) {
		super(display, SWT.SHELL_TRIM);
		
		text = new Text(this, SWT.BORDER);
		text.setEnabled(false);
		text.setBounds(192, 19, 73, 26);
		
		text_1 = new Text(this, SWT.BORDER);
		text_1.setBounds(192, 67, 73, 26);
		
		Label label = new Label(this, SWT.NONE);
		label.setBounds(110, 22, 76, 20);
		label.setText("\u5DE5\u53F7");
		
		Label label_1 = new Label(this, SWT.NONE);
		label_1.setBounds(110, 70, 76, 20);
		label_1.setText("\u5BC6\u7801");
		
		Button button = new Button(this, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
							
				String vx2=text_1.getText().trim();
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
				
			
		
				DB1 db=new DB1();
				String in_value=text.getText().replaceAll(" ", "");
				System.out.println(in_value);
				db.update("update manager set password='"+vx2+"'"+" where num="+in_value);
				db.close();
				close();
			}
		});
		button.setBounds(88, 196, 98, 30);
		button.setText("\u786E\u5B9A\u4FEE\u6539");
		
		Button button_1 = new Button(this, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			close();
			}
		});
		button_1.setBounds(260, 196, 98, 30);
		button_1.setText("\u53D6\u6D88");
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("\u4FEE\u6539\u4FE1\u606F");
		setSize(450, 300);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
