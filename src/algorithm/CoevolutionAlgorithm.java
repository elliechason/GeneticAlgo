package algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import model.Class;
//import model.Schedule; // Make sure this is the correct import for your Schedule class

public class CoevolutionAlgorithm implements Evolution {
    private Data data;
    private List<Population> populations;
    private static final int NUMBER_OF_POPULATIONS = 3;
    private Random generator;
}

    public CoevolutionAlgorithm(Data data) {
        this.data = data;
        this.populations = new ArrayList<>();
        this.generator = new Random(); // Initialize the random generator
        for (int i = 0; i < NUMBER_OF_POPULATIONS; i++) {
            this.populations.add(new Population(data));
        }
    }

    public Population evolve(Population population, Random generator) {
		return mutatePopulation(crossoverPopulation(population, generator), generator);
    }


        @Override
    public Population selectTournamentPopulation(Population population, Random generator) {
        Population tournamentPopulation = new Population(Driver.TOURNAMENT_SELECTION_SIZE, data, generator);
        IntStream.range(0,  Driver.TOURNAMENT_SELECTION_SIZE).forEach(x -> {
            tournamentPopulation.getSchedules().set(x, population.getSchedules().get((int)(generator.nextDouble(1) * population.getSchedules().size())));
            });
        return tournamentPopulation;
    }

    public Population crossoverPopulation(Population population) {
        Population crossoverPopulation = new Population(population.getSchedules().size(), data);
        IntStream.range(0, Driver.NUMB_OF_ELITE_SCHEDULES).forEach(x -> 
            crossoverPopulation.getSchedules().set(x, population.getSchedules().get(x)));
        IntStream.range(Driver.NUMB_OF_ELITE_SCHEDULES, population.getSchedules().size()).forEach(x -> {
            if (Driver.CROSSOVER_RATE > generator.nextDouble()) {
                Schedule schedule1 = selectTournamentPopulation(population).sortByFitness().getSchedules().get(0);
                Schedule schedule2 = selectTournamentPopulation(population).sortByFitness().getSchedules().get(0);
                crossoverPopulation.getSchedules().set(x, crossoverSchedule(schedule1, schedule2, generator));
            } else {
                crossoverPopulation.getSchedules().set(x, population.getSchedules().get(x));
            }
        });
        return crossoverPopulation;
    }

    public Schedule crossoverSchedule(Schedule schedule1, Schedule schedule2, Random generator) {
        Schedule crossoverSchedule = new Schedule(data).initialize(generator);
        IntStream.range(0, crossoverSchedule.getClasses().size()).forEach(x -> {
            if (generator.nextDouble() > 0.5)
                crossoverSchedule.getClasses().set(x, schedule1.getClasses().get(x));
            else
                crossoverSchedule.getClasses().set(x, schedule2.getClasses().get(x));
        });
        return crossoverSchedule;
    }

    public Population mutatePopulation(Population population, Random generator) {
        Population mutatePopulation = new Population(population.getSchedules().size(), data);
        ArrayList<Schedule> schedules = mutatePopulation.getSchedules();
        IntStream.range(0, Driver.NUMB_OF_ELITE_SCHEDULES).forEach(x -> 
            schedules.set(x, population.getSchedules().get(x)));
        IntStream.range(Driver.NUMB_OF_ELITE_SCHEDULES, population.getSchedules().size()).forEach(x -> 
            schedules.set(x, mutateSchedule(population.getSchedules().get(x), generator)));
        return mutatePopulation;
    }

    // Implement the mutateSchedule method based on the guided mutation logic from GeneticAlgorithmGuided
    public Schedule mutateSchedule(Schedule mutateSchedule, Random generator) {
		
		ArrayList<Class> classes = mutateSchedule.getClasses();
		Data data = mutateSchedule.getData();

		ArrayList<String> takenRoomTimeCombos = new ArrayList<String>();
		classes.forEach(x -> {
			takenRoomTimeCombos.add("" + x.getRoom().getNumber() + x.getMeetingTime().getId());
		});

		classes.forEach(x -> {
			
			x = coevolutionaryOperation();
			
			
		});
		mutateSchedule.setClasses(classes);
		return mutateSchedule;
    }

    @Override
    public Population crossoverPopulation(Population population, Random generator) {
        Population crossoverPopulation = new Population(population.getSchedules().size(), data);
        IntStream.range(0, Driver.NUMB_OF_ELITE_SCHEDULES).forEach(x -> 
            crossoverPopulation.getSchedules().set(x, population.getSchedules().get(x)));
        IntStream.range(Driver.NUMB_OF_ELITE_SCHEDULES, population.getSchedules().size()).forEach(x -> {
            if (Driver.CROSSOVER_RATE > generator.nextDouble()) {
                Schedule schedule1 = selectTournamentPopulation(population).sortByFitness().getSchedules().get(0);
                Schedule schedule2 = selectTournamentPopulation(population).sortByFitness().getSchedules().get(0);
                crossoverPopulation.getSchedules().set(x, crossoverSchedule(schedule1, schedule2));
            } else {
                crossoverPopulation.getSchedules().set(x, population.getSchedules().get(x));
            }
        });
        return crossoverPopulation;    }

    private Class coevolutionaryOperation() {
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

