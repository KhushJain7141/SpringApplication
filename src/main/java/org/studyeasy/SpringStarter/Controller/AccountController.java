package org.studyeasy.SpringStarter.Controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.studyeasy.SpringStarter.models.Account;
import org.studyeasy.SpringStarter.services.AccountService;
import org.studyeasy.SpringStarter.services.EmailService;
import org.studyeasy.SpringStarter.util.constants.email.emailDetails;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;



@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;
    
    @Autowired
    EmailService emailService;

    @Value("${site.domain}")
    private String site_domain;

    @Value("${app.upload-dir}")  // Define the upload directory in application.properties
    private String uploadDir;

    @Value("${password.token.reset.timeout.minutes}")
    private int password_token_timeout;

    @GetMapping("/register")
    public String register(Model model) {
        Account account = new Account();
        model.addAttribute("account", account);
        return "account_views/register";
    }

    @PostMapping("/register")
    public String register_user(@Valid @ModelAttribute Account account, BindingResult result) {
        if (result.hasErrors()) {
            return "account_views/register";
        }
        accountService.save(account);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "account_views/login";
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model, Principal principal) {
        String authuser = principal != null ? principal.getName() : "email";
        Optional<Account> optionalAccount = accountService.findOneByEmail(authuser);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            model.addAttribute("account", account);
            model.addAttribute("photo", account.getPhoto());
            return "account_views/profile";
        } else {
            return "redirect:/?error";
        }
    }

    @PostMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String post_profile(@Valid @ModelAttribute Account account, BindingResult bindingResult, Principal principal, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "account_views/profile";
        }
        String authuser = principal != null ? principal.getName() : "email";
        Optional<Account> optionalAccount = accountService.findOneByEmail(authuser);
        if (optionalAccount.isPresent()) {
            Account account_by_id = accountService.findOneById(account.getId()).get();
            account_by_id.setAge(account.getAge());
            account_by_id.setDate_of_birth(account.getDate_of_birth());
            account_by_id.setFirstname(account.getFirstname());
            account_by_id.setGender(account.getGender());
            account_by_id.setLastname(account.getLastname());
            account_by_id.setPassword(account.getPassword());
            accountService.save(account_by_id);
            request.getSession().invalidate();
            SecurityContextHolder.clearContext();
            return "redirect:/";
        } else {
            return "redirect:/?error";
        }
    }

    @PostMapping("/update_photo")
    @PreAuthorize("isAuthenticated()")
    public String update_photo(@RequestParam("file") MultipartFile file, RedirectAttributes attributes, Principal principal) {
        if (file.isEmpty()) {
            attributes.addFlashAttribute("error", "No File uploaded");
            return "redirect:/profile";
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            // Generate a random name for the file
            String generatedString = RandomStringUtils.randomAlphanumeric(10);
            String final_photo_name = generatedString + "_" + fileName;

            // Path where the uploaded file will be saved
            Path path = Paths.get(uploadDir).resolve(final_photo_name);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            String authUser = principal != null ? principal.getName() : "email";
            Optional<Account> optional_Account = accountService.findOneByEmail(authUser);
            if (optional_Account.isPresent()) {
                Account account_by_id = optional_Account.get();
                // Save the relative path to the uploaded file
                String relative_fileLocation = "/uploads/" + final_photo_name;
                account_by_id.setPhoto(relative_fileLocation);
                accountService.save(account_by_id);
                attributes.addFlashAttribute("message", "Photo updated successfully!");
            }
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "File upload failed!");
        }
        return "redirect:/profile";
    }
    @GetMapping("/forgot-password")
    public String forgot_password(Model model) {
        return "account_views/forgot_password";
    }
    @PostMapping("/reset-password")
    public String reset_password(@RequestParam("email") String _email, RedirectAttributes attributes, Model model) {
        Optional<Account> optional_account = accountService.findOneByEmail(_email);
        if (optional_account.isPresent()) {
            Account account = optional_account.get();
            String reset_token = UUID.randomUUID().toString();
            account.setToken(reset_token);
            account.setPassword_reset_token_expiry(LocalDateTime.now().plusMinutes(password_token_timeout));
            accountService.save(account);
           String reset_message= "This is the reset password link: "+site_domain+"change-password?token="+reset_token;
             emailDetails emaildetails = new emailDetails(account.getEmail(),reset_message,"Reset password Study Easy Demo");
             if(emailService.sendSimpleEmail(emaildetails) ==false)
             {
                attributes.addFlashAttribute("error","Error while sending email contact admin");
             }
            attributes.addFlashAttribute("message", "Password reset email sent.");
            return "redirect:/login";
        } else {
            attributes.addFlashAttribute("error", "No user found with this email.");
            return "redirect:/forgot-password";
        }
    }
    
    @GetMapping("/change-password")
    public String change_password(@RequestParam("token")String token,RedirectAttributes attributes, Model model) {
        if(token.equals(""))
        {
            attributes.addFlashAttribute("error","Invalid Token");
            return "redirect:/forgot-password";
        }
       Optional<Account> optional_account = accountService.findByToken(token);
       if(optional_account.isPresent())
       {
          Account account = accountService.findOneById(optional_account.get().getId()).get();
          LocalDateTime now = LocalDateTime.now();
         if (now.isAfter(optional_account.get().getPassword_reset_token_expiry()))
         {
            attributes.addFlashAttribute("error", "Token Expired");
            return "redirect:/forgot-password";
         }
          model.addAttribute("account",account);
          return "account_views/change_password";
       }
       else {
        attributes.addFlashAttribute("error", "Invalid token");
        return "redirect:/forgot-password";
    }
    }
    @PostMapping("/change-password")
    public String post_change_password(@ModelAttribute Account account,RedirectAttributes attributes)
    {
        Account account_by_id = accountService.findOneById(account.getId()).get();
        account_by_id.setPassword(account.getPassword());
        account_by_id.setToken("");
        accountService.save(account_by_id);
        attributes.addFlashAttribute("message","Password Updated");
        return "redirect:/login";
    }
    
}
