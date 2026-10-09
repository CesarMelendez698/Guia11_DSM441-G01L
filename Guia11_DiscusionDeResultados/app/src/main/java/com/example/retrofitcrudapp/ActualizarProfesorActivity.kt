package com.example.retrofitcrudapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActualizarProfesorActivity : AppCompatActivity() {
    private lateinit var nombreEditText: EditText
    private lateinit var apellidoEditText: EditText
    private lateinit var edadEditText: EditText
    private lateinit var actualizarButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_actualizar_profesor)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        nombreEditText = findViewById(R.id.nombreEditTextProfesor)
        apellidoEditText = findViewById(R.id.apellidoEditTextProfesor)
        edadEditText = findViewById(R.id.edadEditTextProfesor)
        actualizarButton = findViewById(R.id.actualizarButtonProfesor)

        val profesorId = intent.getStringExtra("profesor_id") ?: ""
        val nombre = intent.getStringExtra("nombre") ?: ""
        val apellido = intent.getStringExtra("apellido") ?: ""
        val edad = intent.getStringExtra("edad") ?: ""

        nombreEditText.setText(nombre)
        apellidoEditText.setText(apellido)
        edadEditText.setText(edad)

        val api = RetrofitClient.profesorApi

        actualizarButton.setOnClickListener {
            val nuevoNombre = nombreEditText.text.toString().trim()
            val nuevoApellido = apellidoEditText.text.toString().trim()
            val nuevaEdad = edadEditText.text.toString().trim()

            if (nuevoNombre.isEmpty() || nuevoApellido.isEmpty() || nuevaEdad.isEmpty()) {
                Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val profesorActualizado = Profesor(
                id = profesorId,
                nombre = nuevoNombre,
                apellido = nuevoApellido,
                edad = nuevaEdad
            )

            api.actualizarProfesor(profesorId, profesorActualizado).enqueue(object : Callback<Profesor> {
                override fun onResponse(call: Call<Profesor>, response: Response<Profesor>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@ActualizarProfesorActivity, "Profesor actualizado correctamente", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        val error = response.errorBody()?.string()
                        Log.e("API", "Error al actualizar profesor: $error")
                        Toast.makeText(this@ActualizarProfesorActivity, "Error al actualizar el profesor", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Profesor>, t: Throwable) {
                    Log.e("API", "onFailure: ${t.message}")
                    Toast.makeText(this@ActualizarProfesorActivity, "Error de red al actualizar profesor", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
