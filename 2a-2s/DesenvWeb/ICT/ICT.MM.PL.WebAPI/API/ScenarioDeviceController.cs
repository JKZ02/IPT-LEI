using ICT.MM.BLL;
using ICT.MM.Core.DTO;
using Microsoft.AspNetCore.Mvc;

namespace ICT.MM.PL.WebAPI.API
{
    [ApiController]
    [Route("[controller]")]
    public class ScenarioDeviceController : ControllerBase
    {
        [HttpGet(Name = "GetScenarioDevices")]
        public ListScenarioDeviceResponseDTO GetList()
        {
            return ScenarioDevicesBLL.ListScenarioDevices();
        }

        [HttpDelete(Name = "DeleteScenarioDevices")]
        public void DeleteScenarioDevices(DeleteScenarioDeviceRequestDTO dto)
        {
            ScenarioDevicesBLL.DeleteScenarioDevices(dto);
        }

        [HttpPut(Name = "InsertScenarioDevices")]
        public void InsertScenarioDevices(InsertScenarioDeviceRequestDTO dto)
        {
            ScenarioDevicesBLL.InsertScenarioDevices(dto);
        }

        [HttpPatch(Name = "UpdateScenarioDevices")]
        public void UpdateScenarioDevices(UpdateScenarioDeviceRequestDTO dto)
        {
            ScenarioDevicesBLL.UpdateScenarioDevices(dto);
        }
    }
}
