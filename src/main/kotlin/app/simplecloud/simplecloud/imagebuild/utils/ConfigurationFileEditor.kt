/*
 * SimpleCloud is a software for administrating a minecraft server network.
 * Copyright (C) 2023 Frederick Baier & Philipp Eistrach
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package app.simplecloud.simplecloud.imagebuild.utils

import java.io.File
import java.nio.charset.StandardCharsets

/**
 * Created by IntelliJ IDEA.
 * Date: 16.06.2021
 * Time: 09:26
 * @author Frederick Baier
 *
 * A simple file editor to edit configuration files
 * @param linesWithSpaces the lines of the files (all spaces at the beginning of lines will be cut off)
 * @param keyValueSplitter the splitter of name and value. For YML it would be ": "
 *
 */
class ConfigurationFileEditor(
    private val linesWithSpaces: List<String>,
    private val keyValueSplitter: String
) {

    constructor(file: File, keyValueSplitter: String) : this(file.readLines(StandardCharsets.UTF_8), keyValueSplitter)

    private val lines = this.linesWithSpaces.map { removeFirstSpaces(it) }

    private val keyToValues = HashMap(getKeyToValueMapByLines(this.lines))

    private fun getKeyToValueMapByLines(lines: List<String>): Map<String, String> {
        val keyValueSplitArrays = lines.filter { it.contains(keyValueSplitter) }.map { it.split(keyValueSplitter) }
        return keyValueSplitArrays.map { it[0] to (it.getOrNull(1) ?: "") }.toMap()
    }

    fun getValue(key: String): String? {
        return this.keyToValues[key]
    }

    fun setValue(key: String, value: String) {
        if (!this.keyToValues.containsKey(key)) throw IllegalStateException("Key '${key}' does not exist")
        this.keyToValues[key] = value
    }

    fun saveToFile(file: File) {
        val linesToSave = generateNewLines()
        file.writeText(linesToSave.joinToString("\n"))
    }

    private fun generateNewLines(): List<String> {
        val mutableLines = ArrayList(this.linesWithSpaces)
        for ((key, value) in this.keyToValues) {
            val lineIndex = getLineIndexByKey(key)
            val newLine = constructNewLine(key, value, lineIndex)
            mutableLines[lineIndex] = newLine
        }
        return mutableLines
    }

    private fun constructNewLine(key: String, value: String, lineIndex: Int): String {
        val lineWithoutSpaces = key + this.keyValueSplitter + value
        val amountOfStartSpaces = getAmountOfStartSpacesInLine(this.linesWithSpaces[lineIndex])
        val spacesString = getStringWithSpaces(amountOfStartSpaces)
        return spacesString + lineWithoutSpaces
    }

    private fun getStringWithSpaces(amount: Int): String {
        return (0 until amount).joinToString("") { " " }
    }

    private fun removeFirstSpaces(string: String): String {
        val amountOfSpaces = getAmountOfStartSpacesInLine(string)
        return string.drop(amountOfSpaces)
    }

    private fun getAmountOfStartSpacesInLine(line: String): Int {
        var line = line
        var amountOfSPaces = 0
        while (line.startsWith(" ")) {
            line = line.drop(1)
            amountOfSPaces++
        }
        return amountOfSPaces
    }

    private fun getLineIndexByKey(key: String): Int {
        val lineStart = key + this.keyValueSplitter
        val line = lines.firstOrNull { it.startsWith(lineStart) } ?: return -1
        return this.lines.indexOf(line)
    }

    companion object {
        const val YAML_SPLITTER = ": "
        const val PROPERTIES_SPLITTER = "="
        const val TOML_SPLITTER = " = "
    }


}