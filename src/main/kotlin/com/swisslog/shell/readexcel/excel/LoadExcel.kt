package com.swisslog.shell.readexcel.excel

import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import java.io.File
import org.apache.poi.ss.usermodel.DataFormatter
import org.apache.poi.ss.usermodel.Row
import org.springframework.shell.standard.ShellOption

@ShellComponent
class LoadExcel {

    var dataFormatter = DataFormatter()
    @ShellMethod("Excel file")
    @Throws(Exception::class)
    fun helloWorld(@ShellOption(defaultValue = "") filename: String): String {
        return filename
    }
    @ShellMethod("Load locations from Excel file")
    @Throws(Exception::class)
    fun loadExcel(filename: String) {
        WorkbookFactory.create(File(filename)).use { workbook ->
            workbook.forEach workbooks@{ sheet ->
                // We don't load the Information sheet
                if (sheet.sheetName == "Information")
                    return@workbooks

                println("Sheet ${sheet.sheetName}")
                val data = loadSheet(sheet)

                when(sheet.sheetName) {
                    "Facilities" -> loadStructural(data, "facility", "facility", "region")
                }
            }
        }
    }

    @Throws(Exception::class)
    fun loadStructural(rows: Array<Map<String,String>>, nameField: String, type: String, parentType: String, defParent: String? = null, prefix: String = "") {
        rows.forEach entry@{ row ->

        }
    }

    @Throws(Exception::class)
    fun loadSheet(sheet: Sheet): Array<Map<String,String>> {

        val list = arrayListOf<Map<String,String>>()

        var header = true
        var columns: Array<String>? = null

        sheet.rowIterator().forEach rows@{ row ->
            if (header) {
                val cell = row.getCell(0)
                val cellVal = dataFormatter.formatCellValue(cell)
                if (cellVal == null || cellVal == "")
                    return@rows

                columns = getColumns(row)
                header = false

                println("Columns: ${columns?.joinToString()}")
            }
            else {
                val data = getData(row, columns!!)
                list.add(data)
//                logger.info("Data: {}", data.joinToString())
            }
        }

        return list.toTypedArray()
    }

    fun getColumns(row: Row): Array<String> {

        val list = arrayListOf<String>()
        var unknown = 1
        row.forEach { cell ->
            var cellVal = dataFormatter.formatCellValue(cell) ?: ("unknown" + unknown++)

            val i = cellVal.indexOfAny(charArrayOf('(', '?', '/'))
            if (i > 0)
                cellVal = cellVal.substring(0, i).trim()

            list.add(cellVal.toLowerCase())
        }
        return list.toTypedArray()
    }

    fun getData(row: Row, columns: Array<String>): Map<String,String> {
        val map = mutableMapOf<String,String>()
        columns.forEachIndexed columns@{ i, column ->
            val cell = row.getCell(i) ?: return@columns
            val cellVal = dataFormatter.formatCellValue(cell)
            if (cellVal?.trim() == "")
                return@columns
            map[columns[i]] = cellVal
        }
        return map
    }
}