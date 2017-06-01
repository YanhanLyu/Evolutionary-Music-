/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticmusic.choraleRules;

import geneticmusic.domain.Alteration;
import geneticmusic.domain.Note;
import geneticmusic.domain.Pitch;
import geneticmusic.fitness.AbstractCompositionRule;
import org.jgap.Gene;
import org.jgap.IChromosome;

/**
 * Vertical Equilibrium Voice Extension Rule -
 * Each voice has approximately 1.5 octaves, and for every Note that falls within the range,
 * fitness score increases
 * 
 * @author Davide Nunes
 * @author Hazel Que, Yanhan Lyu
 * @version 30 May 2017
 */
public class VEVoiceExtension extends AbstractCompositionRule {

    //create reference notes
    private static final Note MIN_SOPRANO = new Note(Pitch.C, 4, Alteration.N, 4);
    private static final Note MAX_SOPRANO = new Note(Pitch.G, 5, Alteration.N, 4);
    private static final Note MIN_ALTO = new Note(Pitch.G, 3, Alteration.N, 4);
    private static final Note MAX_ALTO = new Note(Pitch.C, 5, Alteration.N, 4);
    private static final Note MIN_TENOR = new Note(Pitch.A, 3, Alteration.N, 4);
    private static final Note MAX_TENOR = new Note(Pitch.G, 4, Alteration.N, 4);
    private static final Note MIN_BASS = new Note(Pitch.D, 2, Alteration.N, 4);
    private static final Note MAX_BASS = MIN_SOPRANO;

    public VEVoiceExtension(double weight) {
        super(weight);
    }

    /**
     * fitness evaluation for Vertical Equilibrium rule
     * @param ic - current chromosome
     * @return - fitness score for VE rule
     */
    @Override
    protected double evaluation(IChromosome ic) {
        double result = 0.0;

        //for each note within range, the note gets a score
        Gene[] genes = ic.getGenes();
        double totalChords = genes.length;

        // compare the current chord with the first note
        for (int i = 0; i < genes.length; i++) {
            Note[] currentNotes = (Note[]) genes[i].getAllele(); // get the current chord
            if (withinRange(0, currentNotes[0])){
                result += 1 / (totalChords * 4.0);
            }
            if (withinRange(0, currentNotes[0])) {
                result += 1 / (totalChords * 4.0);
            }
            if (withinRange(0, currentNotes[0])) {
                result += 1 / (totalChords * 4.0);
            }
            if (withinRange(0, currentNotes[0])) {
                result += 1 / (totalChords * 4.0);
            }
        }
        return result;
    }

    /**
     * A utility method that checks whether a Note is within range
     * @param position - the position of the Note to be checked against
     * @param note -
     * @return a boolean value that indicates whether the Note is within range
     * //TODO why is it always 0
     */
    private static boolean withinRange(int position, Note note) {
        boolean result = false;
        switch (position) {
            case 0:
                result = (note.distance(MIN_SOPRANO) <= 0 && //soprano witin range? 
                        note.distance(MAX_SOPRANO) >= 0);
                break;
            case 1:
                result = (note.distance(MIN_ALTO) <= 0 && //soprano witin range? 
                        note.distance(MAX_ALTO) >= 0);
                break;
            case 2:
                result = (note.distance(MIN_TENOR) <= 0 && //soprano witin range? 
                        note.distance(MAX_TENOR) >= 0);
                break;
            case 3:
                result = (note.distance(MIN_BASS) <= 0 && //soprano witin range? 
                        note.distance(MAX_BASS) >= 0);
                break;
        }
        return result;
    }

    @Override
    public String getName() {
        return "Voice Extension Rule";
    }
}
