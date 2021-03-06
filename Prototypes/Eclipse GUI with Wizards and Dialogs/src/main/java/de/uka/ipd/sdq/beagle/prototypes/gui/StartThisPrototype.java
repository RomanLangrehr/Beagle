package de.uka.ipd.sdq.beagle.prototypes.gui;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * The main method launching this prototype.
 * 
 * @author Christoph Michelbach
 */
public final class StartThisPrototype {

	/**
	 * Private constructor preventing instantiation.
	 */
	private StartThisPrototype() {
	}

	/**
	 * The main method launching this prototype.
	 * 
	 * @param args Parameters will not be considered.
	 */
	public static void main(final String[] args) {
		final Shell shell = new Shell();
		final Wizard myWizard = new BeagleAnalysisWizard();
		// opens the instance of the wizard
		final WizardDialog wizardDialog = new WizardDialog(shell, myWizard);
		wizardDialog.open();
	}
}
