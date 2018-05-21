package t1.view;

import javax.swing.JOptionPane;

import t1.controller.Controller;

public abstract class AbstractView<CONTROLLER extends Controller> implements View {

	protected CONTROLLER controller;

	public AbstractView(CONTROLLER controller) {
		this.controller = controller;
	}

	@Override
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

}
