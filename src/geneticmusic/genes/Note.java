/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticmusic.genes;

import java.util.Random;
import org.jgap.RandomGenerator;

/**
 * A note consists of: pitch (P for pause), octave, alteration and duration
 * 
 * @author Davide Nunes
 * @author Hazel Que, Yanhan Lyu
 * @version 30 May 2017
 */
public class Note {

    public static final int MAX_OCTAVE = 8;
    public static final int MIN_OCTAVE = 0;
    public static final int MIN_DURATION = 1;
    public static final int MAX_DURATION = 32;
    private int octave; //0 to 8 see above constants
    private Pitch pitch; // note pitch
    private Alteration alteration; //simple pitch alteration
    private int duration; //1 2 4 8 16 32 .... starting at whole note

    public Note(Pitch pitch, int octave, Alteration alteration, int duration) {
        this.pitch = pitch;
        this.octave = octave;
        this.alteration = alteration;
        this.duration = duration;
    }

    public Pitch getPitch() {
        return this.pitch;
    }

    public int getOctave() {
        return this.octave;
    }

    public int getDuration() {
        return this.duration;
    }

    public Alteration getAlteration() {
        return this.alteration;
    }

    public void setPitch(Pitch pitch) {this.pitch = pitch;}

    public void setOctave(int octave) {this.octave = octave;}

    public void setAlteration(Alteration alteration) {this.alteration = alteration;}

    public void setDuration(int duration) {this.duration = duration;}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();


        sb.append(pitch);

        if (!pitch.equals(Pitch.R)) {
            sb.append(':');
            sb.append(alteration);
            sb.append(':');
            sb.append(octave);
        }


        sb.append('/');
        sb.append(duration);


        return sb.toString();
    }

    @Override
    public int hashCode() {
        return pitch.hashCode()
                * alteration.hashCode()
                * octave
                * duration;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Note other = (Note) obj;
        if (this.octave != other.octave) {
            return false;
        }
        if (this.pitch != other.pitch) {
            return false;
        }
        if (this.alteration != other.alteration) {
            return false;
        }
        if (this.duration != other.duration) {
            return false;
        }
        return true;
    }

    /**
     * Compare how different two notes are
     * @param other - the note to be compared against
     * @return result - a value that indicates how different two notes are //TODO figure out the range in which result would lie in
     */
    public double distance(Note other) {
        double result = 0.0;
        if (!this.pitch.equals(Pitch.R) && !other.pitch.equals(Pitch.R)) {
            double toneDistance = 0.0;
            toneDistance = -1*((this.pitch.getValue() + alteration.getValue() )
                    - (other.pitch.getValue() + other.alteration.getValue()));
            int octaveAbsDiff = Math.abs(this.octave - other.octave);

            if (this.octave == other.octave)//DISTANCE
            {
                result = toneDistance;
            } else if (this.octave > other.octave) {
                result = -1 * ((octaveAbsDiff * 6) - toneDistance); //negative distance
            } else {
                result = (octaveAbsDiff * 6) + toneDistance; //positive distance  
            }
        }
        return result;
    }
}
