package view;

import info.CreditInfo;
import info.QuesInfo;
import info.RepaycreditInfo;

import java.util.HashMap;
import java.util.LinkedList;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import db.DB1;
import dialog.Intereat_Amend;
import dialog.Intereat_Amend2;
import dialog.Intereat_Amend3;
import dialog.Intereat_Amend4;
import dialog.New_CreditCard;
import dialog.New_deposit;
import dialog.New_loan;
import dialog.Newdepositer;
import dialog.Success;

import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import shell.Amend_depo;
import shell.Comsumption;
import shell.Prelend;
import shell.Repay_credit;
import shell.Repay_loan;
import shell.Withdraw_depo;
public class CreditCard extends ViewPart {

	public static final String ID = "view.CreditCard"; //$NON-NLS-1$
	private Text txtView;
	private Table table;
	private Text text_1;
	private Table table_1;
	private Composite container;

	public CreditCard() {
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		
		parent.setBackgroundImage(ResourceManager.getPluginImage("sy", "pic/509e413c50851.jpg"));
		container = new Composite(parent, SWT.NONE);
		container.setBackground(SWTResourceManager.getColor(245, 222, 179));
		container.setBackgroundMode(SWT.INHERIT_DEFAULT);
		container.setBackgroundImage(ResourceManager.getPluginImage("sy", "pic/d788d43f8794a4c2736d101c0ff41bd5ad6e39be.jpg"));
		
		DB1 db=new DB1();
		LinkedList<HashMap<String, String>> list = db.queryToList("select * from interest_rate where interestid = 4");		
		txtView = new Text(container, SWT.BORDER);
		txtView.setText(list.get(0).get("value"));
		txtView.setBounds(233, 52, 73, 26);
		LinkedList<HashMap<String, String>> list1 = db.queryToList("select * from interest_rate where interestid = 5");
		LinkedList<HashMap<String, String>> list2 = db.queryToList("select * from interest_rate where interestid = 6");
		
		Button button_1 = new Button(container, SWT.BORDER);
		button_1.setImage(ResourceManager.getPluginImage("sy", "pic/document-edit.png"));
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				Shell shl = CreditCard.this.getViewSite().getShell();
				Intereat_Amend4 amend = new Intereat_Amend4(shl,SWT.NONE);
				amend.open();
			}
		});
		button_1.setBounds(137, 99, 169, 86);
		button_1.setText("\u4FEE\u6539\u5229\u7387");
		
		table = new Table(container, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(10, 285, 596, 156);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(100);
		tableColumn.setText("\u4FE1\u7528\u5361\u53F7");
		
		TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setText("\u7528\u6237\u8EAB\u4EFD");
		tableColumn_1.setWidth(112);
		
		TableColumn tableColumn_2 = new TableColumn(table, SWT.NONE);
		tableColumn_2.setWidth(100);
		tableColumn_2.setText("\u989D\u5EA6");
		
		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setWidth(100);
		tblclmnNewColumn_1.setText("\u53EF\u4F7F\u7528\u91D1\u989D");
		
		TableColumn tableColumn_7 = new TableColumn(table, SWT.NONE);
		tableColumn_7.setWidth(80);
		tableColumn_7.setText("\u5229\u7387");
		
		TableColumn tableColumn_8 = new TableColumn(table, SWT.NONE);
		tableColumn_8.setWidth(100);
		tableColumn_8.setText("\u514D\u606F\u65F6\u671F");
		
		Menu menu = new Menu(table);
		table.setMenu(menu);
		
		MenuItem menuItem = new MenuItem(menu, SWT.NONE);
		menuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = table.getSelectionIndex();// 获取选中行的索引
				if(index==-1){
					MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "系统消息","您选中的行有问题，请重新选择！！");
					return;
				}
				TableItem item = table.getItem(index);// 获取选中行的第一列的值
				String idcredit = item.getText(0);
				String user_id = item.getText(1);
				String ava = item.getText(3);
				
				CreditInfo q = new CreditInfo(idcredit, user_id, ava);
				Display display = Display.getDefault();
				
					Comsumption shell = new Comsumption(display);
					shell.setStu(q);
					shell.open();
					shell.layout();
					while (!shell.isDisposed()) {
						if (!display.readAndDispatch()) {
							display.sleep();
						}
					}
					
				
				//getData();
			}
		});
		menuItem.setText("\u5237\u5361\u6D88\u8D39");
		
		MenuItem mntmNewItem = new MenuItem(menu, SWT.NONE);
		mntmNewItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = table.getSelectionIndex();// 获取选中行的索引
				if(index==-1){
					MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "系统消息","您选中的行有问题，请重新选择！！");
					return;
				}
				TableItem item = table.getItem(index);// 获取选中行的第一列的值
				String idcredit = item.getText(0);
				String user_id = item.getText(1);
				String ava = item.getText(3);
				
				CreditInfo q = new CreditInfo(idcredit, user_id, ava);
				Display display = Display.getDefault();
				
					Prelend shell = new Prelend(display);
					shell.setStu(q);
					shell.open();
					shell.layout();
					while (!shell.isDisposed()) {
						if (!display.readAndDispatch()) {
							display.sleep();
						}
					}
			}
		});
		mntmNewItem.setText("\u9884\u501F\u73B0\u91D1");
			
		
		
		Button button_3 = new Button(container, SWT.NONE);
		button_3.setImage(ResourceManager.getPluginImage("sy", "pic/tag_yellow_add.png"));
		button_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Shell shl = CreditCard.this.getViewSite().getShell();
				New_CreditCard newcreditcard = new New_CreditCard(shl,SWT.NONE);
				newcreditcard.open();
				if(newcreditcard.judgesuccess > 0){
			      Success success= new Success(shl,SWT.NONE);
				}
				}
		});
		button_3.setBounds(441, 459, 159, 30);
		button_3.setText("\u65B0\u7684\u4FE1\u7528\u5361");
		
		Button btnNewButton_1 = new Button(container, SWT.NONE);
		btnNewButton_1.setImage(ResourceManager.getPluginImage("sy", "pic/tag_yellow_add.png"));
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Shell shl = CreditCard.this.getViewSite().getShell();
				Newdepositer newdepositer = new Newdepositer(shl,SWT.NONE);
				newdepositer.open();
				if(newdepositer.judgesuccess > 0){
			      Success success= new Success(shl,SWT.NONE);
				}
				
			}
		});
		btnNewButton_1.setBounds(990, 459, 159, 30);
		btnNewButton_1.setText("\u65B0\u4FE1\u7528\u5361\u7528\u6237");
		
		table_1 = new Table(container, SWT.BORDER | SWT.FULL_SELECTION);
		table_1.setBounds(640, 285, 512, 156);
		table_1.setHeaderVisible(true);
		table_1.setLinesVisible(true);
		
		TableColumn tableColumn_6 = new TableColumn(table_1, SWT.NONE);
		tableColumn_6.setWidth(100);
		tableColumn_6.setText("\u8BB0\u5F55\u53F7");
		
		TableColumn tableColumn_3 = new TableColumn(table_1, SWT.NONE);
		tableColumn_3.setWidth(100);
		tableColumn_3.setText("\u4FE1\u7528\u5361\u53F7");
		
		TableColumn tblclmnNewColumn = new TableColumn(table_1, SWT.NONE);
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText("\u4E8B\u4EF6");
		
		TableColumn tableColumn_4 = new TableColumn(table_1, SWT.NONE);
		tableColumn_4.setWidth(100);
		tableColumn_4.setText("\u65E5\u671F");
		
		TableColumn tableColumn_5 = new TableColumn(table_1, SWT.NONE);
		tableColumn_5.setWidth(100);
		tableColumn_5.setText("\u91D1\u989D");
		
		Menu menu_1 = new Menu(table_1);
		table_1.setMenu(menu_1);
		
		MenuItem menuItem_1 = new MenuItem(menu_1, SWT.NONE);
		menuItem_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = table_1.getSelectionIndex();// 获取选中行的索引
				if(index==-1){
					MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "系统消息","您选中的行有问题，请重新选择！！");
					return;
				}
				TableItem item = table_1.getItem(index);// 获取选中行的第一列的值
				String idrecord = item.getText(0);
				String idcredit = item.getText(1);
				String type = item.getText(2);
				String date = item.getText(3);
				String volume = item.getText(4);
				if(type.equals("4")){
					MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "系统消息","该条信用卡消费已经还上！");
					return;
				}
				if(type.equals("3")){
					MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "系统消息","该条是信用卡还款动作信息！");
					return;
				}	
				RepaycreditInfo q = new RepaycreditInfo(idrecord,idcredit,type,date,volume);
				Display display = Display.getDefault();
				
					Repay_credit shell = new Repay_credit(display);
					shell.setStu(q);
					shell.open();
					shell.layout();
					while (!shell.isDisposed()) {
						if (!display.readAndDispatch()) {
							display.sleep();
						}
					}
				
			}
		});
		menuItem_1.setText("\u8FD8\u6B3E");
		
		Button button = new Button(container, SWT.NONE);
		button.setBackground(SWTResourceManager.getColor(176, 196, 222));
		button.setImage(ResourceManager.getPluginImage("sy", "pic/right.png"));
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			table.removeAll();	
			table_1.removeAll();
			String cx=text_1.getText().trim();
			
			DB1 db=new DB1();
			LinkedList<HashMap<String, String>> list = db.queryToList("select * from creditcard where idCreditCard = '"+cx+"'");
			for(int i=0; i<list.size(); i++){
			TableItem tableItem = new TableItem(table, SWT.NONE);
			tableItem.setText(0,list.get(i).get("idCreditCard"));
			tableItem.setText(1, list.get(i).get("user_id"));
			tableItem.setText(2, list.get(i).get("volume"));
			tableItem.setText(3, list.get(i).get("available_money"));
			tableItem.setText(4, list.get(i).get("interest"));
			tableItem.setText(5, list.get(i).get("interestfree_period"));
			}
			
			
			LinkedList<HashMap<String, String>> list1 = db.queryToList("select * from credit_record where idcredit_card = '"+cx+"'");
			for(int i=0; i<list1.size(); i++){
			TableItem tableItem = new TableItem(table_1, SWT.NONE);
			tableItem.setText(0,list1.get(i).get("idrecord"));
			tableItem.setText(1,list1.get(i).get("idcredit_card"));			
			tableItem.setText(2, list1.get(i).get("consumption_t"));			
			tableItem.setText(3, list1.get(i).get("date"));
			tableItem.setText(4, list1.get(i).get("volume"));	
			}
			db.close();
			}
		});
		button.setBounds(465, 209, 56, 36);
		
		Label label_2 = new Label(container, SWT.NONE);
		label_2.setBounds(122, 217, 108, 20);
		label_2.setText("\u67E5\u8BE2\u4FE1\u7528\u5361\u53F7\uFF1A");
		
		Label label = new Label(container, SWT.NONE);
		label.setBounds(82, 55, 123, 20);
		label.setText("\u4FE1\u7528\u5361\u8FD8\u8D37\u5229\u7387");
		text_1 = new Text(container, SWT.BORDER);
		text_1.setBounds(236, 214, 159, 26);
		
		Label label_4 = new Label(container, SWT.NONE);
		label_4.setBounds(640, 459, 140, 20);
		label_4.setText("\u4E8B\u4EF61\uFF1A\u5237\u5361\u6D88\u8D39");
		
		Label label_5 = new Label(container, SWT.NONE);
		label_5.setBounds(639, 484, 113, 20);
		label_5.setText("\u4E8B\u4EF62\uFF1A\u9884\u501F\u73B0\u91D1");
		
		Label label_6 = new Label(container, SWT.NONE);
		label_6.setBounds(640, 506, 111, 20);
		label_6.setText("\u4E8B\u4EF63\uFF1A\u8FD8\u6B3E");
		
		Label label_7 = new Label(container, SWT.NONE);
		label_7.setBounds(639, 526, 154, 20);
		label_7.setText("\u4E8B\u4EF64\uFF1A\u5DF2\u8FD8\u4E0A\u7684\u6B3E\u9879");

		createActions();
		initializeToolBar();
		initializeMenu();
	}

	

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars()
				.getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		IMenuManager menuManager = getViewSite().getActionBars()
				.getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}
}
