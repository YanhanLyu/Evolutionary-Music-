package geneticmusic.choraleRules;

import geneticmusic.genes.ChoraleGene;
import geneticmusic.genes.Note;

public class OurMediumVoiceContinuityRule{
    double weight;

    public OurMediumVoiceContinuityRule(double weight) {
        this.weight = weight;
    }


    public double evaluation(ChoraleGene genes[]) {
        double result = 0.0;
        for (int i = 0; i < genes.length - 1; i++) {
            Note[] currentChord = (Note[]) genes[i].getAllele();
            Note[] nextChord = (Note[]) genes[i + 1].getAllele();

            double distancea = currentChord[1].distance(nextChord[1]);
            distancea = Math.abs(distancea);

            double distancet = currentChord[2].distance(nextChord[2]);
            distancet = Math.abs(distancet);

            double distanceb = currentChord[2].distance(nextChord[2]);
            distanceb = Math.abs(distanceb);

            if (distancea < 2 && distancea > 0) {
                result += 1 / ((genes.length - 1) * 2.0); //proporcional to the number of intervals
            }

            if (distancet < 2 && distancet > 0) {
                result += 1 / ((genes.length - 1) * 2.0); //proporcional to the number of intervals
            }

            if (distanceb < 4 && distanceb > 0) {
                result += 1 / ((genes.length - 1) * 2.0); //proporcional to the number of intervals
            }
        }
        return result;


    }

    public String getName() {
        return "Other Voices Continuity Rule";
    }
}
