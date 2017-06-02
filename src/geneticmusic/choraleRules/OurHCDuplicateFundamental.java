/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticmusic.choraleRules;

import geneticmusic.genes.ChoraleGene;
import geneticmusic.genes.Note;
import geneticmusic.fitness.AbstractCompositionRule;
import geneticmusic.jmusic.bridge.ConverterUtil;
import geneticmusic.jmusic.bridge.HarmonicUtils;
import org.jgap.Gene;
import org.jgap.IChromosome;

/**
 * Fundamental Duplicate Rule -
 *
 * @author davide
 * @author Hazel Que, Yanhan Lyu
 * @version 30 May 2017
 */
public class OurHCDuplicateFundamental{
    private int tonic;
    private int[] scale;
    private double weight=0;

    public OurHCDuplicateFundamental(double weight, Note tonic, int[] scale){
        this.weight = weight;
        this.tonic = ConverterUtil.getPitch(tonic);
        this.scale = scale;
    }

    public double evaluation(ChoraleGene[] genes) {
        double result = 0.0;

        for(Gene g : genes) {
            Note[] chord = (Note[]) g.getAllele();
            int[] foundChord = HarmonicUtils.findChord(chord, tonic, scale);
            if (foundChord != null)
                if (HarmonicUtils.isFundamentalDuplicated(chord, foundChord))
                    result += 1 / (genes.length * 1.0);
        }
        return this.weight*result;
    }

}