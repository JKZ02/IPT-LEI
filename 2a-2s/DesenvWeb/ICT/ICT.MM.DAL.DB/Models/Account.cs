using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ICT.MM.DAL.DB.Models {
    /// <summary>
    /// Dados para as contas
    /// </summary>
    public class Account {
        /// <summary>
        /// Chave primaria para as contas de utilizador
        /// </summary>
        public int Id { get; set; }
        /// <summary>
        /// Nome de utilizador para a contade de utilizador
        /// </summary>
        [Display(Name="Nome de Utilizador")]
        public string Username { get; set; }
        /// <summary>
        /// Palavra Passe da conta de utilizador
        /// </summary>
        [Display(Name = "Palavra Passe")]
        public string Password { get; set; }
        /// <summary>
        /// Nome do utilizador da conta
        /// </summary>
        [Display(Name = "Nome")]
        public string Name { get; set; }
        /// <summary>
        /// Cargo do utilizador da conta
        /// </summary>
        [Display(Name = "Cargo")]
        public string Role { get; set; }
    }
}
