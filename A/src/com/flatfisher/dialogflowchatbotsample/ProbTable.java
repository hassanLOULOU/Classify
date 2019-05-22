package com.flatfisher.dialogflowchatbotsample;



import java.util.*;
import java.io.*;
public class ProbTable implements Serializable{

    HashMap map;
    double constant;

    public ProbTable(HashMap freq, int total, int vocSize){
	map = freq;
	constant =1/((double)total*(double)vocSize);
	calculate((double)total, (double)vocSize);
    }

    public int size(){
     return map.size();   
    }

    private void calculate(double total, double vocSize){
  	 Iterator it = map.entrySet().iterator();
	 while(it.hasNext()){
	     Map.Entry e = (Map.Entry)it.next();
	     double i = (double)((Integer)e.getValue()).intValue() + 1;
	     double f = i/(total + vocSize);
	     map.put(e.getKey(),new Double(f)); 	    
	 }
    }
    
    public double lookup(String s){
	Double f = (Double) map.get(s);
	if (f == null)
	    return constant;
	else
	    return f.floatValue();
    }
    
    public String toString(){
      return map.toString();   
    }
}
