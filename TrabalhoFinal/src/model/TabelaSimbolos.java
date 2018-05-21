package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import model.enums.Categoria;
import model.enums.ContextoEXPR;
import model.enums.ContextoLID;
import model.enums.OpAdd;
import model.enums.OpMult;
import model.enums.OpRel;
import model.enums.Tipo;

@Getter
@Setter
public class TabelaSimbolos {

	// Integer->nívelAtual, String->identificador
	private SortedMap<Integer, Map<String, CategoriaId>> tabela;
	private List<String> listID = new ArrayList<>();

	// variáveis de controle
	private int na;// nívelAtual
	private int tamVetor;
	private int tamCadeia;
	private boolean opUnario;
	private boolean opNega;
	private String idComando;
	private Object valConst;
	private Tipo tipoAtual;
	private Tipo subCategoria;
	private Tipo tipoConst;
	private Tipo tipoExpSimples;
	private Tipo tipoExpr;
	private Tipo tipoTermo;
	private Tipo tipoFator;
	private Tipo tipoVar;
	private Tipo tipoLadoEsq;
	private Tipo tipoVarIndexada;
	private Categoria categoriaID;
	private ContextoLID contextoLID;
	private ContextoEXPR contextoEXPR;
	private OpRel opRel;
	private OpAdd opAdd;
	private OpMult opMult;
	private Constante idPrograma;

	public TabelaSimbolos() {
		this.tabela = new TreeMap<>((i1, i2) -> i1 > i2 ? -1 : i1 == i2 ? 0 : 1); // comparador
																					// custom
																					// para
																					// ordenar
																					// do
																					// maior
																					// pro
																					// menor
		this.listID = new ArrayList<>();

		this.na = 0;
		this.tamVetor = 0;
		this.opUnario = false;
		this.opNega = false;
	}

	public boolean containsID(String id) {
		for (Integer i : this.tabela.keySet()) {
			return this.containsID(id, i);
		}

		return false;
	}

	public boolean containsIDatNA(String id) {
		return this.containsID(id, this.na);
	}

	public boolean containsID(String id, int nivel) {
		Map<String, CategoriaId> constsNA = this.tabela.get(nivel);

		if (constsNA != null) {
			return constsNA.keySet().contains(id);
		}

		if (this.listID == null || this.listID.isEmpty()) {
			return false;
		} else {
			return this.listID.contains(id);
		}
	}

	public Categoria getCategoriaByID(String id) {
		Categoria cat = null;
		for (Integer i : this.tabela.keySet()) {
			cat = this.getCategoriaByID(id, i);
			if (cat != null) {
				return cat;
			}
		}
		return cat;
	}

	public Categoria getCategoriaByID(String id, int nivel) {
		Map<String, CategoriaId> map = this.tabela.get(nivel);
		if (map.containsKey(id)) {
			CategoriaId cat = map.get(id);

			if (cat instanceof Variavel) {
				return Categoria.VARIAVEL;
			}

			if (cat instanceof Constante) {
				return Categoria.CONSTANTE;
			}

		}

		if (nivel == 0 && this.idPrograma != null && this.idPrograma.equals(id)) {
			return Categoria.ID_PROGRAMA;
		}

		return null;
	}

	public void addConstante(String id, Constante constante) {
		this.addCategoriaId(id, constante);
	}

	public void addVariavel(String id, Variavel variavel) {
		this.addCategoriaId(id, variavel);
	}

	private void addCategoriaId(String id, CategoriaId catId) {
		if (this.tabela.get(this.na) == null || this.tabela.get(this.na).isEmpty()) {
			Map<String, CategoriaId> consts = new HashMap<>();
			consts.put(id, catId);
			this.tabela.put(this.na, consts);
		} else {
			this.tabela.get(this.na).put(id, catId);
		}
	}

	public Tipo getTipoById(String id) {
		Tipo tp = null;
		for (Integer i : this.tabela.keySet()) {
			tp = this.getTipoById(id, i);
			if (tp != null) {
				return tp;
			}
		}
		return null;
	}

	public Tipo getTipoById(String id, int nivel) {
		Map<String, CategoriaId> map = this.tabela.get(nivel);
		if (map.containsKey(id)) {
			CategoriaId cat = map.get(id);
			if (cat != null) {
				return cat.getTipo();
			}
		}

		return null;
	}

	// pega a constante a partir do nível atual
	public Constante getConstanteByID(String id, int nivel) {
		List<Integer> list = this.tabela.keySet().stream().filter(i -> i >= nivel).collect(Collectors.toList());
		if (list.isEmpty()) {
			return null;
		}
		for (Integer i : list) {
			Map<String, CategoriaId> consts = this.tabela.get(i);
			if (consts != null) {
				CategoriaId constante = consts.get(id);
				if (constante != null && constante instanceof Constante) {
					return (Constante) constante;
				}
			}
		}

		return null;
	}

	public Variavel getVariavelByID(String id) {
		Variavel var = null;
		for (Integer i : this.tabela.keySet()) {
			var = this.getVariavelByID(id, i);
			if (var != null) {
				return var;
			}
		}
		return var;
	}

	public Variavel getVariavelByID(String id, int nivel) {
		Map<String, CategoriaId> map = this.tabela.get(nivel);
		if (map.containsKey(id)) {
			CategoriaId cat = map.get(id);
			if (cat != null && cat instanceof Variavel) {
				return (Variavel) cat;
			}
		}

		return null;
	}

	/**
	 * @return true se dois tipos são compatíveis em uma atribuição tipoAtribuir
	 *         := tipoValor
	 *
	 *         Diferença com isTipoCompativelOpRel() é que aqui "REAL recebe
	 *         INTEIRO", mas "INTEIRO não recebe REAL" em uma atribuição. Em
	 *         operação relacional a ordem não importa. Ver Regra Semântica #26
	 */
	public boolean isTipoCompativelAtribuicao(Tipo tipoAtribuir, Tipo tipoValor) {
		if (tipoAtribuir.equals(tipoValor)) {
			return true;
		} else if (Tipo.REAL.equals(tipoAtribuir) && Tipo.INTEIRO.equals(tipoValor)) {
			return true;
		} else if (Tipo.CADEIA.equals(tipoAtribuir) && Tipo.CARACTER.equals(tipoValor)) {
			return true;
		}
		return false;
	}

	/**
	 * @return true se dois tipos são compatíveis em uma operação relacional
	 */
	public boolean isTipoCompativelOpRel(Tipo tipoOperando1, Tipo tipoOperando2) {
		if (tipoOperando1.equals(tipoOperando2)) {
			return true;
		} else if (this.isTipoCompativelMath(tipoOperando1, tipoOperando2)) {
			return true;
		} else if (Tipo.CADEIA.equals(tipoOperando1) && Tipo.CARACTER.equals(tipoOperando2)
				|| Tipo.CARACTER.equals(tipoOperando1) && Tipo.CADEIA.equals(tipoOperando2)) {
			return true;
		}
		return false;
	}

	public boolean isTipoCompativelOpAdd(Tipo tipoOperando1, Tipo tipoOperando2) {
		OpAdd tipoOp = this.getOpAdd();
		if (tipoOp == OpAdd.OU) {
			return this.isMesmoTipo(tipoOperando1, tipoOperando2, Tipo.BOOLEANO);
		} else {
			return this.isTipoCompativelMath(tipoOperando1, tipoOperando2);
		}
	}

	public boolean isTipoCompativelOpMult(Tipo tipoOperando1, Tipo tipoOperando2) {
		OpMult tipoOp = this.getOpMult();
		if (tipoOp == OpMult.E) {
			return this.isMesmoTipo(tipoOperando1, tipoOperando2, Tipo.BOOLEANO);
		} else {
			return this.isTipoCompativelMath(tipoOperando1, tipoOperando2);
		}
	}

	private boolean isTipoCompativelMath(Tipo tipoOperando1, Tipo tipoOperando2) {
		return (Tipo.INTEIRO.equals(tipoOperando1) || Tipo.REAL.equals(tipoOperando1))
				&& (Tipo.INTEIRO.equals(tipoOperando2) || Tipo.REAL.equals(tipoOperando2));
	}

	private boolean isMesmoTipo(Tipo tipoOperando1, Tipo tipoOperando2, Tipo tipo) {
		return tipo.equals(tipoOperando1) && tipo.equals(tipoOperando2);
	}

	/**
	 * Obtém Tipo resultante da última operação "OpAdd" entre dois operandos
	 *
	 * Pressupõe que tipos dos operandos são compatíveis e dos tipos inteiro,
	 * real ou booleano
	 */
	public Tipo getTipoResultanteOpAdd(Tipo tipoOperando1, Tipo tipoOperando2) {
		OpAdd tipoOp = this.getOpAdd();
		if (OpAdd.OU.equals(tipoOp)) {
			return Tipo.BOOLEANO;
		} else {
			if (this.isMesmoTipo(tipoOperando1, tipoOperando2, Tipo.INTEIRO)) {
				return Tipo.INTEIRO;
			} else {
				return Tipo.REAL;
			}
		}
	}

	/**
	 * Obtém Tipo resultante da última operação "OpMult" entre dois operandos
	 *
	 * Pressupõe que tipos dos operandos são compatíveis e dos tipos inteiro,
	 * real ou booleano
	 */
	public Tipo getTipoResultanteOpMult(Tipo tipoOperando1, Tipo tipoOperando2) {
		switch (this.getOpMult()) {
		case DIV:
		case DIVIDE:
			return Tipo.REAL;
		case E:
			return Tipo.BOOLEANO;
		case MULTIPLICA:
			if (this.isMesmoTipo(tipoOperando1, tipoOperando2, Tipo.INTEIRO)) {
				return Tipo.INTEIRO;
			} else {
				return Tipo.REAL; // qualquer combinação que inclua ao menos um
									// num_real
			}
		default:
			return null;
		}
	}

	public Tipo getTipoByValor(String valor) {
		if (valor.startsWith("'")) {
			if (valor.length() > 3) {
				return Tipo.CADEIA;
			} else {
				return Tipo.CARACTER;
			}
		} else if ("verdadeiro".equals(valor) || "falso".equals(valor)) {
			return Tipo.BOOLEANO;
		} else if (valor.contains(".") || valor.contains("E") || valor.contains("e")) {
			return Tipo.REAL;
		} else {
			return Tipo.INTEIRO;
		}
	}

	public Object getValor(String valor) {
		if (valor.startsWith("'")) {
			if (valor.length() > 3) {
				return valor.substring(1, valor.length() - 1);// retirando as/
																// aspas
			} else {
				return new Character(valor.charAt(1));// retirando as aspas
			}
		} else if ("verdadeiro".equals(valor)) {
			return true;
		} else if ("falso".equals(valor)) {
			return false;
		} else {
			valor = valor.toUpperCase();
			if (valor.contains(".")) { // real
				String parte1 = valor.substring(0, valor.indexOf("."));
				String parte2;

				if (valor.contains("E")) {
					parte2 = valor.substring(valor.indexOf("."), valor.indexOf("E"));
					int expoente = Integer.parseInt(valor.substring(valor.indexOf("E") + 1, valor.length()));
					Double value = Double.parseDouble(parte1 + parte2);
					return Math.pow(value, expoente);
				} else {
					parte2 = valor.substring(valor.indexOf("."), valor.length());
					return Float.parseFloat(parte1 + parte2);
				}

			} else {// Inteiro
				String parte1;
				if (valor.contains("E") && !valor.contains(".")) {
					parte1 = valor.substring(0, valor.indexOf("E"));
					String s = valor.substring(valor.indexOf("E") + 1, valor.length());
					long expoente = Integer.parseInt(s);
					Double value = Double.parseDouble(parte1);
					return (int) Math.pow(value, expoente);
				} else {
					return (int) Long.parseLong(valor);
				}
			}
		}
	}
}
