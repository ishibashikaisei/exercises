
package com.example.utill;

import java.util.LinkedHashMap;
import java.util.Map;


import org.springframework.mock.web.MockHttpSession;



public class SessionUtil {



	private static MockHttpSession createMockHttpSession(Map<String, Object> sessions) {
		MockHttpSession mockHttpSession = new MockHttpSession();
		for (Map.Entry<String, Object> session : sessions.entrySet()) {
			mockHttpSession.setAttribute(session.getKey(), session.getValue());
		}
		return mockHttpSession;
	}

	public static MockHttpSession createMockHttpSession1() {
		Map<String, Object> sessionMap = new LinkedHashMap<String, Object>();
		sessionMap.put("key", "12345");
		sessionMap.put("userEmail", "tanaka@yahoo.co.jp");
		return createMockHttpSession(sessionMap);
	}

																																					
}
