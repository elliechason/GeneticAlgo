package algorithm;
import java.util.ArrayList;
import java.util.stream.IntStream;
import java.util.Random;

public class GeneticAlgorithm implements Evolution{
	private Data data;
	
	public GeneticAlgorithm(Data data) {
		this.data = data;
	}
	
	public Population evolve(Population population, Random generator) {
		return mutatePopulation(crossoverPopulation(population, generator), generator);
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
		Schedule schedule = new Schedule(data).initialize(generator);
		IntStream.range(0,  mutateSchedule.getClasses().size()).forEach(x -> {
			if(Driver.MUTATION_RATE > generator.nextDouble(1))
				mutateSchedule.getClasses().set(x,  schedule.getClasses().get(x));
		});
		return mutateSchedule;
	}

	public Population selectTournamentPopulation(Population population, Random generator) {
		Population tournamentPopulation = new Population(Driver.TOURNAMENT_SELECTION_SIZE, data, generator);
		IntStream.range(0,  Driver.TOURNAMENT_SELECTION_SIZE).forEach(x -> {
			tournamentPopulation.getSchedules().set(x, population.getSchedules().get((int)(generator.nextDouble(1) * population.getSchedules().size())));
		});
		return tournamentPopulation;
	}
}