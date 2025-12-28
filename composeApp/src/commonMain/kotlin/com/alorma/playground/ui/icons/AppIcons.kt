package com.alorma.playground.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

object AppIcons {
  val LightMode: ImageVector by lazy {
    ImageVector.Builder(
      name = "LightMode",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 24f,
      viewportHeight = 24f
    ).apply {
      path(
        fill = SolidColor(Color.Black),
        fillAlpha = 1.0f,
        stroke = null,
        strokeAlpha = 1.0f,
        strokeLineWidth = 1.0f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Miter,
        strokeLineMiter = 1.0f,
        pathFillType = PathFillType.NonZero
      ) {
        moveTo(12f, 7f)
        curveToRelative(-2.76f, 0f, -5f, 2.24f, -5f, 5f)
        reflectiveCurveToRelative(2.24f, 5f, 5f, 5f)
        reflectiveCurveToRelative(5f, -2.24f, 5f, -5f)
        reflectiveCurveToRelative(-2.24f, -5f, -5f, -5f)
        close()
        moveTo(2f, 13f)
        horizontalLineToRelative(2f)
        curveToRelative(0.55f, 0f, 1f, -0.45f, 1f, -1f)
        reflectiveCurveToRelative(-0.45f, -1f, -1f, -1f)
        horizontalLineTo(2f)
        curveToRelative(-0.55f, 0f, -1f, 0.45f, -1f, 1f)
        reflectiveCurveToRelative(0.45f, 1f, 1f, 1f)
        close()
        moveTo(20f, 13f)
        horizontalLineToRelative(2f)
        curveToRelative(0.55f, 0f, 1f, -0.45f, 1f, -1f)
        reflectiveCurveToRelative(-0.45f, -1f, -1f, -1f)
        horizontalLineToRelative(-2f)
        curveToRelative(-0.55f, 0f, -1f, 0.45f, -1f, 1f)
        reflectiveCurveToRelative(0.45f, 1f, 1f, 1f)
        close()
        moveTo(11f, 2f)
        verticalLineToRelative(2f)
        curveToRelative(0f, 0.55f, 0.45f, 1f, 1f, 1f)
        reflectiveCurveToRelative(1f, -0.45f, 1f, -1f)
        verticalLineTo(2f)
        curveToRelative(0f, -0.55f, -0.45f, -1f, -1f, -1f)
        reflectiveCurveToRelative(-1f, 0.45f, -1f, 1f)
        close()
        moveTo(11f, 20f)
        verticalLineToRelative(2f)
        curveToRelative(0f, 0.55f, 0.45f, 1f, 1f, 1f)
        reflectiveCurveToRelative(1f, -0.45f, 1f, -1f)
        verticalLineToRelative(-2f)
        curveToRelative(0f, -0.55f, -0.45f, -1f, -1f, -1f)
        reflectiveCurveToRelative(-1f, 0.45f, -1f, 1f)
        close()
        moveTo(5.99f, 4.58f)
        curveToRelative(-0.39f, -0.39f, -1.03f, -0.39f, -1.41f, 0f)
        curveToRelative(-0.39f, 0.39f, -0.39f, 1.03f, 0f, 1.41f)
        lineToRelative(1.06f, 1.06f)
        curveToRelative(0.39f, 0.39f, 1.03f, 0.39f, 1.41f, 0f)
        reflectiveCurveToRelative(0.39f, -1.03f, 0f, -1.41f)
        lineTo(5.99f, 4.58f)
        close()
        moveTo(18.36f, 16.95f)
        curveToRelative(-0.39f, -0.39f, -1.03f, -0.39f, -1.41f, 0f)
        curveToRelative(-0.39f, 0.39f, -0.39f, 1.03f, 0f, 1.41f)
        lineToRelative(1.06f, 1.06f)
        curveToRelative(0.39f, 0.39f, 1.03f, 0.39f, 1.41f, 0f)
        curveToRelative(0.39f, -0.39f, 0.39f, -1.03f, 0f, -1.41f)
        lineToRelative(-1.06f, -1.06f)
        close()
        moveTo(19.42f, 5.99f)
        curveToRelative(0.39f, -0.39f, 0.39f, -1.03f, 0f, -1.41f)
        curveToRelative(-0.39f, -0.39f, -1.03f, -0.39f, -1.41f, 0f)
        lineToRelative(-1.06f, 1.06f)
        curveToRelative(-0.39f, 0.39f, -0.39f, 1.03f, 0f, 1.41f)
        reflectiveCurveToRelative(1.03f, 0.39f, 1.41f, 0f)
        lineToRelative(1.06f, -1.06f)
        close()
        moveTo(7.05f, 18.36f)
        curveToRelative(0.39f, -0.39f, 0.39f, -1.03f, 0f, -1.41f)
        curveToRelative(-0.39f, -0.39f, -1.03f, -0.39f, -1.41f, 0f)
        lineToRelative(-1.06f, 1.06f)
        curveToRelative(-0.39f, 0.39f, -0.39f, 1.03f, 0f, 1.41f)
        reflectiveCurveToRelative(1.03f, 0.39f, 1.41f, 0f)
        lineToRelative(1.06f, -1.06f)
        close()
      }
    }.build()
  }

  val DarkMode: ImageVector by lazy {
    ImageVector.Builder(
      name = "DarkMode",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 24f,
      viewportHeight = 24f
    ).apply {
      path(
        fill = SolidColor(Color.Black),
        fillAlpha = 1.0f,
        stroke = null,
        strokeAlpha = 1.0f,
        strokeLineWidth = 1.0f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Miter,
        strokeLineMiter = 1.0f,
        pathFillType = PathFillType.NonZero
      ) {
        moveTo(12f, 3f)
        curveToRelative(-4.97f, 0f, -9f, 4.03f, -9f, 9f)
        reflectiveCurveToRelative(4.03f, 9f, 9f, 9f)
        reflectiveCurveToRelative(9f, -4.03f, 9f, -9f)
        curveToRelative(0f, -0.46f, -0.04f, -0.92f, -0.1f, -1.36f)
        curveToRelative(-0.98f, 1.37f, -2.58f, 2.26f, -4.4f, 2.26f)
        curveToRelative(-2.98f, 0f, -5.4f, -2.42f, -5.4f, -5.4f)
        curveToRelative(0f, -1.81f, 0.89f, -3.42f, 2.26f, -4.4f)
        curveToRelative(-0.44f, -0.06f, -0.9f, -0.1f, -1.36f, -0.1f)
        close()
      }
    }.build()
  }
}
