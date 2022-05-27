package com.iranmobiledev.moodino.ui.more

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.databinding.FragmentAboutBinding


class AboutFragment : BaseFragment() {

    lateinit var binding : FragmentAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutBinding.inflate(inflater , container  , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.tvAbout.text = "تیم توسعه دهندگان : \n" +
                "محمدجواد خوش نشین\n" +
                "طیب شاه بخش\n" +
                "مهدي سیاری\n" +
                "سیدعلی طباطبائی"


        binding.btnAboutContactUs.setOnClickListener {
            val i = Intent(Intent.ACTION_SEND)
            i.type = "message/rfc822"
            i.putExtra(Intent.EXTRA_EMAIL, arrayOf("vistaroidd@gmail.com"))
            try {
                startActivity(Intent.createChooser(i, "Send mail..."))
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(
                    requireContext(),
                    "There are no email clients installed.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.aboutToolbar.setNavigationOnClickListener{
            findNavController().popBackStack()
        }

    }
}