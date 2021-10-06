package com.example.batch

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._

object FirstApp extends App {
  System.setProperty("hadoop.home.dir", "/")
  private[this] implicit val spark: SparkSession = SparkSession
    .builder()
    .config("spark.master", "local")
    .appName("SparkFirst Application")
    .getOrCreate()
  spark.sparkContext.setLogLevel("OFF")

  /*
   DF using inferSchema
   */
  val carDF = spark.read
    .format("json")
    .option("inferSchema", "true")
    .load("src/resources/data/cars.json")

  println("Print the result")
  // carDF.show(10)

  /**
    *  DF using Custom schema
    *
    *
    */
  val carSchema = StructType(
    Array(
      StructField("Cylinders", IntegerType),
      StructField("Displacement", IntegerType),
      StructField("Horsepower", IntegerType),
      StructField("Miles_per_Gallon", IntegerType),
      StructField("Acceleration", LongType),
      StructField("Name", StringType),
      StructField("Weight_in_lbs", DoubleType),
      StructField("Year", StringType),
      StructField("Origin", StringType)
    )
  )

  val cardDFWithSchema = spark.read
    .format("json")
    .schema(carSchema)
    .load("src/resources/data/cars.json")
  // cardDFWithSchema.where(col("Origin") =!= "USA").show()

  /**
    *  DF using Unstructured data
    *
    *
    */

  val personData = Seq(
    ("Amit", "Vapi", 110011),
    ("Sumit", "Vapi", 898711),
    ("Raj", "Mumbai", 50000)
  )

  val personDF = spark.createDataFrame(personData)
  //personDF.show()

  /**
    *  Create DF using header
    *
    *
    */
  val personDFWithHeader = personDF.toDF("Name", "City", "Zipcode")
  //personDFWithHeader.show()

  /**
      1. Create mobile manual DF describing smartphones
    */

  val moviesData = Seq(
    ("Samsung", "Galaxy", 6, 28),
    ("Nokia", "E11", 4, 20),
    ("Apple", "Iphone11", 11, 32),
    ("Oneplus", "3", 6, 32)
  )
  import spark.implicits._
  val moviesDF = moviesData.toDF("Make", "Model", "RAM(GB)", "Megapixel")
  // moviesDF.show()

  /**
    * Read movies.json
    * Print schema
    * Count total rows
    *
    */

  val moviesFromJSONDF = spark.read
    .format("json")
    .load("src/resources/data/movies.json")
  moviesFromJSONDF.printSchema()
  moviesFromJSONDF.show()
  println("Total records in movies DF: " + moviesFromJSONDF.count())

}
