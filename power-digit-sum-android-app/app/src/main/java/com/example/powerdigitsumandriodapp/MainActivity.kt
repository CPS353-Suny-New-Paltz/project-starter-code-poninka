package com.example.powerdigitsumandriodapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.powerdigitsumandriodapp.ui.theme.PowerDigitSumTheme
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import project.networkapi.grpc.NetworkSubmissionServiceGrpc
import project.networkapi.grpc.SubmitRequest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PowerDigitSumTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PowerDigitSumScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun PowerDigitSumScreen(modifier: Modifier = Modifier) {
    var number by remember { mutableStateOf("") }
    var serverAddress by remember { mutableStateOf("10.0.2.2:50051") }
    var output by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Server address at the very top
        TextField(
            value = serverAddress,
            onValueChange = { serverAddress = it },
            label = { Text("Server Address") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Center content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // n^n display with superscript
            Text(
                text = buildAnnotatedString {
                    val n = number.ifBlank { "n" }
                    append(n)
                    withStyle(
                        SpanStyle(
                            fontSize = 20.sp,
                            baselineShift = BaselineShift.Superscript
                        )
                    ) {
                        append(n)
                    }
                },
                fontSize = 48.sp
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Single input for n
            TextField(
                value = number,
                onValueChange = { if (it.length <= 10 && it.all { char -> char.isDigit() }) number = it },
                label = { Text("Enter n") },
                modifier = Modifier.width(200.dp),
                textStyle = LocalTextStyle.current.copy(fontSize = 28.sp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Result text box
            TextField(
                value = output,
                onValueChange = { },
                label = { Text("Digit Sum Result") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                minLines = 2,
                maxLines = 4
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Send button - aligned to the right
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        if (number.isNotBlank()) {
                            output = "Sending..."
                            coroutineScope.launch {
                                output = sendGrpcRequest(serverAddress, number)
                            }
                        } else {
                            output = "Enter a number"
                        }
                    }
                ) {
                    Text("Send")
                }
            }
        }
    }
}

suspend fun sendGrpcRequest(serverAddress: String, number: String): String {
    return withContext(Dispatchers.IO) {
        try {
            val parts = serverAddress.split(":")
            val host = parts.getOrElse(0) { "10.0.2.2" }
            val port = parts.getOrElse(1) { "50051" }.toIntOrNull() ?: 50051
            
            val channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build()
            
            try {
                val stub = NetworkSubmissionServiceGrpc.newBlockingStub(channel)
                
                val request = SubmitRequest.newBuilder()
                    .setInputType("memory")
                    .setInputValue(number)
                    .setOutputDestination("/tmp/android_output.txt")
                    .setDelimiter(",")
                    .build()
                
                val response = stub.submit(request)
                
                if (response.status == "SUCCESS") {
                    response.message.ifBlank { "Success" }
                } else {
                    "Error: ${response.status}"
                }
            } finally {
                channel.shutdown()
            }
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PowerDigitSumPreview() {
    PowerDigitSumTheme {
        PowerDigitSumScreen()
    }
}
