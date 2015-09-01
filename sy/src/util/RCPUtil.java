package util;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;


/*
 * 
 *  ªÒ»°getActivePage();
 *  
 *  */

public class RCPUtil {
	public static IWorkbenchPage getPage(){				//
	       return	PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

	}
}
