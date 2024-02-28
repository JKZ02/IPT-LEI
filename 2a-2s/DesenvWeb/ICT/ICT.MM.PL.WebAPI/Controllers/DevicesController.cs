using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using System.Security;
using ICT.MM.DAL.DB;
using System.Web.Http;
using HttpPostAttribute = Microsoft.AspNetCore.Mvc.HttpPostAttribute;
using ActionNameAttribute = Microsoft.AspNetCore.Mvc.ActionNameAttribute;
using Microsoft.AspNetCore.Authorization.Policy;
using Microsoft.AspNetCore.Authorization;
using AuthorizeAttribute = Microsoft.AspNetCore.Authorization.AuthorizeAttribute;

namespace ICT.MM.PL.WebAPI.Controllers
{
    [ResponseCache(Location = ResponseCacheLocation.None, NoStore = true)]
    [Authorize(Policy = "LoggedIn")]
    public class DevicesController : Controller
    {
        private readonly ICTDbContext _context;

        public DevicesController(ICTDbContext context)
        {
            _context = context;
        }

        // GET: Devices

        public async Task<IActionResult> Index()
        {
            var iCTDbContext = _context.Devices.Include(d => d.DeviceType);

            return View(await iCTDbContext.ToListAsync());
        }

        // GET: Devices/Details/5
        public async Task<IActionResult> Details(int? id)
        {
            if (id == null || _context.Devices == null)
            {
                return NotFound();
            }

            var device = await _context.Devices
                .Include(d => d.DeviceType)
                .FirstOrDefaultAsync(m => m.Id == id);
            if (device == null)
            {
                return NotFound();
            }

            return View(device);
        }

        // GET: Devices/Create
        [Authorize(Policy = "Admin")]
        public IActionResult Create()
        {
            ViewData["Id_DeviceType"] = new SelectList(_context.DeviceTypes, "Id", "Name");
            return View();
        }

        // POST: Devices/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        [Authorize(Policy = "Admin")]
        public async Task<IActionResult> Create([Bind("Id,Id_DeviceType,Name,Description,ManufacturedDate,LastMaintenanceDate,MaintenanceDueDate,ManufacturedBy,CreatedBy,CreatedDate,ModifiedBy,ModifiedDate")] Device device)
        {
            if (ModelState.IsValid)
            {
                device.LastMaintenanceDate = null;
                device.ModifiedDate = null;
                device.ModifiedBy = null;
                device.CreatedDate = DateTime.Now;
                device.CreatedBy = User.Identity.Name;
                _context.Add(device);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            ViewData["Id_DeviceType"] = new SelectList(_context.DeviceTypes, "Id", "Name", device.Id_DeviceType);
            return View(device);
        }

        // GET: Devices/Edit/5
        [Authorize(Policy = "Admin")]
        public async Task<IActionResult> Edit(int? id)
        {
            if (id == null || _context.Devices == null)
            {
                return NotFound();
            }

            var device = await _context.Devices.FindAsync(id);
            if (device == null)
            {
                return NotFound();
            }
            ViewData["Id_DeviceType"] = new SelectList(_context.DeviceTypes, "Id", "Name", device.Id_DeviceType);
            return View(device);
        }

        // POST: Devices/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        [Authorize(Policy = "Admin")]
        public async Task<IActionResult> Edit(int id, [Bind("Id,Id_DeviceType,Name,Description,ManufacturedDate,LastMaintenanceDate,MaintenanceDueDate,ManufacturedBy,CreatedBy,CreatedDate,ModifiedBy,ModifiedDate")] Device device)
        {
            if (id != device.Id)
            {
                return NotFound();
            }
            if (ModelState.IsValid)
            {
                
                try
                {
                    device.ModifiedDate = DateTime.Now;
                    device.ModifiedBy = User.Identity.Name;

                    _context.Update(device);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!DeviceExists(device.Id))
                    {
                        return NotFound();
                    }
                    else
                    {
                        throw;
                    }
                }
                return RedirectToAction(nameof(Index));
            }
            ViewData["Id_DeviceType"] = new SelectList(_context.DeviceTypes, "Id", "Name", device.Id_DeviceType);
            return View(device);
        }

        // GET: Devices/Delete/5
        [Authorize(Policy = "Admin")]
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null || _context.Devices == null)
            {
                return NotFound();
            }

            var device = await _context.Devices
                .Include(d => d.DeviceType)
                .FirstOrDefaultAsync(m => m.Id == id);
            if (device == null)
            {
                return NotFound();
            }

            return View(device);
        }

        // POST: Devices/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        [Authorize(Policy = "Admin")]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            if (_context.Devices == null)
            {
                return Problem("Entity set 'ICTDbContext.Devices'  is null.");
            }
            var device = await _context.Devices.FindAsync(id);
            if (device != null)
            {
                _context.Devices.Remove(device);
            }

            var scenarioDevices = _context.ScenarioDevices.Where(m => m.Id_Device == id);

            if (scenarioDevices != null)
            {
                _context.ScenarioDevices.RemoveRange(scenarioDevices);
            }
            
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool DeviceExists(int id)
        {
          return (_context.Devices?.Any(e => e.Id == id)).GetValueOrDefault();
        }
    }
}
