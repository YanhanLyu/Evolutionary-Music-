/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author davide
 */
public interface CompositionRule{
    public double evaluate(ChoraleGene[] gene);

    public String getName();
}
