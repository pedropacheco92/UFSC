package model;

import java.lang.reflect.Method;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import anotacoes.Coluna;

public class PessoaTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private final List<?> lista;
	private Class<?> classe;

	public PessoaTableModel(List<?> lista) {
		this.lista = lista;
		this.classe = lista.get(0).getClass();
	}

	@Override
	public int getRowCount() {
		return this.lista.size();
	}

	@Override
	public int getColumnCount() {
		int colunas = 0;
		for (Method metodo : this.classe.getDeclaredMethods()) {
			if (metodo.isAnnotationPresent(Coluna.class)) {
				colunas++;
			}
		}
		return colunas;
	}

	@Override
	public String getColumnName(int coluna) {
		for (Method metodo : this.classe.getDeclaredMethods()) {
			if (metodo.isAnnotationPresent(Coluna.class)) {
				Coluna anotacao = metodo.getAnnotation(Coluna.class);
				if (anotacao.posicao() == coluna) {
					return anotacao.nome();
				}
			}
		}
		return "";
	}

	@Override
	public Object getValueAt(int linha, int coluna) {
		try {
			Object objeto = this.lista.get(linha);
			for (Method metodo : this.classe.getDeclaredMethods()) {
				if (metodo.isAnnotationPresent(Coluna.class)) {
					Coluna anotacao = metodo.getAnnotation(Coluna.class);
					if (anotacao.posicao() == coluna) {
						return metodo.invoke(objeto);
					}
				}
			}
		} catch (Exception e) {
			return "Erro";
		}
		return "";
	}

	public void removeColumn(int row) {
		this.fireTableRowsDeleted(row, row);
		this.lista.remove(row);
	}
}