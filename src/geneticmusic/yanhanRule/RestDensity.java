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
     * @param density double value
     * @param referenceDuration Reference duration from JMUSIC Duration constants
     */
    public RestDensityRule(double density, double referenceDuration){
        this.density = density;
        this.referenceDuration = referenceDuration;
    }

    @Override
    public double evaluate(IChromosome ic) {
        double result = 0.0;
       Phrase chromosome = ConverterUtil.convert(ic);
        try {
            double currentDensity = PhraseAnalysis.restDensity(chromosome, referenceDuration);
            if(currentDensity > density)
                result += currentDensity;
        } catch(Exception e){}
        return result;
    }
    
}
