package com.example.gmail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gmail.ui.theme.*
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GmailTheme {
                GmailApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GmailApp() {
    var emails by remember { mutableStateOf(getInitialEmails()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Inbox") }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(emails) { email ->
                EmailItem(email) { updatedEmail ->
                    emails = emails.map { if (it.id == updatedEmail.id) updatedEmail else it }
                }
            }
        }
    }
}

@Composable
fun EmailItem(email: Email, onStarredChange: (Email) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SenderAvatar(sender = email.sender)
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = email.sender,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = if (email.isRead) FontWeight.Normal else FontWeight.Bold
                    )
                )
                Text(
                    text = email.subject,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = if (email.isRead) FontWeight.Normal else FontWeight.Bold
                    )
                )
                Text(
                    text = email.body,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = email.timestamp,
                    style = MaterialTheme.typography.bodySmall
                )
                IconButton(onClick = { onStarredChange(email.copy(isStarred = !email.isStarred)) }) {
                    Icon(
                        imageVector = if (email.isStarred) Icons.Filled.Star else Icons.Outlined.StarOutline,
                        contentDescription = "Star",
                        tint = if (email.isStarred) AvatarOrange else Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun SenderAvatar(sender: String) {
    val colors = listOf(AvatarBlue, AvatarGreen, AvatarOrange, AvatarRed, AvatarPurple)
    val color = remember(sender) { colors.random() }
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = sender.first().toString(),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
    }
}

fun getInitialEmails(): List<Email> {
    return listOf(
        Email(id = 1, sender = "Edurila.com", subject = "$19 Only (First 10 spots) - Bestselling...", body = "Are you looking to Learn Web Designin...", timestamp = "12:34 PM", isRead = false, isStarred = false),
        Email(id = 2, sender = "Chris Abad", subject = "Help make Campaign Monitor better", body = "Let us know your thoughts! No Images...", timestamp = "11:22 AM", isRead = true, isStarred = true),
        Email(id = 3, sender = "Tuto.com", subject = "8h de formation gratuite et les nouvea...", body = "Photoshop, SEO, Blender, CSS, WordPre...", timestamp = "11:04 AM", isRead = false, isStarred = false),
        Email(id = 4, sender = "support", subject = "Société Ovh : suivi de vos services - hp...", body = "SAS OVH - http://www.ovh.com 2 rue K...", timestamp = "10:26 AM", isRead = true, isStarred = false),
        Email(id = 5, sender = "Matt from Ionic", subject = "The New Ionic Creator Is Here!", body = "Announcing the all-new Creator, builo", timestamp = "9:58 AM", isRead = false, isStarred = true)
    )
}

@Preview(showBackground = true)
@Composable
fun GmailAppPreview() {
    GmailTheme {
        GmailApp()
    }
}
