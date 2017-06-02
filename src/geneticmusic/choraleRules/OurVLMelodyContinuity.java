/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticmusic.choraleRules;

import geneticmusic.genes.ChoraleGene;
import geneticmusic.genes.Note;
import geneticmusic.fitness.AbstractCompositionRule;
import org.jgap.Gene;
import org.jgap.IChromosome;

/**
 * Voice Logic Rule -
 *
 * Compare the distance between every two adjacent Notes, fitness score increases if the distance is reasonably small
 *
 * @author Davide Nunes
 */
public class OurVLMelodyContinuity {
    private double weight;

    public OurVLMelodyContinuity(double weight){
        this.weight = weight;
    }

    public double evaluation(ChoraleGene[] genes) {
        double result = 0.0;


        for (int i = 0; i < genes.length - 1; i++) {
            Note[] currentChord = (Note[]) genes[i].getAllele();
            Note[] nextChord = (Note[]) genes[i + 1].getAllele();

            double distance = currentChord[0].distance(nextChord[0]);
            distance = Math.abs(distance);

            if (distance < 2 && distance >= 0) {
                result += 1 / ((genes.length - 1) * 1.0); //proportional to the number of intervals
            }
        }
        return this.weight*result;
    }
}