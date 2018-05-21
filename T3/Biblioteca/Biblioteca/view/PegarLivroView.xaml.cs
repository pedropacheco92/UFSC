using Biblioteca.controller;
using Biblioteca.model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace Biblioteca.view
{
    /// <summary>
    /// Interaction logic for PegarLivroView.xaml
    /// </summary>
    public partial class PegarLivroView : Window
    {

        public BibliotecaController controller { get; set; }

        public PegarLivroView()
        {
            InitializeComponent();
        }

        private void button_cancelar_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }     

        private void button_pegar_Click(object sender, RoutedEventArgs e)
        {
            Livro livro = (Livro)comboBox.SelectedItem;
            string matricula = this.textBox_matricula.Text;
            if (!controller.VerificaMatricula(matricula))
            {
                MessageBox.Show("Não existe essa matrícula!");
            }
            else
            {
                controller.PegaLivro(livro, matricula);
                this.updateComboBox();
                MessageBox.Show("Livro obtido com sucesso!");
            }
        }

        public void updateComboBox()
        {
            comboBox.ItemsSource = null; // para limpar o combo!!
            comboBox.ItemsSource = controller.biblioteca.livros;
            comboBox.DisplayMemberPath = "titulo";
            comboBox.SelectedValuePath = "id";
        }
    }
}
