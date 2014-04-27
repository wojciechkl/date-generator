package com.klicki.generators;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.junit.Test;

import com.klicki.generators.conditions.DateEndCondition;
import com.klicki.generators.conditions.EndCondition;
import com.klicki.generators.rules.DailyDateRule;
import com.klicki.generators.rules.DateRule;
import com.klicki.generators.rules.DayOfMonth;
import com.klicki.generators.rules.MonthlyByDayDateRule;
import com.klicki.generators.rules.WeeklyDateRule;

public class DateGeneratorTest {

	@Test
	public void testDaily() {
		DateRule dr = new DailyDateRule(1);
		DateTime startDate = new DateTime(2010,4,23,0,0,0);
		DateTime endDate = new DateTime(2010,4,30,0,0,0);
		EndCondition ed = new DateEndCondition(endDate);
		
		DateGenerator dg = new DateGenerator(startDate, ed, dr);
		dg.generateDates();
		List<DateTime> res = dg.getGeneratedDates();
		assertEquals(8, res.size());
		assertEquals(startDate, res.get(0));
		assertEquals(endDate, res.get(7));
	}
	
	@Test
	public void testWeekly() {
		List<Integer> daysOfWeek = Arrays.asList(DateTimeConstants.MONDAY, DateTimeConstants.WEDNESDAY);
		DateRule dr = new WeeklyDateRule(2, daysOfWeek);
		DateTime startDate = new DateTime(2014,4,23,0,0,0);
		DateTime endDate = new DateTime(2014,5,24,0,0,0);
		EndCondition ed = new DateEndCondition(endDate);
		
		DateGenerator dg = new DateGenerator(startDate, ed, dr);
		dg.generateDates();
		List<DateTime> res = dg.getGeneratedDates();
		assertEquals(5, res.size());
		assertEquals(startDate, res.get(0));
		assertEquals(new DateTime(2014,5,7,0,0,0), res.get(2));
	}
	
	@Test
	public void testDailyEveryTwoDontMove() {
		DateRule dr = new DailyDateRule(2);
		DateTime startDate = new DateTime(2014,5,9,0,0,0);
		DateTime endDate = new DateTime(2014,5,12,0,0,0);
		EndCondition ed = new DateEndCondition(endDate);
		
		DateGenerator dg = new DateGenerator(startDate, ed, dr);
		dg.generateDates();
		List<DateTime> res = dg.getGeneratedDates();
		assertEquals(2, res.size());
		assertEquals(startDate, res.get(0));
		assertEquals(new DateTime(2014,5,11,0,0,0), res.get(1));
	}

	@Test
	public void testDailyEveryTwoDelete() {
		DateRule dr = new DailyDateRule(2);
		DateTime startDate = new DateTime(2014,5,9,0,0,0);
		DateTime endDate = new DateTime(2014,5,12,0,0,0);
		EndCondition ed = new DateEndCondition(endDate);
		
		DateGenerator dg = new DateGenerator(startDate, ed, dr);
		dg.setMovePolicy(ExceptionalDateMovePolicy.DELETE);
		dg.generateDates();
		List<DateTime> res = dg.getGeneratedDates();
		assertEquals(1, res.size());
		assertEquals(startDate, res.get(0));
	}
	
	@Test
	public void testDailyEveryTwoMoveToNext() {
		DateRule dr = new DailyDateRule(2);
		DateTime startDate = new DateTime(2014,5,9,0,0,0);
		DateTime endDate = new DateTime(2014,5,12,0,0,0);
		EndCondition ed = new DateEndCondition(endDate);
		
		DateGenerator dg = new DateGenerator(startDate, ed, dr);
		dg.setMovePolicy(ExceptionalDateMovePolicy.MOVE_TO_NEXT);
		dg.generateDates();
		List<DateTime> res = dg.getGeneratedDates();
		assertEquals(2, res.size());
		assertEquals(startDate, res.get(0));
		assertEquals(new DateTime(2014,5,12,0,0,0), res.get(1));
	}
	
	@Test
	public void testDailyEveryTwoMoveToNear() {
		DateRule dr = new DailyDateRule(2);
		DateTime startDate = new DateTime(2014,5,9,0,0,0);
		DateTime endDate = new DateTime(2014,5,12,0,0,0);
		EndCondition ed = new DateEndCondition(endDate);
		
		DateGenerator dg = new DateGenerator(startDate, ed, dr);
		dg.setMovePolicy(ExceptionalDateMovePolicy.MOVE_TO_NEAREST);
		dg.generateDates();
		List<DateTime> res = dg.getGeneratedDates();
		assertEquals(2, res.size());
		assertEquals(startDate, res.get(0));
		assertEquals(new DateTime(2014,5,12,0,0,0), res.get(1));
	}
	
	@Test
	public void testDailyEveryTwoMoveToPrev() {
		DateRule dr = new DailyDateRule(2);
		DateTime startDate = new DateTime(2014,5,9,0,0,0);
		DateTime endDate = new DateTime(2014,5,12,0,0,0);
		EndCondition ed = new DateEndCondition(endDate);
		
		DateGenerator dg = new DateGenerator(startDate, ed, dr);
		dg.setMovePolicy(ExceptionalDateMovePolicy.MOVE_TO_PREV);
		dg.generateDates();
		List<DateTime> res = dg.getGeneratedDates();
		assertEquals(1, res.size());
		assertEquals(startDate, res.get(0));
	}
	
	//TODO: test for ContinuingForEndCondtition
	
	@Test
	public void testMonthlyByDate4th() {
		DayOfMonth dm1 = new DayOfMonth(4, DateTimeConstants.THURSDAY);
		List<DayOfMonth> daysOfMonth = Arrays.asList(dm1);
		
		DateRule dr = new MonthlyByDayDateRule(1, daysOfMonth);
		DateTime startDate = new DateTime(2014,4,17,0,0,0);
		DateTime endDate = new DateTime(2014,6,20,0,0,0);
		EndCondition ed = new DateEndCondition(endDate);
		
		DateGenerator dg = new DateGenerator(startDate, ed, dr);

		dg.generateDates();
		List<DateTime> res = dg.getGeneratedDates();
		assertEquals(2, res.size());
		assertEquals(new DateTime(2014,4,24,0,0,0), res.get(0));
		assertEquals(new DateTime(2014,5,22,0,0,0), res.get(1));
	}
	
	@Test
	public void testMonthlyByDate4thAndLast() {
		DayOfMonth dm1 = new DayOfMonth(4, DateTimeConstants.THURSDAY);
		DayOfMonth dm2 = new DayOfMonth(4, DateTimeConstants.MONDAY);
		List<DayOfMonth> daysOfMonth = Arrays.asList(dm1, dm2);
		
		DateRule dr = new MonthlyByDayDateRule(1, daysOfMonth);
		DateTime startDate = new DateTime(2014,4,17,0,0,0);
		DateTime endDate = new DateTime(2014,6,20,0,0,0);
		EndCondition ed = new DateEndCondition(endDate);
		
		DateGenerator dg = new DateGenerator(startDate, ed, dr);

		dg.generateDates();
		List<DateTime> res = dg.getGeneratedDates();
		System.out.println(res);
		assertEquals(4, res.size());
		assertEquals(new DateTime(2014,4,24,0,0,0), res.get(0));
		assertEquals(new DateTime(2014,4,28,0,0,0), res.get(1));
		assertEquals(new DateTime(2014,5,22,0,0,0), res.get(2));
		assertEquals(new DateTime(2014,5,26,0,0,0), res.get(3));
	}
}
