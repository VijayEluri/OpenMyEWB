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

package ca.myewb.model;

import ca.myewb.frame.HibernateUtil;
import ca.myewb.logic.EvaluationResponseLogic;

public class EvaluationResponseModel extends EvaluationResponseLogic
{

	private EvaluationResponseModel(EvaluationCriteriaModel crit, int response, EvaluationModel eval)
	{
		super(crit, response, eval);
	}
	
	public EvaluationResponseModel()
	{
		super();
	}

	public static EvaluationResponseModel newEvaluationResponse(EvaluationCriteriaModel crit, int response, EvaluationModel eval)
	{
		EvaluationResponseModel res = new EvaluationResponseModel(crit, response, eval);
		HibernateUtil.currentSession().save(res);
		HibernateUtil.currentSession().flush();
		
		return res;
	}

}
