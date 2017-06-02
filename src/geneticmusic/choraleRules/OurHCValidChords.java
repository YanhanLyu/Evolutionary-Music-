package geneticmusic.choraleRules;

/**
 * Created by yanhanlyu on 02/06/2017.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import geneticmusic.genes.ChoraleGene;
import geneticmusic.genes.Note;
import geneticmusic.jmusic.bridge.ConverterUtil;
import geneticmusic.jmusic.bridge.HarmonicUtils;


/**
 * Harmonic Consistency Rule -
 * Gives a bonus to correctly constructed chords
 *
 * @author davide nunes
 * @author Hazel Que, Yanhan Lyu
 * @version 30 May 2017
 */
public class OurHCValidChords{
    private int[] scale;
    private int tonic;
    private double weight = 0;

    public OurHCValidChords(double weight, int[] scale, Note tonic){
        this.weight = weight;
        this.scale = scale;
        this.tonic = ConverterUtil.getPitch(tonic);
    }


    public double evaluation(ChoraleGene[] genes) {
        double result = 0.0;
        for(int i = 0; i < genes.length; i++){
            Note[] currentChord = (Note[]) genes[i].getAllele();
            if(HarmonicUtils.isValidChord(currentChord, scale, tonic))
                result+= 1/(genes.length*1.0);
        }
        return weight*result;
    }
}

