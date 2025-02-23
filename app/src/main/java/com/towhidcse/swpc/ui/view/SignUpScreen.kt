package com.towhidcse.swpc.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.towhidcse.swpc.R
import com.towhidcse.swpc.Routes.MainRoute.ForgotPassword.toForgotPassword
import com.towhidcse.swpc.Routes.MainRoute.Home.toHome
import com.towhidcse.swpc.Routes.MainRoute.Login.toLogin

@Composable
fun SignUpScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val deviceId = remember { mutableStateOf("") }
    val deviceName = remember { mutableStateOf("") }

    val nameError = remember { mutableStateOf("") }
    val emailError = remember { mutableStateOf("") }
    val passwordError = remember { mutableStateOf("") }
    val deviceIdError = remember { mutableStateOf("") }
    val deviceNameError = remember { mutableStateOf("") }

    fun validateFields(): Boolean {
        var isValid = true

        if (name.value.isBlank()) {
            nameError.value = "Name is required"
            isValid = false
        } else nameError.value = ""

        if (email.value.isBlank()) {
            emailError.value = "Email is required"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {
            emailError.value = "Invalid email format"
            isValid = false
        } else emailError.value = ""

        if (password.value.isBlank()) {
            passwordError.value = "Password is required"
            isValid = false
        } else if (password.value.length < 6) {
            passwordError.value = "Password must be at least 6 characters"
            isValid = false
        } else passwordError.value = ""

        if (deviceId.value.isBlank()) {
            deviceIdError.value = "Device Mac Address is required"
            isValid = false
        } else deviceIdError.value = ""

        if (deviceName.value.isBlank()) {
            deviceNameError.value = "Device Name is required"
            isValid = false
        } else deviceNameError.value = ""

        return isValid
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Login",
            modifier = Modifier.fillMaxSize().blur(8.dp),
            contentScale = ContentScale.Crop
        )
    }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .padding(28.dp)
                .alpha(0.8f)
                .clip(CutCornerShape(10.dp))
                .background(Color.White)
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier.padding(48.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                SignUpHeader()
                Spacer(modifier = Modifier.height(20.dp))

                SignUpFields(
                    name.value, email.value, password.value, deviceId.value, deviceName.value,
                    nameError.value, emailError.value, passwordError.value, deviceIdError.value, deviceNameError.value,
                    onNameChange = { name.value = it },
                    onEmailChange = { email.value = it },
                    onPasswordChange = { password.value = it },
                    onDeviceIdChange = { deviceId.value = it },
                    onDeviceNameChange = { deviceName.value = it }
                )

                SignUpFooter(
                    onSignUpClick = {
                        if (validateFields()) {
                            navController.toHome()
                        }
                    },
                    onSignInClick = { navController.toLogin() }

                )
            }
        }
    }
}
@Composable
fun SignUpHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Register New Device", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
//        Text(text = "Sign in to continue", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
    }
}
@Composable
fun SignUpFields(
    name: String, email: String, password: String, deviceId: String, deviceName: String,
    nameError: String, emailError: String, passwordError: String, deviceIdError: String, deviceNameError: String,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onDeviceIdChange: (String) -> Unit,
    onDeviceNameChange: (String) -> Unit
) {
    Column {
        TextFieldWithError(
            value = name, label = "Name", placeholder = "Enter your Name",
            error = nameError, onValueChange = onNameChange, icon = Icons.Default.Person
        )
        TextFieldWithError(
            value = email, label = "Email", placeholder = "Enter your email address",
            error = emailError, onValueChange = onEmailChange, icon = Icons.Default.Email
        )
        TextFieldWithError(
            value = password, label = "Password", placeholder = "Enter your password",
            error = passwordError, onValueChange = onPasswordChange,
            icon = Icons.Default.Lock, isPassword = true
        )
        TextFieldWithError(
            value = deviceId, label = "Device Mac Address", placeholder = "Enter your device Mac",
            error = deviceIdError, onValueChange = onDeviceIdChange, icon = Icons.Default.Build
        )
        TextFieldWithError(
            value = deviceName, label = "Device Name", placeholder = "Enter your Device Name",
            error = deviceNameError, onValueChange = onDeviceNameChange, icon = Icons.Outlined.Create
        )
    }
}

@Composable
fun TextFieldWithError(
    value: String, label: String, placeholder: String, error: String,
    onValueChange: (String) -> Unit, icon: androidx.compose.ui.graphics.vector.ImageVector,
    isPassword: Boolean = false
) {
    Column {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            placeholder = { Text(placeholder) },
            leadingIcon = { Icon(icon, contentDescription = label) },
            keyboardOptions = KeyboardOptions(
                keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            modifier = Modifier.fillMaxWidth()
        )
        if (error.isNotEmpty()) {
            Text(text = error, color = Color.Red, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun SignUpFooter(
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = onSignUpClick, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Sign Up")
        }
        TextButton(onClick = onSignInClick) {
            Text(text = "Already Registered, click here")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSignUp() {
    val navController = rememberNavController()
    SignUpScreen(navController)
}