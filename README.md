# Improved Genetic Algorithms for Course Scheduling

## Background
The Course Scheduling Problem poses a struggle for universities in the creation of a class timetable. The goal is to create an academic course calendar to properly schedule all desired classes; the tricky part involves satisfying all university-specific requirements: class times should not overlap, professors cannot be scheduled to teach two classes at once, classrooms cannot host more than one course in a time slot, etc. Violating any of these constraints makes a schedule infeasible. Furthermore, in addition to the desired timetable not violating any constraints, it should maximize them to breed efficiency.

Since it is an NP-hard problem, however, there is no optimal algorithm that can solve every instance of this problem in polynomial time. We are proposing the usage of three different Genetic Algorithms to better optimize this problem, including the standard version, the Guided Creep Mutation version, and the Coevolutionary version.

## Description
The Genetic Algorithm is a process by which the program takes in a list of desired courses and specified constraints (class times, professor availability, and classroom capacity) and produces an optimized course schedule that does not violate any constraints.

- **Standard Genetic Algorithm:** 
  - Creates a randomized schedule as a starting point that violates several constraints.
  - Undergoes a simulated evolution process to create a non-violating, optimized course schedule through random crossovers and mutations, fitness evaluations, and new generation breeding.

- **Guided Creep Mutation Genetic Algorithm:**
  - Similar to the standard version but undergoes a guided mutation rather than a random one, mutating only violating courses.

- **Coevolutionary Genetic Algorithm:**
  - Similar to the standard version but starts with multiple initial random schedules.
  - Breeds both inter- and intra-population crossovers.

While all algorithms will procure an optimal schedule, the efficiencies of these algorithms can be compared in terms of both generation number and time taken to breed the optimal result.

## Usage: Environment Setup
(Note: this section is how to run the code. Please see "Results Interpretation" to learn how to read the outputs)

### Standard Genetic Algorithm
1. Select the `Driver.java` file and run it.
2. When prompted, enter a number between 19 and 23, inclusive, to determine the number of classes you wish to schedule.
3. Enter an integer value to determine a random seed value that will be used to construct the initial, constraint-violating course schedule and mutations.
4. Once you hit enter, the program will run and produce an output.

### Guided Creep Mutation Genetic Algorithm
1. Select the `DriverGuided.java` file and run it.
2. When prompted, enter a number between 19 and 20, inclusive, to determine the number of classes you wish to schedule.
3. Enter an integer value to determine the random seed value that will be used to construct the initial, constraint-violating course schedule.
4. Once you hit enter, the program will run and produce an output.

### Coevolutionary Genetic Algorithm
1. Select the `DriverCo.java` file and run it.
2. When prompted, enter a number between 19 and 23, inclusive, to determine the number of classes you wish to schedule.
3. Enter an integer value to determine the random seed value that will be used to construct the initial, constraint-violating course schedule.
4. Once you hit enter, the program will run and produce an output.

## Results Interpretation
Each of the 3 Genetic Algorithms will start by printing out a list of the available courses with size capacities, professors, and rooms. This is to provide the user with the behind-the-scenes look at the information being used in the algorithms.

Next, you will see several lists of ordered course numbers, starting from label "0." These represent the generations of schedules that the algorithm iterates through until it reaches the optimal solution.

Two charts will then be printed:
1. A representation of the initial, random, and constraint-violating schedule that is generated as a starting point for the algorithm.
2. The optimal course schedule; this represents the optimal solution for this algorithm that is feasible and violates no constraints.

The number of generations and total executable time is printed below this second chart, as shown:
	Solution Found in __ generations
- - - - - - - - - - - - - - - - - - -
  Total execution time: __ ms

While each algorithm will return the optimized schedule, the number of generations required and execution time taken will differ. The user can use this information to evaluate and compare the algorithms. Feel free to change the number of courses you want to schedule to see how the algorithms change in efficiency!

## Sample Output
To achieve the results shown during the presentation, enter the corresponding number of classes (shown at the top left of each grid) and random seed 15. Please note that you may only enter class size 19-23 for Standard GA, 19-20 for Guided GA, and 19-23 for Coevolution GA (although 19-21 is advised for shorter runtimes). This is to prevent long runtimes of over 30 minutes.

Dataset: 19 classes

  Standard GA: 314 generations, 631 ms
  GA with Guided Creep Mutation: 5 generations, 279 ms
  Coevolution GA: 1194 generations, 2855 ms

Dataset: 20 classes

  Standard GA: 187 generations, 396 ms
  GA with Guided Creep Mutation: 9 generations, 193 ms
  Coevolution GA: 674 generations, 1943 ms

Dataset: 21 classes

  Standard GA: 736 generations, 788 ms
  Coevolution GA: 36679 generations, 74178 ms

Dataset: 22 classes

  Standard GA: 141 generations, 536 ms
  Coevolution GA: 69505 generations, 155716 ms

Dataset: 23 classes

  Standard GA: 414 generations, 728 ms
  Coevolution GA: 169871 generations, 417336 ms




## References
Dataset taken from select Fall 2023 Vanderbilt Computer Science class data

Original genetic code from https://github.com/sanjaytharagesh31/Genetic-Algorithm

Guided mutation code implemented from algorithm developed in http://eprints.uty.ac.id/7745/1/Artikel%20ISRITI%202021%20-%20Fachrie%26Anita.pdf
