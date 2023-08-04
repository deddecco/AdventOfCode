import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static java.lang.Integer.parseInt;
import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;

/**
 * helper class for Playlist-- a Playlist is an implementation of
 * LinkedList made up of Track objects which can be sorted based on the attributes of the Track objects the Playlist contains
 */
 class Track {

    public double trackTime;
    public int trackMinute;
    public int trackSec;
    /**
     * pointers to the previous and next tracks, allowing the user
     * to call for movement to adjacent tracks,
     * like the >| and |< buttons on actual music streaming applications
     * without worrying about implementing the GUI those real buttons require
     */

    // turns a Playlist into a doubly-linked list
    // next of the last track cycles back to the beginning of the list
    // prev of the first track cycles back to the end of the list
    public Track prevTrack;
    public Track nextTrack;
    int trackYear;

    //todo
    int numPlays;

    String trackName;
    String trackStudio;
    String trackGenre;
    String trackArtist;
    String trackAlbum;

    public int getTrackMinute() {
        return trackMinute;
    }

    public void setTrackMinute(int trackMinute) {
        this.trackMinute = trackMinute;
    }

    public int getTrackSec() {
        return trackSec;
    }

    public void setTrackSec(int trackSec) {
        this.trackSec = trackSec;
    }

    public Track getPrevTrack() {
        return prevTrack;
    }

    public void setPrevTrack(Track prevTrack) {
        this.prevTrack = prevTrack;
    }

    public Track getNextTrack() {
        return nextTrack;
    }

    public void setNextTrack(Track nextTrack) {
        this.nextTrack = nextTrack;
    }

    public int getNumPlays() {
        return this.numPlays;
    }

    public String getTrackAlbum() {
        return trackAlbum;
    }

    public void setTrackAlbum(String trackAlbum) {
        this.trackAlbum = trackAlbum;
    }

    public int getTrackYear() {
        return trackYear;
    }

    public void setTrackYear(int trackYear) {
        this.trackYear = trackYear;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getTrackStudio() {
        return trackStudio;
    }

    public void setTrackStudio(String trackStudio) {
        this.trackStudio = trackStudio;
    }

    public String getTrackGenre() {
        return trackGenre;
    }

    public void setTrackGenre(String trackGenre) {
        this.trackGenre = trackGenre;
    }

    public String getTrackArtist() {
        return trackArtist;
    }

    public void setTrackArtist(String trackArtist) {
        this.trackArtist = trackArtist;
    }

    public void setTrackTime() {
        trackTime = (this.trackMinute * 60 + this.trackSec);
    }

    public double getTrackTime() {
        return trackTime;
    }

    public static void printTrackInfo(Track track) {
        System.out.println(track.trackName + "\t" + "(" + track.trackMinute + ":" + track.trackSec + ") {"
                + track.trackYear + "} " + " by " + track.trackArtist + " in " + track.trackAlbum +
                " which has been played " + track.numPlays + " times");
    }

    public void setNumPlays(int numPlays) {
        this.numPlays = numPlays;
    }

    public int getNumPlays(Track track) {
        return track.numPlays;
    }
}

public class Playlist {


    // fixme: track is "exposed beyond its visibility scope"-- problem? how to fix?
    public LinkedList<Track> myPlaylist = new LinkedList<>();
    public String playListName;
    public String playlistOwner;
    public double totalTime;

    /**
     * compute and set the total time of all tracks in the playlist by finding the time
     * for each track, and computing a running sum over yje whole playlist.
     */
    public void setTotalTime() {
        totalTime = 0;
        for (Track track : myPlaylist) {
            totalTime += track.trackTime;
        }
    }

    /**
     * add a track to the playlist only if it isn't already there.
     * If the user attempts to add a track to the playlist which
     * is already in the playlist, disallow that, and throw an exception
     */
    public void addTrack(Track track) throws IllegalArgumentException {
        if (!playlistHasTrack(track)) {
            myPlaylist.add(track);
        } else {
            throw new IllegalArgumentException("Cannot add song to playlist already in playlist");
        }
    }

    /**
     * remove a track from the playlist only if it is there to begin with.
     * Throw an exception if the user attempts to remove a track that does not exist in the playlist
     */
    public void removeTrack(Track track) throws IllegalArgumentException {
        if (playlistHasTrack(track)) {
            myPlaylist.remove(track);
        } else {
            throw new IllegalArgumentException("Cannot remove a track from playlist if track not in playlist");
        }
    }

    /**
     * check if the playlist contains the track passed in
     */
    private boolean playlistHasTrack(Track track) {
        return myPlaylist.contains(track);
    }

    /**
     * return the total time of the playlist in a formatted string
     */
    public void reportTotalTime() throws RuntimeException {

        // the number of whole minutes corresponding to some number of seconds is that
        // number after it undergoes *integer* division by 60-- truncating off any remainders
        int mins = (int) totalTime / 60;
        // the number of excess seconds (on top of any whole minutes) corresponding to some
        // time in seconds is the remainder when the number of seconds is divided by 60
        int secs = (int) totalTime % 60;

/*        System.out.println("mins = " + mins);
        System.out.println("secs = " + secs);*/

        if (totalTime > 0) {
            System.out.println("The total time of this playlist is " + mins + " minutes and " + secs + " seconds");
        } else {
            throw new RuntimeException("Total time cannot be less than 0:00");
        }
    }

    /**
     * count and return the number of tracks in the playlist, even for
     * an empty playlist, which is defined to have size 0.
     */
    public int getNumTracks() {
        return this.myPlaylist.size();
    }

    /**
     * return the name of the owner of the playlist, if that field is defined.
     * If the field is undefined and a return is requested anyway,
     * prompt the user to set the value, and one cit is set, return it.
     */
    public String getPlaylistOwner() {
        if (playlistOwner != null) {
            return playlistOwner;
        } else {
            System.out.println("Please define a new playlist owner");
            Scanner input = new Scanner(System.in);
            String name = input.next();
            setPlaylistOwner(name);
            return name;
        }
    }

    public void setPlaylistOwner(String playlistOwner) {
        this.playlistOwner = playlistOwner;
    }

    /**
     * return the name of the current playlist if already set; otherwise, set it first, and then return it
     */
    public String getPlayListName() {
        if (playListName != null) {
            return playListName;
        } else {
            System.out.println("Please define a new playlist name");
            Scanner input = new Scanner(System.in);
            String name = input.next();
            setPlayListName(name);
            return name;
        }
    }

    public void setPlayListName(String name) {
        this.playListName = name;
    }

    // the playlist is empty if it has no tracks
    public boolean isPlaylistEmpty() {
        return this.myPlaylist.isEmpty();
    }

    // if the playlist isn't empty-- if it has any positive, nonzero number of tracks-- this returns true
    public boolean doesPlaylistHaveTracks() {
        return !myPlaylist.isEmpty();
    }

    /**
     * sort the playlist by a comparator which has been configured to check the genres of tracks
     * in A-Z order
     */
    public void sortByGenre() {
        Comparator<Track> trackComparatorByGenre = comparing(o -> o.trackGenre);
        myPlaylist.sort(trackComparatorByGenre);
    }

    /**
     * sort the playlist by a comparator which has been configured to return the playlist
     * in ascending order by length
     */
    public void sortByLength() {
        Comparator<Track> lengthComparator = comparingInt(o -> o.trackMinute * 60 + o.trackSec);
        myPlaylist.sort(lengthComparator);
    }

    /**
     * sort the playlist by a comparator which has been configured to return the playlist
     * from oldest to mo recent
     */
    public void sortByDate() {
        Comparator<Track> trackComparatorByLength = comparingInt(o -> o.trackYear);
        myPlaylist.sort(trackComparatorByLength);
    }

    /**
     * sort the playlist by a comparator which has been configured to check the provided artists' names
     * in A-Z order
     */
    public void sortByArtist() {
        Comparator<Track> trackComparatorByArtist = comparing(o -> o.trackArtist);
        myPlaylist.sort(trackComparatorByArtist);
    }

    /**
     * sort the playlist by a comparator which has been configured to check the names of the recording studios
     * in A-Z order
     */
    public void sortByLabel() {
        Comparator<Track> trackComparatorByLabel = comparing(o -> o.trackStudio);
        myPlaylist.sort(trackComparatorByLabel);
    }

    public void sortByPlays() {
        Comparator<Track> trackComparatorByPlays = comparingInt(o -> o.numPlays);
        myPlaylist.sort(trackComparatorByPlays);
    }

    // constructor
    public Playlist() {

    }

    public static void main(String[] args) throws IOException {
        Playlist playlist = new Playlist();
        playlist.setPlaylistOwner("Andre");

        List<String[]> tracks = processPlaylistFile(args);
        populatePlaylistFromFile(playlist, tracks);

        if (!playlist.isPlaylistEmpty()) {
            for (Track t : playlist.myPlaylist) {
                Track.printTrackInfo(t);
            }
            // link every element of the playlist to its immediate predecessor and successor
            // the link backward from the first element should point to null
            // the link forward from the last element should point to null
            setBidirectionalLinks(playlist);

            //printListBothLinks(playlist);
        } else {
            System.out.println("Playlist" + playlist.playListName + " is empty");
        }
        //playlist.reportTotalTime();
        // System.out.println("This playlist has " + playlist.getNumTracks() + " tracks.");
    }


    // debugging method for use while setting links forward and backward to make a playlist a doubly-linked list,
    // showing the current track and the tracks to which the .next and .prev pointers point to
    private static void printListBothLinks(Playlist playlist) {
        for (Track t : playlist.myPlaylist) {
            System.out.println("~~~~~~~~~~~~~");
            System.out.print("T: ");
            Track.printTrackInfo(t);

            System.out.println();

            System.out.print("Track.printTrackInfo(t.prevTrack) = ");
            Track.printTrackInfo(t.prevTrack);

            System.out.println();

            System.out.print("Track.printTrackInfo(t.nextTrack) = ");
            Track.printTrackInfo(t.nextTrack);

            System.out.println();

            System.out.println("~~~~~~~~~~~~~");
        }
    }


    // for every track, set prev and next links to make a playlist doubly-linked
    private static void setBidirectionalLinks(Playlist playlist) {

        // the counter of which track we're processing right now
        int i = 0;

        // every track is considered
        for (Track t : playlist.myPlaylist) {
            // every track gets a pointer from itself to the track one position ahead of itself
            // if the track under consideration is the last one,
            // the "next" pointer should point to the first track
            // in order to turn the playlist into a cycle that can loop indefinitely
            if (i == playlist.myPlaylist.size() - 1) {
                t.nextTrack = playlist.myPlaylist.get(0);
            } else {
                // otherwise, the next pointer should point to the track one
                // index ahead of where the pointer came from
                t.nextTrack = playlist.myPlaylist.get(i + 1);
            }
            // if the track under consideration is the first track, the "prev" pointer should point to the last one,
            // in order to turn the playlist into a cycle that can loop indefinitely
            if (i == 0) {
                t.prevTrack = playlist.myPlaylist.get(playlist.myPlaylist.size() - 1);
            } else {
                t.prevTrack = playlist.myPlaylist.get(i - 1);
            }
            i++;
        }

    }

    /**
     * Read in from the file.
     * Create a new Track object from each record in the file.
     * Place the newest Track object t1 at the end of the playlist, and connect it
     * to the former end of the playlist, as that track's successor.
     * Leave the newest Track's successor pointer null, until a track t2 that is newer still is added after it,
     * at which point t1's successor pointer will pointy to t2
     */
    private static void populatePlaylistFromFile(Playlist playlist, List<String[]> tracks) {
        // create the Playlist object from the list of text records read in from
        // the file which was the command-line-argument from main()
        for (String[] track : tracks) {
            Track newTrack = new Track();
            newTrack.setTrackName(track[0]);
            int minutes = playlist.parseTrackMinute(track);
            newTrack.setTrackMinute(minutes);

            int seconds = playlist.parseTrackSecond(track);
            newTrack.setTrackSec(seconds);
            newTrack.setTrackTime();

            int indexOfStartOfYear = track[2].indexOf('{') + 1;
            String yearSubstring = track[2].substring(indexOfStartOfYear, indexOfStartOfYear + 4);

            newTrack.setTrackYear(parseInt(yearSubstring));
            newTrack.setTrackArtist(track[3]);
            newTrack.setTrackAlbum(track[4]);
            newTrack.setTrackStudio(track[5]);
            newTrack.setTrackGenre(track[6]);
            newTrack.setNumPlays(parseInt(track[7]));
            playlist.addTrack(newTrack);
        }
        // once the whole playlist has been loaded, calculate the total time of the playlist
        playlist.setTotalTime();
    }

    /**
     * Look through the string[] for each track and find the time.
     * Within that time, determine the number of seconds,
     * The number of seconds is the integer parsed from the string
     * that begins at the character after : and ends at the character before )
     * then convert that string to an integer and return it.
     */
    private int parseTrackSecond(String[] track) {
        int indexOfStartOfSecond = track[1].indexOf(':') + 1;
        int indexOfEndOfSecond = track[1].indexOf(')');
        String secondString = track[1].substring(indexOfStartOfSecond, indexOfEndOfSecond);
        return parseInt(secondString);
    }

    /**
     * Look through the string[] for each track and find the time.
     * Within that time, determine the number of minutes.
     * The number of minutes is the integer parsed from the string
     * that begins at the character after ( and ends at the character before :
     * then convert that string to an integer and return it.
     */
    private int parseTrackMinute(String[] track) {
        int indexOfStartOfMinute = track[1].indexOf('(') + 1;
        int indexOfEndOfMinute = track[1].indexOf(':');
        String minuteString = track[1].substring(indexOfStartOfMinute, indexOfEndOfMinute);
        return parseInt(minuteString);
    }

    /**
     * Given the file path provided from args, read the file to find the header.
     * Then read the rest of the records in the file and split each record into an array,
     * such that each element of the array is the entry of the particular data point
     * recorded at that same position in the header, particular to the track
     * about which the record is being created
     */
    private static List<String[]> processPlaylistFile(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(args[0]));
        /*
         has column heading info like "TITLE," "TIME," "YEAR," "ARTIST," etc.
         in a predefined order used by all records in the file
        */
        printHeader(reader);
        String line;
        /*
         anything after the first line (at a rate of one per line, until the end of the file)
         is a record of a track in the playlist
         corresponding to the file, laid out in the format dictated by
         the header in the first line of the file
        */
        List<String[]> trackInfo = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            String[] track = line.split("\t");
            trackInfo.add(track);
        }
        return trackInfo;
    }

    /**
     * Read and print out the first line of the file passed in.
     */
    private static void printHeader(BufferedReader reader) throws IOException {
        String header = reader.readLine();
        System.out.println(header);
    }

    /**
     * shuffle the contents of the playlist.
     */
    public void shuffle() {
        Collections.shuffle(myPlaylist);
    }


    //todo
    // maybe this kind of thing needs a run() implementation from Thread,
    // and so listen() can call run() to put run() in the context of a Playlist?
    // pair-program this??? do I know enough Java to find the solution myself??
    public void listen(Playlist playlist) throws InterruptedException {

        // todo: keep the process open for however long the user wants
        while (true) {

            // todo: to "play" a track means to display "current song: {title of the current song}" to the console
            // keep it there for the length of the current song, from the field trackTime
            // wait for as long as "trackTime for the given track"
            // if still listening to a track after 30 seconds for tracks >= 90 secs, or >50% of the length
            // for shorter tracks, give credit for 1 play
            // update file and display on screen the CUMULATE TOTAL plays-- not just the plays this session
            // add new comparator to be able to sort by most-played
            // continuously listen out for "prev" or "next" inputs in the console
            // navigate to either as soon as command comes in
            // by default, go to next song after duration of current song has elapsed in current thread

            // a console input of > indicates going forward one track even before the current track has finished
            // a console input of < indicates going backward one track even before the current track has finished
            // a console input of p indicates the user wants to pause even before the current track has finished
            // a console input of x indicates the user wants to exit even before the current track has finished
            // a console input of r indicates the user wants to repeat something
            // if what follows r (separated by a space) is any substring of the name of the playlist, the whole playlist
            // should run on a loop, in order, until the user exits
            // if what follows r (separated by a space) is any substring of the name of a track, or a substring thereof,
            // that track should play in a loop until the user exits
            // a console input of j followed by another string indicates the user wants to jump to the track in the playlist
            // whose title contains that substring anywhere, if the track with that substring exists; otherwise, ignore the jump
            // the file with plays should update after each track is credited with a play, and when a session ends
        }
    }

    /**
     * todo
     * search the whole playlist for a track with a title that contains the substring passed in as an argument
     * if that track exists in the playlist, point to it as the current track and return true
     * if multiple tracks exist in the playlist that fit the search criterion, return the first track found under the current ordering of the playlist
     * if that track does not exist in the playlist, do not move the current track pointer and return false
     */
    public boolean jumpToTrack(String trackTitleSubString) {
        return false;
    }

    // delete all tracks from a playlist
    public void clearThisPlaylist(Playlist playlist) {
        playlist.myPlaylist.clear();
    }
}