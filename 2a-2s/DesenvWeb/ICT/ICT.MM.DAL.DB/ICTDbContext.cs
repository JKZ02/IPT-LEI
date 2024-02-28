using ICT.MM.DAL.DB.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;

namespace ICT.MM.DAL.DB 
{
    public class ICTDbContext : DbContext{

        public DbSet<DeviceType> DeviceTypes { get; set; }
        public DbSet<Device> Devices { get; set; }
        public DbSet<Scenario> Scenarios { get; set; }
        public DbSet<ScenarioDevice> ScenarioDevices { get; set; }
        public DbSet<Account> Accounts { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            IConfigurationRoot configuration = new ConfigurationBuilder()
                .SetBasePath(AppDomain.CurrentDomain.BaseDirectory)
                .AddJsonFile("appsettings.json")
                .Build();
            optionsBuilder.UseSqlServer(configuration.GetConnectionString("ICTDatabase"));
        }
        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            // Defined database relations
            DeviceType.ConfigureRelations(modelBuilder);
            Device.ConfigureRelations(modelBuilder);
            Scenario.ConfigureRelations(modelBuilder);
            // Defined primary key
            ScenarioDevice.ConfigureRelations(modelBuilder);

        }
    }
}
