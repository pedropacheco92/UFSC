package view;

import javax.swing.JMenuItem;

import helper.Messages;
import listeners.LocaleChangeListener;

public class JMenuItem2 extends JMenuItem implements LocaleChangeListener {

	private static final long serialVersionUID = 4113068599116857933L;

	private String msg;

	public JMenuItem2(String msg) {
		super(Messages.getString(msg));
		this.msg = msg;
	}

	@Override
	public void onLocaleChange() {
		super.setText(Messages.getString(this.msg));
	}

}
