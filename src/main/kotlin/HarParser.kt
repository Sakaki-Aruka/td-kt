package online.aruka.td_kt

import java.io.File
import java.net.URI
import java.net.URL
import java.util.regex.Matcher
import java.util.regex.Pattern

object HarParser {
    private const val URL_REGEX = ".*(https://pbs.twimg.com/media/.{15})\\?format=(.+)&name=.*"

    fun parse(file: File, config: Config): List<URL>? {
        if (!file.isFile || !file.canRead() || !file.exists()) {
            System.err.println("'${file.name}' is not a readable file.")
            return null
        }

        val uris: MutableCollection<URI> =
            if (config.noDuplicate) mutableSetOf()
            else mutableListOf()

        file.forEachLine {
            getFormattedImageUrl(it)?.let { urlString ->
                uris.add(URI(urlString))
            }
        }

        return uris.map { it.toURL() }
    }

    private fun getFormattedImageUrl(line: String): String? {
        if (line.isBlank()) return null
        val matcher: Matcher = Pattern.compile(URL_REGEX).matcher(line)
        if (!matcher.matches()) return null
        val base: String = matcher.group(1)
        val extension: String = matcher.group(2)
        return "${base}.${extension}"
    }
}