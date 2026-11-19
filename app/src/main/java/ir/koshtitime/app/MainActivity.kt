package ir.koshtitime.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

private val Forest = Color(0xFF07833B)
private val Orange = Color(0xFFFF6B00)
private val Ink = Color(0xFF17251C)
private val Pale = Color(0xFFF2F7F1)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { KoshtiTimeApp() }
    }
}

@Composable
private fun KoshtiTimeApp() {
    var selected by remember { mutableIntStateOf(0) }
    MaterialTheme {
        Scaffold(
            containerColor = Pale,
            bottomBar = {
                NavigationBar(containerColor = Color.White) {
                    val items = listOf(
                        Triple("خانه", Icons.Default.Home, 0),
                        Triple("برنامه", Icons.Default.CalendarMonth, 1),
                        Triple("نتایج", Icons.Default.EmojiEvents, 2),
                        Triple("تنظیمات", Icons.Default.Settings, 3)
                    )
                    items.forEach { (label, icon, index) ->
                        NavigationBarItem(
                            selected = selected == index,
                            onClick = { selected = index },
                            icon = { Icon(icon, contentDescription = label) },
                            label = { Text(label) }
                        )
                    }
                }
            }
        ) { padding ->
            when (selected) {
                0 -> HomeScreen(Modifier.padding(padding))
                1 -> InfoScreen("برنامه مسابقات", "مسابقات آینده پس از اتصال منبع معتبر اطلاعات نمایش داده می‌شوند.", Modifier.padding(padding))
                2 -> InfoScreen("نتایج مسابقات", "نتایج رسمی پس از اتصال منبع معتبر اطلاعات نمایش داده می‌شوند.", Modifier.padding(padding))
                else -> InfoScreen("تنظیمات", "زبان: فارسی و انگلیسی\nمحدوده مسابقات: جهانی", Modifier.padding(padding))
            }
        }
    }
}

@Composable
private fun HomeScreen(modifier: Modifier = Modifier) {
    val date = LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE، d MMMM yyyy", Locale("fa")))
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(Modifier.height(14.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("کشتی‌تایم", color = Forest, fontSize = 29.sp, fontWeight = FontWeight.Black)
                    Text("دنیای کشتی، هر روز همراه تو", color = Ink.copy(alpha = .68f), fontSize = 13.sp)
                }
                Box(
                    modifier = Modifier.background(Forest, RoundedCornerShape(16.dp)).padding(horizontal = 14.dp, vertical = 10.dp)
                ) {
                    Text("KT", color = Orange, fontWeight = FontWeight.Black, fontSize = 22.sp)
                }
            }
        }
        item {
            Card(colors = CardDefaults.cardColors(containerColor = Forest), shape = RoundedCornerShape(22.dp)) {
                Column(Modifier.fillMaxWidth().padding(20.dp)) {
                    Text("امروز", color = Color.White.copy(alpha = .8f), fontSize = 14.sp)
                    Text(date, color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(12.dp))
                    Text("مسابقات مهم کشتی در سراسر جهان", color = Color.White, fontSize = 16.sp)
                }
            }
        }
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("رویدادهای امروز", color = Ink, fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Icon(Icons.Default.Refresh, contentDescription = "تازه‌سازی", tint = Orange)
            }
        }
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    Modifier.fillMaxWidth().padding(22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = Orange)
                    Spacer(Modifier.height(10.dp))
                    Text("هنوز منبع داده وصل نشده است", color = Ink, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "در مرحله بعد، منابع معتبر مسابقات و نتایج را متصل می‌کنیم تا اطلاعات واقعی نمایش داده شود.",
                        color = Ink.copy(alpha = .7f),
                        textAlign = TextAlign.Center,
                        fontSize = 13.sp,
                        lineHeight = 20.sp
                    )
                }
            }
        }
        item {
            Text("امکانات برنامه", color = Ink, fontSize = 19.sp, fontWeight = FontWeight.Bold)
            FeatureCard("تقویم جهانی مسابقات", "برنامه رویدادهای کشتی در روزهای آینده")
            FeatureCard("نتایج و رده‌بندی", "نتایج رسمی و اطلاعات رقابت‌ها")
            FeatureCard("کشتی‌گیران و لیگ‌ها", "نام ورزشکاران، کشورها و برگزارکنندگان")
        }
        item { Spacer(Modifier.height(12.dp)) }
    }
}

@Composable
private fun FeatureCard(title: String, subtitle: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(top = 9.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(title, color = Forest, fontWeight = FontWeight.Bold)
            Text(subtitle, color = Ink.copy(alpha = .68f), fontSize = 12.sp)
        }
    }
}

@Composable
private fun InfoScreen(title: String, message: String, modifier: Modifier = Modifier) {
    Column(
        modifier.fillMaxSize().padding(22.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title, color = Forest, fontSize = 25.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(14.dp))
        Text(message, color = Ink, textAlign = TextAlign.Center, lineHeight = 24.sp)
    }
}
