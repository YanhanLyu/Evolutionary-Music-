/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticmusic.genes;

import geneticmusic.domain.Note;
import java.io.Serializable;
import org.jgap.BaseGene;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import org.jgap.RandomGenerator;
import org.jgap.UnsupportedRepresentationException;

/**
 * Each chorale gene represents a quarter note in terms of rhythm
 * Each chorale gene represents a set of notes for the current tempo in the measure
 * 
 * The notes can be quarter (4) or semi-quarter (8), so each gene can have 2 notes maximum
 * For simplicity reasons the notes are all quarter for now
 *
 * @author Davide Nunes
 * @author Hazel Que, Yanhan Lyu
 * @version 30 May 2017
 */
public class ChoraleGene extends BaseGene implements Gene, Serializable{

    private Note soprano;
    private Note alto;
    private Note tenor;
    private Note bass;

    /**
     * Constructor that creates a random Note
     * @param conf - current configuration
     * @throws InvalidConfigurationException
     */
    public ChoraleGene(Configuration conf) throws InvalidConfigurationException{
        this(conf, 
                NoteGenerator.getRandomNote(4, 5, 4, 4), 
                NoteGenerator.getRandomNote(3, 5, 4, 4), 
                NoteGenerator.getRandomNote(3, 4, 4, 4), 
                NoteGenerator.getRandomNote(2, 4, 4, 4));  
    }

    /**
     * Set up a new Note within the given values
     *
     * @param conf - default configuration object
     * @param soprano - soprano Note
     * @param alto - alto Note
     * @param tenor - tenor Note
     * @param bass - bass Note
     * @throws InvalidConfigurationException
     */
    public ChoraleGene(Configuration conf, Note soprano, 
                                           Note alto,
                                           Note tenor,
                                           Note bass    ) throws InvalidConfigurationException{
        super(conf);
        this.soprano = soprano;
        this.alto = alto;
        this.tenor = tenor;
        this.bass = bass;
    }

    @Override
    protected Object getInternalValue() {
        return new Note[] {soprano, alto, tenor, bass};
    }

    /**
     * Provides an implementation-independent means for creating new Gene instances
     * @return - new ChoraleGene
     */
    @Override
    protected Gene newGeneInternal() {
        try {
        // Construct a NoteGene with the values from the currently created gene
        // -------------------------------------------------------------
        return new ChoraleGene(getConfiguration(),soprano,alto,tenor,bass); 
              
      } catch (InvalidConfigurationException ex) {
            throw new IllegalStateException(ex.getMessage());
      }
    }

    @Override
    public void setAllele(Object o) {
        Note[] notes = (Note []) o;
        this.soprano = notes[0];
        this.alto = notes[1];
        this.tenor = notes[2];
        this.bass = notes[3];
    }

    //for persistence in XML 
    @Override
    public String getPersistentRepresentation() throws UnsupportedOperationException {
        return "( "+soprano.toString()+" | "+
                   alto.toString()+" | "+
                   tenor.toString()+" | "+
                   bass.toString()+
                ") ";
    }
    
    @Override
    public String toString(){
        return getPersistentRepresentation();
    }

    //note parse from XML persistence
    @Override
    public void setValueFromPersistentRepresentation(String string) throws UnsupportedOperationException, UnsupportedRepresentationException {
      //TODO implements this if needed
    }

    /**
     * set a chosen note to have random values
     * @param rg - a random generator
     */
    @Override
    public void setToRandomValue(RandomGenerator rg) {
        if(!(rg instanceof NoteGenerator))
            throw new IllegalArgumentException("needs a Note generator in the configuration");
    
        NoteGenerator generator = (NoteGenerator) rg;
        
        int noteChosen = generator.nextInt(4);
        switch(noteChosen){
            case 0: this.soprano = generator.nextNote(4, 5, 4, 4); 
                break;
            case 1:  this.alto = generator.nextNote(3, 5, 4, 4); 
                break;
            case 2: this.tenor = generator.nextNote(3, 4, 4, 4); 
                break;
            case 3:  this.bass = generator.nextNote(2, 4, 4, 4);  
                break;
        }
    }

    @Override
    public void applyMutation(int i, double d) {
       setToRandomValue(new NoteGenerator());
    }

    @Override
    public int compareTo(Object t) {
        ChoraleGene other = (ChoraleGene) t;
        Note[] otherNotes = (Note[]) other.getAllele();
        return (int) soprano.distance(otherNotes[0]);
    }
}
