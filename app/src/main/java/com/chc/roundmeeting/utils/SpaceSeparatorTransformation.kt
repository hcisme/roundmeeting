package com.chc.roundmeeting.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

/**
 * 每几个字符空一格 默认 4
 */
class SpaceSeparatorTransformation(private val chunkSize: Int = 4) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        // 原始文本处理（去除所有空格）
        val original = text.text.replace(" ", "")

        // 每4个字符插入空格
        val formatted = original.chunked(chunkSize).joinToString(" ")

        // 偏移量映射（处理光标位置）
        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                // 计算前面有多少个完整的分组
                val spaceCount = if (offset == 0) 0 else (offset - 1) / chunkSize
                return offset + spaceCount
            }

            override fun transformedToOriginal(offset: Int): Int {
                // 计算每个分组占用的显示位置（字符数 + 空格）
                val groupSize = chunkSize + 1
                // 计算完整的分组数和余数
                val fullGroups = offset / groupSize
                val remainder = offset % groupSize

                // 计算原始位置
                return fullGroups * chunkSize + minOf(remainder, chunkSize)
            }
        }

        return TransformedText(
            text = AnnotatedString(formatted),
            offsetMapping = offsetMapping
        )
    }
}
