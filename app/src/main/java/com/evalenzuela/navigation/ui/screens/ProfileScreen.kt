package com.evalenzuela.navigation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.evalenzuela.navigation.ui.viewmodel.MainViewModel
import com.evalenzuela.navigation.ui.viewmodel.CurrentProfileType
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import android.widget.Toast
import androidx.compose.foundation.text.KeyboardOptions


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: MainViewModel) {

    val profileType = viewModel.userProfileType.collectAsState().value

    val isBuyer = profileType == CurrentProfileType.BUYER
    val nextProfileType = if (isBuyer) CurrentProfileType.SELLER else CurrentProfileType.BUYER
    val buttonText = if (isBuyer) "Cambiar a Perfil de Vendedor" else "Cambiar a Perfil de Comprador"
    val title = when (profileType) {
        CurrentProfileType.BUYER -> "Perfil de Comprador"
        CurrentProfileType.SELLER -> "Perfil de Vendedor"
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text(title) }) }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            when (profileType) {
                CurrentProfileType.BUYER -> BuyerProfileContent(viewModel)
                CurrentProfileType.SELLER -> SellerProfileContent()
            }
            Spacer(Modifier.height(32.dp))

            Button(onClick = {

                viewModel.switchProfileType(nextProfileType)
            }) {
                Text(buttonText)
            }
        }
    }
}
@Composable
fun BuyerProfileContent(viewModel: MainViewModel) {

    val email by viewModel.userEmail.collectAsState()
    val password by viewModel.password.collectAsState()
    val emailError by viewModel.emailError.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()

    var passwordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {

        Text(
            "Ingrese sus credenciales (Validación):",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(24.dp))


        OutlinedTextField(
            value = email,
            onValueChange = viewModel::onEmailChange,
            label = { Text("Email (Usuario)") },
            isError = emailError != null,
            trailingIcon = { if (emailError != null) Icon(Icons.Default.Warning, contentDescription = "Error", tint = MaterialTheme.colorScheme.error) },
            modifier = Modifier.fillMaxWidth(0.9f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        if (emailError != null) {
            Text(
                text = emailError!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 8.dp).align(Alignment.Start)
            )
        }

        Spacer(Modifier.height(16.dp))


        OutlinedTextField(
            value = password,
            onValueChange = viewModel::onPasswordChange,
            label = { Text("Contraseña") },
            isError = passwordError != null,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = "Toggle password visibility")
                }
            },
            modifier = Modifier.fillMaxWidth(0.9f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        if (passwordError != null) {
            Text(
                text = passwordError!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 8.dp).align(Alignment.Start)
            )
        }

        Spacer(Modifier.height(24.dp))


        Button(
            onClick = {
                val success = viewModel.validateAndSaveProfile()
                if (success) {
                    Toast.makeText(context, "Credenciales validadas con éxito.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Corrige los errores del formulario.", Toast.LENGTH_LONG).show()
                }
            },
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            Text("Validar Credenciales")
        }
    }
}

@Composable
fun SellerProfileContent() {
    Text("Bienvenido, Vendedor.", style = MaterialTheme.typography.headlineSmall)
    Spacer(Modifier.height(8.dp))
    Text("Administra tus productos y revisa tus estadísticas de venta.")
    Spacer(Modifier.height(16.dp))
    Button(onClick = { }) {
        Text("Ver Mis Productos")
    }
}