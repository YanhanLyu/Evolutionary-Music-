
public class Melody{
	private ChoraleGene chorale[];
	 public Melody(int length, ChoraleGene[] gene){
        chorale = new ChoraleGene[length];
        for (int i = 0; i < length; i++){
        	chorale[i] = gene[i];
        }
    }

    public ChoraleGene[] getGene(){
    	return chorale;
    }
    
}