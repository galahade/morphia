package com.google.code.morphia.issue173;


import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;
import com.google.code.morphia.TestBase;
import com.google.code.morphia.annotations.Converters;
import com.google.code.morphia.testutil.TestEntity;
import com.mongodb.WriteConcern;
import org.junit.Assert;


public class TestIdTwice extends TestBase {

  @Test
  public final void testCalendar() {
    morphia.map(A.class);
    final A a = new A();
    a.c = GregorianCalendar.getInstance();
    ds.save(a, WriteConcern.SAFE);
    // occasionally failed, so i suspected a race cond.
    final A loaded = ds.find(A.class).get();
    Assert.assertNotNull(loaded.c);
    Assert.assertEquals(a.c, loaded.c);
  }

  @Converters(CalendarConverter.class)
  private static class A extends TestEntity {
    private static final long serialVersionUID = 1L;
    Calendar c;
  }

}
