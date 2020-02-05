package com.swisslog.shell.readexcel

import org.jline.utils.AttributedString
import org.jline.utils.AttributedStyle
import org.springframework.shell.jline.PromptProvider
import org.springframework.stereotype.Component

@Component
class ExcelPromptProvider(): PromptProvider {
    override fun getPrompt(): AttributedString {
        return AttributedString("excelread:> ", AttributedStyle.DEFAULT.foreground(AttributedStyle.RED))
    }
}