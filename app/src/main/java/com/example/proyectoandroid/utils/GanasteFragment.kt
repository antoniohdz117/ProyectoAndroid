package com.example.proyectoandroid.utils

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class GanasteFragment : DialogFragment() {
     override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("¡Ganaste!")
            .setMessage("¡Felicidades por encontrar todas las palabras!")
            .setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
                activity?.finish() // o vuelve al MainActivity si quieres
            }
            .create()
    }
}
