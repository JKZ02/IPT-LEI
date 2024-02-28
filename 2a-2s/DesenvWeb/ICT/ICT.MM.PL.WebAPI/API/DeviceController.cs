using ICT.MM.Core.DTO;
using Microsoft.AspNetCore.Mvc;
using System.Web.Http.Cors;

namespace ICT.MM.PL.WebAPI.API
{
    [EnableCors("*", "*", "*")]
    [ApiController]
    [Route("[controller]")]
    public class DeviceController : ControllerBase
    {

        [HttpGet(Name = "GetDevices")]
        public ListDeviceResponseDTO GetList()
        {
            return DeviceBLL.ListDevices();
        }

        [HttpDelete(Name = "DeleteDevice")]
        public void DeleteDevice(DeleteDeviceRequestDTO dto)
        {
            DeviceBLL.DeleteDevice(dto);
        }

        [HttpPut(Name = "InsertDevice")]
        public void InsertDevice(InsertDeviceRequestDTO dto)
        {
            DeviceBLL.InsertDevice(dto);
        }

        [HttpPatch(Name = "UpdateDevice")]
        public void UpdateDevice(UpdateDeviceRequestDTO dto)
        {
            DeviceBLL.UpdateDevice(dto);
        }
    }
}
