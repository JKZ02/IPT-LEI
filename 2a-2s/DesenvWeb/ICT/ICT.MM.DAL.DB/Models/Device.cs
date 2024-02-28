using System.ComponentModel.DataAnnotations;
using Microsoft.EntityFrameworkCore;

namespace ICT.MM.DAL.DB
{
	/// <summary>
	/// Device Class
	/// </summary>
	public class Device {
		/// <summary>
		/// Chave primaria para os devices
		/// </summary>
		[Key]
		public int Id { get; set; }
		/// <summary>
		/// Chave forasteira para o DeviceType
		/// </summary>
		[Display(Name="Tipo de Device")]
		public int Id_DeviceType { get; set; }
		/// <summary>
		/// Nome do device
		/// 32 Character Limit
		/// </summary>
		[Required]
		[StringLength(32)]
		[Display(Name="Nome do Dispositivo")]
		public string Name { get; set; }
		/// <summary>
		/// Descrição do device
		/// 64 Character Limit
		/// </summary>
		[Required]
		[StringLength(32)]
		[Display(Name="Descrição do Dispositivo")]
		public string Description { get; set; }
		/// <summary>
		/// Data de fabricação do dispositivo
		/// </summary>
		[Display(Name="Data de Fabricação")]
		public DateTime? ManufacturedDate { get; set; }
		/// <summary>
		/// Data da última manutenção dos dispositivo
		/// </summary>
		[Display(Name="Data da última manutenção")]
		public DateTime? LastMaintenanceDate { get; set; }
		/// <summary>
		/// Data da próxima manutenção
		/// </summary>
		[Display(Name="Data da próxima manutenção")]
		public DateTime? MaintenanceDueDate { get; set; }
		/// <summary>
		/// Nome do frabicante do dispositivo
		/// </summary>
		[Display(Name="Fabricado por")]
		public string ManufacturedBy { get; set; }
		/// <summary>
		/// Utilizador que adicionou o dispositivo ao sistema
		/// </summary>
		[Display(Name="Criado por")]
		public string CreatedBy { get; set; }
		/// <summary>
		/// Data a que o dispositivo foi adicionado ao sistema
		/// </summary>
		[Display(Name="Data de criação")]
		public DateTime? CreatedDate { get; set; }
		/// <summary>
		/// Utilizador que modifico o dispositivo pela última vez
		/// </summary>
		[Display(Name="Modificado por")]
		public string ModifiedBy { get; set; }
		/// <summary>
		/// Data da última modificação
		/// </summary>
		[Display(Name="Data da última modificação")]
		public DateTime? ModifiedDate { get; set; }

		public ICollection<ScenarioDevice> ScenarioDevices { get; set; }
		/// <summary>
		/// Tipo de dispositivo
		/// </summary>
		[Display(Name="Tipo de dispositivo")]
		public DeviceType DeviceType { get; set; }

		/// <summary>
		/// Configura as relações
		/// </summary>
		/// <param name="modelBuilder"></param>
		public static void ConfigureRelations(ModelBuilder modelBuilder)
		{
			//Relationship from Device to ScenarioDevices
			modelBuilder.Entity<ScenarioDevice>()
				.HasOne(d => d.Device)
				.WithMany(dt => dt.ScenarioDevices)
				.HasForeignKey(d => d.Id_Device);
		}

	}

}