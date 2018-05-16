import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jdk.nashorn.internal.runtime.regexp.joni.ast.ConsAltNode;

public class Atividade {

    public static void main(String[] args) {
        Map<String, Map<String, Vocabulario>> map = new HashMap<>();

        String text1 = "Abordagem mais conservadora: indexar o conteúdo completo do texto disponível. Completude de conteúdo disponível no momento da busca; uso de termos pouco significativos (ruído); abrangência do resultado obtido, tamanho do índice gerado";
        String text2 = "Abordagem menos conservadora: indexar conteúdo selecionado. Apenas conteúdo relevante disponível no momento da busca; desempenho de busca; perda de termos significativos para alguns contextos; abrangência do resultado obtido";

        List<String> list1 = text1.split(" ");

        for (i = 0; i < list1.size(); i++) {
            s = list1.get(i);
            if (map.containsKey(s)) {
                Vocabulario v = map.get(s).get("1");
                v.pos.get(1).add(i);
            } else {
                Vocabulario v = new Vocabulario();
                v.valor = s;
                List<Integer> list = new ArrayList<>();
                list.add(i);
                v.pos.put(1, list);


                Map<String, Vocabulario> map2 = new HashMap<>();
                map2.put("1", v);
                map.put(s, map2);
            }
        }

        System.out.println(map);

        
    }

    class Vocabulario {

        public String valor;
        public Map<Integer, List<Integer>> pos = new HashMap<>();  // doc, list pos

        public String toString() {
            String x = valor;

            x.concat(" / ");
            x.concat(String.valueOf(pos.size()));
            x.concat(" / ");
            for (Integer i : pos.keySet()) {

            }
 
            return x;
        }

    }

}