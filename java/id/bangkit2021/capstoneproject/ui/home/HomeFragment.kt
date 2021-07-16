package id.bangkit2021.capstoneproject.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.auth.FirebaseAuth
import id.bangkit2021.capstoneproject.R
import id.bangkit2021.capstoneproject.databinding.FragmentHomeBinding
import id.bangkit2021.capstoneproject.ui.about.AboutActivity
import id.bangkit2021.capstoneproject.ui.article.ArticleActivity
import id.bangkit2021.capstoneproject.ui.hospitals.HospitalsActivity
import id.bangkit2021.capstoneproject.ui.preference.PreferenceActivity

@Suppress("DEPRECATION")
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var mAuth: FirebaseAuth

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        binding.textName.text = String.format(getString(R.string.hello), currentUser?.displayName)
        imageAds()
        loadHospitalsData()
        loadArticleData()
        binding.btnPreference.setOnClickListener {
            val intent = Intent(context, PreferenceActivity::class.java)
            startActivity(intent)
        }

        binding.btnAbout.setOnClickListener {
            val intent = Intent(context, AboutActivity::class.java)
            startActivity(intent)
        }

        binding.btnHospitals.setOnClickListener {
            val intent = Intent(context, HospitalsActivity::class.java)
            startActivity(intent)
        }
        binding.btnArticle.setOnClickListener {
            val intent = Intent(context, ArticleActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadHospitalsData() {
        val hospitalsViewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            )[HomeViewModel::class.java]
        val hospitals = hospitalsViewModel.getHospitalsData()
        val hospitalsAdapter = HorizontalAdapter()
        hospitalsAdapter.setHospitals(hospitals)
        with(binding.rvList) {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = hospitalsAdapter
        }
    }

    private fun loadArticleData() {
        val articleViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[HomeViewModel::class.java]
        val articles = articleViewModel.getArticleData()
        val articleAdapter = LinearAdapter()
        articleAdapter.setArticle(articles)
        with(binding.rvArticle) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = articleAdapter
        }
    }


    private fun imageAds() {
        val imageAds = ArrayList<SlideModel>()

        imageAds.add(SlideModel(R.drawable.ads_1))
        imageAds.add(SlideModel(R.drawable.ads_2))
        imageAds.add(SlideModel(R.drawable.ads_3))

        val imageSlider = activity?.findViewById<ImageSlider>(R.id.imageSlider)
        imageSlider?.setImageList(imageAds)
    }

}