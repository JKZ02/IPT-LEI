using Microsoft.AspNetCore.Diagnostics;
using Microsoft.AspNetCore.Mvc;

namespace ICT.MM.PL.WebAPI.Controllers
{
    public class HomeController : Controller
    {
        public IActionResult Index()
        {
            return View();
        }

        public IActionResult Error()
        {
            return View("~/Views/Shared/Error.cshtml");
        }

        public IActionResult About()
        {
            return View("~/Views/Shared/About.cshtml");
        }

        public IActionResult Credits()
        {
            return View("~/Views/Shared/Credits.cshtml");
        }
    }
}
