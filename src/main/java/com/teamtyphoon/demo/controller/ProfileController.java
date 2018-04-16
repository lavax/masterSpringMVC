package com.teamtyphoon.demo.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.teamtyphoon.demo.date.CNLocalDateFormatter;
import com.teamtyphoon.demo.profile.UserProfileSession;

@Controller
public class ProfileController {
	private UserProfileSession userProfileSession;

	@Autowired
	public ProfileController(UserProfileSession userProfileSession) {
		this.userProfileSession = userProfileSession;
	}

	@ModelAttribute("dateFormat")
	public String localeFormat(Locale locale) {
		return CNLocalDateFormatter.getPattern(locale);
	}

	@ModelAttribute
	public ProfileForm getProfileForm() {
		return userProfileSession.toForm();
	}

	@RequestMapping("/profile")
	public String displayProfile(ProfileForm profileForm) {
		return "profile/profilePage";
	}

	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public String saveProfile(@Valid ProfileForm profileForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "profile/profilePage";
		}
		userProfileSession.saveForm(profileForm);
		return "redirect:/search/mixed;keywords=" + String.join(",", profileForm.getTastes());
	}

	@RequestMapping(value = "/profile", params = { "addTaste" })
	public String addRow(ProfileForm profileForm) {
		profileForm.getTastes().add(null);
		return "profile/profilePage";
	}

	@RequestMapping(value = "/profile", params = { "removeTaste" })
	public String removeRow(ProfileForm profileForm, HttpServletRequest req) {
		Integer rowId = Integer.valueOf(req.getParameter("removeTaste"));
		profileForm.getTastes().remove(rowId.intValue());
		return "profile/profilePage";
	}
}