package t2.bd;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import modelos.Figura;
import modelos.Pilha;
import modelos.Ponto;

public class CSVWriter {

	public static void writeFiguras(Figura figura) throws IOException {
		PrintWriter writer = new PrintWriter(new File(CSVUtils.FIGURAS));
		CSVUtils.writeLine(writer, Arrays.asList("Nome Figura", "Quantidade de pontos", "Pontos"));	
                writer.flush();
		writer.close();
	}
        
        public static void writePontos(Pilha pilha) throws IOException {
		PrintWriter writer = new PrintWriter(new File(CSVUtils.FIGURAS));
		CSVUtils.writeLine(writer, Arrays.asList("x", "y"));
                
                while (!pilha.empty()) {
                    Ponto ponto = (Ponto) pilha.pop();
                    CSVUtils.writeLine(writer, Arrays.asList(String.valueOf(ponto.getX()), String.valueOf(ponto.getY())));   
                }                                      
                writer.flush();
		writer.close();
	}

}
