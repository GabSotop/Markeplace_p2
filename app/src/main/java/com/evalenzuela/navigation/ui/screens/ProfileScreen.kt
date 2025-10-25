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
                CurrentProfileType.BUYER -> BuyerProfileContent()
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
fun BuyerProfileContent() {
    Text("Bienvenido, Comprador.", style = MaterialTheme.typography.headlineSmall)
    Spacer(Modifier.height(8.dp))
    Text("Aquí verás tu historial de compras y métodos de pago.")
}


@Composable
fun SellerProfileContent() {
    Text("Bienvenido, Vendedor.", style = MaterialTheme.typography.headlineSmall)
    Spacer(Modifier.height(8.dp))
    Text("Administra tus productos y revisa tus estadísticas de venta.")
    Spacer(Modifier.height(16.dp))
    Button(onClick = { /* Acción para ver productos */ }) {
        Text("Ver Mis Productos")
    }
}
