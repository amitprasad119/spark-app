package com.example.batch

import org.apache.spark.sql.functions.{col, to_date}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession}

object DataFrameUsingSeqDemo extends App {

  val spark = SparkSession
    .builder()
    .appName("Seq Demo")
    .config("spark.master", "local")
    .getOrCreate()
  spark.sparkContext.setLogLevel("ERROR")
  case class Person(name: String, id: Long, date: String)

  val data = Seq(
    Row("Amit", 200, "2021-01-19"),
    Row("Sumit", 300, "2021-01-21"),
    Row("Raj", 500, "2020-11-18"),
    Row("Wilson", 600, "2019-01-23"),
    Row("Peter", 1000, "2018-07-24")
  )

  val schema = StructType(
    Array(
      StructField("name", StringType, true),
      StructField("id", IntegerType, true),
      StructField("date", StringType, true)
    )
  )

  val rdd = spark.sparkContext.makeRDD(data)
  val df = spark.createDataFrame(rdd, schema)
  df.select(to_date(col("date"), "yyyy-MM-dd").as("final_date")).printSchema()

}
