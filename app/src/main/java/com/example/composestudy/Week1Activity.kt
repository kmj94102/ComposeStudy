package com.example.composestudy

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import com.example.composestudy.ui.theme.ComposeStudyTheme
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class Week1Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeStudyTheme {
                ConstraintLayoutContent()
            }
        }
    }
}

data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg: Message) {
    Row(
        modifier = Modifier
            .padding(all = 8.dp)
            .clip(RoundedCornerShape(4.dp))
    ) {
       Image(
           painter = painterResource(id = if (msg.author == "옴팡이") R.drawable.image else R.drawable.image2),
           contentDescription = "image",
           modifier = Modifier
               .size(40.dp)
               .clip(CircleShape)
               .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
       )

        // 이미지와 열 사이에 수평 공간 추가
        Spacer(
            modifier = Modifier.width(8.dp)
        )

        // 메시지가 확장되었는지 여부를 추적합니다.
        var isExpanded by remember { mutableStateOf(false) }
        // surfaceColor 는 한 색상에서 다른 색상으로 점진적으로 업데이트됩니다.
        val surfaceColor: Color by animateColorAsState(
            targetValue = if (isExpanded) MaterialTheme.colors.secondary else MaterialTheme.colors.surface,
            animationSpec = tween(durationMillis = 1000)
        )

        // 이 열을 클릭하면 isExpanded 변수를 토글합니다.
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = msg.author,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 4.dp,
                // surfaceColor 색상은 기본에서 표면으로 점진적으로 변경됩니다.
                color = surfaceColor,
                // animateContentSize 는 표면 크기를 점진적으로 변경합니다.
                modifier = Modifier
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                    .padding(1.dp)
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    // 메시지가 확장되면 모든 내용을 표시합니다. 그렇지 않으면 첫 번째 줄만 표시합니다.
                    maxLines = if(isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.body2
                )
            }

        }
    }
}

@Composable
fun Conversation(message: List<Message>) {
    LazyColumn {
        items(message) { message ->
            MessageCard(msg = message)
        }
    }
}

@Composable
fun ScaffoldTest(){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "LayoutsCodelab")
                },
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(Icons.Filled.Favorite, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        BodyContent(Modifier.padding(innerPadding))
    }

}

@Composable
fun BodyContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(8.dp)) {
        Text(text = "Hi there!")
        Text(text = "Thanks for going through the Layouts codelab")
    }
}

@Composable
fun SimpleList() {
    // 목록을 프로그래밍 방식으로 스크롤하는 데에도 사용할 수 있는 이 상태로 스크롤 위치를 저장합니다.
    val scrollState = rememberScrollState()

    Column(Modifier.verticalScroll(scrollState)) {
        repeat(100) {
            Text("Item #$it")
        }
    }
}

@Composable
fun LazyList() {
    // 목록을 프로그래밍 방식으로 스크롤하는 데에도 사용할 수 있는 이 상태로 스크롤 위치를 저장합니다.
    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState) {
        items(100) {
            Text("Item #$it")
        }
    }
}

@Composable
fun ImageListItem(index: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = rememberImagePainter(
                data = "https://developer.android.com/images/brand/Android_Robot.png"
            ),
            contentDescription = "Android Logo",
            modifier = Modifier.size(50.dp)
        )
        Spacer(Modifier.width(10.dp))
        Text("Item #$index", style = MaterialTheme.typography.subtitle1)
    }
}

@Composable
fun ScrollingList() {
    val listSize = 100
    // We save the scrolling position with this state
    val scrollState = rememberLazyListState()
    // We save the coroutine scope where our animated scroll will be executed
    val coroutineScope = rememberCoroutineScope()

    Column {
        Row {
            Button(onClick = {
                coroutineScope.launch {
                    // 0 is the first item index
                    scrollState.animateScrollToItem(0)
                }
            }) {
                Text("Scroll to the top")
            }

            Button(onClick = {
                coroutineScope.launch {
                    // listSize - 1 is the last index of the list
                    scrollState.animateScrollToItem(listSize - 1)
                }
            }) {
                Text("Scroll to the end")
            }
        }

        LazyColumn(state = scrollState) {
            items(listSize) {
                ImageListItem(it)
            }
        }
    }
}

@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout {
        // Creates references for the three composables
        // in the ConstraintLayout's body
        val (button1, button2, text) = createRefs()

        Button(
            onClick = { /* Do something */ },
            modifier = Modifier.constrainAs(button1) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text("Button 1")
        }

        Text("Text", Modifier.constrainAs(text) {
            top.linkTo(button1.bottom, margin = 16.dp)
            centerAround(button1.end)
        })

        val barrier = createEndBarrier(button1, text)
        Button(
            onClick = { /* Do something */ },
            modifier = Modifier.constrainAs(button2) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(barrier)
            }
        ) {
            Text("Button 2")
        }
    }
}

@Preview(
    name = "Light Mode",
    showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun DefaultPreview2() {
    ComposeStudyTheme {
        ConstraintLayoutContent()
    }
}

open class SampleData {
    val conversationSample : List<Message> =
        listOf(
            Message("옴팡이", "안녕? 난 옴팡이다!안녕? 난 옴팡이다!안녕? 난 옴팡이다!안녕? 난 옴팡이다!안녕? 난 옴팡이다!안녕? 난 옴팡이다!안녕? 난 옴팡이다!안녕? 난 옴팡이다!안녕? 난 옴팡이다!안녕? 난 옴팡이다!안녕? 난 옴팡이다!안녕? 난 옴팡이다!안녕? 난 옴팡이다!안녕? 난 옴팡이다!안녕? 난 옴팡이다!"),
            Message("또가스", "난 또가스!난 또가스!난 또가스!난 또가스!난 또가스!난 또가스!난 또가스!난 또가스!난 또가스!난 또가스!난 또가스!난 또가스!난 또가스!난 또가스!난 또가스!난 또가스!난 또가스!난 또가스!난 또가스!"),
            Message("옴팡이", "ㅎㅎㅎㅎㅎ~~~ㅎㅎㅎㅎㅎ~~~ㅎㅎㅎㅎㅎ~~~ㅎㅎㅎㅎㅎ~~~ㅎㅎㅎㅎㅎ~~~ㅎㅎㅎㅎㅎ~~~ㅎㅎㅎㅎㅎ~~~ㅎㅎㅎㅎㅎ~~~ㅎㅎㅎㅎㅎ~~~ㅎㅎㅎㅎㅎ~~~ㅎㅎㅎㅎㅎ~~~ㅎㅎㅎㅎㅎ~~~"),
            Message("또가스", "ㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇ"),
            Message("옴팡이", "안녕? 난 OmPangI!안녕? 난 OmPangI!안녕? 난 OmPangI!안녕? 난 OmPangI!안녕? 난 OmPangI!안녕? 난 OmPangI!안녕? 난 OmPangI!안녕? 난 OmPangI!안녕? 난 OmPangI!안녕? 난 OmPangI!안녕? 난 OmPangI!안녕? 난 OmPangI!"),
            Message("또가스", "난 ドガース Koffing!난 ドガース Koffing!난 ドガース Koffing!난 ドガース Koffing!난 ドガース Koffing!난 ドガース Koffing!난 ドガース Koffing!난 ドガース Koffing!난 ドガース Koffing!난 ドガース Koffing!난 ドガース Koffing!난 ドガース Koffing!난 ドガース Koffing!난 ドガース Koffing!"),
            Message("옴팡이", "옴팡 옴팡이옴팡 옴팡이옴팡 옴팡이옴팡 옴팡이옴팡 옴팡이옴팡 옴팡이옴팡 옴팡이옴팡 옴팡이옴팡 옴팡이옴팡 옴팡이옴팡 옴팡이옴팡 옴팡이옴팡 옴팡이"),
            Message("또가스", "또가 또가스또가 또가스또가 또가스또가 또가스또가 또가스또가 또가스또가 또가스또가 또가스또가 또가스또가 또가스또가 또가스또가 또가스또가 또가스또가 또가스"),
            Message("옴팡이", "잘가~~~잘가~~~잘가~~~잘가~~~잘가~~~잘가~~~잘가~~~잘가~~~잘가~~~잘가~~~잘가~~~잘가~~~잘가~~~잘가~~~잘가~~~잘가~~~잘가~~~잘가~~~잘가~~~잘가~~~잘가~~~"),
            Message("또가스", "ㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇㅂㅇ"),
        )
}