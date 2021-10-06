package com.example.batch

import org.apache.spark.sql.{SaveMode, SparkSession}

object DataSourcesExample extends App {
  System.setProperty("hadoop.home.dir", "/")

  val spark = SparkSession
    .builder()
    .appName("DifferentDataSources App")
    .config("spark.master", "local")
    .getOrCreate()

  val moviesDF = spark.read
    .format("json")
    .option("inferSchema", "true")
    .load("src/resources/data/movies.json")

  moviesDF.show()

  println("I am writing to csv file now")

  moviesDF.write
    .format("csv")
    .mode(SaveMode.Overwrite)
    .option("sep", " ")
    .save("src/resources/output/movies.csv")

  moviesDF.write
    .mode(SaveMode.Overwrite)
    .save("src/resources/output/movies.parquet")
}
