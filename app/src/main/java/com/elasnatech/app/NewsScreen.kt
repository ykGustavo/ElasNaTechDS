package com.elasnatech.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun NewsScreen() {
    // null = mostra todas. Tocar de novo no chip selecionado remove o filtro.
    var selected by rememberSaveable { mutableStateOf<String?>(null) }
    val filtered = MockData.news.filter { selected == null || it.category == selected }

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { SectionTitle("Notícias") }
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(MockData.newsCategories) { category ->
                    FilterChip(
                        selected = selected == category,
                        onClick = { selected = if (selected == category) null else category },
                        label = { Text(category) }
                    )
                }
            }
        }
        items(filtered, key = { it.id }) { NewsCard(it) }
    }
}

@Composable
private fun NewsCard(news: NewsItem) {
    AppCard(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    news.category,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(news.date, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurface)
            }
            Text(
                news.title,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 4.dp),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(news.description, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}
