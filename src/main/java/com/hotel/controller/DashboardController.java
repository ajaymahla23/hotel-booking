package com.hotel.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hotel.Repo.AgentInviteCodeRepo;
import com.hotel.Repo.MyOrderRepository;
import com.hotel.Repo.RechargeWalletRepo;
import com.hotel.Repo.RoleTypeRepo;
import com.hotel.Repo.UserRepo;
import com.hotel.Repo.WalletRepo;
import com.hotel.Repo.WithdrawalRequestRepo;
import com.hotel.model.AgentInviteCode;
import com.hotel.model.HotelInfo;
import com.hotel.model.MyOrder;
import com.hotel.model.RechargeWallet;
import com.hotel.model.RoleType;
import com.hotel.model.User;
import com.hotel.model.Wallet;
import com.hotel.service.HotelInfoService;
import com.hotel.service.MyOrderService;
import com.hotel.service.WalletService;

@Controller
public class DashboardController {
	@Autowired
	RechargeWalletRepo rechargeWalletRepo;
	@Autowired
	HotelInfoService hotelInfoService;
	@Autowired
	UserRepo userRepo;
	@Autowired
	RoleTypeRepo roleTypeRepo;
	@Autowired
	WalletRepo walletRepo;
	@Autowired
	WalletService walletService;
	@Autowired
	WithdrawalRequestRepo withdrawalRequestRepo;
	@Autowired
	AgentInviteCodeRepo agentInviteCodeRepo;
	@Autowired
	MyOrderService myOrderService;
	@Autowired
	MyOrderRepository myOrderRepo;

	@Value("${hotelbooking.image}")
	private String uploadDirHotelImg;

	@GetMapping("/personal-profile")
	public String viewPersonalProfile(Model model, Principal principal) {
		User user = userRepo.findByEmail(principal.getName());
		model.addAttribute("userAdmin", user);
		List<User> userList = userRepo.findAll();
		model.addAttribute("userList", userList);
		return "dashboard/personalProfile";
	}

	@GetMapping("/reachargeWalletlist")
	public String listofReachargeWallet(Model model) {
		model.addAttribute("rechargeDetail", rechargeWalletRepo.findAll(Sort.by(Sort.Direction.DESC, "id")));
		model.addAttribute("wallet", new Wallet());
		return "dashboard/listofReachargeWallet";
	}

	@PostMapping("/addAmountOnWallet")
	public String addAmountOnWallet(@ModelAttribute("wallet") Wallet wallet, RedirectAttributes redirectAttributes,
			@RequestParam Long rechargeId) {

		RechargeWallet rechargeWallet = rechargeWalletRepo.findById(rechargeId).get();
		Wallet wallet1 = walletRepo.findByUser(rechargeWallet.getUser());

		if (wallet1 == null) {
			walletService.addWalletAmtAndId(wallet, rechargeWallet.getRoleType(), rechargeId);
		} else {
			double oldWalletAmount = wallet1.getWalletAmount();
			double newWalletAmount = oldWalletAmount + rechargeWallet.getAmountByUser();
			walletService.updateAmountByUser(newWalletAmount, rechargeWallet.getUser(), rechargeId);
		}
		redirectAttributes.addFlashAttribute("message", "Amount has been added!!!");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/reachargeWalletlist";
	}

	@GetMapping("/hotelInfo")
	public String viewHotelInfo(Model model) {
		model.addAttribute("hotelInfo", new HotelInfo());
		return "dashboard/hotel_info";
	}

	@PostMapping("/addHotelInfo")
	public String addHotelInfo(@ModelAttribute("hotelInfo") HotelInfo hotelInfo,
			@RequestParam("hotelImage") MultipartFile file) throws IOException {
		String imageUUID;
		if (!file.isEmpty()) {
			imageUUID = UUID.randomUUID().toString(); // generate a unique identifier
			Path fileNameAndPath = Paths.get(uploadDirHotelImg, imageUUID);
			Files.write(fileNameAndPath, file.getBytes());
		} else {
			imageUUID = hotelInfo.getHotelImage();
		}
		hotelInfo.setHotelImage(imageUUID);
		hotelInfoService.addHotelInfo(hotelInfo);
		return "redirect:/hotelInfo";
	}

	@GetMapping("/addhotelimg/{imageName}")
	public ResponseEntity<byte[]> getHotelImage(@PathVariable String imageName) throws IOException {
		uploadDirHotelImg = uploadDirHotelImg.trim().replaceAll("\\s", "");
		Path imagePath = Paths.get(uploadDirHotelImg, imageName);
		byte[] imageBytes = Files.readAllBytes(imagePath);
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
	}

	@GetMapping("/roles")
	public String viewrole(Model model) {
		model.addAttribute("roleType", new RoleType());
		return "Role";
	}

	@PostMapping("/addRoleInfo")
	public String addRole(@ModelAttribute("roleType") RoleType roleType, Model model) {
		roleTypeRepo.save(roleType);
		return "redirect:/roles"; // Assuming you want to redirect to the roles view after saving.
	}

	@GetMapping("/meruser")
	public String viewmerchant(Model model) {
		List<RoleType> roles = roleTypeRepo.findAll();
		model.addAttribute("roles", roles);
		model.addAttribute("user", new User());
		// model.addAttribute("user", new YourUserModel()); // You may need to replace
		// YourUserModel with your actual user model
		return "dashboard/merchant_user"; // Replace "yourTemplate" with the actual name of your Thymeleaf template
	}

	@PostMapping("/addUser")
	public String addUser(@ModelAttribute("user") User user, Model model) {
		userRepo.save(user);
		return "dashboard/merchant_user";
	}

	@GetMapping("/payout")
	public String listofWithdrawlRequest(Model model) {
		model.addAttribute("withdrawRequest", withdrawalRequestRepo.findAll());
		return "dashboard/listofWithdrawlReq";
	}

	@PostMapping("/addAmountforWithdraw")
	public String addAmountforWithdraw(RedirectAttributes redirectAttributes, @RequestParam Long withdrawReqid) {
		walletService.updateTotalWalletAmt(withdrawReqid);
		redirectAttributes.addFlashAttribute("message", "Amount has been added!!!");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/withdralReqlist";
	}

	@GetMapping("/transaction")
	public String viewTransaction(Model model) {
		model.addAttribute("rechargeDetail", rechargeWalletRepo.findAll(Sort.by(Sort.Direction.DESC, "id")));
		return "dashboard/transaction";
	}

	@PostMapping("/update_transaction")
	public String updateTransaction(@RequestParam("freezeAmt") double freezeAmt,
			@RequestParam("rechargeId") Long rechargeId) {
		RechargeWallet rechargeWallet = rechargeWalletRepo.findById(rechargeId).get();
		Wallet wallet = walletRepo.findByUser(rechargeWallet.getUser());
		double userWalletAmt = wallet.getWalletAmount() - freezeAmt;

		rechargeWallet.setFreezeAmt(freezeAmt);
		rechargeWallet.setAfterAmt(userWalletAmt);
		wallet.setWalletAmount(userWalletAmt);
		rechargeWalletRepo.save(rechargeWallet);
		walletRepo.save(wallet);
		return "redirect:/transaction";
	}

	@GetMapping("/membership-management")
	public String viewMember(Model model) {
		List<AgentInviteCode> agentInviteCodeList = agentInviteCodeRepo.findAll();
		model.addAttribute("agentInviteCodeList", agentInviteCodeList);

		return "dashboard/membership_management";
	}

	@GetMapping("/orderlist")
	public String viewOrderlist(Model model) {
		List<MyOrder> myOrderList = myOrderRepo.findAll();
		model.addAttribute("myOrderList", myOrderList);
		return "dashboard/order_list";
	}

}
