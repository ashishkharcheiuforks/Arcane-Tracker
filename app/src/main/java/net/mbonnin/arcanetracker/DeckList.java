package net.mbonnin.arcanetracker;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by martin on 10/20/16.
 */

public class DeckList {
    public static final String ARENA_DECK_ID = "ARENA_DECK_ID";
    private static ArrayList<Deck> sList;
    static final String KEY_LIST = "list";
    static final String KEY_ARENA_DECK = "arena_deck";
    private static Deck sArenaDeck;
    private static Deck sOpponentDeck;

    public static Deck createDeck(int classIndex) {
        Deck deck = new Deck();
        deck.classIndex = classIndex;
        deck.id = UUID.randomUUID().toString();
        deck.name =  ArcaneTrackerApplication.Companion.getContext().getString(R.string.yourDeck);

        get().add(deck);

        save();
        return deck;
    }

    public static void addDeck(Deck deck) {
        sList.add(deck);
        save();
    }
    public static void deleteDeck(Deck deck) {
        sList.remove(deck);
        save();
    }
    static ArrayList<Deck> get() {
        if (sList == null) {
            sList = PaperDb.INSTANCE.read(KEY_LIST);
        }
        if (sList == null) {
            sList = new ArrayList<>();
            save();
        }

        return sList;
    }

    public static void save() {
        PaperDb.INSTANCE.write(KEY_LIST, sList);
    }
    public static void saveArena() {
        PaperDb.INSTANCE.write(KEY_ARENA_DECK, getArenaDeck());
    }

    public static Deck getArenaDeck() {
        if (sArenaDeck == null) {
            sArenaDeck = PaperDb.INSTANCE.read(KEY_ARENA_DECK);
            if (sArenaDeck == null) {
                sArenaDeck = new Deck();
                sArenaDeck.id = ARENA_DECK_ID;
                sArenaDeck.name = ArcaneTrackerApplication.Companion.getContext().getString(R.string.arenaDeck);
            }
        }
        sArenaDeck.checkClassIndex();
        return sArenaDeck;
    }

    public static Deck getOpponentDeck() {
        if (sOpponentDeck == null) {
            sOpponentDeck = new Deck();
            sOpponentDeck.name = ArcaneTrackerApplication.Companion.getContext().getString(R.string.opponentsDeck);
        }
        return sOpponentDeck;
    }

    public static void saveDeck(Deck deck) {
        if (ARENA_DECK_ID.equals(deck.id)) {
            saveArena();
        } else {
            save();
        }
    }
}
