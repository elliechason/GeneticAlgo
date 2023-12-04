package algorithm;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

import model.*;
//data class to store all the available data
public class Data {
	private ArrayList<Room> rooms;	//list of rooms available
	private ArrayList<Instructor> instructors;	//list of instructors
	private ArrayList<Course> courses;	//list of courses
	private ArrayList<Department> depts;	//list of departments
	private ArrayList<MeetingTime> meetingTimes;	//list of meeting times
	private int numberOfClasses = 0;	//total number of classes from all departments for which the time should be scheduled
	
	public Data() {
		initialize();
	}
	
	private Data initialize() {
		Room room1 = new Room("FGH138", 55);
		Room room2 = new Room("FGH132", 50);
        Room room3 = new Room("FGH298", 15);
        Room room4 = new Room("FGH134", 110);
        Room room5 = new Room("FGH136", 50);
        Room room6 = new Room("FGH110", 50);
        Room room7 = new Room("FGH211", 40);
        Room room8 = new Room("FGH258", 40);
        Room room9 = new Room("OH131", 40);
		rooms = new ArrayList<Room>(Arrays.asList(room1, room2, room3, room4, room5, room6, room7, room8, room9));
        //MeetingTime meetingTime1 = new MeetingTime("MT1", "MWF 08:00 - 08:50");
        MeetingTime meetingTime2 = new MeetingTime("MT2", "MWF 09:05 - 09:55"); //
        MeetingTime meetingTime3 = new MeetingTime("MT3", "MWF 10:10 - 11:00"); //
        MeetingTime meetingTime4 = new MeetingTime("MT4", "MWF 11:15 - 12:05"); //
        //MeetingTime meetingTime5 = new MeetingTime("MT5", "MWF 12:20 - 01:10"); 
        MeetingTime meetingTime6 = new MeetingTime("MT6", "MWF 01:25 - 02:15"); //
        MeetingTime meetingTime7 = new MeetingTime("MT7", "MWF 02:30 - 03:20"); //
        //MeetingTime meetingTime8 = new MeetingTime("MT8", "MWF 03:35 - 04:25");
        //MeetingTime meetingTime9 = new MeetingTime("MT9", "MWF 04:40 - 05:30");
		//MeetingTime meetingTime10 = new MeetingTime("MT10", "TR  08:00 - 09:15");
		MeetingTime meetingTime11 = new MeetingTime("MT11", "TR  09:30 - 10:45"); //
        MeetingTime meetingTime12 = new MeetingTime("MT12", "TR  11:00 - 12:15"); //
        MeetingTime meetingTime13 = new MeetingTime("MT13", "TR  01:15 - 02:30"); //
		MeetingTime meetingTime14 = new MeetingTime("MT14", "TR  02:45 - 04:00"); //
		//MeetingTime meetingTime15 = new MeetingTime("MT15", "TR  04:30 - 05:30");
		meetingTimes = new ArrayList<MeetingTime>(Arrays.asList(meetingTime2, meetingTime3, meetingTime4, meetingTime6, meetingTime7, meetingTime11, meetingTime12, meetingTime13, meetingTime14));
		Instructor instructor1 = new Instructor("I1", "Arena          ");
		Instructor instructor2 = new Instructor("I2", "Hajiamini      ");
		Instructor instructor3 = new Instructor("I3", "Hemingway      ");
        Instructor instructor4 = new Instructor("I4", "Johnson        ");
        Instructor instructor5 = new Instructor("I5", "Ma             ");
		Instructor instructor6 = new Instructor("I6", "Roth           ");
		Instructor instructor7 = new Instructor("I7", "Tairas         ");
        Instructor instructor8 = new Instructor("I8", "Fisher         ");
        Instructor instructor9 = new Instructor("I9", "Kolouri        ");
		Instructor instructor10 = new Instructor("I10", "Beckers        ");
		Instructor instructor11 = new Instructor("I11", "Biegl          ");
        Instructor instructor12 = new Instructor("I12", "Bai            ");
        Instructor instructor13 = new Instructor("I13", "Hasan          ");
        Instructor instructor14 = new Instructor("I14", "Koutsoukos     ");
        Instructor instructor15 = new Instructor("I15", "Ward           ");
        Instructor instructor16 = new Instructor("I16", "Neema          ");
        Instructor instructor17 = new Instructor("I17", "Hedgecock      ");
		instructors = new ArrayList<Instructor>(Arrays.asList(instructor1, instructor2, instructor3, instructor4, instructor5, instructor6, instructor7, instructor8, instructor9, instructor10, instructor11, instructor12, instructor13, instructor14));
        Course course1_1 = new Course("C1.1", "CS1101", new ArrayList<Instructor>(Arrays.asList(instructor11, instructor12, instructor12)), 100);
        Course course1_2 = new Course("C1.2", "CS1101", new ArrayList<Instructor>(Arrays.asList(instructor12)), 100);
        Course course1_3 = new Course("C1.3", "CS1101", new ArrayList<Instructor>(Arrays.asList(instructor12)), 100);
        
        Course course2_1 = new Course("C2.1", "CS2201", new ArrayList<Instructor>(Arrays.asList(instructor6)), 54);
        Course course2_2 = new Course("C2.2", "CS2201", new ArrayList<Instructor>(Arrays.asList(instructor6)), 54);
        Course course2_3 = new Course("C2.3", "CS2201", new ArrayList<Instructor>(Arrays.asList(instructor6)), 54);
        Course course2_4 = new Course("C2.4", "CS2201", new ArrayList<Instructor>(Arrays.asList(instructor12)), 54);
        
        Course course3_1 = new Course("C3.1", "CS2212", new ArrayList<Instructor>(Arrays.asList(instructor1)), 35);
        Course course3_2 = new Course("C3.2", "CS2212", new ArrayList<Instructor>(Arrays.asList(instructor13)), 35);
        Course course3_3 = new Course("C3.3", "CS2212", new ArrayList<Instructor>(Arrays.asList(instructor13)), 35);
        Course course3_4 = new Course("C3.4", "CS2212", new ArrayList<Instructor>(Arrays.asList(instructor13)), 35);
        Course course3_5 = new Course("C3.5", "CS2212", new ArrayList<Instructor>(Arrays.asList(instructor14)), 35);

        Course course4_1 = new Course("C4.1", "CS3250", new ArrayList<Instructor>(Arrays.asList(instructor1)), 35);
        Course course4_2 = new Course("C4.2", "CS3250", new ArrayList<Instructor>(Arrays.asList(instructor1)), 35);
        Course course4_3 = new Course("C4.3", "CS3250", new ArrayList<Instructor>(Arrays.asList(instructor4)), 35);
        Course course4_4 = new Course("C4.4", "CS3250", new ArrayList<Instructor>(Arrays.asList(instructor4)), 35);

        Course course5_1 = new Course("C5.1", "CS3251", new ArrayList<Instructor>(Arrays.asList(instructor3)), 50);
        Course course5_2 = new Course("C5.2", "CS3251", new ArrayList<Instructor>(Arrays.asList(instructor3)), 50);
        Course course5_3 = new Course("C5.3", "CS3251", new ArrayList<Instructor>(Arrays.asList(instructor3)), 50);

        //Course course6_1 = new Course("C6.1", "CS3270", new ArrayList<Instructor>(Arrays.asList(instructor7)), 50);
        //Course course6_2 = new Course("C6.2", "CS3270", new ArrayList<Instructor>(Arrays.asList(instructor7)), 50);
        //Course course6_3 = new Course("C6.3", "CS3270", new ArrayList<Instructor>(Arrays.asList(instructor7)), 50);

        //Course course7_1 = new Course("C7.1", "CS4260", new ArrayList<Instructor>(Arrays.asList(instructor5)), 50);
        //Course course7_2 = new Course("C7.2", "CS4260", new ArrayList<Instructor>(Arrays.asList(instructor8)), 35);

        //Course course8_1 = new Course("C8.1", "CS4262", new ArrayList<Instructor>(Arrays.asList(instructor9)), 35);
        //Course course8_2 = new Course("C8.2", "CS4262", new ArrayList<Instructor>(Arrays.asList(instructor10)), 20);

        //Course course9_1 = new Course("C9.1", "CS3281", new ArrayList<Instructor>(Arrays.asList(instructor15)), 40);
        //Course course9_2 = new Course("C9.2", "CS3281", new ArrayList<Instructor>(Arrays.asList(instructor16)), 40);
        //Course course9_3 = new Course("C9.3", "CS3281", new ArrayList<Instructor>(Arrays.asList(instructor17)), 13);

        // course6_1, course6_2, course6_3, course7_1, course7_2, course8_1, course8_1, course9_1, course9_2, course9_3
        courses = new ArrayList<Course>(Arrays.asList(course1_1, course1_2, course1_3, course2_1, course2_2, course2_3, course2_4, course3_1, course3_2, course3_3, course3_4, course3_5, course4_1, course4_2, course4_3, course4_4, course5_1, course5_2, course5_3));
		Department dept1 = new Department("CS", new ArrayList<Course>(Arrays.asList(course1_1, course1_2, course1_3, course2_1, course2_2, course2_3, course2_4, course3_1, course3_2, course3_3, course3_4, course3_5, course4_1, course4_2, course4_3, course4_4, course5_1, course5_2, course5_3)));
		depts = new ArrayList<Department>(Arrays.asList(dept1));
		depts.forEach(x -> numberOfClasses += x.getCourses().size());	//store number of total number of classes from all depts
		return this;
	}
	public ArrayList<Room> getRooms() {	//return room data
		return rooms;
	}
	public ArrayList<Instructor> getInstructors() {	//return faculty data
		return instructors;
	}
	public ArrayList<Course> getCourses() {	//return course data
		return courses;
	}
	public ArrayList<Department> getDepts() {	//return department data
		return depts;
	}
	public ArrayList<MeetingTime> getMeetingTimes() {	//return meeting times
		return meetingTimes;
	}
	public int getNumberOfClasses() {
		return numberOfClasses;     
	}

}
