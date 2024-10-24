package online.aruka.td_kt

import java.io.File
import java.net.URL
import java.nio.file.Path

fun main(args: Array<String>) {
    /*
     * arguments
     *
     * NEED
     * - "-f FILE_NAME": specify a target .har file.
     *
     * OPTIONAL
     * - "-n": no duplicate download links
     * - "-u": converting a file-name to an uuid-style file-name.
     * - "-d": specify a destination directory.
     * - "-i": include image extensions list that is separated with ",".
     * - "-e": exclude image extensions list that is separated with ",".
     * - "-s": log silent. (no downloading log)
     *
     * NOT_IMPLEMENTED
     *
     * You can write some simple arguments with concatenated single string.
     * e.g.) -nu
     *
     * If you write an argument that needs optional string argument with some simple arguments, you can use that ONLY on the end of parameters.
     * e.g.) -nuf x.har
     *
     * You cannot write consecutive some arguments are require optional string arguments.
     * e.g.) NG: -nufd x.har ./download
     * e.g.) OK: -nuf x.har -d ./download/
     *
     */

   val config: Config = ArgumentsParser.parse(args) ?: run {
      System.err.println("Failed to build a config.")
      return
   }

   val urls: List<URL> = HarParser.parse(config.har, config) ?: run {
      System.err.println("Failed to get urls.")
      return
   }
   Downloader.download(config, urls)
}

class Config(
   val har: File,
   val noDuplicate: Boolean = false,
   val uuidStyle: Boolean = false,
   val destination: Path? = null,
   val includeExtensions: Set<String>? = null,
   val excludeExtensions: Set<String>? = null,
   val logSilent: Boolean = false,
)