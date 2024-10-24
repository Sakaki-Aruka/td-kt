package online.aruka.td_kt

import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.exists
import kotlin.io.path.isDirectory
import kotlin.io.path.isRegularFile

object ArgumentsParser {

    /*
     * parse examples
     *
     * valid
     * -f FILE_NAME
     * -nf FILE_NAME
     * -nuf FILE_NAME -d DIRECTORY
     * -n -u -f FILE_NAME -d DIRECTORY
     *
     * invalid
     * -nudf FILE_NAME DIRECTORY
     * -nu
     * -nud DIRECTORY
     */

    fun parse(args: Array<String>): Config? {
        var har: File? = null
        var noDuplicate = false
        var uuidStyle = false
        var destination: Path? = null
        val includeExtensions: MutableSet<String> = mutableSetOf()
        val excludeExtensions: MutableSet<String> = mutableSetOf()
        var logSilent = false

        val iter: Iterator<String> = args.iterator()
        while (iter.hasNext()) {
            val arg: String = iter.next()
            if (!arg.startsWith("-")) {
                System.err.println("Need '-' at first.")
            }

            val chars = arg.chars().iterator()
            while (chars.hasNext()) {
                val c = chars.next().toChar()
                when (c) {
                    'n' -> noDuplicate = true
                    'u' -> uuidStyle = true
                    's' -> logSilent = true
                    'f', 'd', 'i', 'e' -> {
                        if (chars.hasNext()) {
                            System.err.println("'${c}' parameter must be last on argument section.")
                            System.err.println("Failed to parse arguments.")
                            return null
                        }

                        val nextParam: String =
                            if (iter.hasNext()) iter.next()
                            else {
                                System.err.println("'${c}' parameter requires next argument.")
                                return null
                            }

                        if (c == 'f') {
                            val path: Path = Paths.get(nextParam).takeIf { it.exists() && it.isRegularFile() }
                                ?: run {
                                    System.err.println("'f' parameter requires regular file.")
                                    return null
                                }
                            har = path.toFile().takeIf { it.exists() && it.isFile && it.canRead() } ?: run {
                                System.err.println("'f' parameter requires a readable file.")
                                return null
                            }
                        } else if (c == 'd') {
                            val path: Path = Paths.get(nextParam).takeIf { it.exists() && it.isDirectory() }
                                    ?: run {
                                        System.err.println("'d' parameter requires directory path.")
                                        return null
                                    }
                            destination = path.toAbsolutePath()
                        } else if (c == 'i') {
                            includeExtensions.addAll(nextParam.split(",").filter { it.isNotBlank() })
                        } else {
                            // c == 'e'
                            excludeExtensions.addAll(nextParam.split(",").filter { it.isNotBlank() })
                        }
                    }
                }
            }
        }

        if (har == null) {
            System.err.println("Valid 'f' parameter not found.")
            return null
        }
        return Config(
            har = har.absoluteFile,
            noDuplicate = noDuplicate,
            uuidStyle = uuidStyle,
            destination = destination,
            logSilent = logSilent,
            includeExtensions = includeExtensions.takeIf { it.isNotEmpty() },
            excludeExtensions = excludeExtensions.takeIf { it.isNotEmpty() },
        )
    }
}