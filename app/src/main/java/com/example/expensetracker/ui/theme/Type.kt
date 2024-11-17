package com.example.expensetracker.ui.theme

import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 9.sp,
        lineHeight = 8.sp,
        letterSpacing = 0.5.sp
    )

)


val AmountTextExpense = TextStyle(
    fontSize = 22.sp,
    fontWeight = FontWeight.Medium,
    color = textExpenseColor,
    textAlign = TextAlign.End,
    letterSpacing = 0.5.sp,
    fontFamily = FontFamily.Default,
    background = Color.Transparent,
    textDecoration = TextDecoration.None,
    shadow = Shadow(
        color = Color.Gray,
        offset = Offset(1f, 1f),
        blurRadius = 1f
    )
)

val AmountTextIncome = TextStyle(
    fontSize = 22.sp,
    fontWeight = FontWeight.Medium,
    color = textIncomeColor,
    textAlign = TextAlign.End,
    letterSpacing = 0.5.sp,
    fontFamily = FontFamily.Default,
    background = Color.Transparent,
    textDecoration = TextDecoration.None,
//    shadow = Shadow(
//        color = Color.Gray,
//        offset = Offset(1f, 1f),
//        blurRadius = 1f
//    )
)

val NoteInput = TextStyle(
    fontSize = 16.sp,
    fontWeight = FontWeight.Normal,
    color = Color.Gray,
    textAlign = TextAlign.Start,
    letterSpacing = 0.5.sp,
    fontFamily = FontFamily.Default,
    background = Color.Transparent,
    textDecoration = TextDecoration.None,
//    shadow = Shadow(
//        color = Color.Gray,
//        offset = Offset(1f, 1f),
//        blurRadius = 1f
//    )
)




object TextFieldStyles {
    @Composable
    fun defaultColors() = TextFieldDefaults.colors(
        unfocusedContainerColor = Color.White,
        focusedContainerColor =  Color.White,
        unfocusedIndicatorColor =  Color.White,
        focusedIndicatorColor =  Color.White,
        disabledContainerColor = Color.White,
        disabledTextColor = Color.Black,
        disabledPlaceholderColor =  Color.Black,
        disabledIndicatorColor =  Color.White,
    )
}


