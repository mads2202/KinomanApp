package com.mads2202.kinomanapp.model.jsonModel.upcomingMovies

data class Root (
    var page:Int?,
    var results: List<Result>?,
    var dates: Dates?,
    var total_pages:Int?,
    var total_results:Int?)