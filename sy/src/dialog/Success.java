package dialog;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class Success extends Dialog {

	protected Object result;
	protected Shell shlSuccess;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public Success(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlSuccess.open();
		shlSuccess.layout();
		Display display = getParent().getDisplay();
		while (!shlSuccess.isDisposed()) {
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
		shlSuccess = new Shell(getParent(), getStyle());
		shlSuccess.setSize(345, 212);
		shlSuccess.setText("Success!");
		
		Label label = new Label(shlSuccess, SWT.NONE);
		label.setBounds(123, 68, 76, 20);
		label.setText("\u64CD\u4F5C\u6210\u529F\uFF01\uFF01\uFF01\uFF01");
		
		Button button = new Button(shlSuccess, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			shlSuccess.close();
			}
		});
		button.setBounds(112, 122, 98, 30);
		button.setText("\u5173\u95ED\u7A97\u53E3");

	}

}
