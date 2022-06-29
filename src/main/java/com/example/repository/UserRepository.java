package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;


import com.example.domain.User;



@Repository
public class UserRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;
	@Autowired PasswordEncoder passwordEncoder;
	
	
	private static final RowMapper<User> USER_ROW_MAPPER = new BeanPropertyRowMapper<>(User.class);
	
	
	 public List<User> receiveUser(String mail) {
	        String sql="SELECT * FROM users WHERE mail_address = :mail;";
	        
	        
	        SqlParameterSource param=new MapSqlParameterSource().addValue("mail", mail);
	        	
	        List<User> userList = template.query(sql,param,USER_ROW_MAPPER);
	        if(userList.size()==0) {
	        	return null;
	        }else {
	        return userList;
	        }
	    }
	 
	 public void userSet(User user) {
		 user.setPass(passwordEncoder.encode(user.getPass()));
		 String insertsql="INSERT INTO users (name,kana,zipcode,address,tel,regist_date,update_date,del_flg,password,mail_address)"
		 		+ "VALUES (:name,:kana,:zipcode,:address,:tel,:registDate,:updateDate,:delFlg,:pass,:mailAddress);";
		 SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		 template.update(insertsql, param);
	 }
}
