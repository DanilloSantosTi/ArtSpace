package com.example.artspace

import androidx.lifecycle.ViewModel
import com.example.artspace.ConstantsArtWorks.CODE.ALMOND_PICTURE
import com.example.artspace.ConstantsArtWorks.CODE.NIGHT_STAR_PICTURE
import com.example.artspace.ConstantsArtWorks.CODE.SUNFLOWERS_PICTURE
import com.example.artspace.ConstantsArtWorks.CODE.WHEAT_FIELD_PICTURE
import com.example.artspace.ConstantsArtWorks.CODE.ZERO
import com.example.artspace.model.ArtWorksResponse
import com.google.gson.Gson

class MainActivityViewModel : ViewModel() {

    private fun getListArtWorks(): ArtWorksResponse {
        return Gson().fromJson(DataPicturesMock.data, ArtWorksResponse::class.java)
    }

    internal fun getArtWorks(codePicture: Int): Int {
        return when (codePicture) {
            NIGHT_STAR_PICTURE -> {
                R.drawable.a_noite_estrelada_jpg
            }
            ALMOND_PICTURE -> {
                R.drawable.amendoeira_em_flor_jpg
            }
            WHEAT_FIELD_PICTURE -> {
                R.drawable.campo_de_trigo_com_ciprestes
            }
            SUNFLOWERS_PICTURE -> {
                R.drawable.girassois
            }
            else -> {
                R.drawable.sunflowers
            }
        }
    }

    internal fun getArtWorksCode(): List<Int> {
        val responseArtWorks = getListArtWorks().data
        val listCodes = mutableListOf<Int>()

        responseArtWorks.forEach { artWork ->
            listCodes.add(artWork.codePicture)
        }

        return listCodes
    }

    internal fun getArtWorksDescription(index: Int): String {
        val responseArtWorks = getListArtWorks().data
        return responseArtWorks[index].descriptionPicture
    }

    internal fun getArtWorksArtist(index: Int): String {
        val responseArtWorks = getListArtWorks().data
        return responseArtWorks[index].artist
    }

    internal fun codeToIndex(code: Int): Int {
        val responseArtWorks = getListArtWorks().data
        var currentIndex = 0
        responseArtWorks.forEachIndexed { index, artWorks ->
            if (artWorks.codePicture == code) {
                currentIndex = index
            }
        }
        return currentIndex
    }

    internal fun onChangedPictureContinue(codePicture: Int): Int {
        var codeToIndex = codeToIndex(codePicture)
        val responseArtWorks = getListArtWorks().data

        return if (codeToIndex == responseArtWorks.lastIndex){
            codePicture
        } else {
            codeToIndex++
            responseArtWorks[codeToIndex].codePicture
        }
    }

    internal fun onChangedPicturePrevious(codePicture: Int): Int {
        var codeToIndex = codeToIndex(codePicture)
        val responseArtWorks = getListArtWorks().data

        return if (codeToIndex == ZERO){
            codePicture
        } else {
            codeToIndex--
            responseArtWorks[codeToIndex].codePicture
        }
    }
}