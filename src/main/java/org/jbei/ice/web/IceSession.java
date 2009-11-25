package org.jbei.ice.web;

import java.util.HashMap;

import javax.servlet.http.Cookie;

import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.WebResponse;
import org.apache.wicket.protocol.http.WebSession;
import org.jbei.ice.lib.logging.Logger;
import org.jbei.ice.lib.managers.AccountManager;
import org.jbei.ice.lib.managers.ManagerException;
import org.jbei.ice.lib.managers.SessionManager;
import org.jbei.ice.lib.models.Account;
import org.jbei.ice.lib.utils.JbeirSettings;

public class IceSession extends WebSession {

	private static final long serialVersionUID = 1L;
	private Account account = null;
	private Authenticator authenticator = null;
	private boolean authenticated = false;
	private SessionData sessionData = null;
	private String COOKIE_NAME = "gd-ice";
	
	public IceSession(Request request, Response response, Authenticator authenticator2) {
		super(request);
		this.authenticator = authenticator2;
		
		SessionData sessionData = getSavedSession(request);
		if (sessionData != null) {
			HashMap<String, Object> data = sessionData.getData();
			if (data != null) {
				if (data.containsKey("accountId")) {
					Integer accountId = (Integer) data.get("accountId"); 
					try {
						setAccount(AccountManager.get(accountId));
						authenticated = true;
						setSessionData(sessionData);
					} catch (ManagerException e) {
						e.printStackTrace();
					}
				}
			} else {
				sessionData.delete();
				sessionData = createNewSavedSession(request, response);
			}
		} else {
			sessionData = createNewSavedSession(request, response);
		}
		setSessionData(sessionData);
		
	}

	public SessionData getSavedSession(Request request) {
		SessionData sessionData = null;
		
		Cookie userCookie = ((WebRequest) request).getCookie(COOKIE_NAME);
		
		if (userCookie != null) {
			try {
				String sessionKey = userCookie.getValue();
				sessionData = SessionManager.get(sessionKey);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return sessionData;
	}
	
	public SessionData createNewSavedSession(Request request, Response response) {
		String host = ((WebRequest)request).getHttpServletRequest().getRemoteAddr();
		SessionData sessionData = null;
		try {
			sessionData = new SessionData(host,JbeirSettings.getSetting("SITE_SECRET"));
			Cookie cookie = new Cookie(COOKIE_NAME, sessionData.getSessionKey());
			((WebResponse)response).addCookie(cookie);
						
		} catch (ManagerException e) {
						
						e.printStackTrace();
		}
		return sessionData;
	}
	
	public void clearSavedSession() {
		SessionData savedSession = getSessionData();
		savedSession.delete();
		
	}
	
	public boolean authenticateUser(String login, String password) {
		Account account = null;
		boolean result = false;
		try {
			account = authenticator.authenticate(login, password);
			
			if (account != null) {
				result = true;
				setAccount(account);
				this.authenticated = true;
			}
		} catch (Exception e) {
			Logger.warn("Could not authenticate user " + login + ": " + e.toString());
			e.printStackTrace();
		}
		return result; 
	}
	
	public void deAuthenticateUser() {
		account = null;
		authenticated = false;
		sessionData.delete();
	}
	
	public boolean isAuthenticated() {
		return authenticated;
	}
	
	//getters and setters
	public static IceSession get() {
		return (IceSession) Session.get();
	}
	
	public void setSessionData(SessionData sessionData) {
		this.sessionData = sessionData;
	}

	public SessionData getSessionData() {
		return sessionData;
	}

	private void setAccount(Account account) {
		this.account = account;
	}

	public Account getAccount() {
		return account;
	}
}
