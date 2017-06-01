package geneticmusic;

import geneticmusic.fitness.ChoraleFitnessFunction;
import geneticmusic.genes.ChoraleGene;
import geneticmusic.genes.NoteGenerator;
import geneticmusic.jmusic.bridge.ConverterUtil;
import geneticmusic.domain.*;
import javax.swing.JFrame;
import jm.JMC;
import jm.util.Write;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYBarDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.Population;
import org.jgap.UnsupportedRepresentationException;
import org.jgap.audit.EvolutionMonitor;
import org.jgap.impl.BestChromosomesSelector;
import org.jgap.impl.CrossoverOperator;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.GABreeder;
import org.jgap.impl.MutationOperator;
import org.jgap.impl.TournamentSelector;
import org.jgap.impl.WeightedRouletteSelector;

/**
 * @author Hazel Que, Yanhan Lyu
 * @version 31 May 2017
 */
public class GeneticMusicChoraleNew implements JMC {

    public static void main(String[] args) throws InvalidConfigurationException,
            UnsupportedOperationException, UnsupportedRepresentationException {

        // default settings - even if user does not pass in command-line arguments, the program would run
        int populationSize = 40;
        int chromosomeSize = 8;
        int numGenerations = 100;
        String selector = "tournament";
        int tournamentk = 3;
        double tournamentp = 0.7;

        // parse the arguments
        // TODO: add command-line help text
        if (args.length > 0) {
            populationSize = Integer.parseInt(args[0]);
            chromosomeSize = Integer.parseInt(args[1]);
            numGenerations = Integer.parseInt(args[2]);
            selector = args[3];

            if(args.length > 4) {
                tournamentk = Integer.parseInt(args[4]);
                tournamentp = Double.parseDouble(args[5]);
            }
        }

        // print the input parameters to the terminal
        System.out.println("GA Configuration:");
        System.out.println("Population Size: " + populationSize);
        System.out.println("Chromosome Size: " + chromosomeSize);
        System.out.println("Number of Generations: " + numGenerations);
        System.out.println("Selection Operator" + selector);

        if(selector.equals("tournament")) {
            System.out.println("Tournament Size: " + tournamentk);
            System.out.println("Tournament p: " + tournamentp);
        }

        // default configuration object
        Configuration cfg = new DefaultConfiguration();

        // initialize a new population
        ChoraleGene[] population = new ChoraleGene[chromosomeSize];
        for(int i = 0; i < chromosomeSize; i++) {
            population[i] = new ChoraleGene(cfg);
            System.out.println(population[i].toString());
        }
    }
}
