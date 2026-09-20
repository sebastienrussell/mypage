package ca.russellmania.mypage.ui.home

data class EducationInfo(
    val title: String = "",
    val startYear: Int = 0,
    val endYear: Int = 0,
    val school: String = "",
)

internal val defaultEducation = EducationInfo(
    title = "DEC en technique de l'informatique de gestion",
    startYear = 2013,
    endYear = 2016,
    school = "CÉGEP André-Laurendeau, Montréal, QC",
)
