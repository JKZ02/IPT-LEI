using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using ICT.MM.DAL.DB;
using Microsoft.AspNetCore.Authorization;

namespace ICT.MM.PL.WebAPI.Controllers
{
    [Authorize(Policy = "LoggedIn")]
    public class ScenarioDevicesController : Controller
    {
        private readonly ICTDbContext _context;

        public ScenarioDevicesController(ICTDbContext context)
        {
            _context = context;
        }

        // GET: ScenarioDevices
        public async Task<IActionResult> Index()
        {
            var iCTDbContext = _context.ScenarioDevices.Include(s => s.Device).Include(s => s.Scenario);
            return View(await iCTDbContext.ToListAsync());
        }

        // GET: ScenarioDevices/Details/5
        public async Task<IActionResult> Details(int? idScen, int? idDevi)
        {
            if (idScen == null || idDevi == null || _context.ScenarioDevices == null)
            {
                return NotFound();
            }

            var scenarioDevice = await _context.ScenarioDevices
                .Include(s => s.Device)
                .Include(s => s.Scenario)
                .FirstOrDefaultAsync(m => m.Id_Scenario == idScen && m.Id_Device == idDevi);
            if (scenarioDevice == null)
            {
                return NotFound();
            }

            return View(scenarioDevice);
        }

        // GET: ScenarioDevices/Create
        [Authorize(Policy = "Admin")]
        public IActionResult Create()
        {
            ViewData["Id_Device"] = new SelectList(_context.Devices, "Id", "Name");
            ViewData["Id_Scenario"] = new SelectList(_context.Scenarios, "Id", "Name");
            return View();
        }

        // POST: ScenarioDevices/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        [Authorize(Policy = "Admin")]
        public async Task<IActionResult> Create([Bind("Id_Scenario,Id_Device,ManufacturedDate,LastMaintenanceDate,MaintenanceDueDate,OriginalState,CurrentState")] ScenarioDevice scenarioDevice)
        {
            if (ModelState.IsValid)
            {
                _context.Add(scenarioDevice);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            ViewData["Id_Device"] = new SelectList(_context.Devices, "Id", "Name", scenarioDevice.Id_Device);
            ViewData["Id_Scenario"] = new SelectList(_context.Scenarios, "Id", "Name", scenarioDevice.Id_Scenario);
            return View(scenarioDevice);
        }

        // GET: ScenarioDevices/Edit?idScen=2&idDevi=6
        [Authorize(Policy = "Admin")]
        public async Task<IActionResult> Edit(int? idScen, int? idDevi)
        {
            if (idScen == null || idDevi == null || _context.ScenarioDevices == null)
            {
                return NotFound();
            }

            var scenarioDevice = await _context.ScenarioDevices.FindAsync(idScen,idDevi);
            if (scenarioDevice == null)
            {
                return NotFound();
            }
            return View(scenarioDevice);
        }

        // POST: ScenarioDevices/Edit?idScen=2&idDevi=6
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        [Authorize(Policy = "Admin")]
        public async Task<IActionResult> Edit([Bind("Id_Scenario,Id_Device,ManufacturedDate,LastMaintenanceDate,MaintenanceDueDate,OriginalState,CurrentState")] ScenarioDevice scenarioDevice)
        {
            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(scenarioDevice);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!ScenarioDeviceExists(scenarioDevice.Id_Scenario, scenarioDevice.Id_Device))
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
            return View(scenarioDevice);
        }

        // GET: ScenarioDevices/Delete/5
        [Authorize(Policy = "Admin")]
        public async Task<IActionResult> Delete(int? idScen, int? idDevi)
        {
            if (idScen == null || idDevi == null || _context.ScenarioDevices == null)
            {
                return NotFound();
            }

            var scenarioDevice = await _context.ScenarioDevices
                .Include(s => s.Device)
                .Include(s => s.Scenario)
                .FirstOrDefaultAsync(m => m.Id_Scenario == idScen && m.Id_Device == idDevi);
            if (scenarioDevice == null)
            {
                return NotFound();
            }

            return View(scenarioDevice);
        }

        // POST: ScenarioDevices/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        [Authorize(Policy = "Admin")]
        public async Task<IActionResult> DeleteConfirmed(ScenarioDevice sc)
        {
            if (_context.ScenarioDevices == null)
            {
                return Problem("Entity set 'ICTDbContext.ScenarioDevices'  is null.");
            }
            var scenarioDevice = await _context.ScenarioDevices.FindAsync(sc.Id_Scenario, sc.Id_Device);
            if (scenarioDevice != null)
            {
                _context.ScenarioDevices.Remove(scenarioDevice);
            }
            
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool ScenarioDeviceExists(int idScen, int idDevi)
        {
          return (_context.ScenarioDevices?.Any(e => e.Id_Scenario == idScen && e.Id_Device == idDevi)).GetValueOrDefault();
        }
    }
}
