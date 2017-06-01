/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticmusic.choraleRules;

import geneticmusic.domain.Note;
import geneticmusic.fitness.AbstractCompositionRule;
import geneticmusic.jmusic.bridge.ConverterUtil;
import geneticmusic.jmusic.bridge.HarmonicUtils;
import org.jgap.Gene;
import org.jgap.IChromosome;

/**
 * Harmonic Consistency Rule -
 * Gives a bonus to correctly constructed chords
 * 
 * @author davide nunes
 * @author Hazel Que, Yanhan Lyu
 * @version 30 May 2017
 */
public class HCValidChords extends AbstractCompositionRule{
    private int[] scale;
    private int tonic;
    
    public HCValidChords(double weight, int[] scale, Note tonic){
        super(weight);
        this.scale = scale;
        this.tonic = ConverterUtil.getPitch(tonic);
    }

    @Override
    protected double evaluation(IChromosome ic) {
        double result = 0.0;
        
        Gene[] genes = ic.getGenes();
        for(int i = 0; i < genes.length; i++){
            Note[] currentChord = (Note[]) genes[i].getAllele();
            if(HarmonicUtils.isValidChord(currentChord, scale, tonic))
                result+= 1/(genes.length*1.0);
        }
        return result;
    }

    @Override
    public String getName() {
       return "Valid chords rule";
    }
}
