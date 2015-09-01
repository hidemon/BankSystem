package sy;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import single.SingleInfo;
import view.view1;

public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		if(SingleInfo.main().num.startsWith("1")){
		layout.addView("view.view1", IPageLayout.LEFT, 1f, IPageLayout.ID_EDITOR_AREA);
		layout.getViewLayout(view.view1.ID).setCloseable(false);
		layout.getViewLayout(view.view1.ID).setMoveable(false);
		}
		else if(SingleInfo.main().num.startsWith("3")){
		layout.addView("view.ViewLoan", IPageLayout.LEFT, 1f, IPageLayout.ID_EDITOR_AREA);
		layout.getViewLayout(view.ViewLoan.ID).setCloseable(false);
		layout.getViewLayout(view.ViewLoan.ID).setMoveable(false);
		}
		else if(SingleInfo.main().num.startsWith("2")){
			layout.addView("view.CreditCard", IPageLayout.LEFT, 1f, IPageLayout.ID_EDITOR_AREA);
			layout.getViewLayout(view.CreditCard.ID).setCloseable(false);
			layout.getViewLayout(view.CreditCard.ID).setMoveable(false);
			}
		else if(SingleInfo.main().num.startsWith("4")){
			layout.addView("view.manage_navi", IPageLayout.LEFT, 0.25f, IPageLayout.ID_EDITOR_AREA);
			layout.addView("view.Welcome", IPageLayout.LEFT, 1f, IPageLayout.ID_EDITOR_AREA);
			layout.getViewLayout(view.manage_navi.ID).setCloseable(false);
			layout.getViewLayout(view.manage_navi.ID).setMoveable(false);
			layout.getViewLayout(view.Welcome.ID).setCloseable(false);
			layout.getViewLayout(view.Welcome.ID).setMoveable(false);
			}
		else{
			layout.addView("view.view1", IPageLayout.RIGHT, 1f, IPageLayout.ID_EDITOR_AREA);
			layout.getViewLayout(view.view1.ID).setCloseable(false);
			layout.getViewLayout(view.view1.ID).setMoveable(false);
			}
	}
}
