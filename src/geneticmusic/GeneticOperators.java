package geneticmusic;

import geneticmusic.genes.*;
import org.jgap.InvalidConfigurationException;

import java.util.*;
import java.lang.Math;

/**
 * Provides genetic operations
 * @author Hazel Que
 * @version 31 May 2017
 */
public class GeneticOperators {

    Pitch[] allPitches = new Pitch[] {Pitch.A, Pitch.B, Pitch.C, Pitch.D, Pitch.E, Pitch.F, Pitch.G, Pitch.R};
    Alteration[] allAlterations = new Alteration[] {Alteration.F, Alteration.N, Alteration.S};

    /**
     * Mutate each component of each Note of ChoraleGene with a probability of mProb
     * @param original - the ChoraleGene to be mutated
     * @param mProb - mutation probability
     * @return - mutated ChoraleGene
     */
    public ChoraleGene mutate(ChoraleGene original, double mProb) {

        Random rand = new Random();

        // looping through the Notes
        for(int i = 0; i < 4; i++) {
            // looping through the components of a Note
            for(int j = 0; j < 4; j++) {
                // small probability that a component of a Note is mutated
                double test = rand.nextDouble();
                if (test <= mProb) {
                    Note noteToBeMutated = original.getInternalValue()[i];
                    // mutate pitch
                    if (j == 0) {
                        int pitchPos = rand.nextInt(8);
                        Pitch newPitch = allPitches[pitchPos];
                        noteToBeMutated.setPitch(newPitch);
                    }
                    // mutate octave
                    else if (j == 1) {
                        int newOctave = rand.nextInt(9);
                        noteToBeMutated.setOctave(newOctave);
                    }
                    // mutate alteration
                    else if (j == 2) {
                        int alterationPos = rand.nextInt(3);
                        Alteration newAlteration = allAlterations[alterationPos];
                        noteToBeMutated.setAlteration(newAlteration);
                    }
                    // mutation duration
                    else if (j == 3) {
                        int durationPos = rand.nextInt(5);
                        int newDuration = (int) Math.pow(2, durationPos);
                        noteToBeMutated.setDuration(newDuration);
                    }
                    original.changeNote(noteToBeMutated, j);
                }
            }
        }
        return original;
    }

    /**
     * Mate two parents with random, one-point crossover. When mating, we only change the alteration, duration and
     * pitch values to make sure that the four Notes are still relative to one another.
     * TODO: doesn't pitch affect this too??
     * @param parent1 first ChoraleGene parent
     * @param parent2 second ChoraleGene parent
     * @return an array containing the two ChoraleGene children
     * @throws InvalidConfigurationException
     */
    public ChoraleGene[] crossover(ChoraleGene parent1, ChoraleGene parent2) throws InvalidConfigurationException {

        ChoraleGene child1 = parent1;
        ChoraleGene child2 = parent2;

        Random rand = new Random();
        int crossOverPoint = rand.nextInt(3);

        // crossover point is after the first/second Note
        if (crossOverPoint <= 1) {
            Note parentOneFirst = parent1.getInternalValue()[0];
            Note parentTwoFirst = parent2.getInternalValue()[0];
            Note childOneFirst = child1.getInternalValue()[0];
            Note childTwoFirst = child2.getInternalValue()[0];

            // change child1's first Note
            childOneFirst.setAlteration(parentTwoFirst.getAlteration());
            childOneFirst.setPitch(parentTwoFirst.getPitch());
            childOneFirst.setDuration(parentTwoFirst.getDuration());

            // change child2's first Note
            childTwoFirst.setAlteration(parentOneFirst.getAlteration());
            childTwoFirst.setPitch(parentOneFirst.getPitch());
            childTwoFirst.setDuration(parentOneFirst.getDuration());

            child1.changeNote(childOneFirst, 0);
            child2.changeNote(childTwoFirst, 0);

            // crossover point is after the second Note
            if (crossOverPoint == 1) {
                Note parentOneSecond = parent1.getInternalValue()[1];
                Note parentTwoSecond = parent2.getInternalValue()[1];
                Note childOneSecond = child1.getInternalValue()[1];
                Note childTwoSecond = child2.getInternalValue()[1];

                // change child1's first Note
                childOneSecond.setAlteration(parentTwoSecond.getAlteration());
                childOneSecond.setPitch(parentTwoSecond.getPitch());
                childOneSecond.setDuration(parentTwoSecond.getDuration());

                // change child2's first Note
                childTwoSecond.setAlteration(parentOneSecond.getAlteration());
                childTwoSecond.setPitch(parentOneSecond.getPitch());
                childTwoSecond.setDuration(parentOneSecond.getDuration());

                child1.changeNote(childOneSecond, 1);
                child2.changeNote(childTwoSecond, 1);
            }
        }

        // crossover point is after the third Note
        else if (crossOverPoint == 2) {
            Note parentOneLast = parent1.getInternalValue()[3];
            Note parentTwoLast = parent2.getInternalValue()[3];
            Note childOneLast = child1.getInternalValue()[3];
            Note childTwoLast = child2.getInternalValue()[3];

            // change child1's first Note
            childOneLast.setAlteration(parentTwoLast.getAlteration());
            childOneLast.setPitch(parentTwoLast.getPitch());
            childOneLast.setDuration(parentTwoLast.getDuration());

            // change child2's first Note
            childTwoLast.setAlteration(parentOneLast.getAlteration());
            childTwoLast.setPitch(parentOneLast.getPitch());
            childTwoLast.setDuration(parentOneLast.getDuration());

            child1.changeNote(childOneLast, 0);
            child2.changeNote(childOneLast, 0);
        }

        ChoraleGene[] children = new ChoraleGene[] {child1, child2};
        return children;
    }
}
