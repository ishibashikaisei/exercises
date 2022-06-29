package com.example.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;



public class UserForm {
	/**名前*/
	@NotBlank(message="名前を入力して下さい")
	private String name;
	/**ふりがな*/
	@NotBlank(message="かなを入力して下さい")
	private String kana;
	/**郵便番号*/
	@NotBlank(message="郵便番号を入力して下さい")
	@Pattern(regexp="^[0-9]{3}-[0-9]{4}$", message="郵便番号はXXX-XXXXの形式で入力してください")
	private String zipcode;
	/**住所*/
	@NotBlank(message="住所を入力して下さい")
	private String address;
	/**電話番号*/
	@NotBlank(message="電話番号を入力して下さい")
	@Pattern(regexp="^[0-9]{3}-[0-9]{4}-[0-9]{4}$", message="電話番号はXXX-XXXX-XXXXの形式で入力してください")
	private String tel;
	/**パスワード*/
	@NotBlank(message="パスワードを入力して下さい")
	@Size(min=4, max=50, message="パスワードは4文字以上50文字以内で設定してください")
	private String pass;
	@NotBlank(message="確認用パスワードを入力して下さい")
	private String confirmpassword;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKana() {
		return kana;
	}
	public void setKana(String kana) {
		this.kana = kana;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}

	
}
