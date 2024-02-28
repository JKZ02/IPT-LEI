using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ICT.MM.Core.DTO;
using ICT.MM.DAL.DB;

namespace ICT.MM.BLL
{
    public class DeviceTypesBLL
    {

        public static void InsertDeviceType(InsertDeviceTypeRequestDTO dto)
        {
            using (ICTDbContext iCTDbContext = new ICTDbContext())
            {
                if (iCTDbContext.DeviceTypes.Find(dto.Id) == null)
                {
                    DeviceType sc = new DeviceType();
                    sc.Name = dto.Name;
                    sc.Description = dto.Description;
                    sc.Id = dto.Id;

                    iCTDbContext.DeviceTypes.Add(sc);

                    iCTDbContext.SaveChanges();
                }
            }
        }

        public static void DeleteDeviceType(DeleteDeviceTypeRequestDTO dto)
        {
            using (ICTDbContext iCTDbContext = new ICTDbContext())
            {
                iCTDbContext.DeviceTypes.RemoveRange(iCTDbContext.DeviceTypes.Where(x => x.Id == dto.Id));

                if (iCTDbContext.DeviceTypes.Find(dto.Id) != null)
                {
                    iCTDbContext.DeviceTypes.Remove(iCTDbContext.DeviceTypes.Find(dto.Id));

                    iCTDbContext.SaveChanges();
                }
            }
        }

        public static void UpdateDeviceType(UpdateDeviceTypeRequestDTO dto)
        {
            using (ICTDbContext iCTDbContext = new ICTDbContext())
            {
                DeviceType sc = iCTDbContext.DeviceTypes.Find(dto.Id);

                sc.Name = dto.Name;

                sc.Description = dto.Description;

                iCTDbContext.SaveChanges();
            }
        }

        public static ListDeviceTypesResponseDTO ListDeviceTypes()
        {

            using (ICTDbContext iCTDbContext = new ICTDbContext())
            {

                List<ListItemDeviceTypesResponseDTO> listItemDeviceTypeResponseDTOs = iCTDbContext.DeviceTypes
                        .Select(x => new ListItemDeviceTypesResponseDTO { Id = x.Id, Name = x.Name, Description = x.Description }).ToList();

                return new ListDeviceTypesResponseDTO { Items = listItemDeviceTypeResponseDTOs, Total = listItemDeviceTypeResponseDTOs.Count() };
            }
        }

    }
}