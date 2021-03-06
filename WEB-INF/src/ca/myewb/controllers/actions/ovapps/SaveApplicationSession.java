/*

    This file is part of OpenMyEWB.

    OpenMyEWB is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    OpenMyEWB is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with OpenMyEWB.  If not, see <http://www.gnu.org/licenses/>.

    OpenMyEWB is Copyright 2005-2009 Nicolas Kruchten (nicolas@kruchten.com), Francis Kung, Engineers Without Borders Canada, Michael Trauttmansdorff, Jon Fishbein, David Kadish

*/

package ca.myewb.controllers.actions.ovapps;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.velocity.context.Context;

import ca.myewb.frame.Message;
import ca.myewb.frame.Controller;
import ca.myewb.frame.RedirectionException;
import ca.myewb.frame.forms.ApplicationSessionEditForm;
import ca.myewb.model.ApplicationSessionModel;


public class SaveApplicationSession extends Controller
{
	public void handle(Context ctx) throws Exception
	{
		ApplicationSessionEditForm form = new ApplicationSessionEditForm(path
		                                           + "/actions/SaveApplicationSession/"
		                                           + urlParams.getParam(),
		                                           requestParams);

		Message m = form.validate();

		// No messages: changes are valid
		if (m != null)
		{
				throw getValidationException(form, m,
				                             path + "/volunteering/EditApplicationSession/"
				                             + urlParams.getParam());
		}

		ApplicationSessionModel session;
		int sessionID;
		
		String name = form.getParameter("Name");
		Date openDate = form.getParameterAsDateTime("Opendate");
		Date dueDate = form.getParameterAsDateTime("Duedate");
		Date closeDate = form.getParameterAsDateTime("Closedate");
		String engInstructions = form.getParameter("EngInstructions");
		String frInstructions = form.getParameter("FrInstructions");
		String completedAppMessage = form.getParameter("Thankyoumessage");
		String closeEmailText = form.getParameter("Sessioncloseemail");
		String rejectionEmailText = form.getParameter("Rejectionemailtext");

		if (urlParams.getParam().equals("new"))
		{
			session = ApplicationSessionModel.newApplicationSession(name, engInstructions, frInstructions, completedAppMessage, closeEmailText, openDate, dueDate, closeDate, rejectionEmailText);
			sessionID = session.getId();
		}
		else
		{
			session = (ApplicationSessionModel)getAndCheckFromUrl(ApplicationSessionModel.class);
			session.save(name, engInstructions, frInstructions, completedAppMessage, closeEmailText, openDate, dueDate, closeDate, rejectionEmailText);
			sessionID = session.getId();
		}
		
		// Leave a message in the session
		setSessionMessage(("Application Session Info Updated"));

		// Redirect to somewhere
		throw new RedirectionException(path + "/volunteering/ApplicationSessionInfo/" + sessionID);
	}

	public Set<String> invisibleGroups()
	{
		Set<String> s = new HashSet<String>();
		s.add("Admin");

		return s;
	}
	
	public String oldName()
	{
		return "SaveApplicationSession";
	}
}
