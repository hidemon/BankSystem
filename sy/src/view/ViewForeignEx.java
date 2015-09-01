package view;

import info.QuesInfo;

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
import dialog.New_deposit;
import dialog.Newdepositer;
import dialog.Success;

import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import shell.Amend_depo;
import shell.Withdraw_depo;
public class ViewForeignEx extends ViewPart {

	public static final String ID = "view.view1"; //$NON-NLS-1$
	private Text txtView;
	private Text text;
	private Table table;
	private Text text_1;
	private Table table_1;

	public ViewForeignEx() {
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		parent.setBackgroundImage(ResourceManager.getPluginImage("sy", "pic/509e413c50851.jpg"));
		Composite container = new Composite(parent, SWT.NONE);
		container.setBackgroundImage(ResourceManager.getPluginImage("sy", "pic/d788d43f8794a4c2736d101c0ff41bd5ad6e39be.jpg"));
		container.setBackgroundMode(SWT.INHERIT_DEFAULT);
		DB1 db=new DB1();
		LinkedList<HashMap<String, String>> list = db.queryToList("select * from interest_rate where interestid = 1");
		
		Label label = new Label(container, SWT.SHADOW_NONE);
		label.setDragDetect(false);
		label.setEnabled(false);
		label.setBackground(SWTResourceManager.getColor(255, 215, 0));
		label.setForeground(SWTResourceManager.getColor(SWT.COLOR_TITLE_FOREGROUND));
		label.setBounds(22, 85, 94, 20);
		label.setText("\u6D3B\u671F\u5B58\u6B3E\u5229\u7387");
		
		txtView = new Text(container, SWT.BORDER);
		txtView.setText(list.get(0).get("value"));
		txtView.setBounds(122, 82, 73, 26);
		
		Label label_1 = new Label(container, SWT.NONE);
		label_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_1.setBounds(22, 153, 94, 20);
		label_1.setText("\u5B9A\u671F\u5B58\u6B3E\u5229\u7387");
		LinkedList<HashMap<String, String>> list1 = db.queryToList("select * from interest_rate where interestid = 2");
		text = new Text(container, SWT.BORDER);
		text.setText(list1.get(0).get("value"));
		text.setBounds(122, 153, 73, 26);
		
		Button button_1 = new Button(container, SWT.BORDER);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
//				Display display = PlatformUI.createDisplay();
				Shell shl = ViewForeignEx.this.getViewSite().getShell();
//				Shell shl = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				Intereat_Amend amend = new Intereat_Amend(shl,SWT.NONE);
				amend.open();
			}
		});
		button_1.setBounds(268, 82, 98, 30);
		button_1.setText("\u4FEE\u6539\u5229\u7387");
		
		Button button_2 = new Button(container, SWT.BORDER);
		button_2.setBounds(268, 153, 98, 30);
		button_2.setText("\u4FEE\u6539\u5229\u7387");
		
		table = new Table(container, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(54, 285, 496, 156);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(100);
		tableColumn.setText("\u50A8\u84C4\u53F7");
		
		TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setText("\u50A8\u6237\u8EAB\u4EFD\u8BC1\u53F7");
		tableColumn_1.setWidth(112);
		
		TableColumn tableColumn_2 = new TableColumn(table, SWT.NONE);
		tableColumn_2.setWidth(100);
		tableColumn_2.setText("\u65E5\u671F");
		
		TableColumn tableColumn_7 = new TableColumn(table, SWT.NONE);
		tableColumn_7.setWidth(80);
		tableColumn_7.setText("\u91D1\u989D");
		
		TableColumn tableColumn_8 = new TableColumn(table, SWT.NONE);
		tableColumn_8.setWidth(100);
		tableColumn_8.setText("\u7C7B\u578B");
		
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
				String iddeposit = item.getText(0);
				String depositer_id = item.getText(1);
				String type = item.getText(2);
				
				QuesInfo q = new QuesInfo(iddeposit, depositer_id, type);
				Display display = Display.getDefault();
				
					Withdraw_depo shell = new Withdraw_depo(display);
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
		menuItem.setText("\u7528\u6237\u53D6\u6B3E");
			
		text_1 = new Text(container, SWT.BORDER);
		text_1.setBounds(236, 214, 159, 26);
		
		Button button = new Button(container, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			table.removeAll();	
			String cx=text_1.getText().trim();
			
			DB1 db=new DB1();
			LinkedList<HashMap<String, String>> list = db.queryToList("select * from deposit where depositer_id = '"+cx+"'");
			for(int i=0; i<list.size(); i++){
			TableItem tableItem = new TableItem(table, SWT.NONE);
			tableItem.setText(0,list.get(i).get("iddeposit"));
			tableItem.setText(1, list.get(i).get("depositer_id"));
			tableItem.setText(2, list.get(i).get("date"));
			tableItem.setText(3, list.get(i).get("volume"));
			tableItem.setText(4, list.get(i).get("type"));
			}
			
			
			LinkedList<HashMap<String, String>> list1 = db.queryToList("select * from depositer where iddepositer = '"+cx+"'");
			for(int i=0; i<list1.size(); i++){
			TableItem tableItem = new TableItem(table_1, SWT.NONE);
			tableItem.setText(0,list1.get(i).get("iddepositer"));
			tableItem.setText(1, list1.get(i).get("first_name"));
			tableItem.setText(2, list1.get(i).get("last_name"));
			tableItem.setText(3, list1.get(i).get("address"));
			tableItem.setText(4, list1.get(i).get("phone"));
			
			db.close();
			}
			
			}
		});
		button.setBounds(452, 212, 98, 30);
		button.setText("\u786E\u5B9A");
		
		Button button_3 = new Button(container, SWT.NONE);
		button_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Shell shl = ViewForeignEx.this.getViewSite().getShell();
				New_deposit newdepositer = new New_deposit(shl,SWT.NONE);
				newdepositer.open();
				if(newdepositer.judgesuccess > 0){
			      Success success= new Success(shl,SWT.NONE);
				}
				}
		});
		button_3.setBounds(452, 459, 98, 30);
		button_3.setText("\u65B0\u7684\u50A8\u84C4\u8D26\u6237");
		
		Button btnNewButton_1 = new Button(container, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Shell shl = ViewForeignEx.this.getViewSite().getShell();
				Newdepositer newdepositer = new Newdepositer(shl,SWT.NONE);
				newdepositer.open();
				if(newdepositer.judgesuccess > 0){
			      Success success= new Success(shl,SWT.NONE);
				}
				
			}
		});
		btnNewButton_1.setBounds(976, 459, 98, 30);
		btnNewButton_1.setText("\u65B0\u50A8\u6237");
		
		table_1 = new Table(container, SWT.BORDER | SWT.FULL_SELECTION);
		table_1.setBounds(569, 285, 505, 156);
		table_1.setHeaderVisible(true);
		table_1.setLinesVisible(true);
		
		TableColumn tableColumn_3 = new TableColumn(table_1, SWT.NONE);
		tableColumn_3.setWidth(100);
		tableColumn_3.setText("\u8EAB\u4EFD\u8BC1\u53F7");
		
		TableColumn tblclmnNewColumn = new TableColumn(table_1, SWT.NONE);
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText("\u540D");
		
		TableColumn tableColumn_4 = new TableColumn(table_1, SWT.NONE);
		tableColumn_4.setWidth(100);
		tableColumn_4.setText("\u59D3");
		
		TableColumn tableColumn_5 = new TableColumn(table_1, SWT.NONE);
		tableColumn_5.setWidth(100);
		tableColumn_5.setText("\u5730\u5740");
		
		TableColumn tableColumn_6 = new TableColumn(table_1, SWT.NONE);
		tableColumn_6.setWidth(100);
		tableColumn_6.setText("\u7535\u8BDD");
		
		Label label_2 = new Label(container, SWT.NONE);
		label_2.setBackground(SWTResourceManager.getColor(255, 215, 0));
		label_2.setBounds(119, 220, 111, 20);
		label_2.setText("\u67E5\u8BE2\u50A8\u6237\u4FE1\u606F\uFF1A");

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
