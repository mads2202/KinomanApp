package com.mads2202.kinomanapp.room

import androidx.room.TypeConverter
import com.mads2202.kinomanapp.model.jsonModel.moviesModel.Genres

class Converters {
    @TypeConverter
    fun fromGenresListToString(genres: List<Genres>): String {
        val stringBuilder = StringBuilder()
        genres.forEach {
            stringBuilder.append(it.name).append(',')
        }

        return stringBuilder.toString()
    }

    @TypeConverter
    fun fromStringToGenres(genreString: String): List<Genres> {
        val genres = ArrayList<Genres>()

        genreString.split(",").forEach {
            genres.add(Genres(it))
        }
        return genres
    }
}