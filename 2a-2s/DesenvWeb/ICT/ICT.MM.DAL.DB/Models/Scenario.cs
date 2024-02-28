using System.ComponentModel.DataAnnotations;
using System.Reflection.Emit;
using Microsoft.EntityFrameworkCore;

namespace ICT.MM.DAL.DB
{
    public class Scenario {
        /// <summary>
        /// Chave primaria para os cenarios
        /// </summary>
        [Key]
        public int Id { get; set; }

        /// <summary>
        /// Nome do Scenario
        /// </summary>
        [Required]
        [StringLength(64)]
        [Display(Name="Nome")]
        public String Name { get; set; }

        /// <summary>
        /// Descrição do Scenario
        /// </summary>
        [Required]
        [StringLength(64)]
        [Display(Name="Descrição")]
        public String Description { get; set; }

        public ICollection<ScenarioDevice> ScenarioDevices { get; set; }
        /// <summary>
        /// Configuração dos Scenarios
        /// </summary>
        /// <param name="modelBuilder"></param>
        public static void ConfigureRelations(ModelBuilder modelBuilder)
        {
            //Relationship from Scenario to ScenarioDevices
            modelBuilder.Entity<ScenarioDevice>()
                .HasOne(d => d.Scenario)
                .WithMany(dt => dt.ScenarioDevices)
                .HasForeignKey(d => d.Id_Scenario);
        }
    }
}
