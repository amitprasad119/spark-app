package com.example.stream

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object StreamApp {

  val spark = SparkSession
    .builder()
    .appName("Spark stream using session")
    .master("local[*]")
    .getOrCreate()

  def readFromSockets() = {
    val df = spark.readStream
      .format("socket")
      .option("host", "localhost")
      .option("port", "12345")
      .load()
    val cap = df.select(upper(col("value")))

    cap.writeStream.format("console").outputMode("append").start().awaitTermination()
  }

  def main(args: Array[String]): Unit = {
    readFromSockets()
  }

}
