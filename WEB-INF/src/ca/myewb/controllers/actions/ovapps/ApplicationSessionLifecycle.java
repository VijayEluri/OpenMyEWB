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

import java.util.HashSet;
import java.util.Set;

import org.apache.velocity.context.Context;

import ca.myewb.frame.Controller;
import ca.myewb.frame.RedirectionException;
import ca.myewb.model.ApplicationSessionModel;


public class ApplicationSessionLifecycle extends Controller
{
	public void handle(Context ctx) throws Exception
	{
		urlParams.processParams(new String[]{"action", "sessionid"},
		                        new String[]{null, "-1"});

		ApplicationSessionModel s = (ApplicationSessionModel)getAndCheckFromUrl(ApplicationSessionModel.class, "sessionid");
		String returnPath = path + "/volunteering/ApplicationSessionInfo/" + s.getId();
		
		
		if((urlParams.get("action") == null) 
				|| ((!urlParams.get("action").equals("close") 
			    && (!urlParams.get("action").equals("open")))))
		{
			setSessionErrorMessage(("Invalid action."));
			throw new RedirectionException(returnPath);
		}
		
		requireConfirmation("Are you sure you want to " + urlParams.get("action") + " this session?",
				"",
                returnPath, 
                path + "/actions/ApplicationSessionLifecycle/" + urlParams.get("action") + "/" + urlParams.get("sessionid"), 
                "volunteering",
                null);
		
		if(urlParams.get("action").equals("close"))
		{
			s.close();
			
			setSessionMessage(("Application session closed."));
		}
		else if(urlParams.get("action").equals("open"))
		{
			s.open();
			
			setSessionMessage(("Application session opened."));
		}

		throw new RedirectionException(returnPath);
	}

	public Set<String> invisibleGroups()
	{
		Set<String> s = new HashSet<String>();
		s.add("Admin");

		return s;
	}
	
	public String oldName()
	{
		return "ApplicationSessionLifecycle";
	}
}
