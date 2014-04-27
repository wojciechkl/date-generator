package com.klicki.generators.conditions;

import org.joda.time.DateTime;

public class DateEndCondition implements EndCondition{
	private DateTime endDate;
	
	public DateEndCondition(DateTime endDate) {
		if(endDate == null){
			throw new NullPointerException("End date is null");
		}
		this.endDate = endDate;
	}
	
	@Override
	public boolean isEndDate(DateTime startDate, DateTime date) {
		return endDate.equals(date);
	}
}
