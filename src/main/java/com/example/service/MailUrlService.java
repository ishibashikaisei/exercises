package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.MailUrl;
import com.example.repository.MailurlRepository;

@Service
public class MailUrlService {
	@Autowired
	private MailurlRepository repository;
	
	public List<MailUrl> receiveMail(String mail){
		return repository.receiveMail(mail);
	}
	
	public void insert(MailUrl set) {
		repository.insert(set);
	}
	
	public void update(String mail) {
		repository.update(mail);
	}
	
	public List<MailUrl> check(String key){
		return repository.check(key);
	}
}

