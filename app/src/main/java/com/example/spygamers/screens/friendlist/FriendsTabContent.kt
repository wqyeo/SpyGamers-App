package com.example.spygamers.screens.friendlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
fun FriendsTabContent(
    acceptedFriends: List<Friendship>,
    onRemoveFriend: (targetFriendID: Int) -> Unit,
    onFriendSelected: (selected: Friendship) -> Unit
) {
    if (acceptedFriends.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                modifier = Modifier.fillMaxSize(),
                text = "No friends...",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h4
            )
        }
        return
    }

    // Implement the UI for displaying friends
    LazyColumn {
        items(acceptedFriends.size) {index ->
            val friend = acceptedFriends[index]
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // TODO: Replace with actual profile picture...
                ProfilePictureIcon()

                // Name of friend, with onclick callback to selected...
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .clickable{onFriendSelected(friend)}
                    .padding(8.dp)
                    .weight(4f)
                ) {
                    Text(
                        text = friend.username,
                        style = MaterialTheme.typography.body1
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Tap here to start a conversation!",
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.secondary
                    )
                }

                // Reject icon
                IconButton(
                    onClick = {
                        onRemoveFriend(friend.accountID)
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