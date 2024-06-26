package com.example.spygamers.screens.friendlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.spygamers.components.ProfilePictureIcon
import com.example.spygamers.models.Friendship

@Composable
fun OutgoingRequestsTabContent(
    requests: List<Friendship>,
    retractRequest: (targetAccountID: Int) -> Unit
) {
    if (requests.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                modifier = Modifier.fillMaxSize(),
                text = "No outgoing friend requests...",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h4
            )
        }
        return
    }
    LazyColumn {
        items(requests.size) { index ->
            val friend = requests[index]
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // TODO: Replace with actual pfp
                ProfilePictureIcon()

                // Friend name
                Text(
                    text = friend.username,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp, end = 16.dp),
                    style = MaterialTheme.typography.body1,
                )

                // Reject icon
                IconButton(
                    onClick = {
                        retractRequest(friend.accountID)
                    },
                    modifier = Modifier
                        .padding(4.dp)
                        .background(Color.Red)
                ) {
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = "Reject",
                        tint = Color.White
                    )
                }
            }
        }
    }
}