package kexjobb;

import java.util.ArrayList;
import java.util.Random;

/**
 * Contains the id of the songs used.
 * @author Shaan.
 */
public class InfoExtractor {
	ArrayList<int[]> songs;
	int demo = 0;
	
	/**
	 * Instantiates an array with the songids of all the songs as well as generates an arraylist of song pairs.
	 * @param ip The user's ip
	 * @throws Exception 
	 */
	InfoExtractor(String ip) throws Exception{
		int[] allsongs = new int[10];
		allsongs[0] = 3253551;				//Celine Dion - A New Day Has Come
		allsongs[1] = 3107323;				//Dido - White Flag
		allsongs[2] = 73499;			//Green Day - Basket Case
		allsongs[3] = 417192;		   //Metallica - The Unforgiven
		allsongs[4] = 434647;		  //Iron Maiden - The Trooper
		allsongs[5] = 3107327;		  //Natalie Imbruglia - Smoke
		allsongs[6] = 518324;				//Oasis - Wonderwall
		allsongs[7] = 9048617;				//Scorpions - Wind Of Change
		allsongs[8] = 8470520;		  //Timo Räisänen - About You Now
		allsongs[9] = 1079677;				//Whitesnake - Here I Go Again
		
		Database db = new Database();
		ArrayList<int[]> voted = db.getVoted(ip);
		songs = new ArrayList<>();
		
		if(voted != null){
			for(int i = 0; i<allsongs.length;i++){
				for(int j = i; j<allsongs.length;j++){
					if(j != i){
						int[] k = new int[2];
						k[0] = allsongs[i];
						k[1] = allsongs[j];
						if(!voted.contains(k)){
							songs.add(k);
						}
					}
				}
			}
		}
		else{
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
	}
	
	/**
	 * Returns the URL to 7digital with the song in the .mp3 format.
	 * @param songId The id used to fetch the song from 7digital.
	 * @return The full URL to a playable mp3.
	 */
	public String getUrl(String songId){
		String consumerkey = "7dtywchqwpuh";
		String url = "http://api.7digital.com/1.2/track/preview?trackid=".concat(songId).concat("&oauth_consumer_key=").concat(consumerkey);
		return url;
	}
	
	/**
	 * Returns a URL with the song in the .ogg format. This is used for Firefox since it can't play .mp3. 
	 * @param songId The id used to fetch the song.
	 * @return The relative URL to a playable .ogg.
	 */
	public String getUrlFirefox(String songId){
		return "oggs/"+songId+".ogg";
	}
	
	/**
	 * Randoms a pair of songs to play next.
	 * @return An array of two strings representing a pair of songs.
	 */
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
	
	/**
	 * Returns one out of four example pairs of songs and the approximate rating for it. 
	 * @return An array of two strings representing a pair of songs.
	 */
	public String[] getDemoSong(){
		String[] ret = new String[3];
		if(demo == 0){
			ret[0] = "7161792"; //30 Seconds To March - Kings And Queens
			ret[1] = "721629"; //The Killers - When We Were Young
			ret[2] = "between 60 and 80";
		}
		else if(demo == 1){
			ret[0] = "4044466"; //36-Crazyfists - Sad Lisa
			ret[1] = "1094952"; //White Lion - When The Children Cry
			ret[2] = "above 80";
		}
		else if(demo == 2){
			ret[0] = "7799012"; //Peter Bradley Adams - Los Angeles
			ret[1] = "2838052"; //In Flames - Cloud Connected
			ret[2] = "below 15";
		}
		else if(demo == 3){
			ret[0] = "177596"; //Tracy Chapman - Talkin' Bout A Revolution
			ret[1] = "2894679"; //Tom Petty - I Won't Back Down
			ret[2] = "between 40 and 60";
		}
		demo++;
		return ret;
	}
}