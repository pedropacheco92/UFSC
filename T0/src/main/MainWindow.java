package main;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import modelos.Modelos;
import modelos.Pilha;
import modelos.Ponto;
import poo.edgraf.reprodutor.Quadro;
import t2.bd.CSVWriter;

public class MainWindow {

    private JFrame frame;

    private Pilha<Ponto> pilha;

    private Modelos modelo;

    String textoInformado;

    PrintWriter writer;

    public MainWindow() {
        this.initialize();
        pilha = new Pilha<>();
    }

    /**
     * Initialize the contents of the frame.
     *
     * @wbp.parser.entryPoint
     */
    private void initialize() {
        this.frame = new JFrame();
        this.frame.setVisible(true);
        this.frame.setBounds(100, 100, 450, 300);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.getContentPane().setLayout(null);

        Quadro panel = new Quadro();
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pilha.push(new Ponto(e.getX(), e.getY()));
            }

        });

        panel.setBounds(0, 0, 434, 240);
        this.frame.getContentPane().add(panel);

        JMenuBar menuBar = new JMenuBar();
        this.frame.setJMenuBar(menuBar);

        JMenu mnModelos = new JMenu("Menu");
        menuBar.add(mnModelos);

        JMenuItem salvar = new JMenuItem("Salvar");
        salvar.addActionListener(l -> this.salvar());
        mnModelos.add(salvar);
    }

    private void salvar() {
        try {
            
                CSVWriter.writePontos(pilha);
            
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
