package com.example.batch

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, to_date}
import org.apache.spark.sql.types.{DoubleType, StringType, StructField, StructType}

object StockDataDemo extends App {

  val spark = SparkSession
    .builder()
    .appName("Stock data")
    .master("local[*]")
    .getOrCreate()

  val stockSchema = StructType(
    Array(
      StructField("symbol", StringType),
      StructField("date", StringType),
      StructField("price", DoubleType)
    ))

  val stockDF = spark.read
    .format("csv")
    .schema(stockSchema)
    .option("header", "true")
    .option("dateFormat", "MMM d yyyy") // Option 1
    .load("src/resources/data/stocks.csv")
  stockDF.printSchema()

  /**
    *  Option2 to parse date
    */
  val stocksDF2 = stockDF.select("*").withColumn("actual_Date", to_date(col("date"), "MMM d yyyy"))
  stocksDF2.printSchema()
  stocksDF2.show()

}
