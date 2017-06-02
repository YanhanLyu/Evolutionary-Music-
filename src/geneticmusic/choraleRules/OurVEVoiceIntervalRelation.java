/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticmusic.choraleRules;

import geneticmusic.genes.Note;
import geneticmusic.genes.ChoraleGene;
import geneticmusic.fitness.AbstractCompositionRule;
import org.jgap.Gene;
import org.jgap.IChromosome;

/**
 * Vertical Equilibrium Voice Interval Relationsion Rule -
 * Checks the interval between Notes with different voices, for every interval that falls within range,
 * fitness score increases
 *
 * @author davide
 * @author Hazel Que, Yanhan Lyu
 * @version 30 May 2017
 */
public class OurVEVoiceIntervalRelation{

    private static final int SOPRANO = 0;
    private static final int ALTO = 1;
    private static final int TENOR = 2;
    private static final int BASS = 3;
    private double weight = 0;

    public OurVEVoiceIntervalRelation(double weight){

        this.weight = weight;
    }

    /**
     * fitness evaluation for Voice Interval Relation rule
     *
     * @return - fitness score for VEInterval Rule
     */
    public double evaluation(ChoraleGene[] genes) {
        double result = 0.0;

        // for each note within range, the note gets a score
        double totalChords = genes.length;

        for (int i = 0; i < genes.length; i++) {
            Note[] currentNotes = (Note[]) genes[i].getAllele(); //get the current chord
            if(withinRange(SOPRANO, ALTO, currentNotes[0], currentNotes[1]))
                result += 1 / (totalChords * 3.0);
            if(withinRange(ALTO, TENOR, currentNotes[1],currentNotes[2]))
                result += 1 / (totalChords * 3.0);
            if(withinRange(TENOR, BASS, currentNotes[2],currentNotes[3]))
                result += 1 / (totalChords * 3.0);
        }
        return weight*result;
    }

    /**
     * If the two Notes are Bass and Tenor, the interval needs to be <= 12
     * Otherwise, the interval needs to be <= 8
     * @param note1 - voice of the first Note
     * @param note2 - voice of the second Note
     * @param firstNote - first Note
     * @param secondNote - second Note
     * @return - a boolean value indicating whether the two Notes are within range
     */
    private static boolean withinRange(int note1, int note2, Note firstNote, Note secondNote) {
        boolean result = false;
        if(note1 == TENOR && note2 == BASS){
            result = Math.abs(firstNote.distance(secondNote)) <= 12;
        }
        else
            result = Math.abs(firstNote.distance(secondNote)) <= 8;
        return result;
    }
}
