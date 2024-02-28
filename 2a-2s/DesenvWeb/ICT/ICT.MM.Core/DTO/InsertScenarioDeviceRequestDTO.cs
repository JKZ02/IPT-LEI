using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ICT.MM.Core.DTO
{
    public class InsertScenarioDeviceRequestDTO
    {
        public int Id_Scenario { get; set; }
        public int Id_Device { get; set; }
        public DateTime? ManufacturedDate { get; set; }
        public DateTime? LastMaintenanceDate { get; set; }
        public DateTime? MaintenanceDueDate { get; set; }
        public string OriginalState { get; set; }
        public string CurrentState { get; set; }

    }
}