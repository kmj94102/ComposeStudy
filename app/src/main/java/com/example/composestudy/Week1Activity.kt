package com.example.composestudy

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composestudy.ui.theme.ComposeStudyTheme

class Week1Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeStudyTheme {
                Conversation(SampleData().conversationSample)
            }
        }
    }
}

data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg: Message) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
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
                modifier = Modifier.animateContentSize().padding(1.dp)
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
        Conversation(SampleData().conversationSample)
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