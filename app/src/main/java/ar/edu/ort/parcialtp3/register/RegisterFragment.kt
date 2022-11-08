package ar.edu.ort.parcialtp3.register

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import ar.edu.ort.parcialtp3.backmethod.IOnBackPressed
import ar.edu.ort.parcialtp3.databinding.FragmentRegisterBinding
import ar.edu.ort.parcialtp3.model.User
import ar.edu.ort.parcialtp3.repository.UserRepository
import kotlinx.coroutines.launch
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


class RegisterFragment : Fragment(), IOnBackPressed {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var usersRepository: UserRepository




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        context?.let { usersRepository = UserRepository.getInstance(it) }

        binding.btnGoToLogin.setOnClickListener{
            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment(false)
            findNavController().navigate(action)
        }
        binding.btnRegister.setOnClickListener{

            val username = binding.registerName.text.toString()
            val algorithm = "AES/CBC/PKCS5Padding"
            val key = SecretKeySpec("1234567890123456".toByteArray(), "AES")
            val iv = IvParameterSpec(ByteArray(16))

            val cipherText = encrypt(algorithm, binding.registerPassword.text.toString(), key, iv)
            if (username.isNullOrBlank() || decrypt(algorithm, cipherText, key, iv).isNullOrBlank()) {
                Toast.makeText(context, "Falta rellenar campos", Toast.LENGTH_SHORT)
                    .show()
            }
            else
                lifecycleScope.launch {
                    val user = usersRepository.getUser(username)
                    if(user == null) {
                        usersRepository.addUser(User(name = username, password = cipherText))
                        Toast.makeText(context, "Registrado con éxito", Toast.LENGTH_SHORT)
                            .show()
                    }
                    else
                        Toast.makeText(context, "Usuario ya existe", Toast.LENGTH_SHORT)
                            .show()
                }



        }

        return binding.root
    }

    private fun decrypt(algorithm: String, cipherText: String, key: SecretKeySpec, iv: IvParameterSpec): String {
        val cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.DECRYPT_MODE, key, iv)
        val plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText))
        return String(plainText)
    }

    private fun encrypt(algorithm: String, inputText: String, key: SecretKeySpec, iv: IvParameterSpec): String {
        val cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.ENCRYPT_MODE, key, iv)
        val cipherText = cipher.doFinal(inputText.toByteArray())
        return Base64.getEncoder().encodeToString(cipherText)
    }

    override fun onBackPressed(): Boolean {

        return false
    }


}