package com.klicki.generators.conditions;

import org.joda.time.DateTime;

public interface EndCondition {
	public boolean isEndDate(DateTime startDate, DateTime date);
}
