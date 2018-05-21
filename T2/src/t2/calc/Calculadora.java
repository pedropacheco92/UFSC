package t2.calc;

import java.awt.Font;
import java.beans.Customizer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Calculadora extends JPanel implements Customizer {

	private static final long serialVersionUID = 1L;

	private Object bean;

	private Font fonte;

	private JTextField textField;

	private StringBuilder valorAtual;

	private Double valor;

	private boolean calcPanelVisible;

	private Operacao operacao;

	private JButton btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_0, btn_dividir, btn_multiplicar,
			btn_subtrair, btn_decimal, btn_igual, btn_somar, btnC;

	public Calculadora() {
		this.render();
	}

	private void render() {
		this.valorAtual = new StringBuilder();
		this.setLayout(null);
		this.textField = new JTextField();
		this.textField.setHorizontalAlignment(SwingConstants.RIGHT);
		this.textField.setBounds(55, 3, 239, 49);
		this.textField.setText("0");
		this.textField.setEnabled(false);
		this.add(this.textField);
		this.textField.setColumns(10);

		this.btn_7 = new JButton("7");
		this.btn_7.addActionListener(l -> this.addNumber(this.btn_7.getText()));
		this.btn_7.setBounds(2, 55, 71, 36);
		this.add(this.btn_7);

		this.btn_8 = new JButton("8");
		this.btn_8.addActionListener(l -> this.addNumber(this.btn_8.getText()));
		this.btn_8.setBounds(75, 55, 71, 36);
		this.add(this.btn_8);

		this.btn_9 = new JButton("9");
		this.btn_9.addActionListener(l -> this.addNumber(this.btn_9.getText()));
		this.btn_9.setBounds(148, 55, 71, 36);
		this.add(this.btn_9);

		this.btn_dividir = new JButton(Operacao.DIVIDIR.getSimbolo());
		this.btn_dividir.addActionListener(l -> this.setOperacao(Operacao.DIVIDIR));
		this.btn_dividir.setBounds(221, 55, 71, 36);
		this.add(this.btn_dividir);

		this.btn_4 = new JButton("4");
		this.btn_4.addActionListener(l -> this.addNumber(this.btn_4.getText()));
		this.btn_4.setBounds(2, 94, 71, 36);
		this.add(this.btn_4);

		this.btn_5 = new JButton("5");
		this.btn_5.addActionListener(l -> this.addNumber(this.btn_5.getText()));
		this.btn_5.setBounds(75, 94, 71, 36);
		this.add(this.btn_5);

		this.btn_6 = new JButton("6");
		this.btn_6.addActionListener(l -> this.addNumber(this.btn_6.getText()));
		this.btn_6.setBounds(148, 94, 71, 36);
		this.add(this.btn_6);

		this.btn_multiplicar = new JButton(Operacao.MULTIPLICAR.getSimbolo());
		this.btn_multiplicar.addActionListener(l -> this.setOperacao(Operacao.MULTIPLICAR));
		this.btn_multiplicar.setBounds(221, 94, 71, 36);
		this.add(this.btn_multiplicar);

		this.btn_1 = new JButton("1");
		this.btn_1.addActionListener(l -> this.addNumber(this.btn_1.getText()));
		this.btn_1.setBounds(2, 133, 71, 36);
		this.add(this.btn_1);

		this.btn_2 = new JButton("2");
		this.btn_2.addActionListener(l -> this.addNumber(this.btn_2.getText()));
		this.btn_2.setBounds(75, 133, 71, 36);
		this.add(this.btn_2);

		this.btn_3 = new JButton("3");
		this.btn_3.addActionListener(l -> this.addNumber(this.btn_3.getText()));
		this.btn_3.setBounds(148, 133, 71, 36);
		this.add(this.btn_3);

		this.btn_subtrair = new JButton(Operacao.SUBTRAIR.getSimbolo());
		this.btn_subtrair.addActionListener(l -> this.setOperacao(Operacao.SUBTRAIR));
		this.btn_subtrair.setBounds(221, 133, 71, 36);
		this.add(this.btn_subtrair);

		this.btn_0 = new JButton("0");
		this.btn_0.addActionListener(l -> this.addNumber(this.btn_0.getText()));
		this.btn_0.setBounds(2, 172, 71, 36);
		this.add(this.btn_0);

		this.btn_decimal = new JButton(".");
		this.btn_decimal.addActionListener(l -> this.addSeparador());
		this.btn_decimal.setBounds(75, 172, 71, 36);
		this.add(this.btn_decimal);

		this.btn_igual = new JButton(Operacao.IGUAL.getSimbolo());
		this.btn_igual.addActionListener(l -> this.doMath());
		this.btn_igual.setBounds(148, 172, 71, 36);
		this.add(this.btn_igual);

		this.btn_somar = new JButton(Operacao.SOMAR.getSimbolo());
		this.btn_somar.addActionListener(l -> this.setOperacao(Operacao.SOMAR));
		this.btn_somar.setBounds(221, 172, 71, 36);
		this.add(this.btn_somar);

		this.btnC = new JButton("C");
		this.btnC.addActionListener(e -> {
			this.textField.setText("0");
			this.valorAtual.setLength(0);
		});
		this.btnC.setBounds(2, 3, 51, 49);
		this.add(this.btnC);
	}

	private void addSeparador() {
		this.valorAtual.append(".");
		this.textField.setText(this.valorAtual.toString());
	}

	private void setOperacao(Operacao operacao) {
		this.operacao = operacao;
		this.valor = Double.valueOf(this.valorAtual.toString());
		this.valorAtual.setLength(0);
	}

	private void addNumber(String text) {
		this.valorAtual.append(text);
		this.textField.setText(this.valorAtual.toString());
	}

	private void doMath() {
		this.valor = this.operacao.applyAsDouble(this.valor, Double.valueOf(this.valorAtual.toString()));
		String resultado = String.valueOf(this.valor);
		if ((resultado.endsWith(".0"))) {

			this.textField.setText(resultado.substring(0, resultado.length() - 2));
		} else {
			this.textField.setText(resultado);
		}
		this.valorAtual.setLength(0);
		this.valorAtual.append(resultado);
	}

	@Override
	public void setObject(Object bean) {
		this.bean = bean;
	}

	public boolean isCalcPanelVisible() {
		return this.calcPanelVisible;
	}

	public void setCalcPanelVisible(boolean calcPanelVisible) {
		this.calcPanelVisible = calcPanelVisible;
	}

	public Font getFonte() {
		return this.fonte;
	}

	public void setFonte(Font font) {
		this.fonte = font;
		this.btn_1.setFont(font);
		this.btn_2.setFont(font);
		this.btn_3.setFont(font);
		this.btn_4.setFont(font);
		this.btn_5.setFont(font);
		this.btn_6.setFont(font);
		this.btn_7.setFont(font);
		this.btn_8.setFont(font);
		this.btn_9.setFont(font);
		this.btn_0.setFont(font);
		this.btn_dividir.setFont(font);
		this.btn_multiplicar.setFont(font);
		this.btn_subtrair.setFont(font);
		this.btn_decimal.setFont(font);
		this.btn_igual.setFont(font);
		this.btn_somar.setFont(font);
		this.btnC.setFont(font);
		this.textField.setFont(font);

	}
}
