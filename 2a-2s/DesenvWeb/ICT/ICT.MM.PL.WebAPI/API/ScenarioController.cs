using ICT.MM.BLL;
using ICT.MM.Core.DTO;
using Microsoft.AspNetCore.Mvc;

namespace ICT.MM.PL.WebAPI.API
{
    [ApiController]
    [Route("[controller]")]
    public class ScenarioController : ControllerBase
    {
        [HttpGet(Name = "GetScenarios")]
        public ListScenarioResponseDTO GetList()
        {
            return ScenariosBLL.ListScenarios();
        }

        [HttpDelete(Name = "DeleteScenario")]
        public void DeleteScenario(DeleteScenarioRequestDTO dto)
        {
            ScenariosBLL.DeleteScenario(dto);
        }

        [HttpPut(Name = "InsertScenarios")]
        public void InsertScenario(InsertScenarioRequestDTO dto)
        {
            ScenariosBLL.InsertScenario(dto);
        }

        [HttpPatch(Name = "UpdateScenarios")]
        public void UpdateScenario(UpdateScenarioRequestDTO dto)
        {
            ScenariosBLL.UpdateScenario(dto);
        }
    }
}
