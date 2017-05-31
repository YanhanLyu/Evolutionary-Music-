/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticmusic.rulesInConstruction;

import geneticmusic.fitness.CompositionRule;
import geneticmusic.domain.Note;
import geneticmusic.domain.Pitch;
import geneticmusic.jmusic.bridge.ConverterUtil;
import jm.music.data.Phrase;
import org.jgap.Gene;
import org.jgap.IChromosome;

/**
 *
 * @author davide, Yanhan Lyu
 */
public class PauseRegulationRule implements CompositionRule{

    @Override
    public double evaluate(IChromosome ic) {
        int restCount = 0;
        
        for(Gene current: ic.getGenes()){
            Note currentNote = (Note) current.getAllele();
            // pitch.R is for pauses
            if (currentNote.getPitch().equals(Pitch.R)){
            	return 1;
            } else {
            	return 0;
            }
        } 
        
        return -restCount;
    }
    
}
