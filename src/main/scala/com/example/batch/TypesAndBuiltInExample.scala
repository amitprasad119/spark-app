package com.example.batch

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, initcap, regexp_extract}

/**
  *  Filter the cars DF by list of cars obtained from getListOfCars
  */
object TypesAndBuiltInExample extends App {

  val spark = SparkSession
    .builder()
    .config("spark.master", "local")
    .appName("Spark Built in functions example")
    .getOrCreate()

  val carsDF = spark.read
    .json("src/resources/data/cars.json")
  val regexCarsName = getListOfCars.mkString("|")

  val filteredCarsDF = carsDF
    .select(
      col("Name"),
      regexp_extract(col("Name"), regexCarsName, 0).as("Passed_Cars")
    )
    .where(col("Passed_Cars") =!= "")
    .drop("Passed_Cars")
  println(regexCarsName)

  def getListOfCars: List[String] = List("ford pinto", "buick skylark")
  filteredCarsDF.show()

  /**
    *  Capitalize the first initial letter of the car name
    */
  filteredCarsDF.select(initcap(col("Name"))).show()

}
