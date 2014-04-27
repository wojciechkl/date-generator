package com.klicki.generators.rules;

import org.joda.time.DateTime;
import org.joda.time.Days;

import com.klicki.generators.Utils;

public class DailyDateRule implements DateRule {
	private int divisor;
	public DailyDateRule(int divisor){
		this.divisor = divisor;
	}
	
	@Override
	public RuleResult checkDate(DateTime startDate, DateTime date) {
		int daysBetween = Days.daysBetween(startDate, date).getDays();
		if(daysBetween % this.divisor == 0){
			if(Utils.isWorkDay(date)){
				return RuleResult.PASSED;
			}
			else{
				return RuleResult.NEEDS_CORRECTION;
			}
		}
		else{
			return RuleResult.FAILED;
		}
	}

}
