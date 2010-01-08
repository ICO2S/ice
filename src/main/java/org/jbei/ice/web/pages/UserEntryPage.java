package org.jbei.ice.web.pages;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.jbei.ice.lib.managers.EntryManager;
import org.jbei.ice.lib.managers.ManagerException;
import org.jbei.ice.lib.managers.SampleManager;
import org.jbei.ice.lib.models.Account;
import org.jbei.ice.lib.models.Entry;
import org.jbei.ice.lib.models.Sample;
import org.jbei.ice.web.IceSession;
import org.jbei.ice.web.panels.EntryPagingPanel;
import org.jbei.ice.web.panels.SamplePagingPanel;

public class UserEntryPage extends ProtectedPage {
	Panel userPanel;
	public UserEntryPage(PageParameters parameters) {
		super(parameters);

		Account account = IceSession.get().getAccount();
		LinkedHashSet<Entry> entries = EntryManager.getByAccount(account, 0, 1000);
		ArrayList<Entry> entriesList = new ArrayList<Entry> (entries);
		userPanel = new EntryPagingPanel("userPanel", entriesList, 50);
		userPanel.setOutputMarkupId(true);
		
		AjaxFallbackLink entriesLink = new AjaxFallbackLink("userEntriesLink") {
			
			private static final long serialVersionUID = 1L;

			public void onClick(AjaxRequestTarget target) {
				System.out.println("I'm in entries");
				Account account = IceSession.get().getAccount();
				LinkedHashSet<Entry> entries = EntryManager.getByAccount(account, 0, 1000);
				ArrayList<Entry> entriesList = new ArrayList<Entry> (entries);
				userPanel = new EntryPagingPanel("userPanel", entriesList, 50);
				userPanel.setOutputMarkupId(true);
				getPage().replace(userPanel);
				target.addComponent(userPanel);
				
			}
		};
	
		AjaxFallbackLink samplesLink = new AjaxFallbackLink("userSamplesLink") {
			
			private static final long serialVersionUID = 1L;
			public void onClick(AjaxRequestTarget target) {
				System.out.println("I'm in samples");
				Account account = IceSession.get().getAccount();
				Set<Sample> samples;
				try {
					samples = SampleManager.getByAccount(account);
					ArrayList<Sample> samplesList = new ArrayList<Sample> (samples);
					userPanel = new SamplePagingPanel("userPanel", samplesList, 50);
					userPanel.setOutputMarkupId(true);
					getPage().replace(userPanel);
					target.addComponent(userPanel);
					
				} catch (ManagerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
		};
		
		
		add(entriesLink);		
		add(samplesLink);
		add(userPanel);
	}
}