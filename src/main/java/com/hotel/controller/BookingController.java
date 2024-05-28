package com.hotel.controller;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hotel.Repo.HotelInfoRepo;
import com.hotel.Repo.MyBankCardRepo;
import com.hotel.Repo.MyOrderRepository;
import com.hotel.Repo.UserRepo;
import com.hotel.Repo.WalletRepo;
import com.hotel.Repo.WithdrawalRequestRepo;
import com.hotel.model.HotelInfo;
import com.hotel.model.MyBankCard;
import com.hotel.model.MyOrder;
import com.hotel.model.RechargeWallet;
import com.hotel.model.User;
import com.hotel.model.Wallet;
import com.hotel.model.WithdrawalRequest;
import com.hotel.service.MyOrderService;
import com.hotel.service.RechargeWalletService;
import com.hotel.service.UserService;
import com.hotel.service.WalletService;

@Controller
public class BookingController {
	@Autowired
	RechargeWalletService rechargeWalletService;
	@Autowired
	UserService userService;
	@Autowired
	UserRepo userRepo;
	@Autowired
	MyBankCardRepo myBankCardRepo;
	@Autowired
	WalletRepo walletRepo;
	@Autowired
	WithdrawalRequestRepo withdrawalRequestRepo;
	@Autowired
	WalletService walletService;
	@Autowired
	HotelInfoRepo hotelInfoRepo;
	@Autowired
	MyOrderService myOrderService;
	@Autowired
	MyOrderRepository myOrderRepo;

	@ModelAttribute
	public void commonMethod(Principal principal, Model model) {
		User user = userRepo.findByEmail(principal.getName());
		if (model.containsAttribute("userDetail")) {
			model.addAttribute("userDetail", user);
		}
		if (model.containsAttribute("walletAmtList")) {
			Wallet walletAmtList = walletRepo.findByUser(user);
			model.addAttribute("walletAmtList", walletAmtList);
		}
	}

	@GetMapping("/home")
	public String viewhome(Principal principal, Model model) {
		User userDetail = userRepo.findByEmail(principal.getName());
		model.addAttribute("userDetail", userDetail);

		Wallet walletAmtList = walletRepo.findByUser(userDetail);
		model.addAttribute("walletAmtList", walletAmtList);

		List<HotelInfo> hotelInfoList = hotelInfoRepo.findAll();
		model.addAttribute("hotelInfoList", hotelInfoList);
		return "index";
	}

	@GetMapping("/recharge-wallet")
	public String viewRechargeWallet(Principal principal, Model model) {
		User userDetail = userRepo.findByEmail(principal.getName());
		model.addAttribute("userDetail", userDetail);
		return "recharge_wallet";
	}

	@PostMapping("/recharge_walletbyuser")
	public String addAmountInWalletByUser(@ModelAttribute("rechargeWallet") RechargeWallet rechargeWallet, Model model,
			Principal principal, @RequestParam("amountByUser") double amount, @RequestParam("message") String message) {

		Wallet wallet = walletRepo.findByUser(null);

		User user = userRepo.findByEmail(principal.getName());
		rechargeWallet.setAmountByUser(amount);
		rechargeWallet.setUser(user);
		rechargeWallet.setDate(new Date());
		rechargeWallet.setRemarks(message);
		rechargeWallet.setStatus("N");
		rechargeWallet.setFreezeAmt(0);
		if (wallet != null) {
			rechargeWallet.setBeforeAmt(wallet.getWalletAmount());
			rechargeWallet.setAfterAmt(wallet.getWalletAmount());
		} else {
			rechargeWallet.setBeforeAmt(0);
			rechargeWallet.setAfterAmt(0);
		}

		rechargeWalletService.addRechargeWallet(rechargeWallet);
		model.addAttribute("rechargeWalletAmt", new RechargeWallet());
		return "redirect:/recharge-wallet";
	}

	@GetMapping("/book-pass")
	public String viewBookPass(Principal principal, Model model) {
		User userDetail = userRepo.findByEmail(principal.getName());
		model.addAttribute("userDetail", userDetail);
		return "book_pass";
	}

	@GetMapping("/bankcard")
	public String viewCreditCard(Principal principal, Model model) {
		User userDetail = userRepo.findByEmail(principal.getName());
		model.addAttribute("userDetail", userDetail);
		model.addAttribute("myBankCard", new MyBankCard());
		return "my_creditcard";
	}

	@PostMapping("/addBankcard")
	public String addBankcard(@ModelAttribute("myBankCard") MyBankCard myBankCard, Model model, Principal principal) {
		User user = userRepo.findByEmail(principal.getName());
		myBankCard.setUser(user);
		myBankCardRepo.save(myBankCard);
		model.addAttribute("myBankCard", new MyBankCard());
		return "redirect:/bankcard";
	}

	@GetMapping("/withdrawl-amount")
	public String viewWithdrawalAmtPage(Model model, Principal principal) {
		User userDetail = userRepo.findByEmail(principal.getName());
		model.addAttribute("userDetail", userDetail);
		return "withdrawlAmt";
	}

	@PostMapping("/send_withdraw_req")
	public String sendWithdrawlReq(@ModelAttribute("withdrawalRequest") WithdrawalRequest withdrawalRequest,
			Model model, Principal principal, @RequestParam("amtforWithdrawl") double amtforWithdrawl,
			RedirectAttributes redirectAttributes) {
		User user = userRepo.findByEmail(principal.getName());

		MyBankCard myBankCard = myBankCardRepo.findByUser(user);

		Wallet wallet = walletRepo.findByUser(user);
		WithdrawalRequest withdrawalRequest1 = withdrawalRequestRepo.findByUserAndStatus(user, "N");
		if (withdrawalRequest1 != null) {
			redirectAttributes.addFlashAttribute("message", "Request no send!!!");
			redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		} else {
			if (wallet.getWalletAmount() >= amtforWithdrawl) {
				withdrawalRequest.setUser(user);
				withdrawalRequest.setStatus("N");
				withdrawalRequest.setDate(new Date());
				withdrawalRequest.setWithdrawAmout(amtforWithdrawl);
				withdrawalRequest.setMyBankCard(myBankCard);
				walletService.addWithdrawReq(withdrawalRequest);
				model.addAttribute("withdrawalRequest", new WithdrawalRequest());
			} else {
				redirectAttributes.addFlashAttribute("message", "Please, add amount in the wallet!!!");
				redirectAttributes.addFlashAttribute("alertClass", "alert-success");
			}
		}
		return "redirect:/withdrawl-amount";
	}

	@PostMapping("/addOrder")
	public String bookOrder(HttpSession session, Model model, @RequestParam("hotelId") Long hotelId,
			Principal principal) {
		HotelInfo hotelInfo = hotelInfoRepo.findById(hotelId).get();
		User user = userRepo.findByEmail(principal.getName());

		Random random = new Random();
		Long orderNumber = random.nextLong(999999999);

		MyOrder myOrder = new MyOrder();

		myOrder.setOrderNumber(orderNumber.toString());
		while (myOrderRepo.existsByOrderNumber(myOrder.getOrderNumber())) {
			orderNumber = random.nextLong(999999999);
			myOrder.setOrderNumber(orderNumber.toString());
		}

		myOrder.setDate(new Date());
		myOrder.setOrdreStatus("R");
		myOrder.setUser(user);
		myOrder.setHotelInfo(hotelInfo);
		myOrderRepo.save(myOrder);
		return "redirect:/home";
	}

}
