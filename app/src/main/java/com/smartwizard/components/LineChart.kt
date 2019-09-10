package com.smartwizard.components

import android.content.Context
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.data.Set
import com.anychart.enums.Anchor
import com.anychart.enums.MarkerType
import com.anychart.enums.TooltipPositionMode
import com.anychart.graphics.vector.Stroke
import kotlin.random.Random


class LineChart private constructor(
    var chartView: AnyChartView
    , var context: Context
    , var data: HashMap<String, String>
    , var title: String
    , var explanation: String
)
{


    var titleHtml: String =
        "<span style=\"color:#7c868e; font-size: 18px\"> title here </span> <br>" +
                "<span style=\"color:#545f69; font-size: 14px\"> explanation here </span>"

    class Builder
    {

        lateinit var title: String
        lateinit var chartView: AnyChartView
        lateinit var data: HashMap<String, String>
        lateinit var explanation: String
        lateinit var context: Context

        fun withContext(context: Context): Builder
        {
            this.context = context
            return this
        }

        fun withTitle(title: String): Builder
        {
            this.title = title

            return this
        }

        fun withExplanation(explanation: String): Builder
        {
            this.explanation = explanation
            return this
        }

        fun withMapData(data: HashMap<String, String>): Builder
        {
            this.data = data
            return this
        }

        fun chart(chartView: AnyChartView): Builder
        {
            this.chartView = chartView
            return this
        }

        fun show()
        {
            val chart = LineChart(chartView, context, data, title, explanation)
            chart.setUpChart()
        }
    }


    fun setUpChart()
    {
        val cartesian = AnyChart.line()

        cartesian.animation(true)

        cartesian.padding(10.0, 20.0, 5.0, 20.0)

        cartesian.crosshair().enabled(true)
        cartesian.crosshair()
            .yLabel(true)
            // TODO ystroke
            .yStroke(null as Stroke?, null, null, null as String?, null as String?)

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)

        cartesian.title(title)

        cartesian.yAxis(0).title("Sensor data readings")
        cartesian.xAxis(0).labels().padding(5.0, 5.0, 5.0, 5.0)

        val seriesData = ArrayList<DataEntry>()

        for (entry in data)
        {
//            var value: Int = if(entry.value == "Unknown") 0 else Integer.parseInt(entry.value) / 100000

            val dataArray = entry.value.split(":")
            val value2 = dataArray[1].toFloat().toInt()
            val value3 = dataArray[2].toFloat().toInt()
            val value1 =dataArray[0].toFloat().toInt()
            seriesData.add(CustomDataEntry(entry.key, value1, value2, value3))
        }
        /* seriesData.add(CustomDataEntry("1986", 3.6, 2.3, 2.8))
         seriesData.add(CustomDataEntry("1987", 7.1, 4.0, 4.1))
         seriesData.add(CustomDataEntry("1988", 8.5, 6.2, 5.1))
         seriesData.add(CustomDataEntry("1989", 9.2, 11.8, 6.5))
         seriesData.add(CustomDataEntry("1990", 10.1, 13.0, 12.5))
         seriesData.add(CustomDataEntry("1991", 11.6, 13.9, 18.0))
         seriesData.add(CustomDataEntry("1992", 16.4, 18.0, 21.0))
         seriesData.add(CustomDataEntry("1993", 18.0, 23.3, 20.3))
         seriesData.add(CustomDataEntry("1994", 13.2, 24.7, 19.2))
         seriesData.add(CustomDataEntry("1995", 12.0, 18.0, 14.4))
         seriesData.add(CustomDataEntry("1996", 3.2, 15.1, 9.2))
         seriesData.add(CustomDataEntry("1997", 4.1, 11.3, 5.9))
         seriesData.add(CustomDataEntry("1998", 6.3, 14.2, 5.2))
         seriesData.add(CustomDataEntry("1999", 9.4, 13.7, 4.7))
         seriesData.add(CustomDataEntry("2000", 11.5, 9.9, 4.2))
         seriesData.add(CustomDataEntry("2001", 13.5, 12.1, 1.2))
         seriesData.add(CustomDataEntry("2002", 14.8, 13.5, 5.4))
         seriesData.add(CustomDataEntry("2003", 16.6, 15.1, 6.3))
         seriesData.add(CustomDataEntry("2004", 18.1, 17.9, 8.9))
         seriesData.add(CustomDataEntry("2005", 17.0, 18.9, 10.1))
         seriesData.add(CustomDataEntry("2006", 16.6, 20.3, 11.5))
         seriesData.add(CustomDataEntry("2007", 14.1, 20.7, 12.2))
         seriesData.add(CustomDataEntry("2008", 15.7, 21.6, 10))
         seriesData.add(CustomDataEntry("2009", 12.0, 22.5, 8.9))
 */
        val set = Set.instantiate()
        set.data(seriesData)
        val seriesMapping = set.mapAs("{ x: 'x', value: 'value' }")
        val series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }")
        val series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }")

//        for (entry in data){
        val series = cartesian.line(seriesMapping)
        series.name("Temperature Level")
        series.hovered().markers().enabled(true)
        series.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4.0)
        series.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)
            .offsetX(5.0)
            .offsetY(5.0)

        val series2 = cartesian.line(series2Mapping)
        series2.name("Humidity Level")
        series2.hovered().markers().enabled(true)
        series2.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4.0)
        series2.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)
            .offsetX(5.0)
            .offsetY(5.0)

        val series3 = cartesian.line(series3Mapping)
        series3.name("Soil Moisture Level")
        series3.hovered().markers().enabled(true)
        series3.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4.0)
        series3.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)
            .offsetX(5.0)
            .offsetY(5.0)
//        }

        cartesian.legend().enabled(true)
        cartesian.legend().fontSize(13.0)
        cartesian.legend().padding(0.0, 0.0, 10.0, 0.0)

        chartView.setChart(cartesian)
    }

    private inner class CustomDataEntry(x: String, value: Number, value2: Number, value3: Number) :
        ValueDataEntry(x, value)
    {
        init
        {
            setValue("value2", value2)
            setValue("value3", value3)
        }
    }
}