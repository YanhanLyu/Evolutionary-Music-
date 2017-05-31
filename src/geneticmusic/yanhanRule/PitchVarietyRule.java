/*
 * Written by Yanhan
 */
package geneticmusic.rulesInConstruction;

import geneticmusic.fitness.CompositionRule;
import geneticmusic.jmusic.bridge.ConverterUtil;
import jm.music.data.Phrase;
import jm.music.tools.PhraseAnalysis;
import org.jgap.IChromosome;

public class NoteDensityRule implements CompositionRule{
    double density;
    double referenceDuration;
    
    
    /**
     * 
     * Pitch Variety
     */
    public PitchVarietyRule(double density, double referenceDuration){
        this.density = density;
        this.referenceDuration = referenceDuration;
    }

    @Override
    public double evaluate(IChromosome ic) {
        double result = 0.0;
       Phrase chromosome = ConverterUtil.convert(ic);
        try {
            double variety = PhraseAnalysis.pitchVariety(chromosome);
            if(variety > density)
                result += variety;
        } catch(Exception e){}
        return result;
    }
    
}
