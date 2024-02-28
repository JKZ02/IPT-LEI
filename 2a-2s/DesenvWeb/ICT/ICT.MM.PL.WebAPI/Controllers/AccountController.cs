using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using ICT.MM.DAL.DB.Models;
using ICT.MM.DAL.DB;
using System.Security.Claims;
using Microsoft.AspNetCore.Authentication.Cookies;
using Microsoft.AspNetCore.Authentication;
using System.Diagnostics;
using System.Security.Cryptography;
using System.Text;
using System.Web.Http.Controllers;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using Microsoft.AspNetCore.Authorization;
using System.Security.Principal;

namespace ICT.MM.PL.WebAPI.Controllers {
    public class AccountController : Controller {

        ICTDbContext db = new ICTDbContext();

        // GET: AccountController
        public ActionResult Index()
        {
            return View();
        }

        public ActionResult Registration()
        {
            return View();
        }
        [HttpPost]
        public ActionResult Registration(Account acc)
        {
            acc.Role = "User";

            var VerifyUsername = db.Accounts.Where(a=> a.Username==acc.Username);
            if(VerifyUsername != null)
            {
                ViewData["ErrorUser"] = "O nome de utilizador introduzido já existe";
            }
            else
            {
                ViewData["ErrorUser"] = "";
            }

            if (StrongPassword(acc.Password)){
                acc.Password = HashPassword(acc.Password);
                db.Accounts.Add(acc);
                db.SaveChanges();
                ViewData["ErrorPass"] = "";
                return RedirectToAction("Login", "Account");
            }
            else
            {
                ViewData["ErrorPass"] = "A palavra passe tem de ter pelo menos uma letra maiuscula, uma letra minuscula e um número";
            }
            return View();

        }

        public ActionResult Login()
        {
            return View();
        }

        public ActionResult Forbidden()
        {
            return View();
        }

        [HttpPost]
        public async Task<IActionResult> Login(Account acc)
        {
            var lg = db.Accounts.Where(x => x.Username == acc.Username && x.Password == HashPassword(acc.Password)).FirstOrDefault();
            if (lg == null)
            {
                return RedirectToAction("Login", "Account");
            }

            var claims = new List<Claim>
            {
                new Claim(ClaimTypes.Name, lg.Username),
                new Claim("Role", lg.Role),
            };

            var claimsIdentity = new ClaimsIdentity(
                claims, CookieAuthenticationDefaults.AuthenticationScheme);

            var authProperties = new AuthenticationProperties
            {
                AllowRefresh = false,
                // Refreshing the authentication session should be allowed.

                //ExpiresUtc = DateTimeOffset.UtcNow.AddMinutes(10),
                // The time at which the authentication ticket expires. A 
                // value set here overrides the ExpireTimeSpan option of 
                // CookieAuthenticationOptions set with AddCookie.

                IsPersistent = true,
                // Whether the authentication session is persisted across 
                // multiple requests. When used with cookies, controls
                // whether the cookie's lifetime is absolute (matching the
                // lifetime of the authentication ticket) or session-based.

                //IssuedUtc = <DateTimeOffset>,
                // The time at which the authentication ticket was issued.

                //RedirectUri = <string>
                // The full path or absolute URI to be used as an http 
                // redirect response value.
            };

            await HttpContext.SignInAsync(CookieAuthenticationDefaults.AuthenticationScheme, new ClaimsPrincipal(claimsIdentity), authProperties);

            return RedirectToAction("Index", "Home");
        }

        public async Task<IActionResult> Logout()
        {
            // Clear the existing external cookie
            await HttpContext.SignOutAsync(
                CookieAuthenticationDefaults.AuthenticationScheme);
            return RedirectToAction("Login", "Account");
        }

        //GET
        [Authorize(Policy = "LoggedIn")]
        public async Task<IActionResult> ChangePassword(string username)
        {
            if (username == null || db.Accounts == null)
            {
                return NotFound();
            }

            var acc = await db.Accounts.Where(a => a.Username==username).FirstOrDefaultAsync();
            if (acc == null)
            {
                return NotFound();
            }

            return View(acc);
        }

        [HttpPost]
        [Authorize(Policy = "LoggedIn")]
        public async Task<IActionResult> ChangePassword(string CurPass,string NewPass1,string NewPass2,[Bind("Id,Username,Password,Name,Role")] Account acc)
        {
            var verifyAcc = db.Accounts.Where(a=> a.Id == acc.Id);
            if (!(verifyAcc.Any(a => a.Username == acc.Username && a.Password == acc.Password) && HashPassword(CurPass) == acc.Password))
            {
                //logout
                await HttpContext.SignOutAsync(
                CookieAuthenticationDefaults.AuthenticationScheme);
                return RedirectToAction("Account", "Login");
            }
            if(NewPass1 != NewPass2)
            {
                ViewData["ErrorPassDiff"] = "As palavras passe não são iguais.";
                return RedirectToAction("Account", "ChangePassword");
            }

            if (StrongPassword(NewPass1))
            {

                acc.Password = HashPassword(NewPass1);
                db.Update(acc);
                await db.SaveChangesAsync();

                return RedirectToAction("Index","Home");
            }
            else
            {
                ViewData["ErrorPassNotStrong"] = "A palavra passe tem de conter pelo menos uma letra maiúscula, uma letra minúscula, um número e um caracter especial";
                return View(acc);
            }

           
        }

         public string HashPassword(string password)
        {
            var sha = SHA256.Create();

            var asByteArray = Encoding.Default.GetBytes(password);
            var hashedPassword = sha.ComputeHash(asByteArray);

            return Convert.ToBase64String(hashedPassword);
        }

        public Boolean StrongPassword(string password)
        {
            if(password == null)
                return false;
            if (password.Length < 8)
                return false;
            if (!password.Any(c => char.IsDigit(c)))
                return false;
            if (!password.Any(c => char.IsUpper(c)))
                return false;
            if(!password.Any(c => char.IsLower(c)))
                return false;
            //if (!password.Any(c => char.IsSymbol(c)))
                //return false;

            return true;
        }
    }
}
