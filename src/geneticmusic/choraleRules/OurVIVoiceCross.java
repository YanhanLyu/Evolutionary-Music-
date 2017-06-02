package geneticmusic.choraleRules;

import geneticmusic.genes.Note;
import geneticmusic.genes.ChoraleGene;

/**
 * Created by yanhanlyu on 02/06/2017.
 */
public class OurVIVoiceCross {
    private double weight = 0;
    public OurVIVoiceCross(double weight){

        this.weight = weight;
    }

    public double evaluation(ChoraleGene[] genes) {
        double result = 0.0;

        for(int i = 0; i< genes.length; i++){
            Note[] currentChord = (Note[]) genes[i].getAllele();

            if(currentChord[1].distance(currentChord[0]) > 0) //not crossed
                result+= 1/(genes.length*3.0);
            if(currentChord[2].distance(currentChord[1]) > 0)
                result+= 1/(genes.length*3.0);
            if(currentChord[3].distance(currentChord[2]) > 0)
                result+= 1/(genes.length*3.0);
        }
        return weight*result;
    }
}
