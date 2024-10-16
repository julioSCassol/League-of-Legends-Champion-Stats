package com.example.league_of_legends_application.ui.screens

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.league_of_legends_application.R
import com.example.league_of_legends_application.model.Champion
import com.example.league_of_legends_application.utils.loadImageFromUrl
import com.example.league_of_legends_application.viewmodel.ChampionViewModel

private const val CHANNEL_ID = "team_creation_channel"
private const val NOTIFICATION_ID = 1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChampionRandomizerScreen(
    viewModel: ChampionViewModel,
    onBackClick: () -> Unit
) {
    val champions by viewModel.champions.collectAsState(initial = emptyList())
    var team1Champions by remember { mutableStateOf<List<Champion>>(emptyList()) }
    var team2Champions by remember { mutableStateOf<List<Champion>>(emptyList()) }
    var teamsRandomized by remember { mutableStateOf(false) }

    val context = LocalContext.current

    fun createNotificationChannel(context: Context) {
        val name = "Team Creation Channel"
        val descriptionText = "Notificações para a criação de equipes"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun sendTeamNotification(context: Context, team1: List<Champion>, team2: List<Champion>) {
        val team1Names = team1.joinToString(", ") { it.name }
        val team2Names = team2.joinToString(", ") { it.name }
        val notificationText = "Equipe 1: $team1Names\nEquipe 2: $team2Names"

        createNotificationChannel(context)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Equipes Criadas!")
            .setContentText("Equipes foram randomizadas com sucesso.")
            .setStyle(NotificationCompat.BigTextStyle().bigText(notificationText))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
    }

    fun randomizeTeams() {
        val shuffledChampions = champions.shuffled()
        team1Champions = shuffledChampions.take(5)
        team2Champions = shuffledChampions.drop(5).take(5)
        teamsRandomized = true
        sendTeamNotification(context, team1Champions, team2Champions)
    }

    fun shareTeamsViaWhatsApp() {
        if (teamsRandomized) {
            val team1Names = team1Champions.joinToString(", ") { it.name }
            val team2Names = team2Champions.joinToString(", ") { it.name }
            val shareText = "Equipe 1: $team1Names\nEquipe 2: $team2Names"
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                setPackage("com.whatsapp")
                putExtra(Intent.EXTRA_TEXT, shareText)
            }

            try {
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context, "WhatsApp não está instalado", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Por favor, randomize as equipes primeiro", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Criar Equipes", color = Color(0xFFDFD79B), fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = Color.White)
                    }
                },
                actions = {
                    Button(
                        onClick = { randomizeTeams() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDFD79B))
                    ) {
                        Text(text = "Randomizar", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color(0xFF0F1923))
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F1923))
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (teamsRandomized) {
                    Text("Equipe 1", color = Color(0xFFDFD79B), fontSize = 22.sp, modifier = Modifier.padding(bottom = 4.dp))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                            team1Champions.take(3).forEach { champion ->
                                ChampionItem(champion)
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                            team1Champions.drop(3).take(2).forEach { champion ->
                                ChampionItem(champion)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Image(
                        painter = painterResource(id = R.drawable.versus),
                        contentDescription = "Versus",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(150.dp)
                            .align(Alignment.CenterHorizontally)
                            .padding(vertical = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text("Equipe 2", color = Color(0xFFDFD79B), fontSize = 22.sp, modifier = Modifier.padding(bottom = 4.dp))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                            team2Champions.take(3).forEach { champion ->
                                ChampionItem(champion)
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                            team2Champions.drop(3).take(2).forEach { champion ->
                                ChampionItem(champion)
                            }
                        }
                    }
                }
            }

            FloatingActionButton(
                onClick = { shareTeamsViaWhatsApp() },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .size(56.dp),
                containerColor = Color(0xFF25D366),
                shape = CircleShape
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.whatsapp),
                    contentDescription = "Compartilhar no WhatsApp",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@Composable
fun ChampionItem(champion: Champion) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(100.dp)
            .padding(8.dp)
            .background(Color(0xFF1A2634), shape = MaterialTheme.shapes.medium)
    ) {
        var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

        LaunchedEffect(champion.icon) {
            imageBitmap = loadImageFromUrl(champion.icon)
        }

        imageBitmap?.let {
            Image(
                bitmap = it,
                contentDescription = champion.name,
                modifier = Modifier
                    .size(64.dp)
                    .padding(4.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = champion.name,
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
    }
}
