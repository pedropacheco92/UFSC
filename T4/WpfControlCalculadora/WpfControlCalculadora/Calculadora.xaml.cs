using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace WpfControlCalculadora
{
    /// <summary>
    /// Interaction logic for UserControl1.xaml
    /// </summary>
    public partial class Calculadora : UserControl
    {

        private enum Operacao { Soma, Sub, Div, Mult, Igual }

        private Operacao OpAtual = Operacao.Igual;

        private bool depoisOperacao = false;

        private string num_1 = "";

        public Calculadora()
        {
            InitializeComponent();
            textBox.IsReadOnly = true;
        }

        private double calculate(string num_2)
        {
            // verifica se foi digitado somente virgula, caso sim, ele entende como 0 pois quebra a conversao
            if (",".Equals(this.num_1))
            {
                num_1 = "0";
            }
            if (",".Equals(num_2))
            {
                num_2 = "0";
            }
            double d1 = Convert.ToDouble(this.num_1);
            double d2 = Convert.ToDouble(num_2);
            double value = 0.0;
            switch (OpAtual)
            {
                case Operacao.Soma:
                    value = d1 + d2;
                    return value;
                case Operacao.Sub:
                    value = d1 - d2;
                    return value;
                case Operacao.Div:
                    value = d1 / d2;
                    return value;
                case Operacao.Mult:
                    value = d1 * d2;
                    return value;
                case Operacao.Igual:
                    return d1;
                default:
                    return d1;
            }


        }
    
        private void btn_C_Click(object sender, RoutedEventArgs e)
        {
            textBox.Text = "";
        }

        private void btn_9_Click(object sender, RoutedEventArgs e)
        {
            addNumero("9");
        }

        private void btn_8_Click(object sender, RoutedEventArgs e)
        {
            addNumero("8");
        }

        private void btn_7_Click(object sender, RoutedEventArgs e)
        {
            addNumero("7");
        }

        private void btn_6_Click(object sender, RoutedEventArgs e)
        {
            addNumero("6");
        }

        private void btn_5_Click(object sender, RoutedEventArgs e)
        {
            addNumero("5");
        }

        private void btn_4_Click(object sender, RoutedEventArgs e)
        {
            addNumero("4");
        }

        private void btn_3_Click(object sender, RoutedEventArgs e)
        {
            addNumero("3");
        }

        private void btn_2_Click(object sender, RoutedEventArgs e)
        {
            addNumero("2");
        }

        private void btn_1_Click(object sender, RoutedEventArgs e)
        {
            addNumero("1");
        }

        private void btn_0_Click(object sender, RoutedEventArgs e)
        {
            addNumero("0");
        }

        private void btn_virg_Click(object sender, RoutedEventArgs e)
        {
            addNumero(",");
        }

        private void addNumero(string numero)
        {
            if (depoisOperacao)
            {
                textBox.Text = "";
            }
            textBox.Text += numero;
            depoisOperacao = false;
        }

        private void btn_igual_Click(object sender, RoutedEventArgs e)
        {
            double resultado = calculate(textBox.Text);
            string resultadoStirng = Convert.ToString(resultado);
            textBox.Text = resultadoStirng;
            depoisOperacao = true;
        }

        private void btn_soma_Click(object sender, RoutedEventArgs e)
        {
            OpAtual = Operacao.Soma;
            num_1 = textBox.Text;
            depoisOperacao = true;
        }

        private void btn_div_Click(object sender, RoutedEventArgs e)
        {
            OpAtual = Operacao.Div;
            num_1 = textBox.Text;
            depoisOperacao = true;
        }

        private void btn_sub_Click(object sender, RoutedEventArgs e)
        {
            OpAtual = Operacao.Sub;
            num_1 = textBox.Text;
            depoisOperacao = true;
        }

        private void btn_mult_Click(object sender, RoutedEventArgs e)
        {
            OpAtual = Operacao.Mult;
            num_1 = textBox.Text;
            depoisOperacao = true;
        }

        private void NumberValidationTextBox(object sender, TextCompositionEventArgs e)
        {
            Regex regex = new Regex("[^0-9]+");
            e.Handled = regex.IsMatch(e.Text);
        }
    }
}
