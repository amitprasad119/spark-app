package com.example.batch

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object AggregationExercise extends App {

  val spark = SparkSession
    .builder()
    .config("spark.master", "local")
    .appName("Aggregation exercise")
    .getOrCreate()

  val moviesDF = spark.read
    .format("json")
    .option("inferSchema", "true")
    .load("src/resources/data/movies.json")

  // moviesDF.show()
  /**
    *  All profits
    */

  val grossTotalDF = moviesDF
    .select((col("Worldwide_Gross") + col("US_Gross") + col("US_DVD_Sales")).as("Total_Gross"))
    .select(sum(col("Total_Gross")))

  grossTotalDF.show()

  /**
    * Count how many distinct directors
    *
    */
  val distinctDirectorsDF = moviesDF.select(countDistinct(col("Director")))
  distinctDirectorsDF.show()

  /**
    *   Create mean and Standard deviation for us_gross
    */

  val usGrossMeanAndSDDF = moviesDF.select(
    mean(col("US_Gross")).as("Mean(US_Gross)"),
    stddev(col("US_Gross")).as("SD(US_Gross)")
  )
  usGrossMeanAndSDDF.show()

  /**
    *  Compute average IMDB ratings and gross revenue per directors
    */

  val aggMoviesPerDirectorsDF = moviesDF
    .groupBy(col("Director"))
    .agg(
      avg(col("US_Gross")).as("Avg_US_GROSS"),
      avg(col("IMDB_Rating")).as("Avg_ratings")
    )
    .orderBy(col("Avg_ratings").desc_nulls_last)

  aggMoviesPerDirectorsDF.show()

  /**
     Create the average for Rotten_Tomatoes_ratings by genre
    */

  val rottenTomatoesAvgDF = moviesDF
    .groupBy("Major_Genre")
    .avg("Rotten_Tomatoes_Rating")

  rottenTomatoesAvgDF.show()
}
