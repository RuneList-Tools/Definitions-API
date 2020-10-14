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
package com.runelist.utils

import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.security.DigestInputStream
import java.security.MessageDigest

object GenerateMD5 {

    private val hashes: MutableMap<Int, String> = emptyMap<Int, String>().toMutableMap()

    fun init() {

        println("Adding Hashes")

        File("C:/Users/Home/Desktop/Programming/Runelist/Items/").list().forEach {

            val name = it.replace(".json", "").toInt()
            val hash = checksum("C:/Users/Home/Desktop/Programming/Runelist/Items/$it")

            hashes[name] = hash
        }

        println("Writing File")

        File("C:/Users/Home/Desktop/Programming/Runelist/Items/", "output.txt").printWriter().use { out ->
            hashes.forEach {
                out.println("   '\"'${it.key}'\"': \"'${it.value}'\"',".replace("'", ""))
            }
        }
    }

    @Throws(IOException::class)
    private fun checksum(filepath: String): String {
        var md = MessageDigest.getInstance("SHA-256")
        DigestInputStream(FileInputStream(filepath), md).use { dis ->
            while (dis.read() != -1)
                md = dis.messageDigest
        }
        // bytes to hex
        val result = StringBuilder()
        for (b in md.digest()) {
            result.append(String.format("%02x", b))
        }
        return result.toString()
    }
}

fun main() {
    GenerateMD5.init()
}
