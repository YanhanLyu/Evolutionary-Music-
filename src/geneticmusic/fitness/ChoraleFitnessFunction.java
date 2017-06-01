/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticmusic.fitness;

import geneticmusic.choraleRules.HCAvoidDissonances;
import geneticmusic.choraleRules.HCDuplicateFundamental;
import geneticmusic.choraleRules.HCValidChords;
import geneticmusic.choraleRules.HRInScale;
import geneticmusic.choraleRules.VEVoiceExtension;
import geneticmusic.choraleRules.VEVoiceIntervalRelation;
import geneticmusic.choraleRules.VIParallelism;
import geneticmusic.choraleRules.VIVoiceCross;
import geneticmusic.choraleRules.VLMediumVoicesContinuity;
import geneticmusic.choraleRules.VLMelodyContinuity;
import geneticmusic.genes.Alteration;
import geneticmusic.genes.Note;
import geneticmusic.genes.Pitch;
import jm.constants.Scales;
import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

/**
 * The fitness function for evolutionary music
 *
 * @author davide
 * @author Hazel Que, Yanhan Lyu
 * @version 30 May 2017
 */
public class ChoraleFitnessFunction extends AbstractCompositionFitness {

   public ChoraleFitnessFunction(){
       super();
   }

    /**
     * set of rules (associated with weights) to be used for the program
     */
    @Override
    protected void configRules() {
        double weight = 1/10.0;

        // set the first note of a particular scale as a reference point for some of the fitness rules
        Note tonic = new Note(Pitch.C, 5, Alteration.N, 4);
        
        VEVoiceExtension verticalExtension = new VEVoiceExtension(weight);
        VEVoiceIntervalRelation verticalIntervalRelation = new VEVoiceIntervalRelation(weight);
        HRInScale inScale = new HRInScale(Scales.MAJOR_SCALE, tonic, weight*2);
        HCValidChords validChords = new HCValidChords(weight, Scales.MAJOR_SCALE, tonic);
        VIVoiceCross voiceCross = new VIVoiceCross(weight*1);
        VLMelodyContinuity melodyConsistency = new VLMelodyContinuity(weight*2.2);
        VLMediumVoicesContinuity mediumVoices = new VLMediumVoicesContinuity(weight*1);
        HCAvoidDissonances avoidDissonances = new HCAvoidDissonances(weight*2);
        VIParallelism parallelism = new VIParallelism(weight*1);
        HCDuplicateFundamental duplicateFundamental = new HCDuplicateFundamental(weight*2, tonic, Scales.MAJOR_SCALE);

        addRule(verticalExtension);//add rule to rule set
        addRule(verticalIntervalRelation);
        addRule(inScale);
        addRule(validChords);
        addRule(voiceCross);
        addRule(melodyConsistency);
        addRule(mediumVoices);
        addRule(avoidDissonances);
        addRule(parallelism);
        addRule(duplicateFundamental);
    }
    
}
