package kexjobb;

import java.util.ArrayList;
import java.util.Random;

/**
 * Contains the id of the songs used.
 * @author Shaan
 */
public class InfoExtractor {
    ArrayList<int[]> songs;
    InfoExtractor() throws Exception{
        int[] allsongs = new int[10];
        allsongs[0] = 0;                //Celine Dion - A New Day Has Come
        allsongs[1] = 1;                //Dido - White Flag
        allsongs[2] = 73499;            //Green Day - Basket Case
        allsongs[3] = 417192;           //Metallica - The Unforgiven
        allsongs[4] = 5288657;          //Mötley Crue - Kickstart My Heart
        allsongs[5] = 3107327;          //Natalie Imbruglia - Smoke
        allsongs[6] = 5;                //Oasis - Wonderwall
        allsongs[7] = 6;                //Scorpions - Wind Of Change
        allsongs[8] = 8470520;          //Timo Räisänen - About You Now
        allsongs[9] = 8;                //Whitesnake - Here I Go Again
        
        songs = new ArrayList<>();
        for(int i = 0; i<allsongs.length;i++){
            for(int j = i; j<allsongs.length;j++){
                if(j != i){
                    int[] k = new int[2];
                    k[0] = allsongs[i];
                    k[1] = allsongs[j];
                    songs.add(k);
                }
            }
        }
    }
    
    public String getUrl(String songId){
        String consumerkey = "7dtywchqwpuh";
        String url = "http://api.7digital.com/1.2/track/preview?trackid=".concat(songId).concat("&oauth_consumer_key=").concat(consumerkey);
        return url;
    }
    
    public String[] getSong(){
        String songOne = "";
        String songTwo = "";
        if(songs.size() > 0){
            Random rand = new Random();
            int randomSong = rand.nextInt(songs.size());
            songOne = ""+songs.get(randomSong)[0];
            songTwo = ""+songs.get(randomSong)[1];
            songs.remove(randomSong);
        }
        String[] ret = new String[2];
        ret[0] = songOne;
        ret[1] = songTwo;
        return ret;
    }
}