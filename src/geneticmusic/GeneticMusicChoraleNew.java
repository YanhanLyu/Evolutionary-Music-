package geneticmusic;

//import geneticmusic.fitness.ChoraleFitnessFunction;
import geneticmusic.genes.ChoraleGene;
import geneticmusic.choraleRules.*;
//import geneticmusic.genes.NoteGenerator;
//import geneticmusic.jmusic.bridge.ConverterUtil;
//import geneticmusic.domain.*;
//import javax.swing.JFrame;
import geneticmusic.genes.Note;
import geneticmusic.genes.NoteGenerator;
import geneticmusic.jmusic.bridge.ConverterUtil;
import jm.JMC;
import org.jgap.Chromosome;
import geneticmusic.genes.Alteration;
import geneticmusic.genes.Note;
import geneticmusic.genes.Pitch;
//import jm.util.Write;
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.data.xy.DefaultXYDataset;
//import org.jfree.data.xy.XYBarDataset;
//import org.jfree.data.xy.XYDataset;
//import org.jfree.data.xy.XYSeries;
//import org.jfree.data.xy.XYSeriesCollection;
//import org.jgap.Chromosome;
import jm.util.Write;
import org.jgap.Configuration;
//import org.jgap.FitnessFunction;
//import org.jgap.Gene;
//import org.jgap.Genotype;
//import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
//import org.jgap.Population;
import org.jgap.UnsupportedRepresentationException;
//import org.jgap.audit.EvolutionMonitor;
//import org.jgap.impl.BestChromosomesSelector;
//import org.jgap.impl.CrossoverOperator;
import org.jgap.impl.DefaultConfiguration;
//import org.jgap.impl.GABreeder;
//import org.jgap.impl.MutationOperator;
//import org.jgap.impl.TournamentSelector;
//import org.jgap.impl.WeightedRouletteSelector;
import java.util.*;
import jm.constants.Scales;


/**
 * Provides a news of evolving a population of melody
 *
 * @author Hazel Que, Yanhan Lyu
 * @version 31 May 2017
 */
public class GeneticMusicChoraleNew implements JMC {

    private int populationSize, chromosomeSize, selParam;
    private double mutationRate, crossoverRate;

    public GeneticMusicChoraleNew() {
        // defaults
        populationSize = 10;
        chromosomeSize = 4;
        mutationRate = 0.1;
        crossoverRate = 0.1;
        selParam = 1;
    }

    public ChoraleGene[][] GeneticMusicChoraleNew(ChoraleGene[][] pop) throws InvalidConfigurationException,
            UnsupportedOperationException, UnsupportedRepresentationException {

        // ********************** create a random population **********************
        // TODO: move these two lines to the evolve function
        // ChoraleGene[][] population = new ChoraleGene[populationSize][chromosomeSize];
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

        // ********************** single mutation **********************
        GeneticOperators geneticOperator = new GeneticOperators();
        Random rand = new Random();

        for (int i = 0; i < pop.length; i++) {
            double test = rand.nextDouble();
            if (test <= mutationRate) {
                // create a new melody
                ChoraleGene[] newMelody = new ChoraleGene[chromosomeSize];
                for (int j = 0; j < newMelody.length; j++) {
                    ChoraleGene choraleGene = pop[i][j];
                    ChoraleGene newGene = geneticOperator.mutate(choraleGene, mutationRate);
                    newMelody[j] = newGene;
                }
                pop[i] = newMelody;
            }
        }

        // ********************** single crossover **********************
        for (int i = 0; i < pop.length; i++) {
            double test = rand.nextDouble();
            if (test <= crossoverRate) {
                for (int j = 0; j < chromosomeSize - 1; j++) {
                    ChoraleGene currentNote = pop[i][j];
                    ChoraleGene nextNote = pop[i][j + 1];
                    pop[i][j] = nextNote;
                    pop[i][j + 1] = currentNote;
                }
            }
        }
        return pop;
    }

    public ChoraleGene[][] tournamentSelection(ChoraleGene[][] pop, int elitism) throws InvalidConfigurationException {
        Configuration cfg = new DefaultConfiguration();
        ChoraleGene[][] newPopulation = new ChoraleGene[populationSize][chromosomeSize];
        //implement tournament selection here
         for (int i = 0 ; i < populationSize; i ++){
             int fittestIndividual = GeneticMusicChoraleNew.findFittest(3, populationSize,pop);
             newPopulation[i] = pop[fittestIndividual];
         }

        Chromosome sampleChromosome = new Chromosome(cfg, pop[0]);
        Write.midi(ConverterUtil.getChoraleScore(sampleChromosome), "startChorale.mid");
        return newPopulation;
    }

    public static int findFittest(int k, int populationSize, ChoraleGene[][] population){
        int max = 0;
        Random rand = new Random();
        int maxindex = 0;
        for (int i = 0; i < k; i++){
            int index = rand.nextInt(populationSize);
            if (GeneticMusicChoraleNew.calculateFitness(population[index])>max){
                //System.out.println(GeneticMusicChoraleNew.calculateFitness(population[i]));
                maxindex = index;
            }
        }
        return maxindex;
    }

    public static double calculateFitness(ChoraleGene[] choraleGene){
        double weight = 1/10;
        Note tonic = new Note(Pitch.C, 5, Alteration.N, 4);
        OurVEVoiceExtension verticalExtension = new OurVEVoiceExtension(weight);
        OurVEVoiceIntervalRelation verticalIntervalRelation = new OurVEVoiceIntervalRelation(weight);
        OurHRInScale inScale = new OurHRInScale(Scales.MAJOR_SCALE, tonic, weight*2);
        OurHCValidChords validChords = new OurHCValidChords(weight, Scales.MAJOR_SCALE, tonic);
        OurVIVoiceCross voiceCross = new OurVIVoiceCross(weight*1);
        OurVLMelodyContinuity melodyConsistency = new OurVLMelodyContinuity(weight*2.2);
        OurVLMediumVoicesContinuity mediumVoices = new OurVLMediumVoicesContinuity(weight*1);
        OurHCAvoidDissonances avoidDissonances = new OurHCAvoidDissonances(weight*2);
        OurVIParallelism parallelism = new OurVIParallelism(weight*1);
        OurHCDuplicateFundamental duplicateFundamental = new OurHCDuplicateFundamental(weight*2, tonic, Scales.MAJOR_SCALE);
        OurVLMediumVoicesContinuity mvc = new OurVLMediumVoicesContinuity(0.2);
        return mvc.evaluation(choraleGene);
    }
}