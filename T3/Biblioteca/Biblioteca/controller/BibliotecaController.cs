using Biblioteca.model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Biblioteca.controller
{
    public class BibliotecaController
    {
        public BibliotecaModel biblioteca { get; set; }

        public BibliotecaController()
        {
            biblioteca = new BibliotecaModel();
            biblioteca.livros = this.populateLivros();
            biblioteca.usuariosDaBiblioteca = this.populateUsuarios();
        }

        public bool VerificaMatricula(string matricula)
        {
            foreach (Usuario u in biblioteca.usuariosDaBiblioteca)
            {
                if (u.matricula.Equals(matricula))
                {
                    return true;
                }
            }
            return false;
        }

        public void PegaLivro(Livro livro, string matricula)
        {
            foreach (Usuario u in biblioteca.usuariosDaBiblioteca)
            {
                if (u.matricula.Equals(matricula))
                {
                    u.livrosEmprestados.Add(livro);
                    biblioteca.livros.Remove(livro);
                    livro.dataEmprestimo = DateTime.Now;
                }
            }           
        }

        public bool DevolverLivro(Livro livro, string matricula)
        {
            foreach (Usuario u in biblioteca.usuariosDaBiblioteca)
            {
                if (u.matricula.Equals(matricula))
                {
                    bool b = u.livrosEmprestados.Contains(livro);
                    if (b)
                    {
                        biblioteca.livros.Add(livro);
                        livro.dataDevolucao = DateTime.Now;
                        biblioteca.ordenaLivros();
                    }
                    return b;
                }
            }
            return false;
        }

        public List<Livro> LivrosDoUsuario(string matricula)
        {
            foreach (Usuario u in biblioteca.usuariosDaBiblioteca)
            {
                if (u.matricula.Equals(matricula))
                {
                    return u.livrosEmprestados.FindAll(l => (l.dataDevolucao.Equals(null))); // se nao tem data de devolução é pq nao foi devolvido
                }
            }
            return new List<Livro>();
        }

        public List<Livro> TodosLivrosDoUsuario(string matricula)
        {
            foreach (Usuario u in biblioteca.usuariosDaBiblioteca)
            {
                if (u.matricula.Equals(matricula))
                {
                    return u.livrosEmprestados;
                }
            }
            return new List<Livro>();
        }

        private List<Usuario> populateUsuarios()
        {
            List<Usuario> usuariosDaBiblioteca = new List<Usuario>();

            for (int i = 1; i <= 5; i++)
            {
                Usuario u = new Usuario();
                u.nome = "Nome usuario" + i;
                u.matricula = "" + i;
                usuariosDaBiblioteca.Add(u);
            }

            return usuariosDaBiblioteca;
        }

        private List<Livro> populateLivros()
        {
            List<Livro> livros = new List<Livro>();

            for (int i = 1; i <= 20; i++)
            {
                Livro livro = new Livro();
                livro.id = i;
                livro.autor = "Autor Teste";
                livro.titulo = "Titulo Teste" + i;
                livros.Add(livro);
            }

            return livros;
        }

    }
}
