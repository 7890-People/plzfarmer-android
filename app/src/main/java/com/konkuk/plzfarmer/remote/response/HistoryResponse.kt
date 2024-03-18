package com.konkuk.plzfarmer.remote.response

import java.time.LocalDate
import java.time.LocalDateTime

data class HistoryResponse(
    val monthlyHistories: List<DateHistory>
)

data class DateHistory(
    val date: String,
    val historyList: List<DiseaseHistory>,
) {
    val localDate: LocalDateTime = LocalDate.parse(date).atStartOfDay()
}

data class DiseaseHistory(
    val id: String,
    val diseaseKrName: String,
    val diseaseEngName: String,
    val plantName: String,
    val imageUrl: String,
)
