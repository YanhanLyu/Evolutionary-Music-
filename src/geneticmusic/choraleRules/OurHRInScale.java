package geneticmusic.choraleRules;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import geneticmusic.fitness.AbstractCompositionRule;
import geneticmusic.genes.ChoraleGene;
import geneticmusic.genes.Note;
import geneticmusic.jmusic.bridge.ConverterUtil;
import jm.JMC;
import jm.music.tools.PhraseAnalysis;
import org.jgap.Gene;
import org.jgap.IChromosome;


/**
 * Harmonic Rule -
 * All Notes must belong to the same scale. For every Note that falls on the correct scale,
 * fitness score increases
 *
 * @author Davide Nunes
 * @author Hazel Que, Yanhan Lyu
 * @version 30 May 2017
 */
public class OurHRInScale implements JMC {

    // initialize the current scale and tonic Note
    int[] scale;
    int tonic;
    private double weight;

    /**
     * Constructor
     * @param scale - current scale
     * @param tonicP - tonic Note
     * @param weight - relative importance of the rule
     */
    public OurHRInScale(int[] scale, Note tonicP, double weight) {
       this.weight = weight;
        this.scale = scale;
        this.tonic = ConverterUtil.getPitch(tonicP);
    }

    /**
     * fitness evaluation for Harmonic Rule
     * @return - fitness score for Harmonic Rule
     */

    public double evaluation(ChoraleGene[] genes) {
        double result = 0.0;

        for (int i = 0; i < genes.length; i++) {
            Note[] currentNotes = (Note[]) genes[i].getAllele(); //get the current chord

            // extract the pitches of current Notes
            jm.music.data.Note note1 = new jm.music.data.Note(ConverterUtil.getPitch(currentNotes[0]), CROTCHET);
            jm.music.data.Note note2 = new jm.music.data.Note(ConverterUtil.getPitch(currentNotes[1]), CROTCHET);
            jm.music.data.Note note3 = new jm.music.data.Note(ConverterUtil.getPitch(currentNotes[2]), CROTCHET);
            jm.music.data.Note note4 = new jm.music.data.Note(ConverterUtil.getPitch(currentNotes[3]), CROTCHET);

            if (PhraseAnalysis.isScale(note1, tonic, scale)) {
                result += (1 / (genes.length * 4.0));
            }
            if (PhraseAnalysis.isScale(note2, tonic, scale)) {
                result += (1 / (genes.length * 4.0));
            }
            if (PhraseAnalysis.isScale(note3, tonic, scale)) {
                result += (1 / (genes.length * 4.0));
            }
            if (PhraseAnalysis.isScale(note4, tonic, scale)) {
                result += (1 / (genes.length * 4.0));
            }
        }
        return result;
    }

}
