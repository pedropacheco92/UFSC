package view;

import javax.swing.JMenu;

import helper.Messages;
import listeners.LocaleChangeListener;

public class JMenu2 extends JMenu implements LocaleChangeListener {

	private static final long serialVersionUID = 6853549458278745293L;

	private String msg;

	public JMenu2(String msg) {
		super(Messages.getString(msg));
		this.msg = msg;
	}

	@Override
	public void onLocaleChange() {
		super.setText(Messages.getString(this.msg));
	}

}
