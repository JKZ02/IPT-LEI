using System.ComponentModel.DataAnnotations;
using Microsoft.EntityFrameworkCore;

namespace ICT.MM.DAL.DB
{
    public class DeviceType {
        /// <summary>
        /// Chave Primaria para o DeviceType
        /// </summary>
        [Key]
        public int Id { get; set; }

        /// <summary>
        /// Nome do DeviceType
        /// </summary>
        [Required]
        [StringLength(32)]
        [Display(Name="Nome")]
        public string Name { get; set; }

        /// <summary>
        /// Descrição do DeviceType
        /// </summary>
        [Required]
        [StringLength(64)]
        [Display(Name="Descrição")]
        public string Description { get; set; }

        public ICollection<Device> Devices { get; set; }
        /// <summary>
        /// Configurar as relações
        /// </summary>
        /// <param name="modelBuilder"></param>
        public static void ConfigureRelations(ModelBuilder modelBuilder)
        {
            //Relationship from DeviceType to Devices
            modelBuilder.Entity<Device>()
                .HasOne(d => d.DeviceType)
                .WithMany(dt => dt.Devices)
                .HasForeignKey(d => d.Id_DeviceType);
        }
    }

}


