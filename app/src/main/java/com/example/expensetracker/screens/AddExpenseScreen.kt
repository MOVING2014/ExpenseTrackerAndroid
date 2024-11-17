package com.example.expensetracker.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp

import androidx.hilt.navigation.compose.*
import com.example.expensetracker.models.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.sp
import com.example.expensetracker.ui.theme.AmountTextExpense
import com.example.expensetracker.ui.theme.InputButtonBorder
import com.example.expensetracker.ui.theme.NoteInput
import com.example.expensetracker.ui.theme.TextFieldStyles
import java.util.Date
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    var amount by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
//    var selectedCategory by remember { mutableStateOf<Category?>(null) }

    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    val categories by viewModel.categories.collectAsState()
    LaunchedEffect(Unit) {
        if (selectedCategory == null && categories.isNotEmpty()) {
            selectedCategory = categories.first()
        }
    }

    val focusRequester = remember { FocusRequester() }
    var selectedTag by remember { mutableStateOf("") }



    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = { Text("Add Transaction") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },

        bottomBar = {

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()

            ) {

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 1.dp),
                    thickness = 0.5.dp,
                    color = Color.LightGray
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,

                ){

                    TextField(
                        value = note,
                        onValueChange = { note = it },
                        placeholder = { Text("点击输入备注", modifier = Modifier.fillMaxWidth(), style = NoteInput) },

                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier.weight(1f)
                                    .focusRequester(focusRequester) // 添加 focusRequester
                        ,
                        singleLine = true,
                        shape = RectangleShape,
                        textStyle = NoteInput,
                        colors = TextFieldStyles.defaultColors(),
                    )

                    TextField(
                        value = amount,
                        onValueChange = { amount = it },
                        placeholder = { Text("0.0"
                        , modifier = Modifier.fillMaxWidth(), style = AmountTextExpense
                        ) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier.weight(1f)
                            .background(
                                color = Color.White,
                            )

                        ,
                        singleLine = true,
                        shape = RectangleShape,
                        textStyle =  AmountTextExpense,

                        colors = TextFieldStyles.defaultColors(),
                        enabled =  false

                    )

                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 1.dp),
                    thickness = 0.5.dp,
                    color = Color.LightGray
                )

                // 新增标签功能区
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // 这里可以使用 LazyRow 如果标签很多的话
                    TagChip(
                        text = "早餐",
                        selected = selectedTag == "早餐",
                        onClick = { selectedTag = "早餐" }
                    )
                    TagChip(
                        text = "午餐",
                        selected = selectedTag == "午餐",
                        onClick = { selectedTag = "午餐" }
                    )
                    TagChip(
                        text = "晚餐",
                        selected = selectedTag == "晚餐",
                        onClick = { selectedTag = "晚餐" }
                    )
                    // 添加更多标签...
                }




                NumberKeypad(
                    onNumberClick = { num ->
                        if (amount == "0") amount = num
                        else amount += num
                    },
                    onDelete = {
                        amount = if (amount.length > 1) {
                            amount.dropLast(1)
                        } else "0"
                    },
                    onClear = { amount = "0" },
                    onDone = {

                        handleAddExpense(
                            amount = amount,
                            note = note,
                            selectedCategory = selectedCategory,
                            onNavigateBack = onNavigateBack,
                            addExpense = viewModel::addExpense
                        )

                    },
                    focusRequester = focusRequester
                )
            }
        }

    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxWidth()
        ) {


            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { category ->
                    CategoryChip(
                        category = category,
                        selected = category == selectedCategory,
                        onClick = { selectedCategory = category }
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))


        }
    }
}

@Composable
fun CategoryChip(
    category: Category,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .height(32.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        color = if (selected) MaterialTheme.colorScheme.primaryContainer
        else MaterialTheme.colorScheme.surface,
        border = BorderStroke(
            1.dp,
            if (selected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.outline
        )
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = category.name,
                style = MaterialTheme.typography.bodyMedium,
                color = if (selected) MaterialTheme.colorScheme.onPrimaryContainer
                else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}



// 标签组件
@Composable
fun TagChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .height(24.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        color = if (selected) MaterialTheme.colorScheme.primary else Color.LightGray.copy(alpha = 0.2f),
        border = BorderStroke(
            width = 0.5.dp,
            color = if (selected) MaterialTheme.colorScheme.primary else Color.Gray
        )
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = if (selected) Color.White else Color.Black,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


@Composable
fun NumberKeypad(
    onNumberClick: (String) -> Unit,
    onDelete: () -> Unit,
    onClear: () -> Unit,
    onDone: () -> Unit,
    focusRequester: FocusRequester // 添加参数
) {
    val keypadNumbers = listOf(
        listOf("1", "2", "3", "DELETE"),
        listOf("4", "5", "6", "+"),
        listOf("7", "8", "9", "-"),
        listOf(".", "0", "备注", "OK" )
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp) // 设置行间距

    ) {
        keypadNumbers.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                row.forEach { number ->
                    if (number == "DELETE") {
                        KeypadButton(
                            text = "←",
                            onClick = onDelete,
                            modifier = Modifier.weight(1f)
                                .aspectRatio(1f)
                                .padding(4.dp)
                        )
                    }else if(number == "OK"){
                        KeypadButton(
                            text = "确认",
                            onClick = onDone,
                            modifier = Modifier.weight(1f)
                                .aspectRatio(1f)
                                .padding(2.dp)
                        )

                    }else if(number == "备注"){
                        KeypadButton(
                            text = "备注",
                            onClick =   {
                                // 请求焦点并显示键盘
                                focusRequester.requestFocus()

                            },
                            modifier = Modifier.weight(1f)
                                .aspectRatio(1f)
                                .padding(2.dp)
                        )

                    }


                    else {
                        KeypadButton(
                            text = number,
                            onClick = { onNumberClick(number) },
                            modifier = Modifier.weight(1f)
                                .aspectRatio(1f)
                                .padding(2.dp)
                        )
                    }
                }
            }
        }
    }
}



@Composable
fun KeypadButton(
    text: String,
    modifier: Modifier = Modifier,  // 添加 modifier 参数
    backgroundColor: Color = Color.White,
    textColor: Color = Color.Black,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(82.dp, 50.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(backgroundColor)
            .border( // 添加边框
                width = 0.5.dp,
                color = InputButtonBorder,
                shape = RoundedCornerShape(4.dp)
            )
            .clickable(onClick = onClick),

        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    }
}



private fun handleAddExpense(
    amount: String,
    note: String,
    selectedCategory: Category?,
    onNavigateBack: () -> Unit,
    addExpense: (Expense) -> Unit
) {
    amount.toDoubleOrNull()?.let { amountValue ->
        selectedCategory?.let { category ->
            addExpense(
                Expense(
                    id = UUID.randomUUID().toString(),
                    amount = amountValue,
                    note = note.takeIf { it.isNotBlank() },
                    category = category,
                    date = Date()
                )
            )
            onNavigateBack()
        }
    }
}




