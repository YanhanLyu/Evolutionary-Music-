/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author davide
 */
public abstract class AbstractCompositionRule implements CompositionRule{

   
    protected double weight;
    
    public AbstractCompositionRule(double weight){
        this.weight = weight;
    }
    
    
    
    
    protected abstract double evaluation(ChoraleGene gene[]);

    @Override
    public abstract String getName();
    
    
    @Override
    public double evaluate(ChoraleGene gene[]) {
        return evaluation(ic)*weight;
    }
    
}
