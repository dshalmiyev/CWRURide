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
    User testUser = new User();

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
    @Test
    public void checkCanDrive() {assertTrue(testUser.canDrive());}
    /*Verify rides on multiple rides with differing conflicts*/
    @Test
    public void checkVerifyRides1(){assertEquals("time mismatch",testUser.verifyRide(1,1,18,2,2,4,true));}
    @Test
    public void checkVerifyRides2(){assertEquals("can post",testUser.verifyRide(1,1,18,4,1,5,true));}
    @Test
    public void checkVerifyRides3(){assertEquals("can post",testUser.verifyRide(1,1,18,3,2,5,true));}
    @Test
    public void checkVerifyRides4(){assertEquals("can post",testUser.verifyRide(1,1,18,4,2,6,true));}
    @Test
    public void checkVerifyRides5(){assertEquals("time mismatch",testUser.verifyRide(1,1,18,5,2,7,true));}
    @Test
    public void checkVerifyRides6(){assertEquals("time mismatch",testUser.verifyRide(1,1,18,7,2,9,true));}

    //User Info Checks
    @Test
    public void checkGetFirstName(){assertEquals("Anthony",testUser.getFirstName());}
    @Test
    public void checkGetLastName(){assertEquals("Shalmiyev",testUser.getLastName());}
    @Test
    public void checkGetPhoneNumber(){assertEquals("666-666-6666",testUser.getPhoneNumber());}
    @Test
    public void checkGetFeedbackValue(){assertEquals(1.1,testUser.getFeedbackValue(),.00005);}
    @Test
    public void checkGetCCInfo(){assertEquals(123,testUser.getCcInfo());}
    @Test
    public void checkGetCcCCV(){assertEquals(420,testUser.getCcCCV());}
    @Test
    public void checkGetBillAddress(){assertEquals("1357 This Way",testUser.getBillAddress());}
    @Test
    public void checkGetCCName(){assertEquals("Anthony Shalmiyev",testUser.getCcName());}


    //Car Setting Tests
    @Test
    public void checkSetCcInfo(){
        testUser.setCcInfo(100);
        assertEquals(100,testUser.getCcInfo());}
    @Test
    public void checkSetCcCCV(){
        testUser.setCcCCV(70);
        assertEquals(70,testUser.getCcCCV());}
    @Test
    public void checkSetBillAddress(){
        testUser.setBillAddress("That Way");
        assertEquals("That Way",testUser.getBillAddress());}
    @Test
    public void checkSetCcName(){
        testUser.setCcName("Babs Babs");
        assertEquals("Babs Babs",testUser.getCcName());}


    //Checks for pp and venmo
    @Test
    public void checkGetPpInfo(){assertEquals(456,testUser.getPpInfo());}
    @Test
    public void checkGetVmInfo(){assertEquals(789,testUser.getVmInfo());}

    //checks for Car Info
    @Test
    public void checkGetCarYear(){assertEquals(2014,testUser.getCarYear());}
    @Test
    public void checkGetCarMake(){assertEquals("Michael",testUser.getCarMake());}
    @Test
    public void checkGetCarModel(){assertEquals("Mia Malkova",testUser.getCarModel());}
    @Test
    public void checkGetCarPlate(){assertEquals("Steak",testUser.getCarPlate());}
    @Test
    public void checkGetUserLicence(){assertEquals("To Kill",testUser.getUserLicense());}
//I misspelled license a TON
    //Set methods for Cars


    @Test
    public void checkSetCarYear(){
        testUser.setCarYear(2018);
        assertEquals(2018,testUser.getCarYear());}
    @Test
    public void checkSetCarMake(){
        testUser.setCarMake("Tesla");
        assertEquals("Tesla",testUser.getCarMake());}
    @Test
    public void checkSetCarModel(){
        testUser.setCarModel("X");
        assertEquals("X",testUser.getCarModel());}
    @Test
    public void checkSetCarPlate(){
        testUser.setCarPlate("JJAJJA");
        assertEquals("JJAJJA",testUser.getCarPlate());}
    @Test
    public void checkSetUserLicense(){
        testUser.setUserLicense("This is a sandwich no license necessary");
        assertEquals("This is a sandwich no license necessary",testUser.getUserLicense());}
}