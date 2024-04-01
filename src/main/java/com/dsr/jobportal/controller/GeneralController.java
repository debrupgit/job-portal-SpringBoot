package com.dsr.jobportal.controller;

import java.time.LocalDate;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dsr.jobportal.dao.PortalUserDao;
import com.dsr.jobportal.dto.PortalUser;

import jakarta.validation.Valid;

@Controller
public class GeneralController 
{
	@Autowired
	PortalUser portalUser;

	@Autowired
	PortalUserDao userDao;

	@GetMapping("/")
	public String loadHome() {
		return "home.html";
	}

	@GetMapping("/login")
	public String loadLogin() {
		return "login.html";
	}

	@GetMapping("/signup")
	public String loadSignup(ModelMap map) {
		map.put("portalUser",portalUser);
		return "signup.html";
	}

	@PostMapping("/signup")
	public String signup(@Valid PortalUser portalUser, BindingResult result,ModelMap map)
	{
		if (LocalDate.now().getYear() - portalUser.getDob().getYear() < 18)
			result.rejectValue("dob", "error.dob", "* Age should be Greater Than 18");
		if (!portalUser.getPassword().equals(portalUser.getConfirm_password()))
			result.rejectValue("confirm_password", "error.confirm_password",
					"* Password and Confirm Password Should be Matching");
		if(userDao.existsByEmail(portalUser.getEmail())) 
		{
			result.rejectValue("email", "error.email", "* Email already exist");
		}
			
		if(userDao.existsByMobile(portalUser.getMobile()))
		{
			result.rejectValue("mobile", "error.mobile", "* mobile already exist");
		}

		if(result.hasErrors())
		{
			return "signup.html";
		}
			
		else
		{
			int otp=new Random().nextInt(100000,999999);
			portalUser.setOtp(otp);
			userDao.saveUser(portalUser);
			
			//send mail to email
			map.put("msg","otp sent success");
			map.put("id", portalUser.getId());
			return "enter-otp.html";
		}
			
	}
	
	@PostMapping("/submit-otp")
	public String submitOtp(@RequestParam int otp,@RequestParam int id,ModelMap map)
	{
		 PortalUser portalUser=userDao.findUserById(id);
		 if(otp==portalUser.getOtp())
		 {
			 portalUser.setVerified(true);
			 userDao.saveUser(portalUser);
			 map.put("msg", "account created success");
			 return "login.html";
		 }
		 else
		 {
			 map.put("msg", "incorrect otp try again");
			 map.put("id", portalUser.getId());
			 return "enter-otp.html";
		 }
	}
	
	
	
}
