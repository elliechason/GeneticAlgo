package algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import model.Class;
//import model.Schedule; // Make sure this is the correct import for your Schedule class

public class CoevolutionAlgorithm implements EvolutionCo {
    public static final int POPULATION_SIZE = 20;
    private Data data;
    private List<Population> populations;
    private static final int NUMBER_OF_POPULATIONS = 3;
    private Random generator;

    public CoevolutionAlgorithm(Data data, Random generator) {
        this.data = data;
        this.populations = new ArrayList<>();
        this.generator = new Random(); // Initialize the random generator
        for (int i = 0; i < NUMBER_OF_POPULATIONS; i++) {
            this.populations.add(new Population(CoevolutionAlgorithm.POPULATION_SIZE, data, generator));
        }
    }

    public List<Population> getPopulations() {
        return populations;
    }
    public Boolean checkFitness(){
        
        if (populations.get(0).sortbyFitness().getSchedules().get(0).getFitness() == 1.0) {
            return true;
        }
        if (populations.get(1).sortbyFitness().getSchedules().get(0).getFitness() == 1.0) {
            return true;
        }
        if (populations.get(2).sortbyFitness().getSchedules().get(0).getFitness() == 1.0) {
            return true;
        }
        return false;
    }
    public Boolean evolve(Random generator) {

		Population population1 = mutatePopulation(crossoverPopulation(populations.get(0), generator), generator);
        Population population2 = mutatePopulation(crossoverPopulation(populations.get(1), generator), generator);
        Population population3 = mutatePopulation(crossoverPopulation(populations.get(2), generator), generator);
        if (population1.getSchedules().get(0).getFitness() == 1.0) {
            return true;
        }
        if (population2.getSchedules().get(0).getFitness() == 1.0) {
            return true;
        }
        if (population2.getSchedules().get(0).getFitness() == 1.0) {
            return true;
        }
        populations.set(0, population1);
        populations.set(1, population2);
        populations.set(2, population3);
        coevolutionaryOperation();
        return false;

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

    private void coevolutionaryOperation() {
        // Inter-Population Replacement
        for (int i = 0; i < populations.size(); i++) {
            Population currentPopulation = populations.get(i);
            Population nextPopulation = populations.get((i + 1) % populations.size());

            replaceWorstWithBest(currentPopulation, nextPopulation);
        }

        // Intra-Population Replacement
        for (Population population : populations) {
            replaceWorstWithBestWithinPopulation(population);
        }

        
    }

    public void replaceWorstWithBest(Population currentPopulation, Population nextPopulation) {
        List<Schedule> worstHalfCurrent = selectWorstHalf(currentPopulation);
        List<Schedule> bestHalfNext = selectBestHalf(nextPopulation);

        for (int i = 0; i < worstHalfCurrent.size(); i++) {
            int indexToReplace = currentPopulation.getSchedules().indexOf(worstHalfCurrent.get(i));
            currentPopulation.getSchedules().set(indexToReplace, bestHalfNext.get(i));
        }
    }

    public List<Schedule> selectWorstHalf(Population population) {
        return population.getSchedules().stream()
                .sorted(Comparator.comparingDouble(Schedule::getFitness))
                .limit(population.getSchedules().size() / 2)
                .collect(Collectors.toList());
    }

    public List<Schedule> selectBestHalf(Population population) {
        return population.getSchedules().stream()
                .sorted(Comparator.comparingDouble(Schedule::getFitness).reversed())
                .limit(population.getSchedules().size() / 2)
                .collect(Collectors.toList());
    }

    public void replaceWorstWithBestWithinPopulation(Population population) {
        Schedule bestSchedule = selectBestSchedule(population);
        Schedule worstSchedule = selectWorstSchedule(population);

        int indexToReplace = population.getSchedules().indexOf(worstSchedule);
        population.getSchedules().set(indexToReplace, bestSchedule);
    }

    public Schedule selectBestSchedule(Population population) {
        return population.getSchedules().stream()
                .max(Comparator.comparingDouble(Schedule::getFitness))
                .orElse(null);  // Return null or handle appropriately if no schedule is found
    }

    public Schedule selectWorstSchedule(Population population) {
        return population.getSchedules().stream()
                .min(Comparator.comparingDouble(Schedule::getFitness))
                .orElse(null);  // Return null or handle appropriately if no schedule is found
    }

}