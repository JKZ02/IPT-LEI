using ICT.MM.BLL;
using ICT.MM.Core.DTO;
using Microsoft.AspNetCore.Mvc;
using System.Web.Http.Cors;

namespace ICT.MM.PL.WebAPI.API
{
    [EnableCors("*", "*", "*")]
    [ApiController]
    [Route("[controller]")]
    public class DeviceTypeController : ControllerBase
    {

        [HttpGet(Name = "GetDeviceTypes")]
        public ListDeviceTypesResponseDTO GetList()
        {
            return DeviceTypesBLL.ListDeviceTypes();
        }

        [HttpDelete(Name = "DeleteDeviceType")]
        public void DeleteDeviceType(DeleteDeviceTypeRequestDTO dto)
        {
            DeviceTypesBLL.DeleteDeviceType(dto);
        }

        [HttpPut(Name = "InsertDeviceType")]
        public void InsertDeviceType(InsertDeviceTypeRequestDTO dto)
        {
            DeviceTypesBLL.InsertDeviceType(dto);
        }

        [HttpPatch(Name = "UpdateDeviceType")]
        public void UpdateDevice(UpdateDeviceTypeRequestDTO dto)
        {
            DeviceTypesBLL.UpdateDeviceType(dto);
        }
    }
}
