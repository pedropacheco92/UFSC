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
    /// Interaction logic for DevolverLivroView.xaml
    /// </summary>
    public partial class DevolverLivroView : Window
    {
        public BibliotecaController controller { get; set; }

        public DevolverLivroView()
        {
            InitializeComponent();
        }

        private void button_cancelar_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        private void button_devolver_Click(object sender, RoutedEventArgs e)
        {
            Livro livro = (Livro)comboBox.SelectedItem;
            string matricula = this.textBox_matricula.Text;
            controller.DevolverLivro(livro, matricula);
            this.updateComboBox();
            MessageBox.Show("Livro devolvido com sucesso!");
        }

        private void textBox_matricula_LostFocus(object sender, RoutedEventArgs e)
        {
            if (controller.VerificaMatricula(textBox_matricula.Text))
            {
                this.updateComboBox();
            }
        }

        private void updateComboBox()
        {
            comboBox.ItemsSource = null; // para limpar o combo!!
            comboBox.ItemsSource = controller.LivrosDoUsuario(textBox_matricula.Text);
            comboBox.DisplayMemberPath = "titulo";
            comboBox.SelectedValuePath = "id";
        }

    }
}
