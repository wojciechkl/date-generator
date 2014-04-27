package com.klicki.generators.rules;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.Weeks;

public class MonthlyByDayDateRule implements DateRule {
	private int divisor;
	private List<DayOfMonth> daysOfMonth;
	
	public MonthlyByDayDateRule(int divisor, List<DayOfMonth> daysOfMonth){
		this.divisor = divisor;
		this.daysOfMonth = daysOfMonth;
	}
	
	private boolean isDayOfMonth(DateTime date){
		for(DayOfMonth dom: this.daysOfMonth){
			if(date.getDayOfWeek() == dom.dayOfWeek){
				if(isNthDayOfWeek(date, dom)){
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean isNthDayOfWeek(DateTime date, DayOfMonth dom) {
		if(dom.last){
			return daysLeftToEndOfMonth(date) < 7;
		}
		else{
			DateTime firstDay = date.withDayOfMonth(1).withDayOfWeek(dom.dayOfWeek);
			if(firstDay.getMonthOfYear() != date.getMonthOfYear()){
				firstDay = firstDay.plusWeeks(1);
			}
			int diff = Weeks.weeksBetween(firstDay, date).getWeeks() + 1;
			return diff == dom.nth;
		}
	}

	private int daysLeftToEndOfMonth(DateTime date) {
		return date.dayOfMonth().getMaximumValue() - date.getDayOfMonth();
	}

	@Override
	public RuleResult checkDate(DateTime startDate, DateTime date) {
		int monthCount = Months.monthsBetween(startDate, date).getMonths();
		if(monthCount % this.divisor == 0){
			if(isDayOfMonth(date)){
				return RuleResult.PASSED;
			}
		}
		return RuleResult.FAILED;
	}
}
