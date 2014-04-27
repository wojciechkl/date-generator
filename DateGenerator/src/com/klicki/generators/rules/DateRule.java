package com.klicki.generators.rules;

import org.joda.time.DateTime;

public interface DateRule {
	public RuleResult checkDate(DateTime startDate, DateTime date);
}
