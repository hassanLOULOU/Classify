package com.flatfisher.dialogflowchatbotsample;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/** A class containing the trained information */
public class BayesianData   implements Serializable{
    
    private HashMap categories;
    private Vocabulary voc;
    BayesianData data;
    Vocabulary v;
    public BayesianData(Vocabulary voc){
        boolean located=false; 
        File f = new File("bayesian.data");     
        /*for (int i = 0; i < f.list().length; i++) //System.out.println("\n "+f.list());
             if(f.list()[i].equals("bayesian.data"))
             {
               located=true;
               System.out.print("bayesian.data located\n");
            }
*/
       // if(located)
        //    System.out.print("located"); 
        
        try {   
            categories = new HashMap();
        ///first
           // if(!located)
            if(!f.exists())
            { System.out.println("\n\n\n\n\n\n\n\n noot locatedn\n\n\n\n\n"); this.voc = voc;}
            ///second
           if(f.exists()){
            data = BayesianData.load("bayesian.data");
            categories = data.categories;
            this.voc=voc;
            //this.voc = Vocabulary.loadResource("v.data");
            //this.voc.addWords(voc.set.iterator());
             System.out.println("n\n\n\n\n\n located \n\n\n\n\n"); 
            }
        } catch (Exception ex) {
            Logger.getLogger(BayesianData.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /** Add a new category */
    public void addCategory(String category, Category cat){
    categories.put(category,cat);
    }

    /** Get all categories */
    public Iterator getCategories(){
    return (Iterator)(categories.keySet()).iterator();
    }

    /** Get probability of a category occuring */
    public double getProbability(String category){
    Category c = (Category)categories.get(category);
    return c.getProbability();
    }

    public boolean inVoc(String word){
      return voc.member(word);   
    }

    /** Get conditional probability of a given word */
    public double lookup(String category, String word){
    Category c = (Category)categories.get(category);
    return c.lookup(word);
    }

    
    // IO functions

    public void save(String file) throws Exception{
	ObjectOutputStream stream;
        stream = new ObjectOutputStream(new FileOutputStream(file));
        stream.writeObject(this);
    }

    public static BayesianData load(String file) throws Exception{
	ObjectInputStream stream;
	BayesianData data = null;
	stream            = new ObjectInputStream(new FileInputStream(file));
        data              = (BayesianData)stream.readObject();
	return data;
    }

    public static BayesianData loadResource(String file) throws Exception{
	ObjectInputStream stream;
	BayesianData data = null;
	InputStream fis   = ClassLoader.getSystemResourceAsStream(file);
	stream            = new ObjectInputStream(fis);
	data              = (BayesianData) stream.readObject();
	return data;
    }
}
