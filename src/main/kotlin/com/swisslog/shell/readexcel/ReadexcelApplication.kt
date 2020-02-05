package com.swisslog.shell.readexcel

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("com.swisslog.shell")
class ReadexcelApplication

fun main(args: Array<String>) {
    runApplication<ReadexcelApplication>(*args)
}
