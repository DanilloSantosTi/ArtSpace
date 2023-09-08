package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ArtSpaceContent(viewModel)
                }
            }
        }
    }
}

@Composable
fun ArtSpaceContent(viewModel: MainActivityViewModel) {

    val codeListPicture = viewModel.getArtWorksCode()
    var codePicture by remember { mutableStateOf(codeListPicture.first()) }
    var index by remember { mutableStateOf(viewModel.codeToIndex(codePicture)) }
    var enableButtonContinue by remember { mutableStateOf(false) }
    var enableButtonPrevious by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        ArtSpacePictureContent(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally),
            picture = viewModel.getArtWorks(codePicture),
            artWorksDescription = viewModel.getArtWorksDescription(index)
        )
        Spacer(modifier = Modifier.padding(vertical = 24.dp))
        ArtSpacePictureDescriptionContent(
            modifier = Modifier,
            description = viewModel.getArtWorksDescription(index),
            artist = viewModel.getArtWorksArtist(index)
        )
        Spacer(modifier = Modifier.padding(vertical = 24.dp))
        ArtSpaceButtonsContent(
            modifier = Modifier.fillMaxWidth(),
            enableButtonContinue = !enableButtonContinue,
            enableButtonPrevious = !enableButtonPrevious,
            onPictureChangedContinue = {
                codePicture = it
                index = viewModel.codeToIndex(it)
                enableButtonContinue =
                    viewModel.codeToIndex(codePicture) == codeListPicture.lastIndex
                enableButtonPrevious =
                    viewModel.codeToIndex(codePicture) == ConstantsArtWorks.CODE.ZERO
            },
            onPictureChangedPrevious = {
                codePicture = it
                index = viewModel.codeToIndex(it)
                enableButtonPrevious =
                    viewModel.codeToIndex(codePicture) == ConstantsArtWorks.CODE.ZERO
                enableButtonContinue =
                    viewModel.codeToIndex(codePicture) == codeListPicture.lastIndex

            },
            codePicture = codePicture,
            viewModel = viewModel
        )
    }
}

@Composable
internal fun ArtSpacePictureContent(
    modifier: Modifier = Modifier,
    picture: Int,
    artWorksDescription: String
) {
    Box(modifier = modifier) {
        Image(painter = painterResource(id = picture), contentDescription = artWorksDescription)
    }
}

@Composable
internal fun ArtSpacePictureDescriptionContent(
    modifier: Modifier = Modifier,
    description: String,
    artist: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
    ) {
        Text(modifier = modifier.padding(bottom = 24.dp), text = description)
        Text(text = artist)
    }
}

@Composable
internal fun ArtSpaceButtonsContent(
    modifier: Modifier = Modifier,
    enableButtonPrevious: Boolean,
    enableButtonContinue: Boolean,
    onPictureChangedContinue: (Int) -> Unit,
    onPictureChangedPrevious: (Int) -> Unit,
    viewModel: MainActivityViewModel,
    codePicture: Int
) {
    Row(modifier = modifier) {
        Button(
            enabled = enableButtonPrevious,
            onClick = {
                onPictureChangedPrevious(
                    viewModel.onChangedPicturePrevious(
                        codePicture
                    )
                )
            }) {
            Text(text = stringResource(id = R.string.btn_previous))
        }

        Button(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            enabled = enableButtonContinue,
            onClick = {
                onPictureChangedContinue(
                    viewModel.onChangedPictureContinue(
                        codePicture
                    )
                )
            }) {
            Text(text = stringResource(id = R.string.btn_continue))
        }
    }
}

