package dev.itboot.mb.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

//Spring徹底入門サンプルp384
@Service
public class MessageService {
	@Autowired
	MessageSource messageSource;
	public String getMessageByCode(String code) {
		return messageSource.getMessage(code, null, Locale.getDefault());
	}
}