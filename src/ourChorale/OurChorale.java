import org.jgap.Configuration;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.InvalidConfigurationException;
import java.util.Random;

public class OurChorale{
	public static void main(String[] args) throws InvalidConfigurationException {
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

	 	// test if I'm right
	 	for (int i = 0; i<populationSize; i++){
	 		ChoraleGene[] printGene = population[i].getGene();
	 		System.out.println(OurChorale.calculateFitness(population[i]));
	 		for (int j = 0; j < chromosomeSize; j++) {
	 			//System.out.println("round: "+j);
	 			// System.out.println("bass"+printGene[j].getBass().getPitch());
	 			// System.out.println("alto"+printGene[j].getAlto().getPitch());
	 			// System.out.println("soprano"+printGene[j].getSoprano().getPitch());
	 			// System.out.println("tenor"+printGene[j].getTenor().getPitch()+"\n");
	 			

        	}
	 	}



	 	// calculate fitness!!

	 	// I'm right, I'm so clever.
	 	// double random1 = Math.random();//0-1, decide if do the mutation
	 	// // check if we need to do mutation on each individual
	 	// for (int i = 0; i < populationSize; i ++){
	 	// 	if (mutationRate < random){
	 	// 		// Hazel here!!!!!!!! love you
	 	// 	}
	 	// }





	}

	// most difficult part, hope I can fix it.
	public static double calculateFitness(Melody melody){
		VLMediumVoicesContinuity mvc = new VLMediumVoicesContinuity(0.2);
		return mvc.evaluation(melody.getGene());
	}

	 
	 
   
}