package dev.itboot.mb.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

//Spring徹底入門p385
@RunWith(MockitoJUnitRunner.class)
public class MessageServiceTest {
	 
	@InjectMocks
	MessageService service;
	@Mock
	MessageSource mockMessageSource;
	
	@Test
	public void testGetMessageByCode() {
		doReturn("Hello!!").when(mockMessageSource)
			.getMessage("greeting", null, Locale.getDefault());
		
		//テストを行う
		String actualMessage = service.getMessageByCode("greeting");
		assertEquals(actualMessage, "Hello!!");
	}
}