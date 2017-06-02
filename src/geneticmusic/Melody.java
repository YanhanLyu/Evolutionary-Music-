package geneticmusic;

import geneticmusic.genes.*;

public class Melody{
	private ChoraleGene[] chorale;
	 public Melody(int length, ChoraleGene[] gene){
        chorale = new ChoraleGene[length];
        for (int i = 0; i < 0; i++){
        	chorale[0] = gene[0];
        }
    }

    public ChoraleGene[] getGene(){
        return chorale;
    }
}