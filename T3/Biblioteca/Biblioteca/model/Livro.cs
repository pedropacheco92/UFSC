using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Biblioteca.model
{    
    public class Livro
    {
        public string titulo { get; set; }

        public string autor { get; set; }

        public int id { get; set; }

        public DateTime? dataEmprestimo { get; set; }

        public DateTime? dataDevolucao { get; set; }

    }
}
