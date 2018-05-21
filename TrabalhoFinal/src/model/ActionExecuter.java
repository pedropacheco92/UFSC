package model;

import java.lang.reflect.InvocationTargetException;

import model.enums.Categoria;
import model.enums.ContextoEXPR;
import model.enums.ContextoLID;
import model.enums.OpAdd;
import model.enums.OpMult;
import model.enums.OpRel;
import model.enums.Tipo;
import model.gals.SemanticError;
import model.gals.Token;

public class ActionExecuter {

	private TabelaSimbolos tabelaSimbolos;

	public ActionExecuter() {
		this.tabelaSimbolos = new TabelaSimbolos();
	}

	public void executeAction(int executeAction_id, Token token) throws SemanticError {
		String methodname = "executeAction" + executeAction_id;
		try {
			this.getClass().getDeclaredMethod(methodname, Token.class).invoke(this, token);
		} catch (NoSuchMethodException e) {
			System.err.println("ação nao implementada: " + executeAction_id);
		} catch (IllegalAccessException | IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			throw (SemanticError) e.getTargetException();
		}

	}

	/**
	 * #101 – Insere id na TS juntamente com seus atributos: categoria
	 * (id-programa) e nível ( NA ). Inicializa com zero nível atual (NA) e
	 * deslocamento.
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction101(Token token) throws SemanticError {
		String id = token.getLexeme();
		if (this.tabelaSimbolos.containsIDatNA(id)) {
			throw new SemanticError("Identificador " + id + " já declarado", token.getPosition());
		} else {
			Constante constante = new Constante();
			this.tabelaSimbolos.addConstante(id, constante);
			this.tabelaSimbolos.setIdPrograma(constante);
		}
	}

	/**
	 * #102 – seta contextoLID para “decl”
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction102(Token token) throws SemanticError {
		this.tabelaSimbolos.setContextoLID(ContextoLID.DECL);
	}

	/**
	 * #103 – Atualiza atributos dos id de <lid> de acordo com a CategoriaAtual
	 * e com a SubCategoria. Para cálculo do Deslocamento de variáveis,
	 * considere que toda variável ocupa 1 célula de memória (exceto vetor que
	 * ocupa 1 célula para cada elemento)
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction103(Token token) throws SemanticError {
		Tipo tpAtual = this.tabelaSimbolos.getTipoAtual();
		Tipo subCategoria = this.tabelaSimbolos.getSubCategoria();
		for (String s : this.tabelaSimbolos.getListID()) {
			Categoria categoria = this.tabelaSimbolos.getCategoriaID();
			switch (categoria) {
			case CONSTANTE:
				Constante cons = new Constante();
				cons.setTipo(tpAtual);
				this.tabelaSimbolos.addConstante(s, cons);
				break;
			case VARIAVEL:
				Variavel var = new Variavel();
				var.setTipo(tpAtual);

				if (Tipo.CADEIA.equals(tpAtual)) {
					var.setTamanho(this.tabelaSimbolos.getTamCadeia());
				}

				if (Tipo.VETOR.equals(tpAtual)) {
					var.setTipoElementos(this.tabelaSimbolos.getSubCategoria());
					var.setTamanho(this.tabelaSimbolos.getTamCadeia());
				}

				this.tabelaSimbolos.addVariavel(s, var);
				break;
			default:
				break;

			}
		}

		this.tabelaSimbolos.getListID().clear();
		this.tabelaSimbolos.setTipoAtual(null);
	}

	/**
	 * #104 – TipoAtual := “inteiro”
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction104(Token token) throws SemanticError {
		this.tabelaSimbolos.setTipoAtual(Tipo.INTEIRO);
	}

	/**
	 * #105 – TipoAtual := “real”
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction105(Token token) throws SemanticError {
		this.tabelaSimbolos.setTipoAtual(Tipo.REAL);
	}

	/**
	 * #106 – TipoAtual := “booleano”
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction106(Token token) throws SemanticError {
		this.tabelaSimbolos.setTipoAtual(Tipo.BOOLEANO);
	}

	/**
	 * #107 – TipoAtual := “caracter”
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction107(Token token) throws SemanticError {
		this.tabelaSimbolos.setTipoAtual(Tipo.CARACTER);
	}

	/**
	 * #108 – Se TipoConst <> “inteiro” Então ERRO(“esperava-se uma constante
	 * inteira”) Senão Se ValConst > 255 Então ERRO(“tam.da cadeia > que o
	 * permitido”) Senão TipoAtual := “cadeia”
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction108(Token token) throws SemanticError {
		if (!Tipo.INTEIRO.equals(this.tabelaSimbolos.getTipoConst())) {
			throw new SemanticError("esperava-se uma constante inteira", token.getPosition());
		} else {
			Integer tamCadeia = Integer.valueOf(String.valueOf(this.tabelaSimbolos.getValConst()));
			if (tamCadeia > 255) {
				throw new SemanticError("tam. da cadeia > que o permitido", token.getPosition());
			} else {
				this.tabelaSimbolos.setTipoAtual(Tipo.CADEIA);
				this.tabelaSimbolos.setTamCadeia(tamCadeia);
			}
		}
	}

	/**
	 * #109 – Se TipoAtual = “cadeia” Então ERRO(“Vetor do tipo cadeia não é
	 * permitido”) Senão SubCategoria := “vetor”
	 *
	 * @param token
	 * @throws SemanticError
	 */

	@SuppressWarnings("unused")
	private void executeAction109(Token token) throws SemanticError {
		if (Tipo.CADEIA.equals(this.tabelaSimbolos.getTipoAtual())) {
			throw new SemanticError("Vetor do tipo cadeia não é permitido", token.getPosition());
		} else {
			// TODO subcategoria
		}
	}

	/**
	 * #110 – Se TipoConst <> inteiro Então ERRO (“A dimensão deve ser uma
	 * constante inteira”) Senão Seta NumElementos para ValConst
	 *
	 */
	@SuppressWarnings("unused")
	private void executeAction110(Token token) throws SemanticError {
		Tipo tipo = this.tabelaSimbolos.getTipoExpr();
		if (!Tipo.INTEIRO.equals(tipo)) {
			throw new SemanticError("esperava-se uma constante inteira", token.getPosition());
		} else {
			this.tabelaSimbolos.setTamVetor((int) this.tabelaSimbolos.getValConst());
		}
	}

	/**
	 * #111 – Se TipoAtual = “cadeia” Então SubCategoria := “cadeia” Senão
	 * SubCategoria := “pré-definido”
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction111(Token token) throws SemanticError {
		Tipo tipo = this.tabelaSimbolos.getTipoExpr();
		if (Tipo.CADEIA.equals(tipo)) {
			// tabelaSimbolos.setConstanteAtual(Tipo.CADEIA);
		} else {

		}
	}

	/**
	 * #112 – Se contextoLID = “decl” Então Se id já declarado no NA Então
	 * ERRO(“Id já declarado”) Senão insere id na TS
	 *
	 * – Se contextoLID = “leitura” Então Se id não declarado Então ERRO (“Id
	 * não declarado”) Senão Se categoria ou tipo inválido para leitura Então
	 * ERRO(“Tipo inválido para leitura”) Senão (* Gera Código para leitura *)
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction112(Token token) throws SemanticError {
		String id = token.getLexeme();

		if (ContextoLID.DECL.equals(this.tabelaSimbolos.getContextoLID())) {
			if (this.tabelaSimbolos.containsIDatNA(id)) {
				throw new SemanticError("Identificador " + id + " já declarado", token.getPosition());
			} else {
				this.tabelaSimbolos.getListID().add(id);
			}
		}
		if (ContextoLID.LEITURA.equals(this.tabelaSimbolos.getContextoLID())) {
			if (!this.tabelaSimbolos.containsID(id)) {
				throw new SemanticError("Identificador " + id + " não declarado", token.getPosition());
			} else {
				Categoria categoria = this.tabelaSimbolos.getCategoriaByID(id);
				Tipo tipo = this.tabelaSimbolos.getTipoById(id);
				if (categoria != null) {
					if (Categoria.VARIAVEL.equals(categoria)) {
						if (Tipo.VETOR.equals(tipo)) { // somente vetor nao pode
														// ser lido
							throw new SemanticError("Tipo inválido para leitura", token.getPosition());
						} else {
							// TODO gera codigo
						}
					}
				}
			}
		}
	}

	/**
	 * #113 – Se SubCategoria = “cadeia” ou “vetor” Então ERRO (“Apenas ids de
	 * tipo pré-definido podem ser declarados como constante”) Senão Se
	 * TipoConst <> TipoAtual Então ERRO (“ Tipo da constante incorreto ”) Senão
	 * CategoriaAtual := “constante”
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction113(Token token) throws SemanticError {
		Tipo subCategoria = this.tabelaSimbolos.getSubCategoria();
		if (Tipo.CADEIA.equals(subCategoria) || Tipo.VETOR.equals(subCategoria)) {
			throw new SemanticError("Apenas ids de tipo pré-definido podem ser declarados como constante",
					token.getPosition());
		} else if (!this.tabelaSimbolos.isTipoCompativelAtribuicao(this.tabelaSimbolos.getTipoAtual(),
				this.tabelaSimbolos.getTipoConst())) {
			throw new SemanticError("Tipo da constante incorreto", token.getPosition());
		} else {
			this.tabelaSimbolos.setCategoriaID(Categoria.CONSTANTE);
		}
	}

	/**
	 * #114 - CategoriaAtual := “variavel”
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction114(Token token) throws SemanticError {
		this.tabelaSimbolos.setCategoriaID(Categoria.VARIAVEL);
	}

	/**
	 * #115 – Se id não está declarado (não esta na TS) Então
	 * ERRO(“Identificador não declarado”) Senão guarda posição ocupada por id
	 * na TS em POSID
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction115(Token token) throws SemanticError {
		String id = token.getLexeme();
		if (!this.tabelaSimbolos.containsID(id)) {
			throw new SemanticError("Identificador não declarado", token.getPosition());
		} else {
			this.tabelaSimbolos.setIdComando(id);
		}
	}

	/**
	 * #116 – Se TipoExpr <> “booleano” e <> “inteiro” Então ERRO(“Tipo inválido
	 * da expressão”) Senão (* ação de G. Código *)
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction116(Token token) throws SemanticError {
		Tipo tipoExpr = this.tabelaSimbolos.getTipoExpr();
		if (!Tipo.BOOLEANO.equals(tipoExpr) && !Tipo.INTEIRO.equals(tipoExpr)) {
			throw new SemanticError("Tipo inválido de expressão", token.getPosition());
		} // TODO else {geração de codigo }
	}

	/**
	 * #117 – Seta ContextoLID para “Leitura”
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction117(Token token) throws SemanticError {
		this.tabelaSimbolos.setContextoLID(ContextoLID.LEITURA);
	}

	/**
	 * #118 – Seta ContextoEXPR para “impressão” Se TipoExpr = booleano Então
	 * ERRO(“tipo invalido para impressão”) Senão (* Gera Código para impressão
	 * *)
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction118(Token token) throws SemanticError {
		this.tabelaSimbolos.setContextoEXPR(ContextoEXPR.IMPRESSAO);
		Tipo tipoExpr = this.tabelaSimbolos.getTipoExpr();

		if (Tipo.BOOLEANO.equals(this.tabelaSimbolos.getTipoExpr())) {
			throw new SemanticError("Tipo invalido para impressão", token.getPosition());
		} // TODO else { geração de código}
	}

	/**
	 * #119 – Se categoria de id = “Variável” ou “Parâmetro” Então Se tipo de id
	 * = “vetor” Então ERRO (“id deveria ser indexado”) Senão TipoLadoEsq :=
	 * tipo de id Senão ERRO (“id deveria ser variável ”)
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction119(Token token) throws SemanticError {
		String id = this.tabelaSimbolos.getIdComando();
		Categoria categoria = this.tabelaSimbolos.getCategoriaByID(id);
		Tipo tipo = this.tabelaSimbolos.getTipoById(id);

		if (Categoria.VARIAVEL.equals(categoria) || Categoria.PARAMETRO.equals(categoria)) {
			if (Tipo.VETOR.equals(tipo)) {
				throw new SemanticError("Id deveria ser indexado", token.getPosition());
			} else {
				this.tabelaSimbolos.setTipoLadoEsq(tipo);
			}
		} else {
			throw new SemanticError("Id deveria ser variável", token.getPosition());
		}
	}

	/**
	 * #120 – Se TipoExpr não compatível com tipoLadoesq Então ERRO (“tipos
	 * incompatíveis”) Senão (* G. Código *)
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction120(Token token) throws SemanticError {
		Tipo tipoExpr = this.tabelaSimbolos.getTipoExpr();
		Tipo tipoLE = this.tabelaSimbolos.getTipoLadoEsq();
		if (!this.tabelaSimbolos.isTipoCompativelAtribuicao(tipoLE, tipoExpr)) {
			throw new SemanticError("Tipos incompatíveis", token.getPosition());
		} else {
			// TODO gerar código
		}
	}

	/**
	 * #121 – Se categoria de id <> “variável” Então ERRO (“esperava-se uma
	 * variável”) Senão Se tipo de id <> vetor e <> de cadeia Então ERRO(“apenas
	 * vetores e cadeias podem ser indexados”) Senão TipoVarIndexada = tipo de
	 * id (vetor ou cadeia)
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction121(Token token) throws SemanticError {
		String id = this.tabelaSimbolos.getIdComando();
		Categoria categoria = this.tabelaSimbolos.getCategoriaByID(id);
		Tipo tipo = this.tabelaSimbolos.getTipoById(id);

		if (!Categoria.VARIAVEL.equals(categoria)) {
			throw new SemanticError("Esperava-se uma variável", token.getPosition());
		} else if (!Tipo.VETOR.equals(tipo) && !Tipo.CADEIA.equals(tipo)) {
			throw new SemanticError("Apenas vetores e cadeias podem ser indexados", token.getPosition());
		} else {
			this.tabelaSimbolos.setTipoVarIndexada(tipo);
			Variavel v = this.tabelaSimbolos.getVariavelByID(id);
			this.tabelaSimbolos.setSubCategoria(v.getTipoElementosVetor());
		}
	}

	/**
	 * #122 – Se TipoExpr <> “inteiro” Então ERRO(“índice deveria ser inteiro”)
	 * Senão Se TipoVarIndexada = cadeia Então TipoLadoEsq := “caracter” Senão
	 * TipoLadoEsq := Tipo Elementos do vetor
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction122(Token token) throws SemanticError {
		Tipo tipoExpr = this.tabelaSimbolos.getTipoExpr();
		if (!Tipo.INTEIRO.equals(tipoExpr)) {
			throw new SemanticError("Tipo de índice inválido", token.getPosition());
		} else if (Tipo.CADEIA.equals(this.tabelaSimbolos.getTipoVarIndexada())) {
			this.tabelaSimbolos.setTipoLadoEsq(Tipo.CARACTER);
		} else {
			this.tabelaSimbolos.setTipoLadoEsq(this.tabelaSimbolos.getSubCategoria());
		}
	}

	/**
	 * #127 – Se ContextoEXPR = “impressão” Então Se TipoExpr = booleano Então
	 * ERRO(“tipo invalido para impressão”) Senão (* G. Código para impressão *)
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction127(Token token) throws SemanticError {
		if (ContextoEXPR.IMPRESSAO.equals(this.tabelaSimbolos.getContextoEXPR())) {
			if (Tipo.BOOLEANO.equals(this.tabelaSimbolos.getTipoExpr())) {
				throw new SemanticError("Tipo invalido para impressão", token.getPosition());
			} // TODO else {gerar código}
		}
	}

	/**
	 * #128 – TipoExpr := TipoExpSimples
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction128(Token token) throws SemanticError {
		this.tabelaSimbolos.setTipoExpr(this.tabelaSimbolos.getTipoExpSimples());
	}

	/**
	 * #129 - Se TipoExpSimples incompatível com TipoExpr Então ERRO (“Operandos
	 * incompatíveis”) Senão TipoExpr := “booleano”
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction129(Token token) throws SemanticError {
		if (this.tabelaSimbolos.isTipoCompativelOpRel(this.tabelaSimbolos.getTipoExpSimples(),
				this.tabelaSimbolos.getTipoExpr())) {
			this.tabelaSimbolos.setTipoExpr(Tipo.BOOLEANO);
		} else {
			throw new SemanticError("Operandos incompatíveis", token.getPosition());
		}
	}

	/**
	 * #130 a #134 – Guarda Op. Rel. para futura Geração de Cód.
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction130(Token token) throws SemanticError {
		this.tabelaSimbolos.setOpRel(OpRel.IGUAL);
	}

	/**
	 * #130 a #134 – Guarda Op. Rel. para futura Geração de Cód.
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction131(Token token) throws SemanticError {
		this.tabelaSimbolos.setOpRel(OpRel.MENOR);
	}

	/**
	 * #130 a #134 – Guarda Op. Rel. para futura Geração de Cód.
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction132(Token token) throws SemanticError {
		this.tabelaSimbolos.setOpRel(OpRel.MAIOR);
	}

	/**
	 * #130 a #134 – Guarda Op. Rel. para futura Geração de Cód.
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction133(Token token) throws SemanticError {
		this.tabelaSimbolos.setOpRel(OpRel.MAIOR_IGUAL);
	}

	/**
	 * #130 a #134 – Guarda Op. Rel. para futura Geração de Cód.
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction134(Token token) throws SemanticError {
		this.tabelaSimbolos.setOpRel(OpRel.MENOR_IGUAL);
	}

	/**
	 * #130 a #134 – Guarda Op. Rel. para futura Geração de Cód.
	 *
	 * @param token
	 * @throws SemanticError
	 */
	// @SuppressWarnings("unused")
	// private void executeAction134(Token token) throws SemanticError {
	// this.tabelaSimbolos.setOpRel(OpRel.DIFERENTE);
	// }

	/**
	 * #135 – TipoExpSimples := TipoTermo
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction135(Token token) throws SemanticError {
		this.tabelaSimbolos.setTipoExpSimples(this.tabelaSimbolos.getTipoTermo());
	}

	/**
	 * #136 – Se operador não se aplica a TipoExpSimples Então ERRO(“ Operador e
	 * Operando incompatíveis”)
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction136(Token token) throws SemanticError {
		Tipo tipoExp = this.tabelaSimbolos.getTipoExpSimples();
		OpAdd tipoOp = this.tabelaSimbolos.getOpAdd();

		if (OpAdd.OU.equals(tipoOp) && Tipo.BOOLEANO.equals(tipoExp)) {
			return;
		} else {
			if (Tipo.INTEIRO.equals(tipoExp) || Tipo.REAL.equals(tipoExp)) {
				return;
			}
		}
		throw new SemanticError("Operador e Operando incompatíveis", token.getPosition());
	}

	/**
	 * #137 - Se TipoTermo incompatível com TipoExpSimples Então ERRO
	 * (“Operandos incompatíveis”) Senão TipoExpSimples := tipo do res. da
	 * operação (* G. Código de acordo com op_add *)
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction137(Token token) throws SemanticError {
		Tipo tipoTermo = this.tabelaSimbolos.getTipoTermo();
		Tipo tipoExp = this.tabelaSimbolos.getTipoExpSimples();

		if (this.tabelaSimbolos.isTipoCompativelOpAdd(tipoTermo, tipoExp)) {
			Tipo tipoOp = this.tabelaSimbolos.getTipoResultanteOpAdd(tipoTermo, tipoExp);
			this.tabelaSimbolos.setTipoExpSimples(tipoOp);

		} else {
			throw new SemanticError("Operandos incompatíveis", token.getPosition());
		}
	}

	/**
	 * #138 a #140 – guarda operador para futura geração de código
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction138(Token token) throws SemanticError {
		this.tabelaSimbolos.setOpAdd(OpAdd.MAIS);
	}

	/**
	 * #138 a #140 – guarda operador para futura geração de código
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction139(Token token) throws SemanticError {
		this.tabelaSimbolos.setOpAdd(OpAdd.MENOS);
	}

	/**
	 * #138 a #140 – guarda operador para futura geração de código
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction140(Token token) throws SemanticError {
		this.tabelaSimbolos.setOpAdd(OpAdd.OU);
	}

	/**
	 * #141 – TipoTermo := TipoFator
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction141(Token token) throws SemanticError {
		this.tabelaSimbolos.setTipoTermo(this.tabelaSimbolos.getTipoFator());
	}

	/**
	 * #142 – Se operador não se aplica a TipoTermo Então ERRO(“Operador e
	 * Operando incompatíveis”)
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction142(Token token) throws SemanticError {
		Tipo tipoTermo = this.tabelaSimbolos.getTipoTermo();
		OpMult tipoOp = this.tabelaSimbolos.getOpMult();

		if (OpMult.E.equals(tipoOp)) {
			if (Tipo.BOOLEANO.equals(tipoTermo)) {
				return;
			}
		} else {
			if (Tipo.INTEIRO.equals(tipoTermo) || Tipo.REAL.equals(tipoTermo)) {
				return;
			}
		}
		throw new SemanticError("Operador e Operando incompatíveis", token.getPosition());
	}

	/**
	 * #143 - Se TipoFator incompatível com TipoTermo Então ERRO (“Operandos
	 * incompatíveis”) Senão TipoTermo := tipo do resultado da operação (* G.
	 * Código de acordo com op_mult *)
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction143(Token token) throws SemanticError {
		Tipo tipoTermo = this.tabelaSimbolos.getTipoTermo();
		Tipo tipoFator = this.tabelaSimbolos.getTipoFator();
		if (this.tabelaSimbolos.isTipoCompativelOpMult(tipoTermo, tipoFator)) {
			Tipo tipoOp = this.tabelaSimbolos.getTipoResultanteOpMult(tipoTermo, tipoFator);
			this.tabelaSimbolos.setTipoTermo(tipoOp);
		} else {
			throw new SemanticError("Operandos incompatíveis", token.getPosition());
		}
	}

	/**
	 * #144 a #147 – guarda operador para futura geração de código
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction144(Token token) throws SemanticError {
		this.tabelaSimbolos.setOpMult(OpMult.MULTIPLICA);
	}

	/**
	 * #144 a #147 – guarda operador para futura geração de código
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction145(Token token) throws SemanticError {
		this.tabelaSimbolos.setOpMult(OpMult.DIVIDE);
	}

	/**
	 * #144 a #147 – guarda operador para futura geração de código
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction146(Token token) throws SemanticError {
		this.tabelaSimbolos.setOpMult(OpMult.DIV);
	}

	/**
	 * #144 a #147 – guarda operador para futura geração de código
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction147(Token token) throws SemanticError {
		this.tabelaSimbolos.setOpMult(OpMult.E);
	}

	/**
	 *
	 * #148 - Se OpNega Então ERRO(“Op. “não” repetido ”) Senão OpNega := true
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction148(Token token) throws SemanticError {
		if (this.tabelaSimbolos.isOpNega()) {
			throw new SemanticError("Operadores \"não\" consecutivos", token.getPosition());
		} else {
			this.tabelaSimbolos.setOpNega(true);
		}
	}

	/**
	 * #149 – Se TipoFator <> “booleano” Então ERRO(“Op. “não” exige operando
	 * booleano”)
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction149(Token token) throws SemanticError {
		if (!Tipo.BOOLEANO.equals(this.tabelaSimbolos.getTipoFator())) {
			throw new SemanticError("Op. \"não\" exige operando booleano", token.getPosition());
		}
	}

	/**
	 * #150 – Se OpUnario Então ERRO(“Op. “unário” repetido “) Senão OpUnario :=
	 * true
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction150(Token token) throws SemanticError {
		if (this.tabelaSimbolos.isOpUnario()) {
			throw new SemanticError("Ops. \"unário\" consecutivos", token.getPosition());
		} else {
			this.tabelaSimbolos.setOpUnario(true);
		}
	}

	/**
	 * #151 - Se TipoFator <> “inteiro” ou de “real” Então ERRO(“Op. unário
	 * exige operando numérico”)
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction151(Token token) throws SemanticError {
		Tipo tipoFator = this.tabelaSimbolos.getTipoFator();
		if (!Tipo.INTEIRO.equals(tipoFator) && !Tipo.REAL.equals(tipoFator)) {
			throw new SemanticError("Op. unário exige operando numérico", token.getPosition());
		}
	}

	/**
	 * #152 – OpNega := OpUnario := false
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction152(Token token) throws SemanticError {
		this.tabelaSimbolos.setOpNega(false);
		this.tabelaSimbolos.setOpUnario(false);
	}

	/**
	 * #153 – TipoFator := TipoExpr
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction153(Token token) throws SemanticError {
		this.tabelaSimbolos.setTipoFator(this.tabelaSimbolos.getTipoExpr());
	}

	/**
	 * #154 – TipoFator := TipoVar
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction154(Token token) throws SemanticError {
		this.tabelaSimbolos.setTipoFator(this.tabelaSimbolos.getTipoVar());
	}

	/**
	 * #155 – TipoFator := TipoCte
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction155(Token token) throws SemanticError {
		this.tabelaSimbolos.setTipoFator(this.tabelaSimbolos.getTipoConst());
	}

	/**
	 * #156 – Se TipoExpr <> “inteiro” Então ERRO(“índice deveria ser inteiro”)
	 * Senão Se TipoVarIndexada = cadeia Então TipoVar := “caracter” Senao
	 * TipoVar := TipoElementos do vetor
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction156(Token token) throws SemanticError {
		Tipo tipoExpr = this.tabelaSimbolos.getTipoExpr();
		Tipo tipoVarIndexada = this.tabelaSimbolos.getTipoVarIndexada();
		if (!Tipo.INTEIRO.equals(tipoExpr)) {
			throw new SemanticError("Tipo de índice inválido", token.getPosition());
		} else if (Tipo.CADEIA.equals(tipoVarIndexada)) {
			this.tabelaSimbolos.setTipoVar(Tipo.CARACTER);
		} else {
			this.tabelaSimbolos.setTipoVar(this.tabelaSimbolos.getSubCategoria());
		}
	}

	/**
	 * #157 - Se categoria de id = “variável” ou “Parâmetro” Então Se tipo de id
	 * = “vetor” Então ERRO(“vetor deve ser indexado”) Senão TipoVar := Tipo de
	 * id Senão Se categoria de id = “constante” Então TipoVar:= TipoConst Senão
	 * ERRO(“esperava-se var, ou constante”)
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction157(Token token) throws SemanticError {
		String id = token.getLexeme();
		Categoria cat = this.tabelaSimbolos.getCategoriaByID(id);
		Tipo tipoId = this.tabelaSimbolos.getTipoById(id);

		if (Categoria.VARIAVEL.equals(cat) || Categoria.PARAMETRO.equals(cat)) {
			if (Tipo.VETOR.equals(tipoId)) {
				throw new SemanticError("Vetor deve ser indexado", token.getPosition());
			} else {
				this.tabelaSimbolos.setTipoVar(tipoId);
			}
		} else if (Categoria.CONSTANTE.equals(cat)) {
			this.tabelaSimbolos.setTipoVar(tipoId);
		} else {
			throw new SemanticError("Esperava-se var, ou constante", token.getPosition());
		}
	}

	/**
	 * #158 - Se id não está declarado Então ERRO(“Id não declarado”) Senão Se
	 * categoria de id <> constante Então ERRO (“Esperava-se um id de
	 * Constante”) Senão TipoConst = Tipo da constante id ValConst = Valor da
	 * constante id
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction158(Token token) throws SemanticError {
		String id = token.getLexeme();
		if (!this.tabelaSimbolos.containsID(id)) {
			throw new SemanticError("Id " + id + " não declarado", token.getPosition());
		} else if (!Categoria.CONSTANTE.equals(this.tabelaSimbolos.getCategoriaByID(id))) {
			throw new SemanticError("Esperava-se que " + id + " fosse uma Constante", token.getPosition());
		} else {
			Constante constante = this.tabelaSimbolos.getConstanteByID(id, this.tabelaSimbolos.getNa());
			if (constante != null) {
				this.tabelaSimbolos.setTipoConst(constante.getTipo());
				Object constanteValue = constante.getValue();
				this.tabelaSimbolos.setValConst(constanteValue);
			}
		}
	}

	/**
	 * #159 a #163 – TipoCte := tipo da constante ValCte := valor da constante
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction159(Token token) throws SemanticError {
		String id = token.getLexeme();
		this.tabelaSimbolos.setTipoConst(Tipo.INTEIRO);
		this.tabelaSimbolos.setValConst(this.tabelaSimbolos.getValor(id));
	}

	/**
	 * #159 a #163 – TipoCte := tipo da constante ValCte := valor da constante
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction160(Token token) throws SemanticError {
		String id = token.getLexeme();
		this.tabelaSimbolos.setTipoConst(Tipo.REAL);
		this.tabelaSimbolos.setValConst(this.tabelaSimbolos.getValor(id));
	}

	/**
	 * #159 a #163 – TipoCte := tipo da constante ValCte := valor da constante
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction161(Token token) throws SemanticError {
		String id = token.getLexeme();
		this.tabelaSimbolos.setTipoConst(Tipo.BOOLEANO);
		this.tabelaSimbolos.setValConst(this.tabelaSimbolos.getValor(id));
	}

	/**
	 * #159 a #163 – TipoCte := tipo da constante ValCte := valor da constante
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction162(Token token) throws SemanticError {
		String id = token.getLexeme();
		this.tabelaSimbolos.setTipoConst(Tipo.BOOLEANO);
		this.tabelaSimbolos.setValConst(this.tabelaSimbolos.getValor(id));
	}

	/**
	 * #159 a #163 – TipoCte := tipo da constante ValCte := valor da constante
	 *
	 * @param token
	 * @throws SemanticError
	 */
	@SuppressWarnings("unused")
	private void executeAction163(Token token) throws SemanticError {
		String id = token.getLexeme();
		this.tabelaSimbolos.setTipoConst(this.tabelaSimbolos.getTipoByValor(id));
		this.tabelaSimbolos.setValConst(this.tabelaSimbolos.getValor(id));
	}

}
