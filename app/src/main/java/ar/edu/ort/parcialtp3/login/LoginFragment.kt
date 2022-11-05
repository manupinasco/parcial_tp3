package ar.edu.ort.parcialtp3.login

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.*
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import ar.edu.ort.parcialtp3.R
import ar.edu.ort.parcialtp3.databinding.FragmentLoginBinding
import ar.edu.ort.parcialtp3.repository.UserRepository
import kotlinx.coroutines.launch
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var usersRepository: UserRepository
    private lateinit var soundPool: SoundPool

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)


        context?.let { usersRepository = UserRepository.getInstance(it) }


        soundPool = if (Build.VERSION.SDK_INT
            >= Build.VERSION_CODES.LOLLIPOP
        ) {

            val audioAttributes = AudioAttributes.Builder()
                .setUsage(
                    AudioAttributes.USAGE_ASSISTANCE_SONIFICATION
                )
                .setContentType(
                    AudioAttributes.CONTENT_TYPE_SONIFICATION
                )
                .build()
            SoundPool.Builder()
                .setMaxStreams(3)
                .setAudioAttributes(
                    audioAttributes
                )
                .build()
        } else {
            SoundPool(
                3,
                AudioManager.STREAM_MUSIC,
                0
            )
        }
        val imageView = binding.bgImageLogin


        imageView.setOnClickListener {

            val portalSound = soundPool
                .load(
                    context,
                    R.raw.portal_sound_effect,
                    1
                )
            soundPool.setOnLoadCompleteListener { soundPool, sampleId, status ->
                soundPool.play(
                    sampleId, 1F, 1F, 0, 0, 1F
                )

                if (status == 0) {
                    generateAnimation()
                }

            }
        }








        return binding.root
    }

    private fun generateAnimation() {

        val zoomIn = AnimationUtils.loadAnimation(context, R.anim.zoom_in)
        val rotate = AnimationUtils.loadAnimation(context, R.anim.rotate)
        val fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out)
        val imageView = binding.bgImageLogin
        val title = binding.titleLogin
        val btnLogin = binding.btnLogin
        val name = binding.loginName
        val password = binding.loginPassword
        val btnRegister = binding.btnGoToRegister

        imageView.startAnimation(rotate)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                title.visibility = VISIBLE
                title.startAnimation(zoomIn)
                name.visibility = VISIBLE
                name.startAnimation(zoomIn)
                password.visibility = VISIBLE
                password.startAnimation(zoomIn)
                btnLogin.visibility = VISIBLE
                btnLogin.startAnimation(zoomIn)
                btnRegister.visibility = VISIBLE
                btnRegister.startAnimation(zoomIn)
            },
            2000
        )

        Handler(Looper.getMainLooper()).postDelayed(
            {
                imageView.startAnimation(fadeOut)
                imageView.visibility = INVISIBLE
            },
            3000 // value in milliseconds
        )


    }



    override fun onStart() {
        super.onStart()

        val btnLogin = binding.btnLogin
        val name = binding.loginName
        val password = binding.loginPassword
        val btnRegister = binding.btnGoToRegister
        btnRegister.setOnClickListener{
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)
        }

        btnLogin.setOnClickListener{

            val name = binding.loginName.text.toString()
            val algorithm = "AES/CBC/PKCS5Padding"
            val key = SecretKeySpec("1234567890123456".toByteArray(), "AES")
            val iv = IvParameterSpec(ByteArray(16))

            val cipherText = encrypt(algorithm, binding.loginPassword.text.toString(), key, iv)

            if (name.isNullOrBlank() || decrypt(algorithm, cipherText, key, iv).isNullOrBlank()) {
                Toast.makeText(context, "Falta rellenar campos", Toast.LENGTH_SHORT)
                    .show()
            } else {
                lifecycleScope.launch {
                    val user = usersRepository.getUser(name)
                    if(user != null) {
                        if(decrypt(algorithm, cipherText, key, iv).compareTo(user.password) == 0) {
                            val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                            findNavController().navigate(action)
                        }
                        else
                            Toast.makeText(context, "Contraseña incorrecta", Toast.LENGTH_SHORT)
                                .show()
                    }
                    else
                        Toast.makeText(context, "Usuario no encontrado", Toast.LENGTH_SHORT)
                            .show()
                }


            }


        }
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


}