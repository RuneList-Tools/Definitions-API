/*
 * Copyright (c) 2020, Mark <https://github.com/Mark7625>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.runelist.defs.items

import com.beust.klaxon.Klaxon
import com.runelist.Constants
import com.runelist.defs.PramData
import me.tongfei.progressbar.ProgressBar
import mu.KotlinLogging
import org.apache.commons.io.FileUtils
import java.io.File
import java.net.URL
import java.util.Scanner
import kotlin.system.exitProcess

class ItemDefinitionLoader(private val updatecheck: Boolean, private val downloadLocation: File, val logging: Boolean, var ignore: List<Int> = emptyList()) {

    private val logger = KotlinLogging.logger {}

    lateinit var localPrams: PramData
    lateinit var onlinePrams: PramData

    private var download: MutableList<Int> = emptyList<Int>().toMutableList()

    fun init() {

        if (!File(downloadLocation, "prams.json").exists()) {
            log("[Item Loader] First time Setup starting")
            log("[Item Loader] Since this is the first time running RuneList Definitions-API it may take a little longer")
            downloadLocation.mkdirs()
            log("[Item Loader] Downloading Starting Files")
            FileUtils.copyURLToFile(URL(Constants.ONLINE_PRAMS), File(downloadLocation, "prams.json"))
        }

        log("[Item Loader] Loading Files")
        localPrams = Klaxon().parse<PramData>(File(downloadLocation, "prams.json").readText())!!
        log("[Item Loader] Reading online prams Files")
        onlinePrams = Klaxon().parse<PramData>(URL(Constants.ONLINE_PRAMS).readText())!!

        if (updatecheck && needsUpdate()) {
            log("RuneList Definitions api is out of date you are loading ${localPrams.apiVersion} Current Version is ${localPrams.apiVersion}")
            exitProcess(0)
        }

        if (downloadDefinitions()) {
            log("[Item Loader] Checking for Updates")
            if (!firstTime()) {
                onlinePrams.checksums.forEach {
                    when {
                        localPrams.checksums.getValue(it.key) == it.value -> addDownload(it.key.toInt())
                        false -> addDownload(it.key.toInt())
                    }
                }
                System.err.println("[Item Loader] Update Found : ${download.size} Files needs updating (Ingoring ${ignore.size} Files) \nWould you like to would like to update this will replace any custom defs [y/n]?")
                downloadFiles()
            } else {
                log("[Item Loader] This is the first run downloading all defs this may take a while.. (Ingoring ${ignore.size} Files)")
                onlinePrams.checksums.forEach { addDownload(it.key.toInt()) }
                System.err.println("[Item Loader] Are you sure you would like to download [y/n]?")
                downloadFiles()
            }
        } else {
            load()
        }
    }

    private fun downloadFiles() {
        val scanner = Scanner(System.`in`)

        loop@ while (true) {
            when (scanner.nextLine().trim().toLowerCase()) {
                "y" -> {
                    val pb = ProgressBar("Downloading Defs", download.size.toLong()).pause()
                    pb.resume()
                    download.forEach {
                        FileUtils.copyURLToFile(URL(Constants.DOWNLOAD_FILE + "${download[it]}.json"), File("C:/Users/Home/Desktop/Programming/Runelist/TestDefDownload/", "$it.json"))
                        pb.step()
                    }
                    FileUtils.copyURLToFile(URL(Constants.ONLINE_PRAMS), File(downloadLocation, "prams.json"))
                    pb.close()
                }
                "n" -> {
                    break@loop
                }
                else -> {
                    log("[Item Loader] Sorry, I didn't catch that. Please answer y/n")
                }
            }
        }
    }

    private fun load() {
        downloadLocation.list().forEach {
            if (it != "prams.json") {
                val def: ItemDefinition = Klaxon().parse<ItemDefinition>(File(downloadLocation, it).readText())!!
                ItemDefinition.DEFINITIONS[def.id] = def
            }
        }
    }

    private fun addDownload(id: Int) {
        if (!ignore.contains(id)) {
            download.add(id)
        }
    }

    private fun firstTime() = !downloadLocation.exists() && downloadLocation.listFiles().size >= onlinePrams.checksums.size

    private fun downloadDefinitions() = localPrams.rev.equals(onlinePrams.rev) || downloadLocation.listFiles().size == 1

    private fun needsUpdate() = localPrams.apiVersion != onlinePrams.apiVersion

    private fun log(message: String) {
        if (logging) {
            logger.info { message }
        }
    }
}
