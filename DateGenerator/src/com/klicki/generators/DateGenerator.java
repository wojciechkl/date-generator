package com.klicki.generators;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;

import com.klicki.generators.conditions.EndCondition;
import com.klicki.generators.rules.DateRule;

public class DateGenerator {
	private List<DateTime> generatedDates;
	private List<DateTime> datesForCorrection;
	private DateTime startDate;
	private EndCondition endCondition;
	private DateRule dateRule;
	
	private ExceptionalDateMovePolicy expDatePolicy = new ExceptionalDateMovePolicy();
	
	public DateGenerator(DateTime startDate, EndCondition ed, DateRule dr){
		this.startDate = startDate;
		this.endCondition = ed;
		this.dateRule = dr;
	}
	
	public void setMovePolicy(int policy){
		this.expDatePolicy.setPolicy(policy);
	}
	
	public void generateDates(){
		long st = System.nanoTime();
		
		this.generatedDates = new ArrayList<DateTime>();
		this.datesForCorrection = new ArrayList<DateTime>();
		
		DateTime currentDate = this.startDate;
		boolean processingEndDate = false;
		
		while(true){
			if(this.endCondition.isEndDate(startDate, currentDate)){
				processingEndDate = true;
			}
			
			switch(this.dateRule.checkDate(this.startDate, currentDate)){
				case PASSED:
					this.generatedDates.add(currentDate);
					break;
				case NEEDS_CORRECTION:
					this.generatedDates.add(currentDate);
					this.datesForCorrection.add(currentDate);
					break;
				case FAILED:
					break;
			}
			
			if(processingEndDate){
				break;
			}
			
			currentDate = currentDate.plusDays(1);
		}
		
		for(DateTime dateForCorrection:this.datesForCorrection){
			this.expDatePolicy.processDate(dateForCorrection, this.generatedDates);
		}
		
		long rt = TimeUnit.MILLISECONDS.convert((System.nanoTime() - st), TimeUnit.NANOSECONDS);
		System.out.println("Generating done in " + rt + "ms");
	}

	public List<DateTime> getGeneratedDates() {
		return generatedDates;
	}	
}
