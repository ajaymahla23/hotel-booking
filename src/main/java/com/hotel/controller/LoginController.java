package com.hotel.controller;

import java.security.Principal;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hotel.Repo.AgentInviteCodeRepo;
import com.hotel.Repo.MyBankCardRepo;
import com.hotel.Repo.UserRepo;
import com.hotel.Repo.WalletRepo;
import com.hotel.model.AgentInviteCode;
import com.hotel.model.User;
import com.hotel.model.Wallet;
import com.hotel.service.RechargeWalletService;
import com.hotel.service.UserService;

@Controller
public class LoginController {

	@Autowired
	RechargeWalletService rechargeWalletService;
	@Autowired
	UserService userService;
	@Autowired
	UserRepo userRepo;
	@Autowired
	MyBankCardRepo myBankCardRepo;
	@Autowired
	AgentInviteCodeRepo agentInviteCodeRepo;
	@Autowired
	WalletRepo walletRepo;

	@GetMapping("/login")
	public String viewlogin(Model model) {
		return "login";
	}

	@GetMapping("/admin-login")
	public String viewtlogin() {
		return "login/admin_login";

	}

	@GetMapping("/register")
	public String viewRegister(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@PostMapping("/addRegister")
	public String addNewUser(@ModelAttribute("user") User user, Model model,
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement,
			@RequestParam("agentInvitationCode") String agentInvitationCode, RedirectAttributes redirectAttributes,
			Principal principal) {

		User user11 = userRepo.findByInvitationCode(Long.parseLong(agentInvitationCode));

		if (userRepo.existsByEmailOrMobileNumber(user.getEmail(), user.getMobileNumber())) {
			redirectAttributes.addFlashAttribute("errorMessage",
					"Please check e-mail or mobile no. is already exist!!!");
		} else {

			Long agentInvitationCode1 = Long.parseLong(agentInvitationCode);

			// Check if agentInvitationCode exists in the user table
			if (userRepo.existsByInvitationCode(agentInvitationCode1)) {
				Random random = new Random();
				Long inviteNo = random.nextLong(999999);

				user.setInvitationCode(inviteNo);
				while (userRepo.existsByInvitationCode(user.getInvitationCode())) {
					inviteNo = random.nextLong(999999);
					user.setInvitationCode(inviteNo);
				}

				user.setRole("ROLE_USER");
				user.setEnabled(true);
				user.setDate(new Date());
				user.setPass(10L);
				userService.addUserRegister(user);

				AgentInviteCode agentInviteCode = new AgentInviteCode();
				agentInviteCode.setAgentCode(agentInvitationCode);
				agentInviteCode.setUser(user);
				agentInviteCode.setAgentName(user11.getFullName());

				agentInviteCodeRepo.save(agentInviteCode);

				Wallet wallet = new Wallet();
				wallet.setUser(user);
				wallet.setWalletAmount(32);
				wallet.setBookPass(16L);
				walletRepo.save(wallet);

				model.addAttribute("user", new User());
			} else {
				redirectAttributes.addFlashAttribute("errorMessage", "Invalid invitation code!!!");
			}
		}
		return "redirect:/register";
	}

	@GetMapping("/change-password")
	public String changePassword(Principal principal, Model model) {
		User userDetail = userRepo.findByEmail(principal.getName());
		model.addAttribute("userDetail", userDetail);
		return "update_password";
	}

	@PostMapping("/update-password")
	public String updateUserPassword(Principal principal, @RequestParam("newpassword") String newpassword) {
		User user = userRepo.findByEmail(principal.getName());
		userRepo.updateUserPassword(newpassword, user.getId());
		return "redirect:/login";
	}

}
