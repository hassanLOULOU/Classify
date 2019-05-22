package com.flatfisher.dialogflowchatbotsample;



import java.util.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Vocabulary implements Serializable{

    HashSet set;
    //Vocabulary voc;
    public Vocabulary(){
    /*boolean located=false; 
        File f = new File("build/classes");     
        for (int i = 0; i < f.list().length; i++) {
            if(f.list()[i].equals("v.data"))
               located=true;
        }     
            */    
    //if(!located)    
    set = new HashSet();
  
         /*if(located){
            try {
                this.voc = Vocabulary.loadResource("v.data");
                this.set=this.voc.set;
                System.out.println("n\n\n\n\n\n located \n\n\n\n\n");
            } catch (Exception ex) {
                Logger.getLogger(Vocabulary.class.getName()).log(Level.SEVERE, null, ex);
            }
            }*/
    }

    public int size(){
      return set.size();   
    }

    /** Add a list of words (iterated) */
    public void addWords(Iterator it){
    while(it.hasNext()){
        set.add(it.next());
    }
              System.out.println("\n\n\n\n\n\nvvvvoooooooooooooooooooooooooc\n\n\n\n"+ set);
    }

    /** Add a single word (should be stemmed) */
    public void addWord(String word){
    set.add(word);
    }

    /** Test if a word is member of a set */
    public boolean member(String word){
    return set.contains(word);
    }
    
    public Iterator iterator(){
     return set.iterator();   
    }
  
    
    
    
            // IO functions

    public void save(String file) throws Exception{
	ObjectOutputStream stream;
        stream = new ObjectOutputStream(new FileOutputStream(file));
        stream.writeObject(this);
    }

    public static Vocabulary load(String file) throws Exception{
	ObjectInputStream stream;
	Vocabulary data = null;
	stream            = new ObjectInputStream(new FileInputStream(file));
        data              = (Vocabulary)stream.readObject();
	return data;
    }

    public static Vocabulary loadResource(String file) throws Exception{
	ObjectInputStream stream;
	Vocabulary data = null;
	InputStream fis   = ClassLoader.getSystemResourceAsStream(file);
	stream            = new ObjectInputStream(fis);
	data              = (Vocabulary) stream.readObject();
	return data;
    }
}
