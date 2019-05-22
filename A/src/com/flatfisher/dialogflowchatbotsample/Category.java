package com.flatfisher.dialogflowchatbotsample;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/** A class describing a learned category */ 
import java.io.*;
public class Category implements Serializable{
    
    ProbTable freq;
    String    name;
    double    prob;
    //PorterStemmer stemmer;

    public Category(String name, ProbTable freq, double prob){
	this.freq = freq;
	this.name = name;
	this.prob = prob;
    }

    public String getName(){
	return name;
    }
    
    public double getProbability(){
	return prob;
    }

    public double lookup(String word){
	return freq.lookup(word);
    }

}
