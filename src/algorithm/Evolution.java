package algorithm;
import java.util.Random;

public interface Evolution { //interface for methods of genetic algorithm
	Population evolve(Population population, Random generator);
	Population crossoverPopulation(Population population, Random generator);
	Schedule crossoverSchedule(Schedule schedule1, Schedule schedule2, Random generator);
	Population mutatePopulation(Population population, Random generator);
	Schedule mutateSchedule(Schedule mutateSchedule, Random generator);
	Population selectTournamentPopulation(Population population, Random generator);
}
