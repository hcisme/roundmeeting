package com.chc.roundmeeting.component

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import com.chc.roundmeeting.utils.isChineseChar

@Composable
fun FadeImage(
    modifier: Modifier = Modifier,
    coverUrl: String? = null,
    firstChar: Char,
    contentDescription: String? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val backgroundColor = MaterialTheme.colorScheme.primary.toArgb()
    val textColor = MaterialTheme.colorScheme.onPrimary.toArgb()
    val textInitSize =
        if (isChineseChar(firstChar))
            MaterialTheme.typography.titleSmall.fontSize
        else
            MaterialTheme.typography.bodyLarge.fontSize
    val placeholderDrawable = remember(firstChar, backgroundColor, textColor, textInitSize) {
        createPlaceholderDrawable(
            text = firstChar.toString(),
            bgColor = backgroundColor,
            textColor = textColor,
            backGroundInitSize = with(density) { screenWidth.dp.roundToPx() },
            textInitSize = with(density) { textInitSize.toPx() }
        )
    }

    Image(
        painter = rememberAsyncImagePainter(
            model = ImageRequest
                .Builder(context)
                .data(data = coverUrl)
                .crossfade(true)
                .placeholder(placeholderDrawable)
                .error(placeholderDrawable)
                .build()
        ),
        contentDescription = contentDescription,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer),
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter
    )
}

private fun createPlaceholderDrawable(
    text: String,
    bgColor: Int,
    textColor: Int,
    /**
     * px Unit
     */
    backGroundInitSize: Int,
    /**
     * px Unit
     */
    textInitSize: Float,
): Drawable = object : Drawable() {
    private val paint = Paint().apply {
        isAntiAlias = true
        color = bgColor
    }

    private val textPaint = Paint().apply {
        isAntiAlias = true
        color = textColor
        textSize = textInitSize
        textAlign = Paint.Align.CENTER
        typeface = Typeface.DEFAULT_BOLD
    }

    override fun draw(canvas: Canvas) {
        // 绘制圆形背景
        canvas.drawCircle(
            bounds.exactCenterX(),
            bounds.exactCenterY(),
            backGroundInitSize / 2f,
            paint
        )

        canvas.drawText(
            text,
            bounds.exactCenterX(),
            bounds.exactCenterY() - (textPaint.descent() + textPaint.ascent()) / 2,
            textPaint
        )
    }

    override fun setAlpha(alpha: Int) = Unit
    override fun setColorFilter(colorFilter: android.graphics.ColorFilter?) = Unit
    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT
    override fun getIntrinsicWidth() = backGroundInitSize
    override fun getIntrinsicHeight() = backGroundInitSize
}
