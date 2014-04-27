package com.klicki.generators.conditions;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.Weeks;
import org.joda.time.Years;

public class ContinuingForEndCondition implements EndCondition{
	private int count = 1;
	private ContinuingForEndConditionTypes type = ContinuingForEndConditionTypes.DAYS;
	
	public ContinuingForEndCondition(int count, ContinuingForEndConditionTypes type) {
		this.count = count;
		this.type = type;
	}
	
	@Override
	public boolean isEndDate(DateTime startDate, DateTime date) {
		int diff = 0;
		
		switch(this.type){
			case DAYS:
				diff = Days.daysBetween(startDate, date).getDays();
				break;
			case WEEKS:
				diff = Weeks.weeksBetween(startDate, date).getWeeks();
				break;
			case MONTHS:
				diff = Months.monthsBetween(startDate, date).getMonths();
				break;
			case YEARS:
				diff = Years.yearsBetween(startDate, date).getYears();
				break;
		}
		return diff > this.count;
	}
}
