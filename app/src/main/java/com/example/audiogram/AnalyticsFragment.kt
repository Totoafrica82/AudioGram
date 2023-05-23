package com.example.audiogram
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.audiogram.databinding.FragmentAnalyticsBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate

class AnalyticsFragment : Fragment() {

    private val args: AnalyticsFragmentArgs by navArgs()
    private lateinit var binding: FragmentAnalyticsBinding
    private lateinit var lineChart: LineChart

    private var leftEarAmplitudeLevels: ArrayList<Pair<Int, Int>>? = null
    private var rightEarAmplitudeLevels: ArrayList<Pair<Int, Int>>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnalyticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.v("audiogram1", args.item.leftEarAmplitudeLevels.toString())
        Log.v("audiogram1", args.item.toString())
        Log.v("audiogram1", args.item.rightEarAmplitudeLevels.toString())
        lineChart = binding.lineChart

        leftEarAmplitudeLevels = args.item.leftEarAmplitudeLevels
        rightEarAmplitudeLevels = args.item.rightEarAmplitudeLevels

        drawChart()
    }

    private fun drawChart() {
        val leftEarEntries = leftEarAmplitudeLevels?.mapIndexed { index, pair ->
            Entry(pair.first.toFloat(), pair.second.toFloat())
        }
        val rightEarEntries = rightEarAmplitudeLevels?.mapIndexed { index, pair ->
            Entry(pair.first.toFloat(), pair.second.toFloat())
        }

        val leftEarDataSet = LineDataSet(leftEarEntries, "Lewe ucho")
        leftEarDataSet.color = ColorTemplate.MATERIAL_COLORS[0]
        leftEarDataSet.setDrawCircles(false)
        leftEarDataSet.setDrawValues(false)

        val rightEarDataSet = LineDataSet(rightEarEntries, "Prawe ucho")
        rightEarDataSet.color = ColorTemplate.MATERIAL_COLORS[1]
        rightEarDataSet.setDrawCircles(false)
        rightEarDataSet.setDrawValues(false)

        val dataSets: ArrayList<ILineDataSet> = ArrayList()
        dataSets.add(leftEarDataSet)
        dataSets.add(rightEarDataSet)

        val lineData = LineData(dataSets)

        lineChart.data = lineData
        lineChart.axisLeft.setDrawGridLines(false)
        lineChart.axisRight.setDrawGridLines(false)
        lineChart.xAxis.setDrawGridLines(false)
        lineChart.xAxis.setDrawAxisLine(false)
        lineChart.legend.isEnabled = true
        lineChart.description = Description().apply { text = "" }

        lineChart.invalidate()
    }
}
