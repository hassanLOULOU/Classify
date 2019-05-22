

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flatfisher.dialogflowchatbotsample;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;


/**
 *
 * @author HASAN
 */
public class Stemw {
          Vector staticFiles;
          File f=new File("1.txt");
        //Stem stemmedText = new Stem ( f, staticFiles );
        public Stemw(){};
    public  String stem(String w){
                        readInStaticFiles ( );
                        Stem s=new Stem(f,staticFiles);
                        return(s.stemWord(w));
    }

                      // read in the static files
    protected  void readInStaticFiles ( )
    {
        // create a string buffer containing the path to the static files
        String pathToStemmerFiles = new StringBuffer ( System.getProperty ( "user.dir" ) + System.getProperty ( "file.separator" ) + "StemmerFiles" + System.getProperty ( "file.separator" ) ).toString ( );

        // create the vector composed of vectors containing the static files
        staticFiles = new Vector ( );
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "definite_article.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "duplicate.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "first_waw.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "first_yah.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "last_alif.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "last_hamza.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "last_maksoura.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "last_yah.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "mid_waw.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "mid_yah.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "prefixes.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "punctuation.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "quad_roots.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "stopwords.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "suffixes.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "tri_patt.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "tri_roots.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "diacritics.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "strange.txt" ).toString ( ) ) )
        {
            // the vector was successfully created
            //System.out.println( "read in files successfully" );
        }
    }

    //--------------------------------------------------------------------------

    // read in the contents of a file, put it into a vector, and add that vector
    // to the vector composed of vectors containing the static files
    protected  boolean addVectorFromFile ( String fileName )
    {
        boolean returnValue;
        try
        {
            // the vector we are going to fill
            Vector vectorFromFile = new Vector ( );

            // create a buffered reader
            File file = new File ( fileName );
            FileInputStream fileInputStream = new FileInputStream ( file );
            InputStreamReader inputStreamReader = new InputStreamReader ( fileInputStream, "UTF-16" );

            //If the bufferedReader is not big enough for a file, I should change the size of it here
            BufferedReader bufferedReader = new BufferedReader ( inputStreamReader, 20000 );

            // read in the text a line at a time
            String part;
            StringBuffer word = new StringBuffer ( );
            while ( ( part = bufferedReader.readLine ( ) ) != null )
            {
                // add spaces at the end of the line
                part = part + "  ";

                // for each line
                for ( int index = 0; index < part.length ( ) - 1; index ++ )
                {
                    // if the character is not a space, append it to a word
                    if ( ! ( Character.isWhitespace ( part.charAt ( index ) ) ) )
                    {
                        word.append ( part.charAt ( index ) );
                    }
                    // otherwise, if the word contains some characters, add it
                    // to the vector
                    else
                    {
                        if ( word.length ( ) != 0 )
                        {
                            vectorFromFile.addElement ( word.toString ( ) );
                            word.setLength ( 0 );
                        }
                    }
                }
            }

            // trim the vector
            vectorFromFile.trimToSize ( );

            // destroy the buffered reader
            bufferedReader.close ( );
   	        fileInputStream.close ( );

            // add the vector to the vector composed of vectors containing the
            // static files
            staticFiles.addElement ( vectorFromFile );
            returnValue = true;
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            returnValue = false;
        }
        return returnValue;
    }

    //----------
}
