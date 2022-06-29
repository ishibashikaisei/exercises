package com.example.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;



public class MailUrlForm {
	@NotBlank(message="メールアドレスを入力して下さい")
	@Email(message="メールアドレスの形式が不正です")
	private String mailAddress;



	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
}
