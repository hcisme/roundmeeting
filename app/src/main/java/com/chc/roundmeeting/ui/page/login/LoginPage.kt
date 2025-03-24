package com.chc.roundmeeting.ui.page.login

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.chc.roundmeeting.R
import com.chc.roundmeeting.component.AnimatedLabelText
import com.chc.roundmeeting.navigationgraph.HOME_PAGE
import com.chc.roundmeeting.utils.LocalNavController
import com.chc.roundmeeting.utils.LocalSharedPreferences
import com.chc.roundmeeting.utils.saveToken
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginPage() {
    val context = LocalContext.current
    val sharedPreferences = LocalSharedPreferences.current
    val navController = LocalNavController.current
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.login_ani))
    val progress by animateLottieCompositionAsState(
        composition,
        isPlaying = true,
        iterations = LottieConstants.IterateForever
    )
    val loginVM = viewModel<LoginViewModel>()

    LaunchedEffect(Unit) {
        loginVM.getCaptcha()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    focusManager.clearFocus()
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally),
                composition = composition,
                progress = { progress },
            )

            Text(
                text = "Login",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = loginVM.email,
                onValueChange = { loginVM.email = it },
                label = {
                    AnimatedLabelText(defaultLabel = "邮箱", errorMessage = loginVM.emailError)
                },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.AccountCircle,
                        contentDescription = null
                    )
                },
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 20.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = loginVM.password,
                onValueChange = { loginVM.password = it },
                label = {
                    AnimatedLabelText(defaultLabel = "密码", errorMessage = loginVM.passwordError)
                },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Lock,
                        contentDescription = null
                    )
                },
                visualTransformation = if (loginVM.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    Icon(
                        painterResource(if (loginVM.passwordVisible) R.drawable.visible else R.drawable.invisible),
                        contentDescription = null,
                        modifier = Modifier.clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            loginVM.passwordVisible = !loginVM.passwordVisible
                        }
                    )
                },
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 20.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = loginVM.captcha,
                    onValueChange = { loginVM.captcha = it },
                    label = {
                        AnimatedLabelText(
                            defaultLabel = "验证码",
                            errorMessage = loginVM.captchaError
                        )
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier.weight(1F)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Surface(
                    modifier = Modifier
                        .size(
                            140.dp,
                            TextFieldDefaults.MinHeight + 1.dp
                        ),
                    shape = MaterialTheme.shapes.small
                ) {
                    loginVM.captchaBitmap?.let {
                        Image(
                            bitmap = it,
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) {
                                    coroutineScope.launch {
                                        loginVM.getCaptcha()
                                    }
                                },
                            contentDescription = "验证码图片",
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    loginVM.isLoginIng = true
                    coroutineScope.launch {
                        delay(2000)
                        sharedPreferences.saveToken("sjioserjq90343r8jf9s")
                        loginVM.isLoginIng = false
                        navController.navigate(HOME_PAGE) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 90.dp)
            ) {
                Text("登录")
            }

            Spacer(modifier = Modifier.height(50.dp))

            Row {
                Text("没有账号？", fontSize = 13.sp)
                Text(
                    "现在注册",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 13.sp,
                    modifier = Modifier.clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {}
                )
            }
        }
    }

    AnimatedVisibility(visible = loginVM.isLoginIng) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.login_loading))
        val progress by animateLottieCompositionAsState(
            composition,
            isPlaying = true,
            iterations = LottieConstants.IterateForever
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4F))
        ) {
            LottieAnimation(
                modifier = Modifier
                    .size(320.dp)
                    .align(Alignment.Center),
                composition = composition,
                progress = { progress },
            )
        }
    }

    BackHandler {
        (context as Activity).finish()
    }
}
