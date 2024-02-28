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
    [ResponseCache(Location = ResponseCacheLocation.None, NoStore = true)]
    [Authorize(Policy = "LoggedIn")]
    public class DeviceTypesController : Controller
    {
        private readonly ICTDbContext _context;

        public DeviceTypesController(ICTDbContext context)
        {
            _context = context;
        }

        // GET: DeviceTypes
        public async Task<IActionResult> Index()
        {
              return _context.DeviceTypes != null ? 
                          View(await _context.DeviceTypes.ToListAsync()) :
                          Problem("Entity set 'ICTDbContext.DeviceTypes'  is null.");
        }

        // GET: DeviceTypes/Details/5
        public async Task<IActionResult> Details(int? id)
        {
            if (id == null || _context.DeviceTypes == null)
            {
                return NotFound();
            }

            var deviceType = await _context.DeviceTypes
                .FirstOrDefaultAsync(m => m.Id == id);
            if (deviceType == null)
            {
                return NotFound();
            }

            return View(deviceType);
        }

        // GET: DeviceTypes/Create
        [Authorize(Policy = "Admin")]
        public IActionResult Create()
        {
            return View();
        }

        // POST: DeviceTypes/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        [Authorize(Policy = "Admin")]
        public async Task<IActionResult> Create([Bind("Id,Name,Description")] DeviceType deviceType)
        {
            if (ModelState.IsValid)
            {
                _context.Add(deviceType);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            return View(deviceType);
        }

        // GET: DeviceTypes/Edit/5
        [Authorize(Policy = "Admin")]
        public async Task<IActionResult> Edit(int? id)
        {
            if (id == null || _context.DeviceTypes == null)
            {
                return NotFound();
            }

            var deviceType = await _context.DeviceTypes.FindAsync(id);
            if (deviceType == null)
            {
                return NotFound();
            }
            return View(deviceType);
        }

        // POST: DeviceTypes/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        [Authorize(Policy = "Admin")]
        public async Task<IActionResult> Edit(int id, [Bind("Id,Name,Description")] DeviceType deviceType)
        {
            if (id != deviceType.Id)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(deviceType);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!DeviceTypeExists(deviceType.Id))
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
            return View(deviceType);
        }

        // GET: DeviceTypes/Delete/5
        [Authorize(Policy = "Admin")]
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null || _context.DeviceTypes == null)
            {
                return NotFound();
            }

            var deviceType = await _context.DeviceTypes
                .FirstOrDefaultAsync(m => m.Id == id);
            if (deviceType == null)
            {
                return NotFound();
            }

            return View(deviceType);
        }

        // POST: DeviceTypes/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        [Authorize(Policy = "Admin")]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            if (_context.DeviceTypes == null)
            {
                return Problem("Entity set 'ICTDbContext.DeviceTypes'  is null.");
            }
            var deviceType = await _context.DeviceTypes.FindAsync(id);

            if (deviceType != null)
            {
                _context.DeviceTypes.Remove(deviceType);
            }


            var devices = _context.Devices.Where(m => m.Id_DeviceType == id);
            if (devices != null)
            {
                _context.Devices.RemoveRange(devices);
            }
            
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool DeviceTypeExists(int id)
        {
          return (_context.DeviceTypes?.Any(e => e.Id == id)).GetValueOrDefault();
        }
    }
}
