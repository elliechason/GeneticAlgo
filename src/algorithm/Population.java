package algorithm;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;

// population class to create a number of schedules (ie to populate various partial solutions to our problem)
public class Population {
	private ArrayList<Schedule> schedules;	//one schedule stores a list of classes
	
	public Population(int size, Data data, Random generator) {
		schedules = new ArrayList<Schedule>(size);
		IntStream.range(0, size).forEach(x -> schedules.add(new Schedule(data).initialize(generator)));
	}
	
	public void defaultPopulation(int size, Data data) {
		Random generator = new Random(13);
		schedules = new ArrayList<Schedule>(size);
		IntStream.range(0, size).forEach(x -> schedules.add(new Schedule(data).initializeDefault(generator)));
	}

	public ArrayList<Schedule> getSchedules() {
		return this.schedules;
	}
	  
	public Population sortbyFitness() { //sorting in decreasing order
		schedules.sort((schedule1, schedule2) -> {
			int returnValue = 0;
			if(schedule1.getFitness() > schedule2.getFitness())
				returnValue = -1;
			else if(schedule1.getFitness() < schedule2.getFitness())
				returnValue = 1;
			return returnValue;
		}); 
		return this;
	}
	
}
