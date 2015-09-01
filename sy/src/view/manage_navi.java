package view;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.wb.swt.ResourceManager;

public class manage_navi extends ViewPart {

	public static final String ID = "view.manage_navi"; //$NON-NLS-1$

	public manage_navi() {
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setBackgroundImage(ResourceManager.getPluginImage("sy", "pic/5f7731dbb6fd526642796c73ab18972bd5073647.jpg"));
		
		Button button = new Button(container, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IWorkbenchPage page = getSite().getPage();
				try {
					page.showView(manage_depo.ID);
				} catch (PartInitException e1) {
					e1.printStackTrace();
				}
				page.hideView(page.findView(Welcome.ID));
			
			}
		});
		button.setBounds(104, 161, 250, 88);
		button.setText("\u50A8\u84C4\u7BA1\u7406\u5458");
		
		Button button_1 = new Button(container, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IWorkbenchPage page = getSite().getPage();
				try {
					page.showView(Manage_Loan.ID);
				} catch (PartInitException e1) {
					e1.printStackTrace();
				}
				page.hideView(page.findView(Welcome.ID));
			}
		});
		button_1.setBounds(104, 247, 250, 94);
		button_1.setText("\u8D37\u6B3E\u7BA1\u7406\u5458");
		
		Button btnNewButton = new Button(container, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IWorkbenchPage page = getSite().getPage();
				try {
					page.showView(Manage_credit.ID);
				} catch (PartInitException e1) {
					e1.printStackTrace();
				}
				page.hideView(page.findView(Welcome.ID));
			}
		});
		btnNewButton.setBounds(104, 340, 250, 88);
		btnNewButton.setText("\u4FE1\u7528\u5361\u7BA1\u7406\u5458");

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
