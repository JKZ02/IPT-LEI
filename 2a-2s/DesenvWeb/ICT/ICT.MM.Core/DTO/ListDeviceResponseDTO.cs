using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ICT.MM.Core.DTO {
    public class ListDeviceResponseDTO {
        public int Total { get; set; }

        public List<ListItemDeviceResponseDTO> Items { get; set; }
    }
    public class ListItemDeviceResponseDTO {
        public int Id { get; set; }
        public int Id_DeviceType { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
        public DateTime? ManufacturedDate { get; set; }
        public DateTime? LastMaintenanceDate { get; set; }
        public DateTime? MaintenanceDueDate { get; set; }
        public string ManufacturedBy { get; set; }
        public string CreatedBy { get; set; }
        public DateTime? CreatedDate { get; set; }
        public string ModifiedBy { get; set; }
        public DateTime? ModifiedDate { get; set; }
    }
}
