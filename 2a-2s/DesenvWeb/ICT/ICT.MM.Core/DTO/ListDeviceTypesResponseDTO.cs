using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ICT.MM.Core.DTO
{
    public class ListDeviceTypesResponseDTO
    {
        public int Total { get; set; }

        public List<ListItemDeviceTypesResponseDTO> Items { get; set; }
    }
    public class ListItemDeviceTypesResponseDTO
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
        
    }
}
