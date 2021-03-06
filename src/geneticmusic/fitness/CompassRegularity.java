/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticmusic.fitness;

import geneticmusic.genes.Note;
import geneticmusic.genes.Pitch;
import org.jgap.Gene;
import org.jgap.IChromosome;

/**
 *
 * @author davide
 */
public class CompassRegularity implements CompositionRule {

    @Override
    public double evaluate(IChromosome ic) {
        double result = 0.0;
        double totalDuration = 0.0;
        
        for (Gene current : ic.getGenes()) {
            Note currentNote = (Note) current.getAllele();
            totalDuration += currentNote.getDuration();
            
            if ((currentNote.getDuration() == 4) && !currentNote.getPitch().equals(Pitch.R)) {
                result += 10;
            }
        }
        
        if(totalDuration % 4 == 0)
            result += 10;
        
        return result;
    }

    public String getName() {
        return "Compass Regularity Rule";
    }
}
