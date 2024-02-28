using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;
using System.ComponentModel.DataAnnotations;

namespace ICT.MM.DAL.DB
{
    /// <summary>
    /// ScenarioDevice class
    /// </summary>
    public class ScenarioDevice {
        /// <summary>
        /// Parte da chave primaria e chave estrangeira para os Scenarios
        /// </summary>
        [Display(Name ="Cenário")]
        public int Id_Scenario { get; set; }

        /// <summary>
        /// Parte da chave primaria e chave estrangeira para os Devices
        /// </summary>
        [Display(Name = "Dispositivo")]
        public int Id_Device { get; set; }

        /// <summary>
        /// Data de fabricação
        /// </summary>
        [Required]
        [Display(Name = "Data de fabricação")]
        public DateTime? ManufacturedDate { get; set; }

        /// <summary>
        /// Data da última manutenção
        /// </summary>
        [Display(Name = "Data da última manutenção")]
        public DateTime? LastMaintenanceDate { get; set; }

        /// <summary>
        /// Data da próxima manutenção
        /// </summary>
        [Display(Name = "Data da próxima manutenção")]
        public DateTime? MaintenanceDueDate { get; set; }

        /// <summary>
        /// Estado original
        /// </summary>
        [Required]
        [StringLength(64)]
        [Display(Name = "Estado Original")]
        public string OriginalState { get; set; }

        /// <summary>
        /// Estado atual 
        /// </summary>
        [Required]
        [StringLength(64)]
        [Display(Name = "Estado Atual")]
        public string CurrentState { get; set; }
        /// <summary>
        /// Scenario
        /// </summary>
        [Display(Name = "Cenário")]
        public Scenario Scenario { get; set; }
        /// <summary>
        /// Device
        /// </summary>
        [Display(Name = "Dispositivo")]
        public Device Device { get; set; }
        /// <summary>
        /// Configurar as relações e as chaves
        /// </summary>
        /// <param name="modelBuilder"></param>
        public static void ConfigureRelations(ModelBuilder modelBuilder)
        {
            //Defined primary key
            modelBuilder.Entity<ScenarioDevice>()
                .HasKey(sd => new { sd.Id_Scenario, sd.Id_Device });
        }
    }
}
