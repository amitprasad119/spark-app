package com.example.batch

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col

object ManageNulls extends App {

  val spark = SparkSession
    .builder()
    .master("local[*]")
    .appName("Manage Nulls")
    .getOrCreate()

  val moviesDF = spark.read.json("src/resources/data/movies.json")

  //moviesDF.show()
  /**
    * Select null columns
    */
  moviesDF
    .select("*")
    .where(col("Release_Date").isNull)
  //  .show() // alternatively we can use isNotNull

  /**
    *  remove null across rows using na object
    *  and NA replaces only those value which are aligned with the typed passed in fill, like 0 would be applicable to Int and long columns
    *  "value" would be applicable to only string columns
    */
  moviesDF.printSchema()
  moviesDF.na.fill(0).show()

}
