package com.elasnatech.app

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SupportScreen() {
    val context = LocalContext.current

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { SectionTitle("Rede de Apoio") }

        // Card de emergência: abre o discador já com o número (não liga sozinho).
        item {
            AppCard(
                modifier = Modifier.fillMaxWidth(),
                containerColor = MaterialTheme.colorScheme.errorContainer
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        "Ligue 180",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                    Text(
                        "Central de Atendimento à Mulher. Gratuito, 24h, para denúncias e orientação.",
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                    Spacer(Modifier.height(8.dp))
                    Button(
                        onClick = { context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:180"))) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Ligar agora", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        item { SectionTitle("Comunidades") }
        items(MockData.communities) { SupportCard(it) }

        item { SectionTitle("Mentorias e Bolsas") }
        items(MockData.mentorships) { SupportCard(it) }
    }
}

@Composable
private fun SupportCard(item: SupportItem) {
    val uriHandler = LocalUriHandler.current
    AppCard(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text(item.name, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
            Text(item.description, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
            TextButton(
                onClick = { if (item.url.isNotBlank()) uriHandler.openUri(item.url) }
            ) {
                Text("Saiba mais")
            }
        }
    }
}
