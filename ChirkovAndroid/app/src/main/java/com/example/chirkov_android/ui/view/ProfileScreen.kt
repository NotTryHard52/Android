package com.example.chirkov_android.ui.view

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.chirkov_android.R
import com.example.chirkov_android.ui.components.CustomBottomBar
import com.example.chirkov_android.ui.components.NavTab
import com.example.chirkov_android.ui.theme.Accent
import com.example.chirkov_android.ui.theme.Background
import com.example.chirkov_android.ui.theme.Block
import com.example.chirkov_android.ui.theme.SubTextDark
import com.example.chirkov_android.data.createTempImageUri
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chirkov_android.ui.theme.CustomTheme
import com.example.chirkov_android.ui.viewModel.ProfileState
import com.example.chirkov_android.ui.viewModel.ProfileViewModel

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onFabClick: () -> Unit = {},
    onTabSelected: (Int) -> Unit = {}
) {

    var activeTab by remember { mutableIntStateOf(3) }

    val tabs = remember {
        listOf(
            NavTab(R.drawable.home, "Home"),
            NavTab(R.drawable.favorite, "Favorite"),
            NavTab(R.drawable.orders, "Orders"),
            NavTab(R.drawable.profile, "Profile")
        )
    }

    var isEditMode by remember { mutableStateOf(false) }

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    // ===== Camera state =====
    val context = LocalContext.current
    var avatarUri by remember { mutableStateOf<Uri?>(null) }
    var pendingCameraUri by remember { mutableStateOf<Uri?>(null) }
    val vm: ProfileViewModel = viewModel()
    val profileState by vm.state.collectAsState()

    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                val uri = pendingCameraUri
                avatarUri = uri
                if (uri != null) vm.saveAvatar(uri)
            }
            pendingCameraUri = null
        }
    )

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) {
                val uri = context.createTempImageUri()
                pendingCameraUri = uri
                takePictureLauncher.launch(uri)
            }
            // если пользователь запретил — можно показать MessageDialog
        }
    )

    LaunchedEffect(profileState) {
        val p = (profileState as? ProfileState.Ready)?.profile ?: return@LaunchedEffect
        firstName = p.firstname.orEmpty()
        lastName = p.lastname.orEmpty()
        address = p.address.orEmpty()
        phone = p.phone.orEmpty()
    }
    val fullName by remember(firstName, lastName) {
        mutableStateOf(
            listOf(firstName.trim(), lastName.trim())
                .filter { it.isNotEmpty() }
                .joinToString(" ")
                .ifEmpty { "Пользователь" }
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(Background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 90.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // ===== Header =====
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp, start = 16.dp, end = 16.dp)
            ) {
                Text(
                    text = stringResource(R.string.Profile),
                    modifier = Modifier.fillMaxWidth(),
                    style = CustomTheme.typography.BodyRegular16,
                    color = CustomTheme.colors.text,
                    textAlign = TextAlign.Center
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(Accent)
                        .clickable { isEditMode = !isEditMode },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.edit),
                        contentDescription = "Edit",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(Modifier.height(18.dp))

            // ===== Avatar =====
            val profile = (profileState as? ProfileState.Ready)?.profile

            val avatarModel: Any = avatarUri ?: (profile?.photo ?: R.drawable.profile_avatar)

            AsyncImage(
                model = avatarModel,
                contentDescription = "Avatar",
                onError = { err -> android.util.Log.e("AvatarUI", "Coil error", err.result.throwable) },
                modifier = Modifier
                    .size(96.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.height(10.dp))

            Text(
                text = fullName,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = CustomTheme.typography.BodyRegular20,
                color = CustomTheme.colors.text
            )

            if (isEditMode) {
                Spacer(Modifier.height(6.dp))
                Text(
                    text = stringResource(R.string.PhotoProfile),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                        },
                    textAlign = TextAlign.Center,
                    style = CustomTheme.typography.BodyRegular12,
                    color = CustomTheme.colors.accent
                )
            }

            Spacer(Modifier.height(14.dp))

            if (!isEditMode) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Block),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.barcode),
                        contentDescription = "Barcode",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 18.dp)
                            .height(26.dp),
                        contentScale = ContentScale.FillWidth
                    )
                }
                Spacer(Modifier.height(18.dp))
            } else {
                Spacer(Modifier.height(10.dp))
            }

            EditableProfileField(stringResource(R.string.NameProfile), firstName, isEditMode, isEditMode) { firstName = it }
            Spacer(Modifier.height(12.dp))
            EditableProfileField(stringResource(R.string.Surname), lastName, isEditMode, isEditMode) { lastName = it }
            Spacer(Modifier.height(12.dp))
            EditableProfileField(stringResource(R.string.Address), address, isEditMode, isEditMode) { address = it }
            Spacer(Modifier.height(12.dp))
            EditableProfileField(stringResource(R.string.Number), phone, isEditMode, isEditMode) { phone = it }

            if (isEditMode) {
                Spacer(Modifier.height(18.dp))
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(52.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Accent)
                        .clickable {
                            vm.save(firstName, lastName, address, phone)
                            isEditMode = false
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(stringResource(R.string.Save), fontSize = 14.sp, color = Color.White)
                }
            }

            Spacer(Modifier.height(24.dp))
        }

        CustomBottomBar(
            tabs = tabs,
            fabIcon = R.drawable.shop,
            onFabClick = onFabClick,
            activeTab = activeTab,
            onTabClick = {
                activeTab = it
                onTabSelected(it)
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun EditableProfileField(
    label: String,
    value: String,
    enabled: Boolean,
    showCheck: Boolean,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(text = label, style = CustomTheme.typography.BodyRegular14,
            color = CustomTheme.colors.text)
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(14.dp),
            trailingIcon = {
                if (showCheck) {
                    Image(
                        painter = painterResource(id = R.drawable.check),
                        contentDescription = "Ok",
                        modifier = Modifier.size(18.dp)
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Block,
                unfocusedContainerColor = Block,
                disabledContainerColor = Block,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedTextColor = Color.Black,
                unfocusedTextColor = SubTextDark,
                disabledTextColor = SubTextDark
            )
        )
    }
}

@Preview()
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen()
}
