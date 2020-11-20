package framework

import kotlin.js.Date

class DateUtils {

    companion object {
        fun getNumberedMonth(month: String): String {
            val numberedMonth: String
            when (month) {
                "Jan" -> {
                    numberedMonth = "01"
                }
                "Feb" -> {
                    numberedMonth = "02"
                }
                "Mar" -> {
                    numberedMonth = "03"
                }
                "Apr" -> {
                    numberedMonth = "04"
                }
                "May" -> {
                    numberedMonth = "05"
                }
                "Jun" -> {
                    numberedMonth = "06"
                }
                "Jul" -> {
                    numberedMonth = "07"
                }
                "Aug" -> {
                    numberedMonth = "08"
                }
                "Sep" -> {
                    numberedMonth = "09"
                }
                "Oct" -> {
                    numberedMonth = "10"
                }
                "Nov" -> {
                    numberedMonth = "11"
                }
                "Dec" -> {
                    numberedMonth = "12"
                }
                else -> {
                    throw Exception("Unsupported month: $month")
                }
            }

            return numberedMonth
        }

        fun getDateTimeFromStandardFormat(date: Date): String {
            // Parse date from standard format, i.e. yyyy-MM-ddThh:mm
            val dateTimeArray = date.toString().split('T')
            val dateArray = dateTimeArray[0].split('-')
            val timeArray = dateTimeArray[1].split(':')
            val year = dateArray[0]
            val month = dateArray[1]
            val day = dateArray[2]
            val hour = timeArray[0]
            val minute = timeArray[1]
            return "$year-$month-${day} $hour:$minute"
        }

        fun getDateTimeFromLocalDateFormat(date: Date): String {
            val localDate = Date("$date")
            // Parse Local Date, i.e. Fri Oct 30 2020 07:02:03 GMT-0700 (Mountain Standard Time)
            val dateToArray = localDate.toString().split(' ')
            val month = DateUtils.getNumberedMonth(dateToArray[1])
            val day = dateToArray[2]
            val year = dateToArray[3]
            val timeToArray = dateToArray[4].split(':')
            val hour = timeToArray[0]
            val minute = timeToArray[1]
            //yyyy-MM-ddThh:mm
            return "$year-$month-${day}T$hour:$minute"
        }
    }
}