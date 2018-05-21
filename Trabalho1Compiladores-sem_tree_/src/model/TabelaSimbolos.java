package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private Map<Integer, Map<String, Constante>> constantes;
	private Map<Integer, Map<String, Variavel>> variaveis;
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
		this.constantes = new HashMap<>();
		this.variaveis = new HashMap<>();
		this.listID = new ArrayList<>();

		this.na = 0;
		this.tamVetor = 0;
		this.opUnario = false;
		this.opNega = false;
	}

	public boolean containsID(String id) {
		boolean contemId = false;
		int nivel = this.na;

		while (nivel >= 0 && contemId == false) {
			contemId = this.containsID(id, nivel);
			nivel--;
		}

		return contemId;
	}

	public boolean containsIDatNA(String id) {
		return this.containsID(id, this.na);
	}

	// verifica se "id" já foi declarado no nível atual
	public boolean containsID(String id, int nivel) {

		Map<String, Constante> constsNA = this.constantes.get(nivel);
		Map<String, Variavel> varsNA = this.variaveis.get(nivel);

		// pega as variáveis do nível atual para comparação
		if (constsNA != null) {
			return constsNA.keySet().contains(id);
		}

		if (varsNA != null) {
			return varsNA.keySet().contains(id);
		}

		if (this.listID == null || this.listID.isEmpty()) {
			return false;
		} else {
			// se nao encontrou na tabela de símbolos, verificar se está na
			// lista atual
			return this.listID.contains(id);
		}
	}

	// pega do nível mais interno, pois é sempre o mais interno que deve ser
	// acessado
	public Categoria getCategoriaByID(String id) {
		Categoria categoria = null;
		int nivel = this.na;

		while (nivel >= 0 && categoria == null) {
			categoria = this.getCategoriaByID(id, nivel);
			nivel--;
		}

		return categoria;
	}

	// retorna a categoria do "id"
	// para o nível definido
	public Categoria getCategoriaByID(String id, int nivel) {
		Map<String, Constante> consts = this.constantes.get(nivel);
		Map<String, Variavel> vars = this.variaveis.get(nivel);

		if (nivel == 0 && this.idPrograma != null && this.idPrograma.equals(id)) {
			return Categoria.ID_PROGRAMA;
		}

		// pega as variáveis do nível atual para comparação
		if (consts != null) {
			if (consts.keySet().contains(id)) {
				return Categoria.CONSTANTE;
			}
		}

		if (vars != null) {
			if (vars.keySet().contains(id)) {
				return Categoria.VARIAVEL;
			}
		}

		return null;
	}

	// adiciona uma nova constante na tabela de símbolos
	public void addConstante(String id, Constante constante) {
		if (this.constantes.get(this.na) == null || this.constantes.get(this.na).isEmpty()) {
			Map<String, Constante> consts = new HashMap<>();
			consts.put(id, constante);
			this.constantes.put(this.na, consts);
		} else {
			this.constantes.get(this.na).put(id, constante);
		}

	}

	public void addVariavel(String id, Variavel variavel) {
		if (this.variaveis.get(this.na) == null || this.variaveis.get(this.na).isEmpty()) {
			Map<String, Variavel> vars = new HashMap<>();
			vars.put(id, variavel);
			this.variaveis.put(this.na, vars);
		} else {
			this.variaveis.get(this.na).put(id, variavel);
		}

	}

	public Tipo getTipoById(String id) {
		Tipo tipo = null;
		int nivel = this.na;

		while (nivel >= 0 && tipo == null) {
			tipo = this.getTipoById(id, nivel);
			nivel--;
		}

		return tipo;
	}

	public Tipo getTipoById(String id, int nivel) {
		Map<String, Constante> consts = this.constantes.get(nivel);
		Map<String, Variavel> vars = this.variaveis.get(nivel);

		// pega as variáveis do nível atual para comparação
		if (consts != null) {
			Constante c = consts.get(id);
			if (c != null) {
				return c.getTipo();

			}
		}
		if (vars != null) {
			Variavel v = vars.get(id);
			if (v != null) {
				return v.getTipo();
			}
		}

		return null;
	}

	// pega a constante a partir do nível atual
	public Constante getConstanteByID(String id, int nivel) {
		Map<String, Constante> consts = this.constantes.get(nivel);
		if (consts != null) {
			Constante constante = consts.get(id);
			if (nivel >= 0 && constante == null) {
				return this.getConstanteByID(id, nivel - 1);
			} else {
				return constante;// se nao encontrou vai null
			}
		} else {
			if (nivel >= 0) {
				return this.getConstanteByID(id, nivel - 1);
			} else {
				return null;
			}
		}
	}

	public Variavel getVariavelByID(String id) {
		return this.getVariavelByID(id, this.na);
	}

	public Variavel getVariavelByID(String id, int nivel) {
		Map<String, Variavel> vars = this.variaveis.get(nivel);
		if (vars != null) {
			Variavel var = vars.get(id);
			if (nivel >= 0 && var == null) {
				return this.getVariavelByID(id, nivel - 1);
			} else {
				return var;
			}
		} else {
			if (nivel >= 0) {
				return this.getVariavelByID(id, nivel - 1);
			} else {
				return null;
			}
		}
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
		} else if (valor.contains(".") || valor.contains("E-")) {
			return Tipo.REAL;
		} else {
			return Tipo.INTEIRO;
		}
	}

	// return um inteiro se o valor for inteiro, caracter se o valor for
	// caracter,...
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
			if (valor.contains(".") || valor.contains("E-")) { // Float
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
				if (valor.contains("E")) {
					parte1 = valor.substring(0, valor.indexOf("E"));
					String s = valor.substring(valor.indexOf("E") + 1, valor.length());
					long expoente = Integer.parseInt(s);
					Double value = Double.parseDouble(parte1);
					return (int) Math.pow(value, expoente);// TODO
				} else {
					return (int) Long.parseLong(valor);
				}
			}
		}
	}
}
