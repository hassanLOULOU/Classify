package com.flatfisher.dialogflowchatbotsample;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hloulou
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;
import java.util.Vector;
public class ExecuteMe  {
             BayesianData data ;
            
    HashMap map;
    HashSet stop_words;
    int total_num_files;   public static final int LOWER_LIMIT = 3;

    //PorterStemmer stemmer;
    
    public void Trainer(String stopfile, String inputFile){
     ReadData reader = new ReadData();
     DataFile fileReader = new DataFile();

     if(stopfile == null)
     {stop_words = new HashSet(); System.out.print("hiihoooooo");}

     else
	 { 
	     try{
	           System.out.print("hiihoooooo"); stop_words = new HashSet(reader.readWords(stopfile,0));System.out.print("hiihoooooo");
	     }
	     catch(Exception e){
                 e.printStackTrace();
		 System.exit(0);
	     }
	 }
     Pair pair = null;

     try{
     pair  = fileReader.readDataFile(inputFile);
     }
     catch(Exception e){
	 System.out.println("Unable to read training input file: " + inputFile + ". Exit...");
	 System.exit(0);
     }
     map = (HashMap)pair.fst();
     total_num_files = ((Integer)pair.snd()).intValue();
    }
    
    public void train(String output_file) throws Exception{
    Vocabulary voc = buildVoc();
    
    /////////////////understanding
    /*
    Iterator i= voc.iterator();
    textArea1.append(" voc =  ");
       while(i.hasNext())  
           textArea1.append(" "+i.next()+"   "+voc.size() +"\n");
    */
    ////////////////
    
    int vocSize = voc.size();
    BayesianData data = new BayesianData(voc);
    System.out.println("[Constructed Vocabulary (" + vocSize + "words)]");
    ReadData reader = new ReadData();

    Iterator it = map.entrySet().iterator();
    while(it.hasNext()){ // For every category/////oookkkkkkyyyyyy   {b=[b2.txt, b1.txt], a=[a3.txt, a4.txt, a1.txt, a2.txt]} //without 6
        
        Map.Entry entry = (Map.Entry)it.next();
        ////////////textArea1.append("entry =  "+entry);
        String class_name = (String)entry.getKey();
        ////////////textArea1.append("[Training class: " + class_name + "]");
        FreqTable freq = new FreqTable();  
      
        String catName = (String)entry.getKey();
        ////////////textArea1.append(" catName  "+catName);
        
        Set fileSet = (Set)entry.getValue();
        int num_of_files = fileSet.size();
        Iterator file_it = fileSet.iterator(); 
        // For every file in a given category.
        while(file_it.hasNext()){
        String file = (String)file_it.next();
        file=file.substring(1);
        Iterator word_it = null;
        
	try{
	    word_it = (reader.readWords("20_newsgroup/"+catName+"/"+file,0)).iterator();
	}
	catch(Exception e){
	    System.out.println("Failed to read:  20_newsgroup/"+catName+"/"+file + ". Exit...");
	    System.exit(0);
	}
        while(word_it.hasNext()){
            String word = (String)word_it.next();
            if(voc.member(word)){
             freq.insert(word);   
            }
        }
        }
        ProbTable probTable = freq.createProbTable(vocSize);
        double cat_prob = (double)num_of_files/(double)total_num_files;
           //textArea1.append("[Trained class " + class_name + " (" + probTable.size() + " entries, " + cat_prob + ")]");
           //textArea1.append("\n probTable \n"+probTable.map+"\n");
           //textArea1.append("\n freq \n"+freq.total +"\n");
        
        
        ////////for each category  :  add its name and   category(probtable and cat_prob)s
        Category cat = new Category(class_name, probTable, cat_prob);
        data.addCategory(class_name,cat);
    }
    try{
	data.save(output_file);
    }
    catch(Exception e){
	System.out.println("Failed to save output to: " + output_file + ". Exit...");
	System.exit(0);
    }
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////   
    private Vocabulary buildVoc() throws Exception{
        Vocabulary voc = new Vocabulary();
        Vocabulary voc0 = new Vocabulary();

        boolean located = false;
        File f = new File("v.data");
        if(f.exists()){
            voc0 = Vocabulary.load("v.data");
            Iterator r = voc0.set.iterator();
            while (r.hasNext()) {
                String s = r.next().toString();
                voc.addWord(s);
            }

        }

    ReadData reader = new ReadData();
    FreqTable freq = new FreqTable();
    Iterator it2 = map.keySet().iterator();
    //textArea1.append("map keysss"+map.keySet());
    while(it2.hasNext()){

    	
    Iterator it = map. values().iterator();
    //textArea1.append("map values "+map.values());
    // list of sets with filenames
    while(it.hasNext()){
        String ss=it2.next().toString();
        Set fileSet = (Set)it.next();
        Iterator file_it = fileSet.iterator();
        while(file_it.hasNext()){
        String file = (String)file_it.next();
        Iterator word_it = null;
	try{
            /////////////////textArea1.append("\nss= "+ss);
		//textArea1.append("it2.next  "+ss+"/"+file);
	word_it = (reader.readWords("20_newsgroup/"+ss+"/"+file.substring(1),0)).iterator();
	}
	catch(Exception e){
	    System.out.println("Build voc :   Failed to read: " + ss+"/"+file + word_it + ". Exit...");
	    e.printStackTrace();
            System.exit(0);
	}
        while(word_it.hasNext()){
            String word = (String)word_it.next();
            if(!stop_words.contains(word)){
             freq.insert(word);   
            }
        }
        }
    }
    }//while2
    Iterator freq_it = freq.iterator();
    while(freq_it.hasNext()){
     Map.Entry entry = (Map.Entry)freq_it.next();
          voc.addWord((String)entry.getKey());
    }
    /*
        Iterator r22 = voc.set.iterator();
        while (r22.hasNext()) {
            String s = r22.next().toString();
            System.out.println("last voc \n" + s);
        }
    */
    return voc;
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
//    private void initComponents() {
//
//        jPanel1 = new javax.swing.JPanel();
//        jButton1 = new javax.swing.JButton();
//        jScrollPane1 = new javax.swing.JScrollPane();
//        jTextArea1 = new javax.swing.JTextArea();
//        jButton2 = new javax.swing.JButton();
//        textArea1 = new java.awt.TextArea();
//        jButton3 = new javax.swing.JButton();
//        jTextField1 = new javax.swing.JTextField();
//        jButton4 = new javax.swing.JButton();
//        jTextField2 = new javax.swing.JTextField();
//        jTextField3 = new javax.swing.JTextField();
//        jLabel1 = new javax.swing.JLabel();
//        jLabel2 = new javax.swing.JLabel();
//        jTextField4 = new javax.swing.JTextField();
//        jLabel3 = new javax.swing.JLabel();
//        jLabel4 = new javax.swing.JLabel();
//        jLabel5 = new javax.swing.JLabel();
//        jTextField5 = new javax.swing.JTextField();
//        jLabel6 = new javax.swing.JLabel();
//        jLabel7 = new javax.swing.JLabel();
//        jLabel8 = new javax.swing.JLabel();
//        jButton5 = new javax.swing.JButton();
//        jButton6 = new javax.swing.JButton();
//        textArea2 = new java.awt.TextArea();
//        textArea3 = new java.awt.TextArea();
//
//        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
//        setTitle("Real-Time Enriching of ontology Streams");
//
//        jButton1.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
//        jButton1.setText("Ã˜ÂµÃ™â€ Ã™â€˜Ã™ï¿½");
//        jButton1.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                jButton1ActionPerformed(evt);
//            }
//        });
//
//        jTextArea1.setColumns(20);
//        jTextArea1.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
//        jTextArea1.setRows(10);
//        jScrollPane1.setViewportView(jTextArea1);
//
//        jButton2.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
//        jButton2.setText("Ã˜Â¯Ã˜Â±Ã™â€˜Ã˜Â¨");
//        jButton2.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                jButton2ActionPerformed(evt);
//            }
//        });
//
//        jButton3.setText("Ã˜Â£Ã˜Â¶Ã™ï¿½ Ã˜ÂªÃ˜Â¨Ã™Ë†Ã™Å Ã˜Â¨Ã˜Â§Ã™â€¹");
//        jButton3.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                jButton3ActionPerformed(evt);
//            }
//        });
//
//        jButton4.setText("Ã˜Â§Ã˜Â­Ã˜Â°Ã™ï¿½ Ã˜ÂªÃ˜Â¨Ã™Ë†Ã™Å Ã˜Â¨Ã˜Â§Ã™â€¹");
//        jButton4.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                jButton4ActionPerformed(evt);
//            }
//        });
//
//        jLabel1.setFont(new java.awt.Font("Traditional Arabic", 3, 18)); // NOI18N
//        jLabel1.setText("Ã˜Â£Ã˜Â¶Ã™ï¿½ Ã˜ÂµÃ™â€ Ã™ï¿½Ã˜Â§Ã™â€¹ Ã˜Â¬Ã˜Â¯Ã™Å Ã˜Â¯Ã˜Â§ Ã™â€žÃ™Å Ã˜ÂªÃ™â€¦ Ã˜ÂªÃ˜Â¹Ã™â€žÃ™Å Ã™â€¦Ã™â€¡ Ã™â€žÃ™â€žÃ™â€¦Ã˜ÂµÃ™â€ Ã™â€˜Ã™ï¿½ Ã˜Â§Ã™â€žÃ˜Â¢Ã™â€žÃ™Å ");
//
//        jLabel2.setFont(new java.awt.Font("Traditional Arabic", 3, 24)); // NOI18N
//        jLabel2.setText("Ã˜Â¯Ã˜Â±Ã˜Â¨ Ã˜Â¹Ã™â€žÃ™â€° Ã™â€¦Ã™â€žÃ™ï¿½Ã˜Â§Ã˜Âª Ã˜Â¬Ã˜Â¯Ã™Å Ã˜Â¯Ã˜Â©");
//
//        jLabel3.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
//        jLabel3.setText("Ã™â€ Ã˜Âµ Ã˜Â§Ã™â€žÃ™â€¦Ã˜Â§Ã˜Â¯Ã˜Â© Ã˜Â£Ã™Ë† Ã˜Â§Ã™â€žÃ™â€¦Ã™Ë†Ã˜Â§Ã˜Â¯ Ã˜Â§Ã™â€žÃ˜Â¬Ã˜Â¯Ã™Å Ã˜Â¯Ã˜Â©");
//
//        jLabel4.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
//        jLabel4.setText("Ã˜ÂªÃ˜Â¯Ã˜Â±Ã™Å Ã˜Â¨ Ã˜Â§Ã™â€žÃ™â€¦Ã˜ÂµÃ™â€ Ã™ï¿½ ");
//
//        jLabel5.setBackground(new java.awt.Color(255, 204, 0));
//        jLabel5.setFont(new java.awt.Font("Tahoma", 3, 20)); // NOI18N
//        jLabel5.setForeground(new java.awt.Color(255, 51, 51));
//        jLabel5.setText("Ã˜Â§Ã™â€žÃ™â€¦Ã˜Â³Ã˜ÂªÃ˜Â´Ã˜Â§Ã˜Â± Ã˜Â§Ã™â€žÃ™â€šÃ˜Â§Ã™â€ Ã™Ë†Ã™â€ Ã™Å ");
//
//        jLabel6.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
//        jLabel6.setText("Ã˜ÂµÃ™â€ Ã™ï¿½ Ã˜Â­Ã˜Â²Ã™â€¦Ã˜Â© Ã™â€šÃ™Ë†Ã˜Â§Ã™â€ Ã™Å Ã™â€ ");
//
//        jLabel7.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
//        jLabel7.setText("Ã˜Â§Ã˜Â³Ã™â€¦ Ã˜Â§Ã™â€žÃ˜ÂµÃ™â€ Ã™ï¿½");
//
//        jLabel8.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
//        jLabel8.setText("Ã˜Â§Ã˜Â³Ã™â€¦ Ã˜Â§Ã™â€žÃ™â€¦Ã™â€žÃ™ï¿½");
//
//        jButton5.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
//        jButton5.setText("Ã˜Â³Ã˜Â¬Ã™â€ž Ã˜Â§Ã™â€žÃ™â€¦Ã˜Â§Ã˜Â¯Ã˜Â© Ã˜Â§Ã™â€žÃ™â€šÃ˜Â§Ã™â€ Ã™Ë†Ã™â€ Ã™Å Ã˜Â©");
//        jButton5.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                jButton5ActionPerformed(evt);
//            }
//        });
//
//        jButton6.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
//        jButton6.setText("Ã˜Â¥Ã™â€žÃ™â€° Ã˜Â£Ã™Å  Ã˜Â¨Ã˜Â§Ã˜Â¨ Ã™Å Ã™â€ Ã˜ÂªÃ™â€¦Ã™Å  Ã™â€¡Ã˜Â°Ã˜Â§ Ã˜Â§Ã™â€žÃ˜Â³Ã˜Â¤Ã˜Â§Ã™â€ž");
//        jButton6.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                jButton6ActionPerformed(evt);
//            }
//        });
//
//        textArea2.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
//
//        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
//        jPanel1.setLayout(jPanel1Layout);
//        jPanel1Layout.setHorizontalGroup(
//            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
//                .addContainerGap()
//                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
//                        .addComponent(jScrollPane1)
//                        .addGap(10, 10, 10)
//                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
//                            .addComponent(textArea1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 503, javax.swing.GroupLayout.PREFERRED_SIZE)
//                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
//                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
//                                        .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.TRAILING)
//                                        .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
//                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
//                                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                                        .addComponent(jButton3)))))
//                        .addGap(18, 18, 18))
//                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
//                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
//                            .addComponent(textArea3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                            .addGroup(jPanel1Layout.createSequentialGroup()
//                                .addComponent(textArea2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
//                                    .addGroup(jPanel1Layout.createSequentialGroup()
//                                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                        .addGap(161, 161, 161)
//                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
//                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
//                                        .addGap(305, 305, 305)
//                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)))))
//                        .addGap(25, 25, 25))
//                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
//                        .addGap(0, 132, Short.MAX_VALUE)
//                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                            .addGroup(jPanel1Layout.createSequentialGroup()
//                                .addGap(10, 10, 10)
//                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                .addGap(362, 362, 362))
//                            .addGroup(jPanel1Layout.createSequentialGroup()
//                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                .addGap(18, 18, Short.MAX_VALUE)
//                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                .addGap(59, 59, 59)))
//                        .addGap(21, 21, 21)
//                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
//                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
//                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                .addGap(25, 25, 25)))
//                        .addGap(205, 205, 205))))
//            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
//                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addGap(427, 427, 427))
//            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
//                .addGap(184, 184, 184)
//                .addComponent(jButton6)
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addGap(22, 22, 22))
//        );
//        jPanel1Layout.setVerticalGroup(
//            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel1Layout.createSequentialGroup()
//                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addGap(1, 1, 1)
//                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                    .addComponent(jLabel6)
//                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                    .addComponent(jLabel4))
//                .addGap(1, 1, 1)
//                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                    .addComponent(jButton1)
//                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
//                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
//                    .addComponent(textArea1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
//                    .addComponent(jButton6))
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
//                    .addGroup(jPanel1Layout.createSequentialGroup()
//                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                            .addComponent(jButton3)
//                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
//                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                            .addComponent(jButton4))
//                        .addGap(13, 13, 13)
//                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
//                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
//                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
//                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
//                    .addComponent(textArea2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                .addComponent(textArea3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                .addComponent(jButton5)
//                .addContainerGap())
//        );
//
//        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
//        getContentPane().setLayout(layout);
//        layout.setHorizontalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//        );
//        layout.setVerticalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(layout.createSequentialGroup()
//                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//        );
//
//        pack();
//    }// </editor-fold>                        

    private void classify() {                                         
        // TODO add your handling code here:
        double c;
        double max1=0; double max2=0 ;double max3=0;
        String s=new String("");String s2=new String("");String s3=new String("");
        

     try{
	 data = BayesianData.load("bayesian.data");
         System.out.println("\n\n\n\noook\n\n\n\n\n");
     }
     catch(Exception e){
	 //jTextArea1.append(" Unable to read trained data. Exit...");
         e.printStackTrace();
	 System.exit(0);
     }
  ReadData reader = new ReadData();
  double probsum =0;
 // if(args.length != 1){
 //   jTextArea1.append("Usage: runner filename");
 //   System.exit(0);
 // }
  Vector v = null;
  try{
      v = reader.readWords("tesing files/1.txt",0);
      
      Iterator r=v.iterator();
      while(r.hasNext())
      {System.out.print("test stemming ==== "+  r.next());}
  }
  catch(Exception e){
      System.out.println("Unable to open file: " + "tesing files/");//+jTextField5.getText() +". Exit..."+"\n");
      System.exit(0);
  }
  System.out.println("\n=======");
  System.out.println("=    Text Classification      =");
  System.out.println("=======");
  System.out.println("\nRead " + v.size() + " words from " + "file named ");//+jTextField5.getText());
  System.out.println("\nClassifying...\n");
  HashMap m = new HashMap();
  Iterator it = data.getCategories();
  while(it.hasNext()){
      String class_name = (String)it.next();
      double prob=1;
      Iterator words = v.iterator();
      while(words.hasNext()){
          String word = (String)words.next();
          if(data.inVoc(word)){
          prob += Math.log(data.lookup(class_name,word));
      }
      }
      prob+= Math.log(data.getProbability(class_name));
      probsum += prob;
      m.put(class_name,new Double(prob));
  }
  Iterator m_it = m.entrySet().iterator();
   System.out.println("=======");
   System.out.println("=    Table of Probability     =");
   System.out.println("=======\n");
  while(m_it.hasNext()){
   Map.Entry entry = (Map.Entry)m_it.next();
   Iterator m_it2 = m.entrySet().iterator();
   double res=0;
   while(m_it2.hasNext()){  // calculate formula 1/(1+e^(b1-a) + e^(b2-a))
       Map.Entry entry2 = (Map.Entry)m_it2.next();
       if(entry.getKey() != entry2.getKey()){
           res += Math.exp(((Double)entry2.getValue()).doubleValue() - ((Double)entry.getValue()).doubleValue());
       }
   }
   System.out.println(entry.getKey() + "   ");
   double d = ((Double)entry.getValue()).doubleValue();
   c=   (1 / (1 + res));
   if(c>max1){max3=max2; max2=max1; max1=c; s3=s2;s2=s; s=entry.getKey().toString();}
   System.out.println(c+"\n");

  }
      System.out.println("___________________________________________\nthe most probable is \n "+ s+"   "+max1+"\n");
            System.out.println("___________________________________________\nthe second category is \n "+ s2+"   "+max2+"\n");
                  System.out.println("___________________________________________\nthe third category is \n "+ s3+"   "+max3+"\n");
    }                                        

             @SuppressWarnings("CallToPrintStackTrace")
//    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
//        //stemmer =new PorterStemmer();
//        try {
//
//            boolean success = (new File("out.txt")).delete();
//            ///////////////////////////////////////allf//////////////////////////
//            String[] sDirs = null;
//            String[] sDirs2 = null;
//            Vector vDirs = new Vector();
//            Vector vDirs2 = new Vector();
//
//            File f = new File("20_newsgroup");
//                        //System.out.print("f=" + f.list());
//            sDirs = f.list();
//            System.out.println(sDirs.length);
//            //////////////////////////      2      //////////////////////////////////////
//            for (String sDir : sDirs) {
//                    File f2 = new File("20_newsgroup/" + sDir); //stemmer.stem(sDirs[i]));
//                    sDirs2 = f2.list();
//                    System.out.println(sDirs2.length);
//                    // Create file
//                    FileWriter fstream = new FileWriter("out.txt", true);
//                try (BufferedWriter out = new BufferedWriter(fstream)) {
//                        for (String sDirs21 : sDirs2) {
//                            out.write("a" + sDirs21);
//                            out.write(" ");
//                            out.write(sDir);
//                            out.newLine();
//                        }
//                }
//            }
//
//////////////////////////////////////////////////////////////
//            //trainer = new T2("stop.txt","out.txt");
//            //trainer.train("bayesian.data");
//            //textArea1.setText(trainer.s);
//            //for(int u=1;u<222222;u++){ textArea1.append("gg \n"); this.repaint(); textArea1.repaint();}
//            Trainer("stop.txt", "out.txt");
//            System.out.print("hiihoooooo");
//            //textArea1.append("gggg\n");
//            train("bayesian.data");
//
//            Vocabulary v = new Vocabulary();
//            v = buildVoc();
//            ///first
//            v.save("v.data");
//        } catch (Exception ex) {
//
//            System.out.print("Sever Err");
//            //Logger.getLogger(TextMining.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
////    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
////       String strManyDirectories="20_newsgroup/"+jTextField1.getText();
////        try{
////         // Create multiple directories
////             boolean success = (new File(strManyDirectories)).mkdirs();
////             if (success) {
////               System.out.println("Directories: " + strManyDirectories + " created");
////    }
////
////    }catch (Exception e){//Catch exception if any
////      System.err.println("Error: " + e.getMessage());
////    }
////    }
//
////    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
////        boolean bb =( new File("20_newsgroup/"+jTextField3.getText() + jTextField4.getText())).delete();
////        String strManyDirectories="20_newsgroup/"+jTextField2.getText();
////        File ff=new File(strManyDirectories);
////        if(ff.list().length==0)
////        {boolean  success2 = ff.delete();}
////        /*String strManyDirectories="20_newsgroup/"+jTextField2.getText();
////          File ff=new File(strManyDirectories);
////          String[] s=ff.list();
////
////          for(int i=0; i<s.length;i++)
////          {          System.out.print(s[i]); boolean success=(new File(s[i])).delete();}
////          boolean  success = ff.delete();*/
////    }
//
////    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
//        /*FileOutputStream outStream = null;
//        java.io.BufferedOutputStream bos=new BufferedOutputStream(outStream);
//        try {
//           // File f = new File("20_newsgroup/"+jTextField3.getText() + jTextField4.getText());
//
//             outStream = new FileOutputStream("20_newsgroup/"+jTextField3.getText()+"/"+ jTextField4.getText());
//             for(int i=0;i<textArea3.getText().length();++i)
//                 System.out.print(textArea3.toString());
//                outStream.write(textArea3.toString());
//                outStream.close();
//        } catch (IOException ex) {
//            Logger.getLogger(TextMining.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        */
////    try{
////    // Create file
////    FileWriter fstream = new FileWriter("20_newsgroup/"+jTextField3.getText()+"/"+ jTextField4.getText());
////    BufferedWriter out = new BufferedWriter(fstream);
////    //out.write(textArea3.getText());
////    //Close the output stream
////    out.close();
////    }catch (Exception e){//Catch exception if any
////      System.err.println("Error: " + e.getMessage());
////    }
////
//
//
//
////    }
//
//
//    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {
//                         // TODO add your handling code here:
//        double c;
//        double max1=0; double max2=0 ;double max3=0;
//        String s=new String("");String s2=new String("");String s3=new String("");
//
//
//     try{
//	 data = BayesianData.load("bayesian.data");
//         System.out.println("\n\n\n\noook\n\n\n\n\n");
//     }
//     catch(Exception e){
//	 //jTextArea1.append(" Unable to read trained data. Exit...");
//         e.printStackTrace();
//	 System.exit(0);
//     }
//  ReadData reader = new ReadData();
//  double probsum =0;
// // if(args.length != 1){
// //   jTextArea1.append("Usage: runner filename");
// //   System.exit(0);
// // }
//  Vector v = null;
// try{
//      /* FileOutputStream outStream = null;
//        try {
//           // File f = new File("20_newsgroup/"+jTextField3.getText() + jTextField4.getText());
//             outStream = new FileOutputStream("tesing files/1.txt");
//             for(int i=0;i<textArea2.getText().length();++i)
//                outStream.write(textArea2.getText().charAt(i));
//                outStream.close();
//        } catch (IOException ex) {
//            Logger.getLogger(TextMining.class.getName()).log(Level.SEVERE, null, ex);
//        }
//   */
//
//      try{
//    // Create file
//    FileWriter fstream = new FileWriter("tesing files/1.txt");
//    BufferedWriter out = new BufferedWriter(fstream);
//    //out.write(textArea2.getText());
//    out.close();
//    }catch (Exception e){//Catch exception if any
//      System.err.println("Error: " + e.getMessage());
//    }
//      v = reader.readWords("tesing files/1.txt",0);
//
//      Iterator r=v.iterator();
//      while(r.hasNext())
//      {System.out.print("test stemming ==== "+  r.next());}
//  }
//  catch(Exception e){
//      //jTextArea1.append("Unable to open file: " + "tesing files/1.txt . Exit..."+"\n");
//      System.exit(0);
//  }
//
//
//
//  System.out.println("\n=======");
//  System.out.println("=    Text Classification      =");
//  System.out.println("=======");
//  System.out.println("\nRead " + v.size() + " words from " + "file named 1.txt");
//  System.out.println("\nClassifying...\n");
//  HashMap m = new HashMap();
//  Iterator it = data.getCategories();
//  while(it.hasNext()){
//      String class_name = (String)it.next();
//      double prob=1;
//      Iterator words = v.iterator();
//      while(words.hasNext()){
//          String word = (String)words.next();
//          if(data.inVoc(word)){
//          prob += Math.log(data.lookup(class_name,word));
//      }
//      }
//      prob+= Math.log(data.getProbability(class_name));
//      probsum += prob;
//      m.put(class_name,new Double(prob));
//  }
//  Iterator m_it = m.entrySet().iterator();
//   System.out.println("=======");
//   System.out.println("=    Table of Probability     =");
//   System.out.println("=======\n");
//  while(m_it.hasNext()){
//   Map.Entry entry = (Map.Entry)m_it.next();
//   Iterator m_it2 = m.entrySet().iterator();
//   double res=0;
//   while(m_it2.hasNext()){  // calculate formula 1/(1+e^(b1-a) + e^(b2-a))
//       Map.Entry entry2 = (Map.Entry)m_it2.next();
//       if(entry.getKey() != entry2.getKey()){
//           res += Math.exp(((Double)entry2.getValue()).doubleValue() - ((Double)entry.getValue()).doubleValue());
//       }
//   }
//   System.out.println(entry.getKey() + "   ");
//   double d = ((Double)entry.getValue()).doubleValue();
//   c=   (1 / (1 + res));
//   if(c>max1){max3=max2; max2=max1; max1=c; s3=s2;s2=s; s=entry.getKey().toString();}
//   System.out.println(c+"\n");
//  }
//      System.out.println("___________________________________________\nthe most probable is \n "+ s+"   "+max1+"\n");
//            System.out.println("___________________________________________\nthe second category is \n "+ s2+"   "+max2+"\n");
//                  System.out.println("___________________________________________\nthe third category is \n "+ s3+"   "+max3+"\n");
//             }
//
//
    /**
     * @param args the command line arguments
     * @return 
     */
             
             
   private static void  whenWriteStringUsingBufferedWritter_thenCorrect(String filename,String str) 
            		  throws IOException {
            		    BufferedWriter writer = new BufferedWriter(new FileWriter(filename ));
            		    writer.write(str);
            		     
            		    writer.close();
   }
             
    public static void Execute(String query) throws IOException {
        ExecuteMe t= new ExecuteMe();
        whenWriteStringUsingBufferedWritter_thenCorrect("tesing files/1.txt",query);
        t.classify();
    }

public static void main(String args[]) {

  try {
	Execute("ما عقوبة شخص قام بجريمة قتل");
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
}}
