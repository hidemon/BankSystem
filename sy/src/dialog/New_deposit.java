package dialog;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JOptionPane;

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

public class New_deposit extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text text_1;
	private Text text_2;
	private Text text_3;
	private Combo combo;
    public  int judgesuccess;
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public New_deposit(Shell parent, int style) {
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
		
		Combo combo = new Combo(shell, SWT.NONE);
		combo.setItems(new String[] {"\u6D3B\u671F\u5B58\u6B3E", "\u4E94\u5E74\u5B9A\u671F\u5B58\u6B3E", "\u4E00\u5E74\u5B9A\u671F\u5B58\u6B3E", "\u5B9A\u6D3B\u4E24\u4FBF\u5B58\u6B3E"});
		combo.setBounds(232, 248, 142, 28);
		//获取日期
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateformatter=formatter.format(new Date(System.currentTimeMillis()));
		//JOptionPane.showConfirmDialog(null, dateformatter,"提示:", JOptionPane.YES_NO_OPTION);
		
		Button button = new Button(shell, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			DB1 db=new DB1();
			//储蓄类型
            String vx4=combo.getText().trim();				
			if(vx4.equals("")){
				MessageDialog.openInformation((Shell) getParent(), "5系统消息","输入账号为空！");
				return;
			}			
			
			int t;   //t用来指明何种存款类型
			if(vx4.equals("活期存款"))
				t=1;
			else if(vx4.equals("五年定期存款")){
				t=2;
			}
			else if(vx4.equals("一年定期存款")){
				t=3;
			}else{
				t=4;
			}
			
			LinkedList<HashMap<String, String>> list2 = db.queryToList("select * from id where id_id = 1");		
			int idnum = Integer.parseInt(list2.get(0).get("value"));
			
			idnum++;
			db.update("update id set value="+idnum+" where id_id =1");
	         //储蓄号
			
			
			//储户身份ID
			String vx1=text_1.getText().trim();			
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
				JOptionPane.showConfirmDialog(null, "请先创建储户信息！！","新的储户", JOptionPane.YES_NO_OPTION);
				db.close();
				shell.close();
			};				       				
		    
			//金额大小
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
			double vx1_1=Double.parseDouble(vx3);    //vx1_1用double类型保存储蓄金额，检查是否满足要求
			System.out.println(vx1_1);
			JOptionPane.showConfirmDialog(null, vx1_1,"新的储户", JOptionPane.YES_NO_OPTION);
			if(t==1){
				if(vx1_1<1)
				{MessageDialog.openInformation((Shell) getParent(), "2系统消息","存款金额不得小于1！");
				return;}
			}
			if(t==2|t==3){
				if(vx1_1<100)
				{MessageDialog.openInformation((Shell) getParent(), "2系统消息","存款金额不得小于100！");
				return;}
			}
			if(t==4){
				if(vx1_1<50)
				{MessageDialog.openInformation((Shell) getParent(), "2系统消息","存款金额不得小于50！");
				return;}
			}
			
			LinkedList<HashMap<String, String>> list1 = db.queryToList("select * from interest_rate where interestid = 2");
			String fixed_in = list1.get(0).get("value"); 
			JOptionPane.showConfirmDialog(null, fixed_in,"成功创建新的储蓄！", JOptionPane.YES_NO_OPTION);
			judgesuccess = db.update("insert into deposit(iddeposit,depositer_id,date,volume,type,fixed_deposit_interest) values("+idnum+",'"+vx1+"','"+dateformatter+"',"+vx3+","+t+","+fixed_in+")");
			db.close();
			JOptionPane.showConfirmDialog(null, "成功创建新的储蓄！身份号："+vx1+"  储蓄金额：" + vx3,"成功创建新的储蓄！", JOptionPane.YES_NO_OPTION);
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
		
		text_1 = new Text(shell, SWT.BORDER);
		text_1.setBounds(232, 66, 142, 26);
		
		text_2 = new Text(shell, SWT.BORDER);
		text_2.setBounds(232, 127, 142, 26);
		text_2.setText(dateformatter);	
		
		text_3 = new Text(shell, SWT.BORDER);
		text_3.setBounds(232, 181, 142, 26);
		
		Label label = new Label(shell, SWT.NONE);
		label.setBounds(126, 256, 76, 20);
		label.setText("\u50A8\u84C4\u7C7B\u578B\uFF1A");
		
		Label label_3 = new Label(shell, SWT.NONE);
		label_3.setBounds(130, 185, 76, 20);
		label_3.setText("\u5B58\u6B3E\u989D\uFF1A");
		
		Label label_2 = new Label(shell, SWT.NONE);
		label_2.setBounds(93, 131, 111, 20);
		label_2.setText("\u5B58\u6B3E\u66F4\u65B0\u65F6\u95F4\uFF1A");
		
		Label lblid = new Label(shell, SWT.NONE);
		lblid.setBounds(116, 71, 76, 20);
		lblid.setText("\u50A8\u84C4\u6237ID:");
		
		

	}
}
