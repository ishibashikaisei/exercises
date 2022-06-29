package com.example.controller;




import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.MailUrl;
import com.example.domain.User;
import com.example.form.MailUrlForm;
import com.example.form.UserForm;
import com.example.service.MailUrlService;
import com.example.service.UserService;

@Controller
@RequestMapping("/register")
public class registerController {
	@Autowired MailSender mailsender;
	@Autowired HttpSession session;
	@Autowired MailUrlService mailUrlService;
	@Autowired UserService userService;

	@RequestMapping("")
	public String index(MailUrlForm mailurlForm) {
		return "address-set";
	}
	
	@RequestMapping("/receive")
	public String receiveMail(@Validated MailUrlForm form, BindingResult result, RedirectAttributes redirectAttributes,Model model) {
		if (result.hasErrors()) {
			return "address-set";
		}
		List<User> userList = userService.receiveUser(form.getMailAddress());
		List<MailUrl> mailurlList = mailUrlService.receiveMail(form.getMailAddress());
		if(mailurlList!=null) {
		return "send-error";
		}else {
			session.setAttribute("userEmail", form.getMailAddress());
		}
		if(userList != null) {
			return "send-comp";
		}else {
		String key = UUID.randomUUID().toString();

		SimpleMailMessage msg = new SimpleMailMessage();
		MailUrl mailurl = new MailUrl();
		mailurl.setMailAddress(form.getMailAddress());
		mailurl.setKey(key);
		mailurl.setStatus(0);
		Timestamp timestamp = new java.sql.Timestamp(System.currentTimeMillis());
		mailurl.setDate(timestamp);
		mailUrlService.insert(mailurl);
		try {
			// msgにtanakadesuyo118@gmail.comをセット
			msg.setFrom("tanakadesuyo118@gmail.com");

			msg.setTo(form.getMailAddress());
			msg.setSubject("Hogehogeシステムユーザー登録");
			msg.setText("Hogehogeシステム、新規ユーザー登録依頼を受け付けました。以下のURLから本登録処理を行ってください。\n"
						+ "Hogehogeシステム、ユーザー登録URL\n"
						+ "http://localhost:8080/register/regist?key="
						+ key + "\n※上記URLの有効期限は24時間以内です");
			mailsender.send(msg);

		} catch (MailException e) {
			e.printStackTrace();
		}
			return "redirect:/register/rereceive";
		}
	}
	@RequestMapping("rereceive")
	public String rereceive() {
		return "send-comp";
	}
	
	@RequestMapping("/regist")
	public String index2(String key, UserForm userForm) {
		List<MailUrl> checkList = mailUrlService.check(key);
		if(checkList != null) {
			return "register-user";
		}else {
			return "url-error";
		}
	}
	
	@RequestMapping("/complete")
	public String registerFinish(@Validated UserForm form, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
		if(!form.getConfirmpassword().isEmpty() && 
				!form.getPass().isEmpty() && 
				!form.getPass().equals(form.getConfirmpassword())){
			result.rejectValue("confirmpassword", "", "パスワードが一致していません");
		}
		if (result.hasErrors()) {
			return "register-user";
		}
		User user = new User();	
		BeanUtils.copyProperties(form, user);
		Timestamp timestamp = new java.sql.Timestamp(System.currentTimeMillis());
		user.setRegistDate(timestamp);
		user.setUpdateDate(timestamp);
		user.setDelFlg(0);
		user.setMailAddress((String) session.getAttribute("userEmail"));

		userService.userSet(user);
		mailUrlService.update((String)session.getAttribute("userEmail"));
		return "redirect:/register/recomplete";
	}
	
	@RequestMapping("recomplete")
	public String recomp() {
		return "register-comp";
	}
}

