package view;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import single.SingleInfo;
import db.DB1;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.swt.widgets.Label;

public class Login extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text text_1;
	private Button button;
	private Button button_1;
	private Button button_2;
	private Label lblNewLabel;
	private Label label;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public Login(Shell parent, int style) {
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
		shell.setBackgroundImage(ResourceManager.getPluginImage("sy", "pic/rBACE1JXm_ugSfr5AAHvdi33HwE751.jpg"));
		shell.setImage(SWTResourceManager.getImage("C:\\Users\\pc\\Pictures\\5f7731dbb6fd526642796c73ab18972bd5073647.jpg"));
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		shell.setSize(525, 386);
		shell.setText(getText());
		
		Combo combo = new Combo(shell, SWT.NONE);
		combo.setBounds(218, 73, 88, 28);
		
		text_1 = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		text_1.setBounds(218, 137, 88, 23);
		
		button = new Button(shell, SWT.BORDER | SWT.FLAT);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String ex=combo.getText().trim();
				Pattern p=Pattern.compile("[0-9]+");
				Matcher m=p.matcher(ex);
				System.out.println("dddddd");
				if(ex.equals("")){
					MessageDialog.openInformation(getParent(), "系统消息","输入账号为空！");
					return;
				}
				if(!m.matches()){
					MessageDialog.openInformation(getParent(), "系统消息","输入字符非法，只能为数字！");
					return;
				}
				String num=combo.getText().replaceAll(" ", "");
				String psw=text_1.getText();
				DB1 db=new DB1();
				System.out.println("sssss");
				LinkedList<HashMap<String, String>> list = db.queryToList("select * from manager where num='"+num+"' and password='"+psw+"'" );
				if(list.size()>0){
					result="succ";
						shell.close();
					System.out.println(123);
					
					SingleInfo s=SingleInfo.main();
					s.num=num;					
					s.password=psw;			
					
				}else{
					MessageDialog.openInformation(getParent(), "系统消息","登录失败！");
					System.out.println(321);
				}
				db.close();
			
			}
		});
		button.setText("\u767B\u5F55");
		button.setBounds(78, 257, 80, 27);
		
		button_1 = new Button(shell, SWT.BORDER);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				combo.setText("");
				text_1.setText("");
				button_2.setSelection(false);
			}
		});
		button_1.setText("\u91CD\u7F6E");
		button_1.setBounds(218, 257, 80, 27);
		
		button_2 = new Button(shell, SWT.BORDER);
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
		button_2.setText("\u9000\u51FA");
		button_2.setBounds(361, 257, 80, 27);
		
		lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(182, 76, 30, 20);
		lblNewLabel.setText("\u5DE5\u53F7");
		
		label = new Label(shell, SWT.NONE);
		label.setBounds(182, 140, 30, 20);
		label.setText("\u5BC6\u7801");

	}

}
