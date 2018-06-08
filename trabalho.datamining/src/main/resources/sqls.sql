select identificador_caso, concat(nome_popular_comercial, ', ', substancia_genero_especie, ', ', subclasse_agente, ', ', classe_agente) as nome from "06_agente_intoxicante";

select paciente.idade, COALESCE(paciente.peso, '0') as peso, paciente.sexo, manifestacao.manifestacao_apresentada, manifestacao.classificacao_manifestacao, concat(tratamento.grupo_tratamento, ': ', tratamento.subgrupo_tratamento) as tratamento from "04_paciente" paciente
join "07_manifestacao" manifestacao on paciente.identificador_caso = manifestacao.identificador_caso
join "08_tratamento" tratamento on manifestacao.identificador_caso = tratamento.identificador_caso;