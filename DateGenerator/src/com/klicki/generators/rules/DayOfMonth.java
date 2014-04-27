package com.klicki.generators.rules;

public class DayOfMonth{
	int nth;
	boolean last;
	int dayOfWeek;
	
	public DayOfMonth(int dayOfWeek) {
		this.last = true;
		this.dayOfWeek = dayOfWeek;
	}
	
	public DayOfMonth(int nth, int dayOfWeek) {
		this.nth = nth;
		this.dayOfWeek = dayOfWeek;
	}
}