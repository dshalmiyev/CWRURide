package com.example.dshal.cwruride;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    Ride testingRide = new Ride(10,"Testing");
    @Test
    public void rideStartTimeCheck(){assertEquals(10,testingRide.getStartTime());}
    @Test
    public void rideEndTimeCheck(){assertEquals(30,testingRide.getEndTime());}
    @Test
    public void rideCostCheck(){assertEquals(30,testingRide.getCost());}
    @Test
    public void rideUserRatingCheck(){assertEquals(3, testingRide.getUserRating());}
    @Test
    public void rideUserIDCheck(){assertEquals("50",testingRide.getComplementaryUserID());}
    @Test
    public void rideDescriptionCheck(){assertEquals("description10",testingRide.getDescription());}
    @Test
    public void rideDateCheck(){assertEquals("10/20/30",testingRide.getDate());}
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
}