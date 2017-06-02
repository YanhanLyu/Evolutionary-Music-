package geneticmusic;

import geneticmusic.*;
import geneticmusic.genes.ChoraleGene;
import geneticmusic.genes.Note;
import geneticmusic.genes.NoteGenerator;
import org.jgap.Configuration;
import org.jgap.InvalidConfigurationException;
import org.jgap.UnsupportedRepresentationException;
import org.jgap.impl.DefaultConfiguration;

/**
 * @author Hazel Que, Yanhan Lyu
 * @version 2 June 2017
 */
public class EvoMusicRun {

    public static void main(String[] args) throws InvalidConfigurationException,
            UnsupportedOperationException, UnsupportedRepresentationException {

        int numGenerations = 200;
        int chromosomeSize = 8;
        int populationSize = 100;
        double mutationRate = 0.001;
        double crossoverRate = 0.1;
        // selParam is for tournament selection
        int selParam = 3;


        // ********************** create a random population **********************
        ChoraleGene[][] pop = new ChoraleGene[populationSize][chromosomeSize];
        Configuration cfg = new DefaultConfiguration();
        for (int i = 0; i < pop.length; i++) {
            // ChoraleGene[] is a melody
            ChoraleGene[] melody = new ChoraleGene[chromosomeSize];
            // fill the sample melody with individual chords
            for (int j = 0; j < melody.length; j++) {
                ChoraleGene currentGene = new ChoraleGene(cfg);
                // fill the chord with individual Notes
                for (int k = 0; k < 4; k++) {
                    Note note = new NoteGenerator().nextNote();
                    currentGene.changeNote(note, j);
                }
                // add the generated chord to the melody
                melody[j] = currentGene;
            }
            pop[i] = melody;

        }
        GeneticMusicChoraleNew evolve = new GeneticMusicChoraleNew(populationSize,chromosomeSize
                ,mutationRate, crossoverRate, selParam);
        System.out.println("initial average fitness: "+evolve.calculateAveragFitness(pop));
        // evolve generation
        //GeneticMusicChoraleNew(populationSize, chromosomeSize, mutationRate, crossoverRate, selParam)
        for(int i = 0; i < numGenerations; i++) {
            pop = evolve.tournamentSelection(pop,cfg);
            evolve.runGeneration(pop);
            evolve.getMaxFitness(pop);
//            System.out.println("max: "+evolve.getMaxFitness(pop));
//            System.out.println("avg: "+evolve.calculateAveragFitness(pop));
        }
        System.out.println("final average fitness: "+evolve.calculateAveragFitness(pop));
        evolve.convertToMidi(cfg,evolve.getFittestIndividual(pop));
    }
}
