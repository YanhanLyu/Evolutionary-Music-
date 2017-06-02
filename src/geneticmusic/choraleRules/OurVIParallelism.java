package geneticmusic.choraleRules;

import geneticmusic.genes.ChoraleGene;
import geneticmusic.genes.Note;
import org.jgap.Gene;
import org.jgap.IChromosome;

/**
 * Created by yanhanlyu on 02/06/2017.
 */
public class OurVIParallelism {
    private static final double OCTAVE = 6;
    private static final double FIFTH = 3.5;
    private double weight;

    public OurVIParallelism(double weight){

        this.weight = weight;
    }

    public double evaluation(ChoraleGene[] genes) {
        double result = 0.0;

        for(int i = 0; i<genes.length-1; i++){
            Note[] currentChord = (Note[]) genes[i].getAllele(); // get the current chord
            Note[] nextChord = (Note[]) genes[i+1].getAllele(); // get the next chord
            boolean parallelismFound = false;

            for(int k = 0; k <= 3; k++){
                for(int j= 0; j <= 3; j++){
                    if(k!=j){
                        double distance = Math.abs(currentChord[k].distance(currentChord[j]));
                        if(distance == OCTAVE || distance == FIFTH){
                            double distanceNext = Math.abs(nextChord[k].distance(nextChord[j]));
                            if(distanceNext == distance)
                                parallelismFound = true;
                        }
                    }
                }
            }
            if(!parallelismFound)
                result+= 1/(genes.length-1);
        }
        return this.weight*result;
    }
}
