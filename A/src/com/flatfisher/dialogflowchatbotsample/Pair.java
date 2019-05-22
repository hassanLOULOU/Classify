package com.flatfisher.dialogflowchatbotsample;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/** A class for a tuple of two objects. */
public class Pair{

    private Object first;
    private Object second;

    public Pair(Object first, Object second){
	this.first  = first;
	this.second = second;
    }
    
    /** Returns first tuple */
    public Object fst(){
	return first;
    }

    /** Returns second tuple */
    public Object snd(){
	return second;
    }

}
