package com.example.batch

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col

object RDDExercise extends App {

  val spark = SparkSession
    .builder()
    .config("spark.master", "local")
    .appName("RDD Operations")
    .getOrCreate()

  val sc = spark.sparkContext
  case class Movies(title: String, genre: Option[String], rating: Option[Double])

  val moviesDF = spark.read.json("src/resources/data/movies.json")
  import spark.implicits._

  /**
    *  Read the movie json as RDD
    * show the distinct genre as an RDD
    * select all the movies in the drama genre with rating > 6
    * show the average rating of movies by genre
    */
  val moviesRDD =
    moviesDF
      .select(
        col("Title").as("title"),
        col("IMDB_Rating").as("rating"),
        col("Major_Genre").as("genre"))
      .as[Movies]
      .rdd

  val distinctGenre = moviesRDD.flatMap(_.genre).distinct()
  distinctGenre.foreach(println)

//  val dramaWithAvgRating = moviesRDD.filter(_.genre.contains("Drama")).filter(_.rating.getOrElse(0) > Some(6))
//  dramaWithAvgRating.foreach(println)

}
