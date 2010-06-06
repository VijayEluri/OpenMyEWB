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

package ca.myewb.controllers.actions.users;

import java.util.HashSet;
import java.util.Set;

import org.apache.velocity.context.Context;

import ca.myewb.frame.Message;
import ca.myewb.frame.Controller;
import ca.myewb.frame.RedirectionException;
import ca.myewb.frame.forms.UserProfileStudentForm;


public class SaveUserStudent extends Controller
{
	public void handle(Context ctx) throws Exception
	{
		//PARAM regular or null

		// Create & validate form object
		log.debug("Building form");

		UserProfileStudentForm form = new UserProfileStudentForm(path
		                                                         + "/actions/SaveUserStudent",
		                                                         requestParams);

		log.debug("Validating form");

		Message m = form.validate();

		// No messages: changes are valid
		if (m != null)
		{
			// Display error and prompt user to fix
			throw getValidationException(form, m,
			                             path + "/profile/EditProfileStudent");
		}

		String field = form.getParameter("Field");
		String institution = form.getParameter("Institution");
		String studentNo = form.getParameter("Studentnumber");
		String studentlevel = form.getParameter("Level");
		String gradmonth = form.getParameter("Gradmonth");
		String gradyear = form.getParameter("Gradyear");
		
		currentUser.saveStudentData(field, institution, studentNo, studentlevel, gradmonth, gradyear);

		setSessionMessage(("Student info updated!"));
		log.debug("User update successful!");

		throw new RedirectionException(path + "/profile/Profile");
	}

	public Set<String> invisibleGroups()
	{
		Set<String> s = new HashSet<String>();
		s.add("Users");

		return s;
	}
	
	public String oldName()
	{
		return "SaveUserStudent";
	}
}
