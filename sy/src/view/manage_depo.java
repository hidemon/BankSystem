package view;

import info.QuesInfo;

import java.util.HashMap;
import java.util.LinkedList;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import db.DB1;


import dialog.Intereat_Amend;
import dialog.register;

import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.ArmListener;
import org.eclipse.swt.events.ArmEvent;

import shell.Amend_depo;


import util.PageBean;
import org.eclipse.wb.swt.ResourceManager;

public class manage_depo extends ViewPart {

	public static final String ID = "view.manage_depo"; //$NON-NLS-1$
	private Table table;
	int page = 1;
	String str="";
	public manage_depo() {
	}
	
	/*
	 * 
	 * 上一页 ，下一页，查询，跳转按钮事件，设置更新table表格的内容
	*/
	public void getData() {
		String sql = str;
		if(str.equals("")){
			MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "系统消息","请先选择科目和题型  进行查询！");
			return;
		}
		PageBean bean = new PageBean(sql, page);
		page = bean.getPage();
		table.removeAll();
		//label_3.setText(bean.getPage() + "/" + bean.getMaxPage());
		LinkedList<HashMap<String, String>> list = bean.getList();
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, String> map = list.get(i);
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(new String[] { map.get("id"), map.get("subject"),
					map.get("type"), map.get("difficulty"),
					map.get("question").replaceAll("#", "  "), map.get("answer") });
		}
	}
	/*
	 * 
	 * 判等函数
	 * 当试图打开多个editor  窗口时，只允许打开一个
	*/
/*	public boolean equals(Object obj) {
		boolean bEquals = false;
		try {
			if (obj != null && obj instanceof EditorQueryQus) {
				if (getName().equals(((EditorQueryQus) obj).getName())) {
					bEquals = true;
				}  
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bEquals;
	}*/
	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setBackgroundImage(ResourceManager.getPluginImage("sy", "pic/55.jpg"));
		container.setLayout(null);
		
		Button btnNewButton = new Button(container, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				table.removeAll();		
				DB1 db=new DB1();				
				LinkedList<HashMap<String, String>> list = db.queryToList("select * from manager where type = 1");
				for(int i=0; i<list.size(); i++){
				TableItem tableItem = new TableItem(table, SWT.NONE);
				tableItem.setText(0,list.get(i).get("num"));
				tableItem.setText(1, list.get(i).get("password"));
				}
			}
			
		});
		btnNewButton.setBounds(155, 250, 98, 30);
		btnNewButton.setText("\u67E5\u770B\u6240\u6709\u8D26\u6237");
		
		table = new Table(container, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(307, 250, 297, 187);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnid = new TableColumn(table, SWT.NONE);
		tblclmnid.setWidth(152);
		tblclmnid.setText("\u50A8\u84C4\u7BA1\u7406\u5458ID");
		
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(137);
		tableColumn.setText("\u50A8\u84C4\u7BA1\u7406\u5BC6\u7801");
		
		
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
				String id = item.getText(0);
				String password = item.getText(1);
				String type = item.getText(2);
				
				QuesInfo q = new QuesInfo(id, password, type);
				Display display = Display.getDefault();
				
					Amend_depo shell = new Amend_depo(display);
					shell.setStu(q);
					shell.open();
					shell.layout();
					while (!shell.isDisposed()) {
						if (!display.readAndDispatch()) {
							display.sleep();
						}
					}
					
				
				getData();
			}
		});
		menuItem.setText("\u4FEE\u6539");
		
		MenuItem menuItem_1 = new MenuItem(menu, SWT.NONE);
		menuItem_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = table.getSelectionIndex();
				if(index==-1){
					MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "系统消息","您选中的行有问题，请重新选择！！");

					return ;
				}
				TableItem item = table.getItem(index);
				int id = Integer.parseInt(item.getText(0));
				
				DB1 dbd = new DB1();

				String sql = "delete from manager where num=" + id + "";
				int num = dbd.update(sql);
				if (num > 0) {
					table.remove(index);
					MessageDialog.openInformation(PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell(), "系统消息",
							"删除成功！");
				} else {

					MessageDialog.openInformation(PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell(), "系统消息",
							"删除失败！");
				}
				dbd.close();
			}
			
		});
	
		menuItem_1.setText("\u5220\u9664\u6B64\u884C\u4FE1\u606F");
		
		Button button = new Button(container, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Shell shl = manage_depo.this.getViewSite().getShell();
//				Shell shl = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				register register1 = new register(shl,SWT.NONE);
				register1.open();
				
			}
		});
		button.setBounds(155, 395, 98, 30);
		button.setText("\u6CE8\u518C\u65B0\u8D26\u6237");

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
