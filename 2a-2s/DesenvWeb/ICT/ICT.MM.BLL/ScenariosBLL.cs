using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ICT.MM.Core.DTO;
using ICT.MM.DAL.DB;

namespace ICT.MM.BLL {
    public class ScenariosBLL {

        public static void InsertScenario(InsertScenarioRequestDTO dto)
        {
            using (ICTDbContext iCTDbContext = new ICTDbContext())
            {
                if (iCTDbContext.Scenarios.Find(dto.Id) == null)
                {
                    Scenario sc = new Scenario();
                    sc.Name = dto.Name;
                    sc.Description = dto.Description;
                    sc.Id = dto.Id;

                    iCTDbContext.Scenarios.Add(sc);

                    iCTDbContext.SaveChanges();
                }
            }
        }
        
        public static void DeleteScenario(DeleteScenarioRequestDTO dto)
        {
            using (ICTDbContext iCTDbContext = new ICTDbContext())
            {
                if(iCTDbContext.Scenarios.Find(dto.Id) != null)
                {
                    iCTDbContext.Scenarios.Remove(iCTDbContext.Scenarios.Find(dto.Id));

                    if(iCTDbContext.ScenarioDevices.Where(x => x.Id_Scenario == dto.Id).Any())
                        iCTDbContext.ScenarioDevices.RemoveRange(iCTDbContext.ScenarioDevices.Where(x => x.Id_Scenario == dto.Id));

                    iCTDbContext.SaveChanges();
                }                
            }
        }

        public static void UpdateScenario(UpdateScenarioRequestDTO dto)
        {
            using (ICTDbContext iCTDbContext = new ICTDbContext())
            {
                Scenario sc = iCTDbContext.Scenarios.Find(dto.Id);

                sc.Name = dto.Name;

                sc.Description = dto.Description;

                iCTDbContext.SaveChanges();
            }
        }

        public static ListScenarioResponseDTO ListScenarios() {

            using (ICTDbContext iCTDbContext = new ICTDbContext()){ 

            List<ListItemScenarioResponseDTO> listItemScenarioResponseDTOs = iCTDbContext.Scenarios
                    .Select(x => new ListItemScenarioResponseDTO { Id = x.Id, Name = x.Name, Description = x.Description}).ToList();

                return new ListScenarioResponseDTO {Items = listItemScenarioResponseDTOs, Total = listItemScenarioResponseDTOs.Count()};
            }
        }

    }
}
