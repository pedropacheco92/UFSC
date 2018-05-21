package t1.controller;

import t1.model.Model;
import t1.view.View;

public interface Controller {

	public <VIEW extends View> void setView(VIEW view);

	public <MODEL extends Model> void setModel(MODEL model);

	void init();

}
