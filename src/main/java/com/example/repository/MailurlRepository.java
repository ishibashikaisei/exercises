package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.MailUrl;



@Repository
public class MailurlRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<MailUrl> mail_url_ROW_MAPPER = new BeanPropertyRowMapper<>(MailUrl.class);
	
	
	 public List<MailUrl> receiveMail(String mail) {
	        String sql="SELECT * FROM mail_url WHERE mail_address = :mail AND date>(select now()+cast('-1 days' as INTERVAL)) AND status = 0;";
	        
	        
	        SqlParameterSource param=new MapSqlParameterSource().addValue("mail", mail);
	        	
	        List<MailUrl> mulList = template.query(sql,param,mail_url_ROW_MAPPER);
	        if(mulList.size()==0) {
	        	return null;
	        }else {

	        return mulList;
	        }
	    }
	 //データのセット
	 public void insert(MailUrl set) {
			String insertSql = "insert into mail_url(mail_address, key, status, date) values(:mailAddress, :key, :status, :date);";
			SqlParameterSource param = new BeanPropertySqlParameterSource(set);
			template.update(insertSql, param);
		}
	 //登録完了後、mail_urlテーブルのstatusを１にして検索にヒットしないように
	 public void update(String mail) {
		 String updatesql = "update mail_url set status = 1 WHERE mail_address = :mail;";
		 SqlParameterSource param = new MapSqlParameterSource().addValue("mail", mail);
		 template.update(updatesql, param);
	 }
	 
	 //URLの有効無効チェック
	 public List<MailUrl> check(String key) {
		 String sql = "select * from mail_url WHERE key = :key AND status = 0 AND date>(select now()+cast('-1 days' as INTERVAL));";
		 SqlParameterSource param = new MapSqlParameterSource().addValue("key", key);
		 List<MailUrl> mailurl = template.query(sql, param, mail_url_ROW_MAPPER);
		 if(mailurl.size()==0) {
			 return null;
		 }else {
			 return mailurl;
		 }
	 }
}
