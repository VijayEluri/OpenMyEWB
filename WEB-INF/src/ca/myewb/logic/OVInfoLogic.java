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

package ca.myewb.logic;


import ca.myewb.beans.OVInfo;
import ca.myewb.frame.HibernateUtil;
import ca.myewb.model.OVInfoModel;

public abstract class OVInfoLogic extends OVInfo {

	public String getFormattedDob() {
		if(dob == null)
		{
			return "";
		}
		return dateFormat.format(dob);
	}

	public String getFormattedPassportstart() {
		if(passportstart == null)
		{
			return "";
		}
		return dateFormat.format(passportstart);
	}

	public String getFormattedPassportend() {
		if(passportend == null)
		{
			return "";
		}
		return dateFormat.format(passportend);
	}

	public String getFormattedE1Updates() {
		return e1updates ? "yes" : "no";
	}

	public String getFormattedE2Updates() {
		return e2updates ? "yes" : "no";
	}

	public static OVInfoModel getForUser(int userid) {
		return (OVInfoModel)HibernateUtil.currentSession()
		.createQuery("FROM OVInfoModel a where a.userid=?").setInteger(0, userid).uniqueResult();
	}

}
