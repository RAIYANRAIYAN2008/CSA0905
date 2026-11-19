import java.util.*;

class OriginalPlaylist {
    private final List<String> songs = new ArrayList<>();

    void addNext(String song) {
        songs.add(0, song);
    }

    String playNext() {
        return songs.isEmpty() ? null : songs.remove(0);
    }
}

class RedesignedUpNext {
    private final Deque<String> songs = new ArrayDeque<>();

    void addNext(String song) {
        songs.addFirst(song);
    }

    void addLast(String song) {
        songs.addLast(song);
    }

    String playNext() {
        return songs.pollFirst();
    }

    int size() {
        return songs.size();
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("Q7 Design Challenge: Playlist Up Next redesign");
        System.out.println("Original design: ArrayList inserts/removes at front (O(n)).");

        OriginalPlaylist original = new OriginalPlaylist();
        original.addNext("Song A");
        original.addNext("Song B");
        System.out.println("Original next song: " + original.playNext());

        RedesignedUpNext redesigned = new RedesignedUpNext();
        redesigned.addLast("Song A");
        redesigned.addLast("Song B");
        redesigned.addNext("Urgent Song");
        System.out.println("Redesigned next song: " + redesigned.playNext());
        System.out.println("Remaining songs: " + redesigned.size());

        System.out.println("\nRedesign objective: make adding/removing at either end efficient.");
        System.out.println("ArrayDeque addFirst/addLast/pollFirst: O(1) amortized.");
        System.out.println("Trade-off: ArrayDeque does not provide efficient indexed get(k).");
        System.out.println("Use ArrayList for a playlist needing frequent random access; use ArrayDeque for Up Next.");
    }
}
