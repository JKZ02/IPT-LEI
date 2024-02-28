using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ICT.MM.Core.DTO;
using ICT.MM.DAL.DB;

namespace ICT.MM.BLL
{
    public class ScenarioDevicesBLL
    {

        public static void InsertScenarioDevices(InsertScenarioDeviceRequestDTO dto)
        {
            using (ICTDbContext iCTDbContext = new ICTDbContext())
            {
                if (iCTDbContext.ScenarioDevices.Find(dto.Id_Device, dto.Id_Device) == null)
                {
                    ScenarioDevice sd = new ScenarioDevice();
                
                    sd.Id_Device = dto.Id_Device;
                    sd.ManufacturedDate = dto.ManufacturedDate;
                    sd.LastMaintenanceDate = dto.LastMaintenanceDate;
                    sd.MaintenanceDueDate = dto.MaintenanceDueDate;
                    sd.OriginalState = dto.OriginalState;
                    sd.CurrentState = dto.CurrentState;

                    iCTDbContext.ScenarioDevices.Add(sd);

                    iCTDbContext.SaveChanges();
                }
            }
        }

        public static void DeleteScenarioDevices(DeleteScenarioDeviceRequestDTO dto)
        {
            using (ICTDbContext iCTDbContext = new ICTDbContext())
            {
                if (iCTDbContext.ScenarioDevices.Find(dto.Id_Device, dto.Id_Scenario) != null)
                {
                    iCTDbContext.ScenarioDevices.Remove(iCTDbContext.ScenarioDevices.Find(dto.Id_Device, dto.Id_Scenario));

                    iCTDbContext.SaveChanges();
                }
            }
        }

        public static void UpdateScenarioDevices(UpdateScenarioDeviceRequestDTO dto)
        {
            using (ICTDbContext iCTDbContext = new ICTDbContext())
            {
                if (iCTDbContext.ScenarioDevices.Find(dto.Id_Device, dto.Id_Scenario) != null)
                {
                    ScenarioDevice sd = iCTDbContext.ScenarioDevices.Find(dto.Id_Device);

                    sd.ManufacturedDate = dto.ManufacturedDate;
                    sd.LastMaintenanceDate = dto.LastMaintenanceDate;
                    sd.MaintenanceDueDate = dto.MaintenanceDueDate;
                    sd.OriginalState = dto.OriginalState;
                    sd.CurrentState = dto.CurrentState;

                    iCTDbContext.SaveChanges();
                }
            }
        }

        public static ListScenarioDeviceResponseDTO ListScenarioDevices()
        {
            using (ICTDbContext iCTDbContext = new ICTDbContext())
            {
                List<ListItemScenarioDeviceResponseDTO> listItemScenarioDeviceResponseDTOs = iCTDbContext.ScenarioDevices
                        .Select(x => new ListItemScenarioDeviceResponseDTO { Id_Device = x.Id_Device, ManufacturedDate = x.ManufacturedDate,
                                LastMaintenanceDate = x.LastMaintenanceDate, MaintenanceDueDate = x.MaintenanceDueDate, 
                                OriginalState = x.OriginalState, CurrentState = x.CurrentState 
                        }).ToList();

                return new ListScenarioDeviceResponseDTO {Items = listItemScenarioDeviceResponseDTOs, Total = listItemScenarioDeviceResponseDTOs.Count()};
            }
        }

    }
}