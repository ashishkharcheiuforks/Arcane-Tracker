package net.mbonnin.arcanetracker

import android.os.Handler
import net.mbonnin.arcanetracker.parser.LogReader
import timber.log.Timber

class DecksParser: LogReader.LineConsumer {
    val lineList = mutableListOf<String>()
    val handler = Handler()

    override fun onLine(rawLine: String) {
        if (rawLine.contains("Finding Game With Deck:")) {
            lineList.clear()
        } else if (lineList.size < 3) {
            val logLine = LogReader.parseLine(rawLine)
            if (logLine != null) {

                Timber.d(logLine.line)

                lineList.add(logLine.line)
            }
            if (lineList.size == 3) {
                val deck = DeckString.parse(lineList.joinToString("\n"))
                if (deck != null) {
                    handler.post{
                        MainViewCompanion.playerCompanion.deck = deck
                    }
                }
            }
        }
    }

    override fun onPreviousDataRead() {
    }

    companion object {
        var instance: DecksParser? = null
        fun get(): DecksParser {
            if (instance == null) {
                instance = DecksParser()
            }

            return instance!!
        }
    }
}
