package t1.controller;

import t1.model.Model;
import t1.view.View;

public abstract class AbstractController<MODEL extends Model, VIEW extends View> implements Controller {

	protected VIEW view;

	protected MODEL model;

	@Override
	public void init() {
		this.view.createScreen();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setView(View view) {
		this.view = (VIEW) view;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setModel(Model model) {
		this.model = (MODEL) model;
	}

}
