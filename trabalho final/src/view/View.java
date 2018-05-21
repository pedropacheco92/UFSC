package view;

public interface View<PRESENTER, FORM> {

	void paint();

	void fillIn(FORM form);

	FORM fillOut();

	void setPresenter(PRESENTER presenter);

}
