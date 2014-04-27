package com.klicki.generators;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;

public class ExceptionalDateMovePolicy {
	public static final int DONT_MOVE = 0;
	public static final int MOVE_TO_PREV = 1;
	public static final int MOVE_TO_NEXT = 2;
	public static final int MOVE_TO_NEAREST = 3;
	public static final int DELETE = 4;
	
	private int policy = DONT_MOVE;
	
	public void setPolicy(int policy) {
		this.policy = policy;
	}
	
	public void processDate(DateTime date, List<DateTime> generatedDates){
		System.out.println("Correcting date: " + date);
		switch(this.policy){
			case DONT_MOVE:
				break;
			case MOVE_TO_PREV:
				moveDate(date, generatedDates, -1);
				break;
			case MOVE_TO_NEXT:
				moveDate(date, generatedDates, 1);
				break;
			case MOVE_TO_NEAREST:
				DateTime prevDate = findFreeDate(date, generatedDates, -1);
				DateTime nextDate = findFreeDate(date, generatedDates, 1);
				int prevDays = -1;
				if(prevDate != null){
					prevDays = Days.daysBetween(prevDate, date).getDays();
				}
				int nextDays = -1;
				if(nextDate != null){
					nextDays = Days.daysBetween(date, nextDate).getDays();
				}
				if(prevDays > nextDays){
					doMoveDate(date, generatedDates, prevDate);
				}
				else if(prevDays < nextDays){
					doMoveDate(date, generatedDates, nextDate);
				}
				else{ // nextDays and prevDays are equal to -1 which means that there was no free date
					generatedDates.remove(date);
				}
				break;
			case DELETE:
				generatedDates.remove(date);
				break;
		}
	}

	private void moveDate(DateTime date, List<DateTime> generatedDates, int direction) {
		DateTime freeDate = findFreeDate(date, generatedDates, direction);
		if(freeDate != null){
			doMoveDate(date, generatedDates, freeDate);
		}
		else{
			generatedDates.remove(date);
		}
	}

	private void doMoveDate(DateTime date, List<DateTime> generatedDates,
			DateTime newDate) {
		int idx = generatedDates.indexOf(date);
		generatedDates.set(idx, newDate);
	}

	private DateTime findFreeDate(DateTime date, List<DateTime> generatedDates,
			int direction) {
		DateTime iter = date.plusDays(direction);
		while(!generatedDates.contains(iter)){
			if(Utils.isWorkDay(iter)){
				return iter;
			}
			iter = iter.plusDays(direction);
		}
		return null;
	}
}
