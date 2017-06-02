package geneticmusic;

//import geneticmusic.fitness.ChoraleFitnessFunction;
import geneticmusic.genes.ChoraleGene;
import geneticmusic.choraleRules.*;
//import geneticmusic.genes.NoteGenerator;
//import geneticmusic.jmusic.bridge.ConverterUtil;
//import geneticmusic.domain.*;
//import javax.swing.JFrame;
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
        int populationSize = 2;
        int chromosomeSize = 20;
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
        Configuration cfg = new DefaultConfiguration();
        ChoraleGene[] gene = new ChoraleGene[chromosomeSize];
        Melody[] population = new Melody[populationSize];
        for (int i = 0; i<populationSize; i++){
            for (int j = 0; j < chromosomeSize; j++) {
                gene[j] = new ChoraleGene(cfg);
                //System.out.println(gene[j].toString());
            }
            Melody melody = new Melody(chromosomeSize,gene);
            population[i] = melody;
        }

        GeneticOperators geneticOperator = new GeneticOperators();
        //mutate here

        // for (int i = 0; i<populationSize; i++){
        //     //ChoraleGene[] geneArray = population[i].getGene();
        //     ChoraleGene[] geneArray = population[i].getGene();
        //     System.out.println(geneArray[0]);
        //     // System.out.println(geneArray.toString());
        //     // System.out.println(OurChorale.calculateFitness(population[i]));
        //     for (int j = 0; j < chromosomeSize; j++) {
        //         // ChoraleGene c = geneArray[j];
        //         // //System.out.println("!!!!!!!!!"+c.toString());
        //         // ChoraleGene newGene = geneticOperator.mutate(c, mutationRate);
        //         // geneArray[j] = newGene;
        //         System.out.println(geneArray[j]);
        //     }
        //     // Melody melody = new Melody(chromosomeSize,geneArray);
        //     // population[i] = melody;
        // }

        // crossover
        // for (int i = 0; i<populationSize; i++){
        //     ChoraleGene[] geneArray = population[i].getGene();
        //     for (int j = 0; j < chromosomeSize; j= j+2) {
        //         if (i < chromosomeSize-1) {
        //             ChoraleGene c1 = geneArray[j];
        //             ChoraleGene c2 = geneArray[j + 1];
        //             ChoraleGene newGene[] = geneticOperator.crossover(c1, c2);
        //             geneArray[j] = newGene[0];
        //             geneArray[j + 1] = newGene[1];
        //         }
        //     }
        //     Melody melody = new Melody(chromosomeSize,geneArray);
        //     population[i] = melody;
        // }
        // Melody[] newPopulation = new Melody[populationSize];
        // for (int i = 0 ; i < populationSize; i ++){
        //     int fittestIndividual = GeneticMusicChoraleNew.findFittest(tournamentk, populationSize,population);
        //     newPopulation[i] = population[fittestIndividual];
        // }

        //fitness sharing here


        //implement tournament selection here


        // test if I'm right
        for (int i = 0; i<populationSize; i++){
            ChoraleGene[] printGene = population[i].getGene();
            //System.out.println(OurChorale.calculateFitness(population[i]));
            for (int j = 0; j < chromosomeSize; j++) {
                System.out.println("round: "+j);
                System.out.println("bass"+printGene[j].getBass().getPitch());
                System.out.println("alto"+printGene[j].getAlto().getPitch());
                System.out.println("soprano"+printGene[j].getSoprano().getPitch());
                System.out.println("tenor"+printGene[j].getTenor().getPitch()+"\n");
            }
        }
    }

    public static int findFittest(int k, int populationSize, Melody[] population){
        int max = 0;
        Random rand = new Random();
        int maxindex = 0;
        for (int i = 0; i < k; i++){
            int index = rand.nextInt(populationSize)+1;
            Melody melody = population[i];
            if (GeneticMusicChoraleNew.calculateFitness(melody)>max){
                System.out.println(GeneticMusicChoraleNew.calculateFitness(melody));
                maxindex = index;
            }
        }
        return maxindex;
    }

    public static double calculateFitness(Melody melody){
        OurMediumVoiceContinuityRule mvc = new OurMediumVoiceContinuityRule(0.2);
        return mvc.evaluation(melody.getGene());
    }
}