package algorithm;
import java.util.ArrayList;
import java.util.stream.IntStream;
import java.util.Random;

import model.Class;

public class GeneticAlgorithmGuided implements Evolution{
	private Data data;
	
	public GeneticAlgorithmGuided(Data data) {
		this.data = data;
	}
	
	public Population evolve(Population population, Random generator) {
		return mutatePopulation(population, generator);
	}
	public Population crossoverPopulation(Population population, Random generator) {
		Population crossoverPopulation = new Population(population.getSchedules().size(), data, generator);
		IntStream.range(0, Driver.NUMB_OF_ELITE_SCHEDULES).forEach(x -> crossoverPopulation.getSchedules().set(x, population.getSchedules().get(x)));
		IntStream.range(Driver.NUMB_OF_ELITE_SCHEDULES, population.getSchedules().size()).forEach(x -> {
			if(Driver.CROSSOVER_RATE > generator.nextDouble(1)) {
				Schedule schedule1 = selectTournamentPopulation(population, generator).sortbyFitness().getSchedules().get(0);
				Schedule schedule2 = selectTournamentPopulation(population, generator).sortbyFitness().getSchedules().get(0);
				crossoverPopulation.getSchedules().set(x, crossoverSchedule(schedule1, schedule2, generator));
			}
			else {
				crossoverPopulation.getSchedules().set(x,  population.getSchedules().get(x));
			}
		});
		return crossoverPopulation;
	}
	public Schedule crossoverSchedule(Schedule schedule1, Schedule schedule2, Random generator) {

		Schedule crossoverSchedule = new Schedule(data).initialize(generator);
		IntStream.range(0,  crossoverSchedule.getClasses().size()).forEach(x -> {
			if(generator.nextDouble(1) > 0.5)
				crossoverSchedule.getClasses().set(x,  schedule1.getClasses().get(x));
			else
				crossoverSchedule.getClasses().set(x,  schedule2.getClasses().get(x));
		});
		return crossoverSchedule;
	}
	public Population mutatePopulation(Population population, Random generator) {
		Population mutatePopulation = new Population(population.getSchedules().size(), data, generator);
		ArrayList<Schedule> schedules = mutatePopulation.getSchedules();
		IntStream.range(0, Driver.NUMB_OF_ELITE_SCHEDULES).forEach(x -> schedules.set(x, population.getSchedules().get(x)));
		IntStream.range(Driver.NUMB_OF_ELITE_SCHEDULES, population.getSchedules().size()).forEach(x -> {
			schedules.set(x,  mutateSchedule(population.getSchedules().get(x), generator));
		});
		return mutatePopulation;
	}
	public Schedule mutateSchedule(Schedule mutateSchedule, Random generator) {
		//Schedule schedule = new Schedule(data).initialize();
		
		ArrayList<Class> classes = mutateSchedule.getClasses();
		Data data = mutateSchedule.getData();

		//Make list of all room meeting time combinations
		ArrayList<String> takenRoomTimeCombos = new ArrayList<String>();
		classes.forEach(x -> {
			takenRoomTimeCombos.add("" + x.getRoom().getNumber() + x.getMeetingTime().getId());
		});

		classes.forEach(x -> {
			
			x = guidedCreep(x, data, classes);
			
			
		});
		mutateSchedule.setClasses(classes);
		return mutateSchedule;
	}

	public Population selectTournamentPopulation(Population population, Random generator) {
		Population tournamentPopulation = new Population(Driver.TOURNAMENT_SELECTION_SIZE, data, generator);
		IntStream.range(0,  Driver.TOURNAMENT_SELECTION_SIZE).forEach(x -> {
			tournamentPopulation.getSchedules().set(x, population.getSchedules().get((int)(generator.nextDouble(1) * population.getSchedules().size())));
		});
		return tournamentPopulation;
	}


	//guided creep with step 1
	private Class guidedCreep(Class mutateClass, Data data, ArrayList<Class> classes) {
		boolean roomChanged = false;
		
		if(mutateClass.getRoom().getSeatingCapacity() < mutateClass.getCourse().getMaxNumbOfStudents()) {
			int oldRoomIndex = data.getRooms().indexOf(mutateClass.getRoom());
			if(data.getRooms().size() <= (oldRoomIndex + 1)){
				mutateClass.setRoom(data.getRooms().get(0));
			}
			else {
				mutateClass.setRoom(data.getRooms().get(oldRoomIndex + 1));

			}
			
			roomChanged = true;
		}
		
		final boolean roomChangeFromCapacity = roomChanged;
		classes.stream().filter(y -> classes.indexOf(y) >= classes.indexOf(mutateClass)).forEach(y -> {
					if(mutateClass.getMeetingTime() == y.getMeetingTime() && mutateClass.getId() != y.getId()) {
						if(mutateClass.getRoom() == y.getRoom()) {
							if (!roomChangeFromCapacity) {
								int oldRoomIndex = data.getRooms().indexOf(mutateClass.getRoom());
								if(data.getRooms().size() <= (oldRoomIndex + 1)){
									mutateClass.setRoom(data.getRooms().get(0));
		
								}
								else {
									mutateClass.setRoom(data.getRooms().get(oldRoomIndex + 1));
								}
								if (mutateClass.getRoom().getNumber().equals(data.getRooms().get(oldRoomIndex).getNumber())) {
									System.out.println("Error");
								}
							}
						}
						
						if(mutateClass.getInstructor() == y.getInstructor()) {
							int oldTimeIndex = data.getMeetingTimes().indexOf(mutateClass.getMeetingTime());
							
							if(data.getMeetingTimes().size() <= (oldTimeIndex + 1)){
									mutateClass.setMeetingTime(data.getMeetingTimes().get(0));
								}
							else {
								mutateClass.setMeetingTime(data.getMeetingTimes().get(oldTimeIndex + 1));
							}
						}
						
					}
				});
		
		return mutateClass;
	}
}