using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Biblioteca.model
{
    public class BibliotecaModel
    {
        public List<Livro> livros { get; set; }

        public List<Usuario> usuariosDaBiblioteca { get; set; }

        public void ordenaLivros()
        {
            List<Livro> SortedList = livros.OrderBy(l => l.id).ToList();
            livros = SortedList;
        }   
    }
}
