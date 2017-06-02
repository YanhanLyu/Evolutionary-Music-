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
import jm.JMC;
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


/**
 * @author Hazel Que, Yanhan Lyu
 * @version 31 May 2017
 */
public class GeneticMusicChoraleNew implements JMC {

    public static void main(String[] args) throws InvalidConfigurationException,
            UnsupportedOperationException, UnsupportedRepresentationException {

        // // default settings - even if user does not pass in command-line arguments, the program would run
        // int populationSize = 40;
        // int chromosomeSize = 8;
        // int numGenerations = 100;
        // String selector = "tournament";
        // int tournamentk = 3;
        // double tournamentp = 0.7;

        // // parse the arguments
        // // TODO: add command-line help text
        // if (args.length > 0) {
        //     populationSize = Integer.parseInt(args[0]);
        //     chromosomeSize = Integer.parseInt(args[1]);
        //     numGenerations = Integer.parseInt(args[2]);
        //     selector = args[3];

        //     if(args.length > 4) {
        //         tournamentk = Integer.parseInt(args[4]);
        //         tournamentp = Double.parseDouble(args[5]);
        //     }
        // }

        // // print the input parameters to the terminal
        // System.out.println("GA Configuration:");
        // System.out.println("Population Size: " + populationSize);
        // System.out.println("Chromosome Size: " + chromosomeSize);
        // System.out.println("Number of Generations: " + numGenerations);
        // System.out.println("Selection Operator" + selector);

        // if(selector.equals("tournament")) {
        //     System.out.println("Tournament Size: " + tournamentk);
        //     System.out.println("Tournament p: " + tournamentp);
        // }
        int populationSize = 10;
        int chromosomeSize = 4;
        int numGenerations = 100;
        double mutationRate = 0.1;
        String selector = "tournament";
        int tournamentk = 3;
        double tournamentp = 0.7;

        // parse the arguments
        if (args.length > 0) {
            populationSize = Integer.parseInt(args[0]);
            chromosomeSize = Integer.parseInt(args[1]);
            numGenerations = Integer.parseInt(args[2]);
            selector = args[3];
            mutationRate = Integer.parseInt(args[4]);

            if (args.length > 5) {
                tournamentk = Integer.parseInt(args[5]);
                tournamentp = Double.parseDouble(args[6]);
            }
        }
        System.out.println("GA configuration:");
        System.out.println("population size: " + populationSize);
        System.out.println("chromosome size: " + chromosomeSize);
        System.out.println("number of generations: " + numGenerations);
        System.out.println("selection operator: " + selector);

        if(selector.equals("tournament")){
            System.out.println("tournament size: "+tournamentk);
            System.out.println("tournament p: "+tournamentp);

        }

        // default configuration object
        //Configuration cfg = new DefaultConfiguration();

        // initialize a new population
        // ChoraleGene[] population = new ChoraleGene[chromosomeSize];
        // for(int i = 0; i < chromosomeSize; i++) {
        //     population[i] = new ChoraleGene(cfg);
        //     System.out.println(population[i].toString());
        // }

        // ********************** create a random population **********************
        Configuration cfg = new DefaultConfiguration();
        ChoraleGene[][] population = new ChoraleGene[populationSize][chromosomeSize];
        for(int i = 0; i < population.length; i++) {
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
            population[i] = melody;
        }
        
        // ********************** single mutation **********************
        GeneticOperators geneticOperator = new GeneticOperators();
        Random rand = new Random();
        //mutate here
         for (int i = 0; i< populationSize; i++){
             double test = rand.nextDouble();
             if (test <= mutationRate) {
                 // create a new melody
                 ChoraleGene[] newMelody = new ChoraleGene[chromosomeSize];
                 for (int j = 0; j < newMelody.length; j++) {
                     ChoraleGene choraleGene = population[i][j];
                     ChoraleGene newGene = geneticOperator.mutate(choraleGene, mutationRate);
                     newMelody[j] = newGene;
                 }
                 population[i] = newMelody;
             }
         }

         //crossover
         for (int i = 0; i<populationSize; i++){
             ChoraleGene[] newGenes = new ChoraleGene[chromosomeSize];
             for (int j = 0; j < chromosomeSize; j= j+2) {
                 if (i < chromosomeSize-1) {
                     ChoraleGene choraleGene1 = population[i][j];
                     ChoraleGene choraleGene2 = population[i][j+1];
                     //System.out.println("ChoraleGene1"+choraleGene1.toString());
                     //System.out.println("ChoraleGene2"+choraleGene2.toString());
                     ChoraleGene newGene[] = geneticOperator.crossover(choraleGene1, choraleGene2);
                     //System.out.println("new ChoraleGene1"+newGene[0].toString());
                     //System.out.println("new ChoraleGene2"+newGene[1].toString());

                     newGenes[j] = newGene[0];
                     newGenes[j+1] = newGene[1];
                 }
             }
             population[i] = newGenes;
         }

        //fitness sharing here
        ChoraleGene[][] newPopulation = new ChoraleGene[chromosomeSize][populationSize];
        //implement tournament selection here
         for (int i = 0 ; i < populationSize; i ++){
             int fittestIndividual = GeneticMusicChoraleNew.findFittest(200, populationSize,population);
             newPopulation[i] = population[fittestIndividual];
             //test population
//             for (int j = 0; j < chromosomeSize; j++){
//                 System.out.println("newPopulation");
//                 System.out.println(population[i][j].toString());
//             }
         }
    }

    public static int findFittest(int k, int populationSize, ChoraleGene[][] population){
        int max = 0;
        Random rand = new Random();
        int maxindex = 0;
        for (int i = 0; i < k; i++){
            int index = rand.nextInt(populationSize);
            if (GeneticMusicChoraleNew.calculateFitness(population[index])>max){
                System.out.println(GeneticMusicChoraleNew.calculateFitness(population[i]));
                maxindex = index;
            }
        }
        return maxindex;
    }

    public static double calculateFitness(ChoraleGene[] choraleGene){
        OurMediumVoiceContinuityRule mvc = new OurMediumVoiceContinuityRule(0.2);
        return mvc.evaluation(choraleGene);
    }
}