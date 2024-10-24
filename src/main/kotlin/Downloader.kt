package online.aruka.td_kt

import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*

object Downloader {
    fun download(config: Config, urls: List<URL>) {
        var totalSize = 0.0
        var downloaded = 0
        for (url in urls) {
            val fileBase: String = url.file
            val fileExtension: String = fileBase.split(".").last()

            if (config.excludeExtensions != null && config.excludeExtensions.contains(fileExtension)) continue
            if (config.includeExtensions != null && !config.includeExtensions.contains(fileExtension)) continue

            val fileName = "${if (config.uuidStyle) UUID.randomUUID().toString() else fileBase.split("/").last().split(".").first()}.${fileExtension}"
            val filePath: Path = if (config.destination != null) config.destination.resolve(fileName) else Paths.get(fileName)
            val data: ByteArray = url.openStream().readAllBytes()
            if (data.isEmpty()) continue
            Files.newOutputStream(filePath, StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW).write(data)
            val size = data.size / 1000.0
            if (!config.logSilent) {
                println("File name: $fileName")
                println("File type: $fileExtension")
                println("File size: ${size}kB")
            }
            totalSize += size
            downloaded++
        }

        println("Total downloaded files: $downloaded")
        println("Total downloaded file size: ${totalSize / 1000.0}MB")
    }
}