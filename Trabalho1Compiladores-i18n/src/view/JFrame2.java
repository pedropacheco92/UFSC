package view;

import javax.swing.JFrame;

import helper.Messages;
import listeners.LocaleChangeListener;

public class JFrame2 extends JFrame implements LocaleChangeListener {

	private static final long serialVersionUID = 4523607460573024995L;

	private String msg;

	@Override
	public void setTitle(String title) {
		super.setTitle(Messages.getString(title));
		this.msg = title;
	}

	@Override
	public void onLocaleChange() {
		super.setTitle(Messages.getString(this.msg));
	}

}
