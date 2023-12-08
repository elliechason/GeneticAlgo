package algorithm;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import model.Class;

public class DriverCo {
	public static final int POPULATION_SIZE = 20;
	public static final double MUTATION_RATE = 0.3;
	public static final double CROSSOVER_RATE = 0.8;
	public static final int TOURNAMENT_SELECTION_SIZE = 6;
	public static final int NUMB_OF_ELITE_SCHEDULES = 2;
	private int scheduleNumb = 0;
	private int classNumb = 1;
	private Data data;
	
	private void printScheduleAsTable(Schedule schedule, int generation) {
		ArrayList<Class> classes = schedule.getClasses();
		System.out.print("\n                     ");
		System.out.println("Class # | Dept | Course (number, max # of students) | Room (Capacity) |  Instructor (Id)  | Meeting Time (Id)");
		System.out.print("                       ");
		System.out.print("------------------------------------------------------");
		System.out.println("---------------------------------------------------------------");
		classes.forEach(x -> {
			int majorIndex = data.getDepts().indexOf(x.getDept());
			int courseIndex = data.getCourses().indexOf(x.getCourse());
			int roomsIndex = data.getRooms().indexOf(x.getRoom());
			int instructorIndex = data.getInstructors().indexOf(x.getInstructor());
			int meetingTimeIndex = data.getMeetingTimes().indexOf(x.getMeetingTime());
			System.out.print("                        ");
			System.out.print(String.format("  %1$02d  ", classNumb) + " | ");
			System.out.print(String.format("%1$4s", data.getDepts().get(majorIndex).getName()) + " | " );
			System.out.print(String.format("%1$21s", data.getCourses().get(courseIndex).getName() + " ("+data.getCourses().get(courseIndex).getNumber()+", "+x.getCourse().getMaxNumbOfStudents()) + ")          | ");
			System.out.print(String.format("%1$10s",  data.getRooms().get(roomsIndex).getNumber() +" ("+x.getRoom().getSeatingCapacity()) + ")      |  ");
			System.out.print(String.format("%1$15s", data.getInstructors().get(instructorIndex).getName()+ " ("+data.getInstructors().get(instructorIndex).getId()+")")+ "  | ");
			System.out.println(data.getMeetingTimes().get(meetingTimeIndex).getTime()+" ("+data.getMeetingTimes().get(meetingTimeIndex).getId()+")");
			classNumb++;
		});
		if(schedule.getFitness() == 1)
			System.out.println("> Solution Found in "+ (generation + 1) +" generations");
		System.out.print("------------------------------------------------------");
		System.out.println("---------------------------------------------------------------");
	}
	private void printAvailableData() {
		System.out.println("Available Departments ==>");
		data.getDepts().forEach(x -> System.out.println("name: "+x.getName()+", courses: "+x.getCourses()));
		System.out.println("Available Courses ==>");
		data.getCourses().forEach(x -> System.out.println("course #: "+x.getNumber()+", name: "+x.getName()+", max # of students: " + x.getMaxNumbOfStudents()+ ", instructors: " + x.getInstructors()));
		System.out.println("Available Rooms ==>");
		data.getRooms().forEach(x -> System.out.println("room #: "+x.getNumber()+", max seating capacity: "+x.getSeatingCapacity()));
		System.out.println("Available Instructors ==>");
		data.getInstructors().forEach(x -> System.out.println("id: "+x.getId()+", name: "+x.getName()));
		System.out.println("Available Meeting Times ==>");
		data.getMeetingTimes().forEach(x -> System.out.println("id: "+x.getId()+", Meeting time: "+x.getTime()));
		System.out.print("------------------------------------------------------------------------------------------");
		System.out.println("------------------------------------------------------------------------------------------");
	}

	public static int getInput() {
		Scanner input = new Scanner(System.in);
		System.out.println("How many classes would you like to schedule? Enter a number from 19 to 23. Warning, sizes >21 will yield long runtimes:");
		String num = input.nextLine();
		int size = 0;
		switch(num) {
			case "19":
				size = 19;
				break;
			case "20":
				size = 20;
				break;
			case "21":
				size = 21;
				break;
			case "22":
				size = 22;
				break;
			case "23":
				size = 23;
				break;	
			default:
				System.out.println("Sorry, that is not an acceptable input");
				size = getInput();
		}
		return size;
	}

	public static int getSeed() {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter an integer for the random seed value:");
		int num = 0;
		if (input.hasNextInt()) { 
			num = input.nextInt(); 
		} 
		// if no Int is found, 
		// print "Not Found:" and the token 
		else { 
			System.out.println("Not a valid input, please try again"); 
			num = getSeed();
		}
		return num;
	}
	
	public static void main(String[] args) {
		final long startTime = System.currentTimeMillis();
		DriverCo driver = new DriverCo();
		int size = getInput();
		driver.data = new Data(size);
		int generationNumber = 0;
		int seed = getSeed();

		Random crossGenerator = new Random(seed);


		driver.printAvailableData();
		System.out.println("> Generation Number: "+generationNumber);
		System.out.print("  Schedule # |                                            ");
		System.out.print("Classes [dept,class,room,instructor,meeting-time]        ");
		System.out.println( "                                   | Fitness | Conflicts");
		System.out.print("------------------------------------------------------------------------------------");
		System.out.println("------------------------------------------------------------------------------------");
		CoevolutionAlgorithm geneticAlgorithm = new CoevolutionAlgorithm(driver.data, crossGenerator);
		Boolean passed = geneticAlgorithm.checkFitness();
		if (passed){
			System.out.println("Done");
		}

		
		driver.classNumb = 1;
		while(!passed) {
			++generationNumber;
			
            driver.scheduleNumb = 0;
            passed = geneticAlgorithm.evolve(crossGenerator);
			List<Population> populations = geneticAlgorithm.getPopulations();
			if (passed) {
				if (populations.get(0).sortbyFitness().getSchedules().get(0).getFitness() == 1.0) {
					Population optPopulation = populations.get(0);
					driver.printScheduleAsTable(optPopulation.getSchedules().get(0), generationNumber);
				}
				else if (populations.get(1).sortbyFitness().getSchedules().get(0).getFitness() == 1.0) {
					Population optPopulation = populations.get(1);
					driver.printScheduleAsTable(optPopulation.getSchedules().get(0), generationNumber);
				}
				else if (populations.get(2).sortbyFitness().getSchedules().get(0).getFitness() == 1.0) {
					Population optPopulation = populations.get(2);
					driver.printScheduleAsTable(optPopulation.getSchedules().get(0), generationNumber);
				}
			}
			driver.classNumb = 1;
		}
		final long endTime = System.currentTimeMillis();

		System.out.println("Total execution time: " + (endTime - startTime) + " ms");	
	}

}
