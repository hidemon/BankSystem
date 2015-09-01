package dialog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import db.DB1;
import org.eclipse.swt.widgets.Label;

public class Intereat_Amend4 extends Dialog {

	protected Object result;
	protected Shell shlInterestAmend;
	private Text text;
	private Button button_1;
	private Label label;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public Intereat_Amend4(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlInterestAmend.open();
		shlInterestAmend.layout();
		Display display = getParent().getDisplay();
		while (!shlInterestAmend.isDisposed()) {
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
		shlInterestAmend = new Shell(getParent(), getStyle());
		shlInterestAmend.setSize(450, 300);
		shlInterestAmend.setText("Interest Amend");
		
		text = new Text(shlInterestAmend, SWT.BORDER);
		text.setBounds(231, 86, 121, 37);
		
		Button button = new Button(shlInterestAmend, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
					
				System.out.println(text.getText());
				String vx=text.getText().trim();
				System.out.println(text.getText());		
				Pattern p=Pattern.compile("0.[0-9]+");			
				Matcher m=p.matcher(vx);				
				if(vx.equals("")){
					MessageDialog.openInformation(getParent(), "系统消息","输入账号为空！");
					return;
				}
				if(!m.matches()){
					MessageDialog.openInformation(getParent(), "系统消息","输入字符非法，只能为数字！");
					return;
				}
				
		
				DB1 db=new DB1();
				String in_value=text.getText().replaceAll(" ", "");
				System.out.println(in_value);
				db.update("update interest_rate set value="+in_value+" where name='credit'");
			}
		});
		button.setBounds(78, 198, 98, 30);
		button.setText("\u786E\u5B9A");
		
		button_1 = new Button(shlInterestAmend, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlInterestAmend.dispose();
			}
		});
		button_1.setBounds(304, 198, 98, 30);
		button_1.setText("\u53D6\u6D88");
		
		label = new Label(shlInterestAmend, SWT.NONE);
		label.setText("\u4FEE\u6539\u5229\u7387\u4E3A\uFF1A");
		label.setBounds(111, 86, 103, 20);

	}

}
