package com.klicki.generators.rules;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Weeks;


public class WeeklyDateRule implements DateRule {
	private int divisor;
	
	//TODO: enumeracja z dniami tygodnia
	private List<Integer> daysOfWeek;
	
	public WeeklyDateRule(int divisor, List<Integer> daysOfWeek){
		this.divisor = divisor;
		this.daysOfWeek = daysOfWeek;
	}
	
	@Override
	public RuleResult checkDate(DateTime startDate, DateTime date) {
		int weekCount = Weeks.weeksBetween(startDate, date).getWeeks();
		if(weekCount % this.divisor == 0){
			if(this.daysOfWeek.contains(date.getDayOfWeek())){
				return RuleResult.PASSED;
			}
		}
		return RuleResult.FAILED;
	}
}
