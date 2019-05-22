package com.flatfisher.dialogflowchatbotsample;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.*;
import java.io.*;


/** A class for reading data from a file, and gives a of words. */
public class ReadData implements Serializable{
    //PorterStemmer ps=new PorterStemmer();
    Stemw as=new Stemw();
    public ReadData(){}
    
    public Vector readWords(String file,int d) throws Exception{
	return readWords(file,true,true,d);
    }
    
    public Vector readWords(String file, boolean wordInput, boolean lowercase,int d) throws Exception{
	BufferedReader r;
	String s;
	Vector result = new Vector();
	r = new BufferedReader(new FileReader(file));
	StreamTokenizer tokenizer = new StreamTokenizer(r);
        if(lowercase)
	    tokenizer.lowerCaseMode(true);
	if(wordInput){
	    tokenizer.ordinaryChar('.');
	    tokenizer.ordinaryChar('-');
	}
	int type = tokenizer.nextToken();
	while(type != StreamTokenizer.TT_EOF){
	    if(type == StreamTokenizer.TT_WORD)
		if(tokenizer.sval != null){
		    s = tokenizer.sval;
                       //System.out.print("\n\n\n\n\n hhhhhhhhhhhhh"+s+"\n\n\n\n\n\n\n");
                    if(d!=1){
                       //s=ps.stem(s);
                       s=as.stem(s);
                      // System.out.print("\n\n\n\n\n ooooooooooooooooo   stemmed "+s+"\n\n\n\n\n\n\n");
                       
                    }
		    result.add(s);
		}
	    type = tokenizer.nextToken();
	}
	r.close();
	return result;
    }
}
