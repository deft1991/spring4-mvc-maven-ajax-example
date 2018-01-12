package com.mkyong.validators;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by s.golitsyn on 12.01.2018
 *
 * we use to create holidays
 * , that would do without a database.
 * I understand that i need to use an enum but not strong in them.
 * In the future I will read about them
 */

public class Holidays {
    public static final GregorianCalendar VICTORY_DAY = new GregorianCalendar(2018, Calendar.MAY, 9);
    public static final GregorianCalendar NEW_YEAR = new GregorianCalendar(2018, Calendar.JANUARY, 1);
    public static final GregorianCalendar WOMENS_DAY = new GregorianCalendar(2018, Calendar.MARCH, 8);
    public static final GregorianCalendar MY_BIRTHDAY = new GregorianCalendar(2018, Calendar.MARCH, 5); // let's imagine that this is an international holiday =)
    public static final GregorianCalendar[] HOLIDAYS = new GregorianCalendar[]{VICTORY_DAY, NEW_YEAR,WOMENS_DAY, MY_BIRTHDAY}; // let's imagine that this is an international holiday =)


}
