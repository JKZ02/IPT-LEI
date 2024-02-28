using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ICT.MM.Core.DTO {
    public class ListScenarioResponseDTO {
        public  int Total { get; set; }

        public List<ListItemScenarioResponseDTO> Items { get; set; }
    }
    public class ListItemScenarioResponseDTO {
        public int Id { get; set; }

        public string Name { get; set; }

        public string Description { get; set; }
    }
}
