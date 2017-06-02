package geneticmusic.choraleRules;

/**
 * Created by yanhanlyu on 02/06/2017.
 */
/*
 *
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import geneticmusic.fitness.AbstractCompositionRule;
import geneticmusic.genes.Note;
import org.jgap.Gene;
import org.jgap.IChromosome;


import geneticmusic.genes.Note;
import geneticmusic.fitness.AbstractCompositionRule;
import org.jgap.Gene;
import org.jgap.IChromosome;
import geneticmusic.genes.ChoraleGene;

/**
 * Avoid Dissonance Rule -
 * If the distance between Soprano and Alto, Alto and Tenor, and Tenor and Bass is not Minor Second, Major Seven or
 * Augmented Forth, fitness score increases
 *
 * @author davide
 * @author Hazel Que, Yanhan Lyu
 * @version 30 May 2017
 */
public class OurHCAvoidDissonances{

    private static final double MINORSECOND = 0.5;
    private static final double MAJOR_SEVEN = 5.5;
    private static final double AUGMENTED_FORTH = 3;
    private double weight = 0;

    public OurHCAvoidDissonances(double weight){
        this.weight = weight;

    }

    public double evaluation(ChoraleGene[] genes) {
        double result = 0.0;

        for(int i = 0; i< genes.length; i++){
            Note[] currentChord = (Note[]) genes[i].getAllele();

            double distanceSA = Math.abs(currentChord[0].distance(currentChord[1]));
            double distanceAT = Math.abs(currentChord[1].distance(currentChord[2]));
            double distanceTB = Math.abs(currentChord[2].distance(currentChord[3]));

            if(distanceSA != MINORSECOND &&
                    distanceSA != MAJOR_SEVEN &&
                    distanceSA != AUGMENTED_FORTH)
                result+= 1/(genes.length*3.0);

            if(distanceAT != MINORSECOND &&
                    distanceAT != MAJOR_SEVEN &&
                    distanceAT != AUGMENTED_FORTH)
                result+= 1/(genes.length*3.0);

            if(distanceTB != MINORSECOND &&
                    distanceTB != MAJOR_SEVEN &&
                    distanceTB != AUGMENTED_FORTH)
                result+= 1/(genes.length*3.0);
        }
        return this.weight*result;
    }
}

