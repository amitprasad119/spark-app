package com.example.batch

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, column, expr}

/**
  *- Load movies DF
  *- Select columns of your choice
  *- Gross profit
  *- select all average comedy movies ( rating above 6.5)
  */
object ColumnsExercise extends App {
  System.setProperty("hadoop.home.dir", "/")

  val spark = SparkSession
    .builder()
    .appName("Exercise of Movies DF")
    .config("spark.master", "local")
    .getOrCreate()

  val moviesDF = spark.read
    .format("json")
    .option("inferSchema", "true")
    .load("src/resources/data/movies.json")

  /**
    *  col("")
    *  columns("")
    *  $""
    *  expr("")
    *  DF.col("")
    */
  val titleCol = moviesDF.col("Title")
  import spark.implicits._

  val selectedColumnDF = moviesDF.select(
    titleCol,
    col("Release_Date"),
    'Worldwide_Gross,
    column("Production_Budget"),
    $"MPAA_Rating",
    expr("Source")
  )

  // selectedColumnDF.show()

  /**
    *  New column with sum of gross profit
    */
  val sumExpr = moviesDF.col("US_Gross") + moviesDF.col("Worldwide_Gross")

  val moviesDFWithGrossDF =
    moviesDF.select($"Title", col("US_Gross"), expr("Worldwide_Gross"), sumExpr.as("Total Sum"))
  // moviesDFWithGrossDF.show()

  /**
    *  Select comedy movies which are decent( 6.5 avg ratings)
   **/
  val totalRatingExpr = (moviesDF.col("Rotten_Tomatoes_Rating") / 10) + moviesDF.col("IMDB_Rating")
  val avgExpr = totalRatingExpr / 2
  val avgComedyMoviesDF = moviesDF.filter(col("Major_Genre") === "Comedy" and avgExpr >= 6.5)
  avgComedyMoviesDF.withColumn("Average Ratings", avgExpr).show()

}
